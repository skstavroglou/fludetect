package twitter.storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;
import twitter.storm.bolt.ParseTweetBolt;
import twitter.storm.bolt.ReportBolt;
import twitter.storm.bolt.TopUnitAuthoritiesBolt;
import twitter.storm.spout.TweetSpout;

class FluDetectorTopology {
	
  public static void main(String[] args) throws Exception {
	  
    // create the topology
    TopologyBuilder builder = new TopologyBuilder();
      
    // create the tweet spout with the credentials  
    TweetSpout tweetSpout = new TweetSpout(
						"[Your customer key]",
						"[Your secret key]",
						"[Your access token]",
						"[Your access secret]"
					  );

    // attach the tweet spout to the topology - parallelism of 1
    builder.setSpout("tweet-spout", tweetSpout, 1);

    // attach the parse tweet bolt using shuffle grouping
    builder.setBolt("parse-tweet-bolt", new ParseTweetBolt(), 10).shuffleGrouping("tweet-spout");
    
    // attach the top unitary authorities bolt using fields grouping
    builder.setBolt("top-ua-bolt", 	new TopUnitAuthoritiesBolt(),10).fieldsGrouping("parse-tweet-bolt", new Fields("unit_authority_id"));
    
    // attach the report bolt using global grouping
    builder.setBolt("report-bolt", 		new ReportBolt(), 	   1).globalGrouping("top-ua-bolt");
    
    // create the default configuration object
    Config conf = new Config(); 
    
    // set the configuration in debugging mode
    conf.setDebug(true);  

    // run it in a live cluster
    if (args != null && args.length > 0) {
    
      // set the number of workers for running all spout and bolt tasks
      conf.setNumWorkers(3);    
      
      // create the topology and submit with config
      StormSubmitter.submitTopology(args[0], conf, builder.createTopology());  
    }
    // run it in a simulated local cluster
    else {
      
      // set the number of threads to run - similar to setting number of workers in live cluster
      conf.setMaxTaskParallelism(4); 
      
      // create the local cluster instance
      LocalCluster cluster = new LocalCluster(); 
      
      // submit the topology to the local cluster
      cluster.submitTopology("tweet-flu-count", conf, builder.createTopology()); 
      
      // let the topology run for 300 seconds. note topologies never terminate!
      Utils.sleep(300000000); 
      
      // now kill the topology
      cluster.killTopology("tweet-flu-count");  
      
      // we are done, shutdown the local cluster
      cluster.shutdown();  
    }
  }
}
