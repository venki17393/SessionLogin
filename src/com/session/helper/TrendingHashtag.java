package com.session.helper;

import java.util.TreeMap;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class TrendingHashtag {
	private String feedAdded;
	private String occureence;
//	private TreeMap <String, String> feeds;
	@Id
	private String hashtag;
	
	public TrendingHashtag(String feedAdded, String occureence, String hashtag) {
		this.feedAdded = feedAdded;
		this.occureence = occureence;
		this.hashtag = hashtag;
	}
	
	public TrendingHashtag(String occureence, String hashtag) {
		this.occureence = occureence;
		this.hashtag = hashtag;
	}
	public TrendingHashtag() {
		
	}
	
	

	

}
