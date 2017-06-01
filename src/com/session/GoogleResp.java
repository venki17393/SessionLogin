package com.session;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import PojoClasses.GoogleService;

public class GoogleResp extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		String accessToken=null;
		String authcode = req.getParameter("code");
		String email=null;
		boolean isPresent=false;
		System.out.println(authcode);
		if (authcode != null) {

			if (authcode.equals("access_denied")) {
				resp.sendRedirect("login");

			} else {
				 accessToken= GoogleService.getAccessToken(authcode);
			}

		}
		
		if (accessToken!= null) {

			 email = GoogleService.getUserInfo(accessToken);
			}
		
		if(email!=null){
		isPresent=SessionHelper.isPresent(email);
		}
		if(isPresent){
			// s1 = req.getSession();
			//String session = SessionHelper.currentUser(req);
			//System.out.println(session);
			
				HttpSession s1 = req.getSession();
				s1.setAttribute("email",email);
				resp.sendRedirect("dashboard");	
			
			
		}
		else {
			resp.sendRedirect("signup");
		}

	}
}
