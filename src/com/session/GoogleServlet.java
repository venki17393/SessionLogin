package com.session;

import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GoogleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String CLIENT_ID = "905323826918-g2vne7tcluchca01k6giosj88hhbc3el.apps.googleusercontent.com";
	// private static final String CLIENT_SECRET = "vEzUVdnDTVHBV3F8yy0P0pHN";

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		System.out.println("google page");

		String url = req.getRequestURL() + "resp";
		String oauth = "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&access_type=offline&include_granted_scopes=true&state=/googleresp&redirect_uri="
				+ url
				+ "&response_type=code&client_id=905323826918-g2vne7tcluchca01k6giosj88hhbc3el.apps.googleusercontent.com&prompt=consent";
		resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); 
		resp.setDateHeader("Expires", 0);
		
		resp.sendRedirect(oauth);
	}

}

// https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&access_type=offline&include_granted_scopes=true&state=/googleresp&redirect_uri=http://localhost:8888/googleresp&response_type=code&client_id=905323826918-g2vne7tcluchca01k6giosj88hhbc3el.apps.googleusercontent.com&prompt=consent
