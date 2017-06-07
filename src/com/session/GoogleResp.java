package com.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.Entity;
import com.session.helper.Contact;
import com.session.helper.GoogleInfo;
import com.session.helper.GoogleService;

public class GoogleResp extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		try {
			String authcode = req.getParameter("code");
			String error = req.getParameter("error");
			GoogleInfo post = null;

			if ("access_denied".equals(error)) {
				resp.sendRedirect("login?access_denied");

			}

			else {

				String accessToken = GoogleService.getAccessToken(authcode);

				if (accessToken == null) {
					/// login?error=token_fetch_error
					resp.sendRedirect("login?error=token_fetch_error");

				}

				else {
					post = GoogleService.getUserInfo(accessToken);
				}

				if (post != null) {
					String email = post.getEmail();
					if (email != null) {
						boolean isPresent = SessionHelper.isPresent(email);

						if (isPresent) {
							HttpSession s1 = req.getSession();
							s1.setAttribute("email", email);
							resp.sendRedirect("dashboard");

						} else {
							String name = post.getGiven_name();
							String password = null;
							// System.out.println(name);
							SignUp.addinfo(email, name, password);
							HttpSession s1 = req.getSession(false);
							String session = SessionHelper.currentUser(req);
							if (session != null) {
								s1.invalidate();
							}
							s1 = req.getSession();
							s1.setAttribute("email", email);
							resp.sendRedirect("dashboard");
						}
					}
				} else {
					resp.sendRedirect("login?information_fetch_error");
				}
			}
		} catch (Exception e) {
			resp.sendRedirect("login?exception_occured");
		}
	}

}
