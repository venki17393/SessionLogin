package com.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnyWhereWorksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CLIENT_ID = " 2a9ac-2baf139b82055cc1e9d6974edf536f2c";

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		System.out.println("AnyWhere");

		String url = req.getRequestURL() + "resp";
		System.out.println(url);

		String oauth = "https://staging-fullcreative-dot-full-auth.appspot.com/o/oauth2/auth?response_type=code&client_id=2a9ac-2baf139b82055cc1e9d6974edf536f2c&scope= awapis.identity &redirect_uri="+url+"&approval_prompt=force&state=state-token-79sdfs9d7fg907fsud987f";
		resp.sendRedirect(oauth);
	}

}

// "https://access.anywhereworks.com/o/oauth2/auth?response_type=code
// &scope=awapis.users.read
// awapis.feeds.write&access_type=offline&include_granted_scopes=true&state=/anywhereresp&redirect_uri="
//+url+"&client_id= 2a9ac-2baf139b82055cc1e9d6974edf536f2c&approval_prompt=force";