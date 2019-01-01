package twitter.storm.bolt;

import backtype.storm.topology.OutputFieldsDeclarer;

import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import backtype.storm.task.TopologyContext;
import backtype.storm.task.OutputCollector;

import java.util.HashMap;
import java.util.Map;

/**
 * A bolt that counts the flu and total occurences 
 * for all the UK unitary authorities
 */
public class TopUnitAuthoritiesBolt extends BaseRichBolt {

	private static final long serialVersionUID = 5905712870882061066L;
	
	// To output tuples from this bolt to the Report bolt
	private OutputCollector collector;
	
	// HashMap that counts for every unitary authority the flu occurences
	private Map<String, Integer> fluOccurences;
	
	// HashMap that counts for every unitary authority the total occurences
	private Map<String, Integer> totalOccurences;
	
	@Override 
	public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
		
		// save the output collector for emitting tuples
		collector = outputCollector;

		// initialize the hashmaps
		fluOccurences = new HashMap<String, Integer>();
		totalOccurences = new HashMap<String, Integer>();
	}
	
	public void execute(Tuple tuple) {
		
		// get fields from the incoming tuple
		String tweet = tuple.getStringByField("tweet-text");
		String unit_authority_id = tuple.getStringByField("unit_authority_id");		
		int tweetState = tuple.getIntegerByField("tweet-state");
		
		// if the tweet state is 1 (the tweet contains flu words)
		if (tweetState == 1) {

			// increment the fluOccurences value for this unitary authority
			if (fluOccurences.get(unit_authority_id) == null) {  fluOccurences.put(unit_authority_id,1); }
			else { fluOccurences.put(unit_authority_id, fluOccurences.get(unit_authority_id) + 1); }

		}
		// if the tweet state is 0 (the tweet does NOT contain flu words)
		else if (tweetState == 0) {
			
			// initialize the fluOccurences value for this unitary authority
			if (fluOccurences.get(unit_authority_id) == null) { fluOccurences.put(unit_authority_id,0); }
					
		}
		
		// increment the totalOccurences value for this unitary authority
		if (totalOccurences.get(unit_authority_id) == null) { totalOccurences.put(unit_authority_id,1); }
		else { totalOccurences.put(unit_authority_id, totalOccurences.get(unit_authority_id) + 1); }

		// emit the output tuple for this bolt
		collector.emit(new Values(tweet,unit_authority_id,fluOccurences.get(unit_authority_id),totalOccurences.get(unit_authority_id)));
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		
		// tell storm the schema of the output tuple for this bolt
		outputFieldsDeclarer.declare(new Fields("tweet-text","unit_authority_id","flu_occ","all_occ"));
	}
	
}