package com.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;


public class Check extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		String email = SessionHelper.currentUser(req);
		if (email != null) {

			req.getRequestDispatcher("a.html").include(req, resp);
		} else {
			resp.sendRedirect("login");
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		resp.setContentType("text/html");
		int count = 0;
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		/*Query q1 = new Query("Contact");
		PreparedQuery prepd = ds.prepare(q1);
		for (Entity ent : prepd.asIterable()) {
			String dname = (String) ent.getProperty("Email");
			String dpass = ent.getProperty("Password").toString();
			if ((dname.equals(email)) && (dpass.equals(password))) {
				HttpSession s1 = req.getSession();
				s1.setAttribute("email", email);
				count = 1;
				break;
			} else {
				count = 0;

			}*/
		
		
		Filter checkEmail	= new FilterPredicate("Email", FilterOperator.EQUAL, email);
		Filter checkPassword =	new FilterPredicate ("Password", FilterOperator.EQUAL, password);
		Filter heightOutOfRangeFilter = CompositeFilterOperator.and(checkEmail,checkPassword);

		Query q = new Query("Contact").setFilter(heightOutOfRangeFilter);
		PreparedQuery pd=ds.prepare(q);
		int entities = pd.countEntities();
		if(entities == 1){
			HttpSession s1 = req.getSession();
			s1.setAttribute("email",email);
			resp.sendRedirect("dashboard");
		}
		else {
			resp.sendRedirect("login");
		}
		}
	}
