package com.session;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.*;

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
