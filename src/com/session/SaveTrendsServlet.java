package com.session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.session.helper.ShowFeeds;

public class SaveTrendsServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	
	ShowFeeds feeds = new ShowFeeds();
	ObjectMapper mapper = new ObjectMapper();

		long currentDate = feeds.getStartTime();
		long maximumDate = currentDate;
		
		// System.out.println(maximumDate);
		int frequencyOfCron = 24;
		boolean isPresent = true;

		long miniumumDate = (maximumDate - ShowFeeds.getMinimumTime(frequencyOfCron));
		// System.out.println(miniumumDate);

		// long endTime = startTime + 86400000;
		//
		// // System.out.println(startTime);

		String maxDate = null;
		URL url = new URL(
				" https://full-historyservice.appspot.com/Interaction/getInteraction.do?uniquePin=SEN42&apiKey=e2108604-f966-11e4-a322-1697f925ec7b&minimumDate="
						+ miniumumDate + "&maximumDate=" + maximumDate + "&limit=10");
		do {
			// System.out.println("Hi");

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			String line, outputString = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			while ((line = reader.readLine()) != null) {
				outputString += line;
			}
			// System.out.println("this is " + outputString);

			for (int i = 0; i < 10; i++) {
				List<Object> list = mapper.readValue(outputString, new TypeReference<List<Object>>() {
				});
				Map<String, Object> map = mapper.readValue(mapper.writeValueAsString(list.get(i)),
						new TypeReference<Map<String, Object>>() {
						});
				List<Object> list1 = mapper.readValue(mapper.writeValueAsString(map.get("interactionStatusList")),
						new TypeReference<List<Object>>() {
						});
				Map<String, Object> map1 = mapper.readValue(mapper.writeValueAsString(list1.get(0)),
						new TypeReference<Map<String, Object>>() {
						});
				List<Object> list2 = mapper.readValue(mapper.writeValueAsString(map1.get("interactionInfoJDOList")),
						new TypeReference<List<Object>>() {
						});
				Map<String, Object> map2 = mapper.readValue(mapper.writeValueAsString(list2.get(0)),
						new TypeReference<Map<String, Object>>() {
						});
				String value = mapper.writeValueAsString(map2.get("value"));
				maxDate = mapper.writeValueAsString(map.get("maximumDate"));
				maxDate = maxDate.substring(1, maxDate.length() - 1);
				long maxDateInLong = Long.parseLong(maxDate);
				if (value == null || maxDateInLong < miniumumDate) {
					System.out.println("Yes");
					isPresent = false;
					break;
				}
				feeds.addStatus(value);
				// System.out.println(value);

				// System.out.println(maxDate);

				// DateFormat formatter1 = new
				// SimpleDateFormat("dd/MM/yyyy");
				// long milliSeconds1 = Long.parseLong(x);
				// Calendar calendar1 = Calendar.getInstance();
				// calendar.setTimeInMillis(milliSeconds);
				// String date2 = formatter.format(calendar1.getTime());
				// System.out.println("Date is" + date2);
			}
			url = new URL(
					" https://full-historyservice.appspot.com/Interaction/getInteraction.do?uniquePin=SEN42&apiKey=e2108604-f966-11e4-a322-1697f925ec7b&maximumDate="
							+ maxDate + "&limit=10	");
		} while (isPresent);
		feeds.makeTrends();
		feeds.setTrends(currentDate);
		try {
			feeds.postFeed(currentDate);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("Catch");
			e.printStackTrace();
		}
	}
	}

