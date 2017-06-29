package com.session.helper;

import java.util.TreeMap;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class TrendsWithDate {
	@Id private String date;
	private TreeMap<String,String> trends;
	
	public TrendsWithDate(String date, TreeMap<String, String> trends) {
		super();
		this.date = date;
		this.trends = trends;
	}
	
	
	

}
