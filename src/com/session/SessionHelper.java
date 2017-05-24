package com.session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
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

	public static PostPojo getPostContent() throws IOException {

		org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
		URL url = new URL("http://jsonplaceholder.typicode.com/posts/1");

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
		// System.out.println(jsonObj);

		// JSON from file to Object
		PostPojo post = mapper.readValue(string,PostPojo.class );
		/*String str =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
		System.out.println(user.getTitle());
		System.out.println(user.getBody());
		System.out.println(user.getId());*/
		
		
		return post;
	}

}
