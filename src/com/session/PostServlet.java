package com.session;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.session.helper.PostService;

//import org.mortbay.util.ajax.JSON;

public class PostServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	LinkedHashMap<String, Object> finalJson = new LinkedHashMap<String, Object>();

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PostService ps = new PostService();
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		

		PostPojo post = null;

		try {
			String path = req.getPathInfo();
			if(path!=null){
				path=path.substring(1);
			int requestNumber = Integer.parseInt(path);
			if (requestNumber > 0 && requestNumber < 11) {
				post = ps.getPost(requestNumber);
				
				finalJson.put("ok", true);
				finalJson.put("message", "success");
				finalJson.put("post", post);

				out.println(mapper.writeValueAsString(finalJson));
			} 
			
				
			
			else {	
				finalJson.put("ok", false);
				finalJson.put("message", "Please enter between 1 and 10");
				finalJson.put("post", post);
				out.println(mapper.writeValueAsString(finalJson));

			}
			}
			else {
				finalJson.put("ok", false);
				finalJson.put("message", "Please enter between 1 and 10");
				finalJson.put("post", post);
				out.println(mapper.writeValueAsString(finalJson));
			}
			
		} 

		catch (NumberFormatException e) {
			finalJson.put("ok", false);
			finalJson.put("message", "Please enter between 1 and 10");
			finalJson.put("post", post);
			out.println(mapper.writeValueAsString(finalJson));

		}

		
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}