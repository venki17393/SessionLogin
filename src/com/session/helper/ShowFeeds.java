package com.session.helper;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;

import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.googlecode.objectify.LoadResult;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.SimpleQuery;

public class ShowFeeds {
	ArrayList<String> feed = new ArrayList<String>();
	ArrayList<String> trends = new ArrayList<String>();

	public void addStatus(String value) {
		// ArrayList<String> feed = new ArrayList<String>();
		feed.add(value);
	}

	public void printFeeds() {
		// System.out.println(feed);
	}

	public void makeTrends() {
		// Iterator<String> iterator = feed.iterator();
		// while (iterator.hasNext()) {
		// String temp = iterator;
		// System.out.println(temp);
		// iterator.next();
		// //System.out.println(iterator.next());
		// }
		for (String temp : feed) {
			Pattern MY_PATTERN = Pattern.compile("#(\\w+)");
			Matcher mat = MY_PATTERN.matcher(temp);
			while (mat.find()) {
				// System.out.println(mat.group(1));
				trends.add(mat.group(1));
			}
		}
	}

	public void setTrends(String date) {
		TreeMap<String, String> hashTag = new TreeMap<>();
		TrendingHashtag hashtag = null;
		TrendsWithDate trendsWithDate;
		for (String trend : trends) {
			Integer occurenceInt = Collections.frequency(trends, trend);
			
			String occurence = occurenceInt.toString();
			hashTag.put(trend, occurence);

			// hashtag = new TrendingHashtag(date,occurence, trend);

		}
		System.out.println("hashtag is " + hashTag);
		trendsWithDate = new TrendsWithDate(date, hashTag);
		ObjectifyService.ofy().save().entity(trendsWithDate).now();
		// System.out.println(hashTag);

	}

	public  void showTrends() {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q1 = new Query("TrendsWithDate");
		List<TrendsWithDate> users = null;
		// q1.addSort("occureence",SortDirection.DESCENDING);
		PreparedQuery prepd = ds.prepare(q1);
		// System.out.println(q1 + " " + prepd);
		for (Entity ent : prepd.asIterable()) {
			// System.out.println(ent);
			
			Object occureence =  ent.getProperty("trends");
		
			System.out.println( occureence);
			users = ofy().load().type(TrendsWithDate.class).filter("occureence >", occureence).list();
			
		}
			System.out.println("List is" + users);
	}
}
