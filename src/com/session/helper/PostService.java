package com.session.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.session.PostPojo;

public class PostService {
	MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
	Expiration expiration = Expiration.byDeltaSeconds(20);
	JSONObject json = new JSONObject();
	ObjectMapper mapper = new ObjectMapper();

	public PostPojo getPostFromServer(int requestNumber) throws IOException, JSONException {
		
		
		
		String actualUrl = "http://jsonplaceholder.typicode.com/posts/" + requestNumber;

		URL url = new URL(actualUrl);

		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.connect();

		StringBuilder sb = new StringBuilder();

		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line + "\n");
		}
		br.close();
		String string = sb.toString();

		PostPojo post = mapper.readValue(string, PostPojo.class);
		return post;

	}

	public PostPojo getPostFromCache(int requestNumber) {
		
		PostPojo value = (PostPojo) cache.get(requestNumber);
		//System.out.println();
		return value;
	}

	public PostPojo getPost(int requestNumber) throws IOException, JSONException {
	
		PostPojo post = getPostFromCache(requestNumber);
		if(post!= null){
			return post;
		}
		else {
			System.out.println("from server");
			post = getPostFromServer(requestNumber);
			cache.put(requestNumber, post, expiration);
			return post;
		}
	}
}
