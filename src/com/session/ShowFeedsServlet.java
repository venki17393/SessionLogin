package com.session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.google.appengine.api.datastore.Entity;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.datanucleus.store.types.sco.backed.ArrayList;
import com.session.helper.ShowFeeds;

public class ShowFeedsServlet extends HttpServlet {
	ShowFeeds feeds = new ShowFeeds();
	ObjectMapper mapper = new ObjectMapper();

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		LinkedHashMap<String, Object> finalJson = new LinkedHashMap<String, Object>();

		String path = req.getPathInfo();
		PrintWriter out = resp.getWriter();
		// System.out.println(path);
		try {
			if (path != null) {
				path = path.substring(1);
				System.out.println(path);
				long time = Long.parseLong(path);
				if (time > 0) {
					Map ent = feeds.showTrends(time);

					if (ent != null) {
						finalJson.put("ok", true);
						finalJson.put("message", "success");
						finalJson.put("trends", ent);
						System.out.println(finalJson);
						out.println(mapper.writeValueAsString(finalJson));
					} else {
						finalJson.put("ok", true);
						finalJson.put("message", "success");
						finalJson.put("trends", "Trends Not available for the above Date");
						out.println(mapper.writeValueAsString(finalJson));
					}
				} else {
					finalJson.put("ok", true);
					finalJson.put("message", "success");
					finalJson.put("trends", "Date cannot be Zero");
					out.println(mapper.writeValueAsString(finalJson));
				}
			} else {
				finalJson.put("ok", false);
				finalJson.put("message", "Date is out of the Range");
				finalJson.put("trends", null);
				out.println(mapper.writeValueAsString(finalJson));
			}

		} catch (NumberFormatException e) {
			System.out.println(path);
			long time = feeds.getDateInLong(path);
			if (time > 0) {
				Map ent = feeds.showTrends(time);
				if (ent != null) {
					finalJson.put("ok", true);
					finalJson.put("message", "success");
					finalJson.put("trends", ent);
					System.out.println(finalJson);
					out.println(mapper.writeValueAsString(finalJson));
				} else {
					finalJson.put("ok", true);
					finalJson.put("message", "success");
					finalJson.put("trends", "Trends Not available for the above Date");
					out.println(mapper.writeValueAsString(finalJson));
				}	
			}
			else {
				finalJson.put("ok", true);
				finalJson.put("message", "success");
				finalJson.put("trends", "Date is not a proper format");
				out.println(mapper.writeValueAsString(finalJson));
			}
		} catch (Exception e) {
			finalJson.put("ok", false);
			finalJson.put("message", "Date is out of the Range");
			finalJson.put("trends", "Date is not in a proper format");
			out.println(mapper.writeValueAsString(finalJson));
		}
	
	}
}
// public void doPost(HttpServletRequest req, HttpServletResponse resp) throws
// ServletException, IOException {
// long currentDate = System.currentTimeMillis();
// long maximumDate = currentDate;
// // System.out.println(maximumDate);
// int frequencyOfCron = 2;
// boolean isPresent = true;
//
// long miniumumDate = (maximumDate -
// ShowFeeds.getMinimumTime(frequencyOfCron));
// // System.out.println(miniumumDate);
//
// //DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//
// // Calendar calendar = Calendar.getInstance();
// // calendar.setTimeInMillis(milliSeconds);
// // String date = formatter.format(calendar.getTime());
// // System.out.println("Date is" + date);
// // Date date1 = null;
// // try {
// // date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
// // System.out.println(date1);
// // } catch (ParseException e) {
// // // TODO Auto-generated catch block
// // e.printStackTrace();
// // }
// // long startTime = date1.getTime();
// // long endTime = startTime + 86400000;
// //
// // // System.out.println(startTime);
//
// String maxDate = null;
// URL url = new URL(
// "
// https://staging-historysystem-new.appspot.com/Interaction/getInteraction.do?uniquePin=SEN42&apiKey=e2108604-f966-11e4-a322-1697f925ec7b&minimumDate="
// + miniumumDate + "&maximumDate=" + maximumDate + "&limit=10");
// do {
// // System.out.println("Hi");
//
// HttpURLConnection con = (HttpURLConnection) url.openConnection();
// String line, outputString = "";
// BufferedReader reader = new BufferedReader(new
// InputStreamReader(con.getInputStream()));
// while ((line = reader.readLine()) != null) {
// outputString += line;
// }
// // System.out.println("this is " + outputString);
//
// for (int i = 0; i < 10; i++) {
// List<Object> list = mapper.readValue(outputString, new
// TypeReference<List<Object>>() {
// });
// Map<String, Object> map =
// mapper.readValue(mapper.writeValueAsString(list.get(i)),
// new TypeReference<Map<String, Object>>() {
// });
// List<Object> list1 =
// mapper.readValue(mapper.writeValueAsString(map.get("interactionStatusList")),
// new TypeReference<List<Object>>() {
// });
// Map<String, Object> map1 =
// mapper.readValue(mapper.writeValueAsString(list1.get(0)),
// new TypeReference<Map<String, Object>>() {
// });
// List<Object> list2 =
// mapper.readValue(mapper.writeValueAsString(map1.get("interactionInfoJDOList")),
// new TypeReference<List<Object>>() {
// });
// Map<String, Object> map2 =
// mapper.readValue(mapper.writeValueAsString(list2.get(0)),
// new TypeReference<Map<String, Object>>() {
// });
// String value = mapper.writeValueAsString(map2.get("value"));
// maxDate = mapper.writeValueAsString(map.get("maximumDate"));
// maxDate = maxDate.substring(1, maxDate.length() - 1);
// long maxDateInLong = Long.parseLong(maxDate);
// if (value == null || maxDateInLong < miniumumDate) {
// System.out.println("Yes");
// isPresent = false;
// break;
// }
// feeds.addStatus(value);
// // System.out.println(value);
//
// // System.out.println(maxDate);
//
// // DateFormat formatter1 = new
// // SimpleDateFormat("dd/MM/yyyy");
// // long milliSeconds1 = Long.parseLong(x);
// // Calendar calendar1 = Calendar.getInstance();
// // calendar.setTimeInMillis(milliSeconds);
// // String date2 = formatter.format(calendar1.getTime());
// // System.out.println("Date is" + date2);
// }
// url = new URL(
// "https://staging-historysystem-new.appspot.com/Interaction/getInteraction.do?uniquePin=SEN42&apiKey=e2108604-f966-11e4-a322-1697f925ec7b&maximumDate="
// + maxDate + "&limit=10 ");
// } while (isPresent);
// feeds.makeTrends();
// feeds.setTrends(currentDate);
// }
// }
/*
 * catch (Exception e) { System.out.println("Array"); }
 */
