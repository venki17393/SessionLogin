package com.session;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

//import org.mortbay.util.ajax.JSON;

public class Post extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();

		PostPojo post = SessionHelper.getPostContent();
		ObjectMapper mapper = new ObjectMapper();
		out.println(mapper.writeValueAsString(post));

	}
}
