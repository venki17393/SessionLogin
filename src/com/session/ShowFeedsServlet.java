package com.session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Show Feeds");
		long currentDate = System.currentTimeMillis();

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		long milliSeconds = currentDate ;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);
		String date = formatter.format(calendar.getTime());
		System.out.println("Date is" +date);
		Date date1 = null;
		try {
			date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
			 System.out.println(date1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long startTime = date1.getTime();
		long endTime = startTime + 86400000 ;
	
		// System.out.println(startTime);

		URL url = new URL(
				" https://full-historyservice.appspot.com/Interaction/getInteraction.do?uniquePin=SEN42&apiKey=e2108604-f966-11e4-a322-1697f925ec7b&minimumDate="
						+ startTime + "&maximumDate=" + endTime + "&isLabelRequired=false&limit=10");
		// URL url = new URL("
		// https://staging-historysystem-new.appspot.com/Interaction/getInteraction.do");
		/*
		 * HttpURLConnection con = (HttpURLConnection) url.openConnection();
		 * con.setRequestProperty("minimumDate","1497749400000");
		 * con.setRequestProperty("maximumDate","1497869973589");
		 * con.setRequestProperty("uniquePin","SEN42");
		 * con.setRequestProperty("apiKey",
		 * "e2108604-f966-11e4-a322-1697f925ec7b");
		 * con.setRequestMethod("POST"); con.setRequestProperty("limit","20");
		 * con.setDoOutput(true); //
		 * con.setRequestProperty("isLabelRequired","false"); //
		 * con.setRequestProperty("isAssociation","true");
		 * con.setRequestProperty("scheduledMinimumDate","1497777086");
		 * con.setRequestProperty("scheduledMaximumDate","1497863687");
		 * con.setRequestProperty("subInteractionLimit","20");
		 */
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		String line, outputString = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		while ((line = reader.readLine()) != null) {
			outputString += line;
		}
		// System.out.println("this is "+outputString);
		ShowFeeds feeds = new ShowFeeds();
		ObjectMapper mapper = new ObjectMapper();
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
			System.out.println(value);
			feeds.addStatus(value);

			String x = mapper.writeValueAsString(map1.get("dateAddedInMillisecond"));
			x = x.substring(1, x.length() - 1);

			DateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");

			long milliSeconds1 = Long.parseLong(x);
			Calendar calendar1 = Calendar.getInstance();
			calendar.setTimeInMillis(milliSeconds);
			String date2 = formatter.format(calendar1.getTime());
			//System.out.println("Date is" + date2);

		}
		feeds.makeTrends();
		feeds.setTrends(date);
		feeds.showTrends();
	}

}
