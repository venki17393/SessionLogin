package com.session.helper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Mapify;
import com.googlecode.objectify.mapper.Mapper;

//class TrendsMapper implements Mapper<String, Integer> {
//
//	@Override
//	public String getKey(Integer value) {
//		System.out.println("At the get Key");
//		TrendPojo pojo = new TrendPojo();
//		return pojo.getKey(value);
//	}
//
//}


@Entity
public class TrendsWithDate {
	@Id private long ID;
	//@Mapify(TrendsMapper.class)
	HashMap<String, Integer> trends = new LinkedHashMap<String, Integer>();
	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public HashMap<String, Integer> getTrends() {
		return trends;
	}

	public void setTrends(LinkedHashMap<String, Integer> trends) {
		this.trends = trends;
	}

	//private long date;
	
	
	
	public TrendsWithDate(){};

	public TrendsWithDate(long date, HashMap<String, Integer> trends) {
		this.ID = date;
		//this.date = date;
		this.trends = trends;
	}
	
	

}
