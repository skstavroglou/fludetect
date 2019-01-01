package twitter.storm.bolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import twitter.storm.tools.TrackKeywords;
import twitter.storm.tools.UnitaryAuthoritiesLookup;

import java.util.Map;
import org.ahocorasick.trie.Token;
import java.util.Collection;

/**
 * A bolt that parses the incoming tweet
 */
public class ParseTweetBolt extends BaseRichBolt {
	
  private static final long serialVersionUID = -676893541771976399L;
  
  // To output tuples from this bolt to the TopUnitAuthorities bolt
  OutputCollector collector;
  
  // holds the hashmap of all uk unitary authorities
  UnitaryAuthoritiesLookup ualookup;
  
  // class to track words 
  TrackKeywords trackWords;
  
  @Override
  public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
	  
    // save the output collector for emitting tuples
    collector = outputCollector;
    
    // initialize the hashmap of all uk unitary authorities
    ualookup = new UnitaryAuthoritiesLookup();
    
    // create an instance to track words
    trackWords = new TrackKeywords();

  }
  
  // Given in iput a tweet check if it matches with any defined track words 
  public boolean doesTweetMatch(String tweet) {
      
	  // tokenize the tweet text
      Collection<Token> tokens = trackWords.getSearchTrie().tokenize(tweet);
      
      // for all tokens
      for (Token token : tokens) {
    	  // if the tweet matches return true
          if (token.isMatch()) {
        	  return true;
          }
      }
      
      return false;
  }

  @Override
  public void execute(Tuple tuple) {
	
	// get fields from the incoming tuple  
    String tweet = tuple.getStringByField("tweet").split("DELIMITER")[0];
    double latitude = Double.parseDouble(tuple.getStringByField("tweet").split("DELIMITER")[1].split(",")[0]);
    double longitude = Double.parseDouble(tuple.getStringByField("tweet").split("DELIMITER")[1].split(",")[1]);
    
    // assign a unitary authority id for these coordinates (latitude,longitude)
    String unit_authority_id = ualookup.getUACodeByGeo(latitude, longitude);   
        	
    // if the tweet contains at least one defined keyword emit it with the state 1
    if (doesTweetMatch(tweet)) {
    	collector.emit(new Values(tweet,unit_authority_id,1));
    }
    // if the tweet does NOT contain any defined keyword emit the output tuple with the state 0
    else {
    	collector.emit(new Values(tweet,unit_authority_id,0));
    }
	    
  }
  
  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
	  
    // tell storm the schema of the output tuple for this bolt
    declarer.declare(new Fields("tweet-text","unit_authority_id","tweet-state"));
    
  }

}