package com.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionHelper {
	public static String currentUser(HttpServletRequest req){
		HttpSession session =req.getSession();
		String s1 = (String) session.getAttribute("email");
		return s1;
		
	}

}
