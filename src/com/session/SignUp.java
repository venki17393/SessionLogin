package com.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class SignUp extends HttpServlet {
	static DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.getRequestDispatcher("Signup.html").include(req, resp);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException  {
		String name = req.getParameter("name");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		
		
		// to check if email is present using iteration
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
		
		
		// To check if the email is present using Query
		/*Query q = new Query("Contact").addFilter("Email", FilterOperator.EQUAL, email);
		PreparedQuery pd=ds.prepare(q);
		int entities = pd.countEntities();*/
		/*Key emaill = KeyFactory.createKey(email, "Conatct");
		try {
			Entity temp = ds.get(emaill);
		} catch (EntityNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		boolean isPresent = SessionHelper.isPresent(email);
		if(isPresent){
			resp.sendRedirect("signup");
		}
		
		else {
			Entity info = SignUp.addinfo(email, name, password);
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
	public static Entity  addinfo (String email, String name, String password){
		Entity contact = new Entity("Contact", email);
		contact.setProperty("Name", name);
		contact.setProperty("Email", email);
		if(password!=null){
			contact.setProperty("Password", password);
		}
		ds.put(contact);
		return contact;
		
	}
		
		

}