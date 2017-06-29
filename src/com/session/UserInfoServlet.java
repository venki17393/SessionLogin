package com.session;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.session.helper.GoogleInfo;
import com.session.helper.UserInfo;

public class UserInfoServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		resp.setContentType("application/json");
		HttpSession session = req.getSession(false);
		String value = (String) session.getAttribute("email");
		try {

			JSONObject email = SessionHelper.getUserInfo(value, resp);
			
			  String info = email.toString(); 
			  ObjectMapper mapper = new ObjectMapper(); 
			 /* UserInfo user = mapper.readValue(info,UserInfo.class); 
			 
			  PrintWriter out = resp.getWriter(); 
			  System.out.println(user.getEmail());
			  out.println("hello," + user.getEmail());*/
			 
			  

			PrintWriter out = resp.getWriter();
			out.println(email);
		} catch (EntityNotFoundException | JSONException e) {
			// resp.sendRedirect("dashboard");
		}

	}
}
