package com.session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class SessionHelper {
	public static String currentUser(HttpServletRequest req) {
		HttpSession session = req.getSession();
		if (session != null) {
			String s1 = (String) session.getAttribute("email");
			return s1;
		} else {
			return null;
		}
	}

	public static boolean isPresent(String email) {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Key emaill = KeyFactory.createKey("Contact", email);
		try {
			Entity temp = ds.get(emaill);
			System.out.println(temp);
			return true;

		} catch (EntityNotFoundException e1) {
			// TODO Auto-generated catch block
			return false;

			// e1.printStackTrace();
		}

	}

	public static PostPojo getPostContent(String requestNumber) throws IOException, JSONException {

		ObjectMapper mapper = new ObjectMapper();
		PostPojo post = null;
		JSONObject json = new JSONObject();
		try {
			String temp = requestNumber.substring(1);
			int lastChar = Integer.parseInt(temp);
			System.out.println(lastChar);
			if (lastChar > 0 && lastChar < 11) {
				String actualUrl = "http://jsonplaceholder.typicode.com/posts" + requestNumber;
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

				json.put("ok", true);
				json.put("message", "found");
				String string = sb.toString();
				String jsonString = json.toString();
				string=string.concat(jsonString);
				post = mapper.readValue(string, PostPojo.class);
				System.out.println(string);
				//post = mapper.readValue(jsonString, PostPojo.class);

				return post;
			} else {

				json.put("ok", false);
				json.put("message", "invalid post id, please pass between 1 and 10");
				String string = json.toString();
				post = mapper.readValue(string, PostPojo.class);

				return post;
			}
		} catch (NumberFormatException e) {
			// TODO: handle exception

			

			json.put("ok", false);
			json.put("message", "invalid post id, please pass between 1 and 10");
			String string = json.toString();
			post = mapper.readValue(string, PostPojo.class);

			return post;
		}

	}

}
