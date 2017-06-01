package com.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class GoogleRespNext extends HttpServlet {

public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	System.out.println("next page");

	String authcode = req.getParameter("access_token");
	System.out.println(req.getParameter("refresh_token"));
}	

}
