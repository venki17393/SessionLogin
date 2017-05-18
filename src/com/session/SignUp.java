package com.session;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class SignUp extends HttpServlet {
	DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.getRequestDispatcher("Signup.html").include(req, resp);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String name = req.getParameter("name");
		String password = req.getParameter("password");
		String email = req.getParameter("email");

		// Query q1 = new Query("Contact");
		// PreparedQuery prepare = ds.prepare(q1);
		// boolean isPresent = false;
		// no entities with name "John"

		// for (Entity ent : prepare.asIterable()) {
		// String temp = (String) ent.getProperty("Email");
		// System.out.println(temp);
		// if (email.equals(temp)) {
		//
		// isPresent = true;
		// }
		// }

		// if (isPresent) {
		// }
		
		Query q = new Query("Contact").addFilter("Email", FilterOperator.EQUAL, email);
		PreparedQuery pd=ds.prepare(q);
		int entities = pd.countEntities();
		System.out.println(pd);
		System.out.println(entities);
		//boolean result = ds.
		if (entities != 0) {

			resp.sendRedirect("signup");
		} else {
			Entity contact = new Entity("Contact", email);
			contact.setProperty("Name", name);
			contact.setProperty("Password", password);
			contact.setProperty("Email", email);
			ds.put(contact);
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
}
