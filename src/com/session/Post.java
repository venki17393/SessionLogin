package com.session;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONArray;

public class Post {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			URL url = new URL("https://jsonplaceholder.typicode.com/posts/1");
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();
			for (int i = 1; i <= 8; i++) {
				System.out.println(huc.getHeaderFieldKey(i) + " = " + huc.getHeaderField(i));
			}
			huc.disconnect();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
