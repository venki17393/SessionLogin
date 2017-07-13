package com.session.helper;

import java.util.Map;
import java.util.TreeMap;

public class TrendPojo {
	String hashtag;
	Integer occurence;
	TreeMap<String, Integer> map = new TreeMap<String, Integer>();

	
	public TrendPojo(String hashtag, Integer occurence) {
		super();
		this.hashtag = hashtag;
		this.occurence = occurence;
		map.put(hashtag, occurence);
	}

	public  String getKey(Integer occurence){
		for (Map.Entry<String,Integer> e : map.entrySet()) {
		    String key = e.getKey();
		    Integer value = e.getValue();
		    if(value == occurence){
		    	System.out.println(key);
		    	return key;
		    } 
		}
		 return null;
	}

	public TrendPojo() {
		super();
	}

}
