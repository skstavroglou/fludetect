package twitter.storm.spout;

import backtype.storm.Config;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import twitter.storm.tools.CitiesLookup;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.StallWarning;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A spout that uses Twitter streaming API 
 * for continuously getting tweets
 */
public class TweetSpout extends BaseRichSpout {

  private static final long serialVersionUID = -3480833811106377846L;
	
  // Twitter API authentication credentials
  String custkey, custsecret;
  String accesstoken, accesssecret;  

  //To output tuples from spout to the next stage bolt
  SpoutOutputCollector collector;
  
  //Twitter4j - twitter stream to get tweets
  TwitterStream twitterStream; 
  
  //Shared queue for getting buffering tweets received
  LinkedBlockingQueue<String> queue = null; 
  
  // Holds a hash map of major uk cities
  CitiesLookup citieslookup;
  
  // Class for listening on the tweet stream - for twitter4j
  private class TweetListener implements StatusListener {

    // Implement the callback function when a tweet arrives
    @Override
    public void onStatus(Status status) {
    	
    	String geoInfo = "n/a";
    	
    	// Common delimiters
    	String delims = "[\\s;.,:'!?()-]";
    	
    	// The location inside the user's profile 
    	String userLocation = status.getUser().getLocation();

    	// if the tweet has geo metadata
    	if(status.getGeoLocation() != null) {
    		    		
    		// get latitude and longitude
    		geoInfo = String.valueOf(status.getGeoLocation().getLatitude()) +
    			"," + String.valueOf(status.getGeoLocation().getLongitude());

    	   // add the tweet to the queue
       	   queue.offer(status.getText() + "DELIMITER" + geoInfo);
    	}
    	// if the tweet does not have geo metadata 
    	// but the user filled the location field in his profile
    	else if (userLocation != null) {
    		
    		// tokenize the location inside the user's profile 
    	    String[] tokens = userLocation.split(delims);

    	    // for all tokens
    	    for (int i = 0; i < tokens.length; i++) {
    	    	
    	    	// check if this token it matches with the cities hashmap
				if (citieslookup.getCities().containsKey(tokens[i].trim().toUpperCase())) {
					
					// get the coordinates for this matched city
		    		geoInfo = citieslookup.getCities().get(tokens[i].trim().toUpperCase());
		    		
		    		// add the tweet to the queue
		    		queue.offer(status.getText() + "DELIMITER" + geoInfo);
		    		break;
				}
			}
    	}
    	else {
    		
    		// tokenize the tweet text  
    	    String[] tokens = status.getText().split(delims);
   
    	    // for all tokens
    	    for (int i = 0; i < tokens.length; i++) {
    	    	
    	    	// check if this token it matches with the cities hashmap
				if (tokens[i].length() > 1 && citieslookup.getCities().containsKey(tokens[i].trim().toUpperCase())) {
					
					// get the coordinates for this matched city
		    		geoInfo = citieslookup.getCities().get(tokens[i].trim().toUpperCase());	
		    		
		    		// add the tweet to the queue
		    		queue.offer(status.getText() + "DELIMITER" + geoInfo);
		    		break;
				}
			}    	
    	}
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice sdn) {
    
    }

    @Override
    public void onTrackLimitationNotice(int i) {
    
    }

    @Override
    public void onScrubGeo(long l, long l1) {
    
    }

    @Override
    public void onStallWarning(StallWarning warning) {
    
    }

    @Override
    public void onException(Exception e) {
      e.printStackTrace();
    }
  };

  /**
   * Constructor for tweet spout that accepts the twitter credentials
   */
  public TweetSpout(String key, String secret, String token, String tokensecret) {
	  
	  custkey = key;
	  custsecret = secret;
	  accesstoken = token;
	  accesssecret = tokensecret;
  }

  @Override
  public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
    
	// create the buffer to block tweets
    queue = new LinkedBlockingQueue<String>(1000);
    
    // initialize the hashmap of major uk cities
    citieslookup = new CitiesLookup();
        
    // save the output collector for emitting tuples
    collector = spoutOutputCollector;

    // build the config with credentials for twitter 4j
    ConfigurationBuilder config = new ConfigurationBuilder()
						              .setOAuthConsumerKey(custkey)
						              .setOAuthConsumerSecret(custsecret)
						              .setOAuthAccessToken(accesstoken)
						              .setOAuthAccessTokenSecret(accesssecret);

    // create the twitter stream factory with the configuration
    TwitterStreamFactory fact = new TwitterStreamFactory(config.build());

    // get an instance of twitter stream
    twitterStream = fact.getInstance();   
    
    // get an instance of FilterQuery
    FilterQuery filterQuery = new FilterQuery(); 

    // UK Bounding Box filter
    filterQuery.locations(new double[][] { 
					      	new double[]{-14.0155172348,49.6739997864}, 
					      	new double[]{2.0919117928,61.061000824}
    					 }); 
    
    // Language filter
    String[] lang = {"en"};
    filterQuery.language(lang);
    
    twitterStream.addListener(new TweetListener());
    twitterStream.firehose(0);
    
    // add the filter to the stream
    twitterStream.filter(filterQuery);
     
  }

  @Override
  public void nextTuple()  {
	  
    // try to pick a tweet from the buffer
    String tweet = queue.poll();
    
    String geoInfo;
    String tweetText;
    
    // if no tweet is available, wait for 50 ms and return
    if (tweet == null) {
      Utils.sleep(50);
      return;
    }
    else {
    	// get the tweet text
        tweetText = tweet.split("DELIMITER")[0];
        // get the tweet geospatial info (lat/long)
        geoInfo = tweet.split("DELIMITER")[1];
    }
    
    if (geoInfo != null && !geoInfo.equals("n/a")) {
    	
    	// emit the tweet to the next bolt
        collector.emit(new Values(tweet));   
    }
    
  }

  @Override
  public void close() {
	 
	// shutdown the stream - when we are going to exit  
    twitterStream.shutdown();  
  }

  /**
   * Component specific configuration
   */
  @Override
  public Map<String, Object> getComponentConfiguration() {

    Config conf = new Config();  // create the component config
    conf.setMaxTaskParallelism(1);  // set the parallelism for this spout to be 1
    return conf;
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
	  
    // tell storm the schema of the output tuple for this spout
    // tuple consists of a single column called 'tweet'
    outputFieldsDeclarer.declare(new Fields("tweet"));
  }
}