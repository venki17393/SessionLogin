package com.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class SessionLoginServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
	
		
		resp.setContentType("text/html");
		String mail = SessionHelper.currentUser(req);
		if (mail != null) {
		HttpSession s1 = req.getSession(false);
			resp.sendRedirect("dashboard");
		} else {
			req.getRequestDispatcher("index.html").include(req, resp);
		}
	}
}
