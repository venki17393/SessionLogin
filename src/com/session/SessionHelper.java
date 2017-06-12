package com.session;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.session.helper.Contact;

public class SessionHelper {
	static PostPojo post = null;
	static ObjectMapper mapper = new ObjectMapper();
	static JSONObject json = new JSONObject();

	public static String currentUser(HttpServletRequest req) {
		HttpSession session = req.getSession();
		if (session != null) {
			String s1 = (String) session.getAttribute("email");
			return s1;
		} else {
			return null;
		}
	}

	public static boolean isPresent(String email) {
		// TO CHECK USING LOW-LEVEL
		/*
		 * DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		 * Key emaill = KeyFactory.createKey("Contact", email); try { Entity
		 * temp = ds.get(emaill); System.out.println(temp); return true;
		 * 
		 * } catch (EntityNotFoundException e1) { // TODO Auto-generated catch
		 * block return false;
		 * 
		 * // e1.printStackTrace(); }
		 */

		// TO CHECK USING OBJECTIFY
		// List<Contact> existingEmail =
		// ofy().load().type(Contact.class).filter("Email",email).list();
		/*
		 * UserService userService = UserServiceFactory.getUserService();
		 * List<Contact> existingEmail =
		 * ofy().load().type(Contact.class).filter("Email==",email).list();
		 * 
		 * System.out.println(existingEmail.toString());
		 * if(existingEmail.isEmpty()){ System.out.println("empty"); return
		 * false; } else { System.out.println("not empty"); return true; }
		 */

		List<Contact> q = ofy().load().type(Contact.class).filter("Email ==", email).list();
		System.out.println(q);
		if (q.isEmpty()) {
			// System.out.println("if");
			return false;
		}

		else {

			return true;
		}
	}

	public static PostPojo wrongUrl() throws JSONException, JsonParseException, JsonMappingException, IOException {
		json.put("ok", false);
		json.put("message", "invalid post id, please pass between 1 and 10");
		String string = json.toString();
		post = mapper.readValue(string, PostPojo.class);

		return post;
	}
	
	public static void getUserInfo(String email,HttpServletResponse resp) throws IOException, EntityNotFoundException {
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		out.println("hello,"+email);
	
		
	}

	public static PostPojo getPostContent(String requestNumber) throws IOException, JSONException {
		PostPojo pj = new PostPojo();
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		String key = requestNumber;
		Expiration expiration = Expiration.byDeltaSeconds(20);
		byte[] value;
		long count = 1;
		value = (byte[]) syncCache.get(key);
		// System.out.println(value);

		try {
			if (value == null) {
				System.out.println("Setting the cache");
				value = BigInteger.valueOf(count).toByteArray();
				syncCache.put(key, pj, expiration);

				String temp = requestNumber.substring(1);
				int lastChar = Integer.parseInt(temp);
				if (lastChar > 0 && lastChar < 11) {
					String actualUrl = "http://jsonplaceholder.typicode.com/posts" + requestNumber;
					URL url = new URL(actualUrl);

					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("GET");
					con.connect();

					StringBuilder sb = new StringBuilder();

					BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

					String line;
					while ((line = br.readLine()) != null) {
						sb.append(line + "\n");
					}
					br.close();

					json.put("ok", true);
					json.put("message", "found");
					String string = sb.toString();
					String jsonString = json.toString();
					string = string.concat(jsonString);
					post = mapper.readValue(string, PostPojo.class);
					// System.out.println(string);
					// post = mapper.readValue(jsonString, PostPojo.class);

					return post;

				} else {

					post = wrongUrl();
					return post;
				}
			} else {
				System.out.println("Getting from the cache");
				count = new BigInteger(value).longValue();
				count++;
				value = BigInteger.valueOf(count).toByteArray();
				syncCache.put(key, value, expiration);
				return post;
				// syncCache.get(key)
			}

		} catch (NumberFormatException e) {
			post = wrongUrl();
			return post;
		}

		catch (NullPointerException e) {
			post = wrongUrl();
			return post;
		}

	}

}
