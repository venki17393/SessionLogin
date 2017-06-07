package com.session;

import static com.googlecode.objectify.ObjectifyService.ofy;

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
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;
import com.session.helper.Contact;

public class SignUp extends HttpServlet {
	static DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	static UserService userService = UserServiceFactory.getUserService();
	static User user = userService.getCurrentUser(); 
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.getRequestDispatcher("Signup.html").include(req, resp);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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
		/*
		 * Query q = new Query("Contact").addFilter("Email",
		 * FilterOperator.EQUAL, email); PreparedQuery pd=ds.prepare(q); int
		 * entities = pd.countEntities();
		 */
		/*
		 * Key emaill = KeyFactory.createKey(email, "Conatct"); try { Entity
		 * temp = ds.get(emaill); } catch (EntityNotFoundException e1) { // TODO
		 * Auto-generated catch block e1.printStackTrace(); }
		 */

		boolean isPresent = SessionHelper.isPresent(email);
		
		if (isPresent) {
			//System.out.println("if");
			resp.sendRedirect("signup");
			
		}

		else {
			
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

	public static void addinfo(String email, String name, String password) {

		// This type is used to check using Low Level DS
		/*
		 * Entity contact = new Entity("Contact", email);
		 * contact.setProperty("Name", name); contact.setProperty("Email",
		 * email); if(password!=null){ contact.setProperty("Password",
		 * password); } ds.put(contact); return contact;
		 */

		// The SignUp using the Objectify
		try{
			Contact contact;

			if (password != null) {
				contact = new Contact(email, name, password);
			} else {
				contact = new Contact(email, name);
			}

			
		
			 ObjectifyService.ofy().save().entity(contact).now();
			System.out.println(contact);
			
		}
		catch(Exception e){
			
			
		}
	}
}