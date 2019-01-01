package twitter.storm.tools;

import org.ahocorasick.trie.Trie;

/**
 * Class that initializes the Trie library 
 * to track the flu words in tweets 
 */

public class TrackKeywords {
	
	Trie searchTrie;
	
	public Trie getSearchTrie() {
		return searchTrie;
	}
	
	public TrackKeywords() {
	
		searchTrie = new Trie().onlyWholeWords().caseInsensitive();
		
	    searchTrie.addKeyword("vomit");
	    searchTrie.addKeyword("sorethroat");
	    searchTrie.addKeyword("headache");
	    searchTrie.addKeyword("aches");
	    searchTrie.addKeyword("feel sick");
	    searchTrie.addKeyword("feeling sick");
	    searchTrie.addKeyword("runny nose");
	    searchTrie.addKeyword("catarrh");
	    searchTrie.addKeyword("feeling unwell");
	    searchTrie.addKeyword("shivers");
	    searchTrie.addKeyword("fever");
	    searchTrie.addKeyword("sick");
	    searchTrie.addKeyword("#flu");
	    searchTrie.addKeyword("#fluseason");
	    searchTrie.addKeyword("flu");
	    searchTrie.addKeyword("sneezes");
	    searchTrie.addKeyword("gastroenteritis");
	    searchTrie.addKeyword("sore throat");
	    searchTrie.addKeyword("cough");
	    searchTrie.addKeyword("spasm");
	    searchTrie.addKeyword("stomach ache");
	    searchTrie.addKeyword("inflammation");
	    searchTrie.addKeyword("diarrhea");
	    searchTrie.addKeyword("muscles ache");
	    searchTrie.addKeyword("muscles pain");
	    searchTrie.addKeyword("nausea");
        searchTrie.addKeyword("exhausted");
        searchTrie.addKeyword("exhaustion");
		
	}

}
