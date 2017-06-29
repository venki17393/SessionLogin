package com.session;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Logout extends HttpServlet {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
HttpSession s1 = req.getSession(false);
if(s1 != null) {
	s1.invalidate();	
	resp.sendRedirect("login");
	resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); 
	resp.setHeader("Pragma", "no-cache"); 
	resp.setDateHeader("Expires", 0);
	
}
else {
	resp.sendRedirect("login");
}

}
}