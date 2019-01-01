package twitter.storm.bolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import java.util.Map;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;

/**
 * A bolt that prints messages to redis
 */
public class ReportBolt extends BaseRichBolt {

  private static final long serialVersionUID = 532484535287440101L;

  // place holder to keep the connection to redis
  transient RedisConnection<String,String> redis;
  
  @Override
  public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
    
	// instantiate a redis connection
    RedisClient client = new RedisClient("localhost", 6379); 
    
    // initiate the actual connection
    redis = client.connect(); 
  }
   
  @Override
  public void execute(Tuple tuple) {

	// get fields from the incoming tuple
	String tweet = tuple.getStringByField("tweet-text");
    String unit_authority_id = tuple.getStringByField("unit_authority_id");
    int fluOccurences = tuple.getIntegerByField("flu_occ");
    int allOccurences = tuple.getIntegerByField("all_occ");
    
    // publish a message to redis using the channel FluDetectorTopology 
    redis.publish("FluDetectorTopology", unit_authority_id + "DELIMITER" +
    									 fluOccurences + "DELIMITER" + 
    									 allOccurences + "DELIMITER" +
    									 tweet 
    			 );
  }

  public void declareOutputFields(OutputFieldsDeclarer declarer)  {
	  
    // nothing to add - since it is the final bolt
	  
  }
}