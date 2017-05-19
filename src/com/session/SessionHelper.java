package com.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class SessionHelper {
	public static String currentUser(HttpServletRequest req){
		HttpSession session =req.getSession();
		if(session!= null){
		String s1 = (String) session.getAttribute("email");
		return s1;
		}
		else {
			return null ;
		}
	}
	
	public static String  isPresent(String email){
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Key emaill = KeyFactory.createKey("Contact",email);
		try {
			Entity temp = ds.get(emaill);
			System.out.println(temp);
			return "present";
			
		}
		 catch (EntityNotFoundException e1) {
			// TODO Auto-generated catch block
				return null;
			
			//e1.printStackTrace();
		}
		
	}
	

}
