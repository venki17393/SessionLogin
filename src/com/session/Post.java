package com.session;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.appengine.labs.repackaged.org.json.JSONException;

//import org.mortbay.util.ajax.JSON;

public class Post extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		String  requestNumber = req.getPathInfo();
		

		PostPojo post;
		try {
			post = SessionHelper.getPostContent(requestNumber);
			ObjectMapper mapper = new ObjectMapper();
			out.println(mapper.writeValueAsString(post));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}
