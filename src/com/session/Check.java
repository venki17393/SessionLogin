package com.session;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Check extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		resp.setContentType("text/html");

		String email = SessionHelper.currentUser(req);
		if (email != null) {

			req.getRequestDispatcher("a.html").include(req, resp);
		} else {
			req.getRequestDispatcher("index.html").include(req, resp);
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		resp.setContentType("text/html");
		PrintWriter o = resp.getWriter();
		String email = req.getParameter("email");
		String password = req.getParameter("password");

		if ((email.equals("admin@testing.com")) && (password.equals("123"))){
				HttpSession s1 = req.getSession();
				s1.setAttribute("email", email);
				req.getRequestDispatcher("a.html").include(req, resp);
			}
		 else {
			resp.sendRedirect("login");
		}
	}

}