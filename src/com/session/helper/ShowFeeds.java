package com.session.helper;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

public class ShowFeeds {
	ArrayList<String> feed = new ArrayList<String>();
	ArrayList<String> trends = new ArrayList<String>();

	public void addStatus(String value) {
		// ArrayList<String> feed = new ArrayList<String>();
		value = value.toLowerCase();
		// System.out.println(value);
		feed.add(value);

	}

	public static long getMinimumTime(int frequency) {
		return (frequency * 60 * 60 * 1000);
	}

	public void makeTrends() {
		// Iterator<String> iterator = feed.iterator();
		// while (iterator.hasNext()) {
		// String temp = iterator;
		// System.out.println(temp);
		// iterator.next();
		// //System.out.println(iterator.next());
		// }
		System.out.println("Total Feeds retrieved " + feed.size());
		for (String temp : feed) {
			Pattern MY_PATTERN = Pattern.compile("#(\\w+)");
			Matcher mat = MY_PATTERN.matcher(temp);
			while (mat.find()) {
				// System.out.println(mat.group(1));
				trends.add(mat.group(1));
			}
		}
	}

	public long getStartTime() {

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		long milliSeconds = System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);
		String date = formatter.format(calendar.getTime());
		System.out.println("Date is" + date);
		Date date1 = null;
		try {
			date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
			// System.out.println(date1);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		long startTime = date1.getTime();
		System.out.println(startTime);
		return startTime;
	}

	public void setTrends(long date) {
		HashMap<String, Integer> hashTag = new HashMap<>();

		TrendsWithDate trendsWithDate;
		for (String trend : trends) {
			Integer occurenceInt = Collections.frequency(trends, trend);
			if (occurenceInt > 3) {
				// St ring occurence = occurenceInt.toString();

				hashTag.put(trend, occurenceInt);
			}
		}

		System.out.println("hashtag is " + hashTag);
		trendsWithDate = new TrendsWithDate(date, hashTag);
		// System.out.println(trendsWithDate.getTrends());
		ObjectifyService.ofy().save().entity(trendsWithDate).now();
		// System.out.println(hashTag);

	}

	public long getDateInLong(String date) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dateCheck = date;
		if (date.length() == 10) {
			String monthString = date.substring(5, 7);
			String yearString = date.substring(0, 4);
			String dayString = date.substring(8);
			int month = Integer.parseInt(monthString);
			int day = Integer.parseInt(dayString);
			int year = Integer.parseInt(yearString);
			System.out.println("Month is " + month);
			System.out.println("Day is " + day);
			int maxDays = getMinimumDays(month, year);
			if (maxDays >= day) {
				Date startDate;
				try {
					startDate = df.parse(date);
					long time = startDate.getTime();
					System.out.println(time);
					return time;

				} catch (ParseException e) {
					System.out.println("catch");
					return 0;
				}
			} else {
				System.out.println("else");
				return 0;
			}
		}
		return 0;

	}

	public int getMinimumDays(int month, int year) {
		int daysInMonth;
		boolean leapYear;
		leapYear = checkLeap(year);
		if (month == 4 || month == 6 || month == 9 || month == 11)
			daysInMonth = 30;
		else if (month == 2)
			daysInMonth = (leapYear) ? 29 : 28;
		else
			daysInMonth = 31;
		return daysInMonth;
	}

	public boolean checkLeap(int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
	}

	public Map<String, Integer> showTrends(long dateRequested) {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q1 = new Query("TrendsWithDate");
		List<TrendsWithDate> trends = null;
		PreparedQuery prepd = ds.prepare(q1);

		trends = ofy().load().type(TrendsWithDate.class).filterKey(Key.create(TrendsWithDate.class, dateRequested))
				.list();
		if (trends.isEmpty()) {
			return null;
		} else {
			TrendsWithDate trend = trends.get(0);
			System.out.println(trend.getTrends());
			HashMap hashTag = trend.getTrends();
			Set<Entry<String, Integer>> set = hashTag.entrySet();
			List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
			Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
				public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
					// return (o1.getValue()).compareTo( o2.getValue()
					// );//Ascending
					// order
					return (o2.getValue()).compareTo(o1.getValue());// Descending
																	// order
				}
			});
			LinkedHashMap<String, Integer> sortedTrends = new LinkedHashMap<String, Integer>();
			int countOfTrends = 0;
			for (Map.Entry<String, Integer> entry : list) {
				countOfTrends++;
				sortedTrends.put(entry.getKey(), entry.getValue());
				System.out.println(entry.getKey() + " ==== " + entry.getValue());
				if (countOfTrends == 5) {
					break;
				}
			}
			System.out.println(sortedTrends);
			return sortedTrends;
		}

	}

	public String getMessage(long date) {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q1 = new Query("TrendsWithDate");
		List<TrendsWithDate> trends = null;
		PreparedQuery prepd = ds.prepare(q1);
		String message = "Today's top 5 trending hashtags are :";
		trends = ofy().load().type(TrendsWithDate.class).filterKey(Key.create(TrendsWithDate.class, date)).list();
		if (trends.isEmpty()) {
			return null;
		} else {
			TrendsWithDate trend = trends.get(0);
			System.out.println(trend.getTrends());
			HashMap hashTag = trend.getTrends();
			Set<Entry<String, Integer>> set = hashTag.entrySet();
			List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
			Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
				public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
					// return (o1.getValue()).compareTo( o2.getValue()
					// );//Ascending
					// order
					return (o2.getValue()).compareTo(o1.getValue());// Descending
																	// order
				}
			});
			LinkedHashMap<String, Integer> sortedTrends = new LinkedHashMap<String, Integer>();
			int countOfTrends = 0;
			for (Map.Entry<String, Integer> entry : list) {
				countOfTrends++;
				sortedTrends.put(entry.getKey(), entry.getValue());
				message = message + '#' + entry.getKey()+' ';
				if (countOfTrends == 5) {
					break;
				}
			}

			return message;

		}
	}
	public String requestNewAccessToken(String refreshToken) throws IOException{
		
		URL url = new URL("https://staging-fullcreative-dot-full-auth.appspot.com/o/oauth2/v1/token");
		String params = "refresh_token="+refreshToken
				+ "&client_id=2a9ac-2baf139b82055cc1e9d6974edf536f2c&client_secret=T7_Buj9BRe2odJuV-iASsgowJ4xFFmxDS1-7DaC2&grant_type="+refreshToken;
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
		writer.write(params);

		writer.flush();

		String line, outputString = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		while ((line = reader.readLine()) != null) {
			outputString += line;
		}
		System.out.println("Refresh response"+outputString);
		
		
	return null;	
		
	}
	
	
	
	
	
	public void postFeed(long date) throws IOException, JSONException {
		String message = getMessage(date);
		System.out.println(message);
		String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdGFnaW5nLWZ1bGxjcmVhdGl2ZS5mdWxsYXV0aC5jb20iLCJpYXQiOjE0OTk5NDIxMjAsInVzZXJfaWQiOiIyYjE1YzhlNS04ODM2LTQyMDQtOWM1MC0zOGZmNWNkYWZhYjQiLCJleHAiOjE0OTk5NDkzMjAsImp0aSI6IjU4NDJiLlVETmUxMmtLdm4ifQ.JZwhrAA3G6sK13Rl67xNJKOb7v82s38KD4FH73ADNwE";
		String refreshToken = "1d2fb1a20cM_ipgQOVZ2KPRvGbV-_yXMJHtlv46wdLNXX";
		System.out.println("Post Feed");
		URL url = new URL("https://api-dot-staging-fullspectrum.appspot.com/api/v1/feed");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Authorization", "Bearer " + accessToken);
		
		// con.setRequestProperty( "Content-Length", Integer.toString(
		// postDataLength ));
		con.setDoOutput(true);
//		JSONObject obj = new JSONObject();
//		obj.put("content", message);
//		obj.put("type", "update");
		
	
//		OutputStreamWriter output = new OutputStreamWriter(con.getOutputStream());
//		output.write(obj.toString());
//		
//		System.out.println("Output " + output);
		
		// try( DataOutputStream wr = new DataOutputStream(
		// con.getOutputStream())) {
		// wr.write( postData );
		// }
		//
		// System.out.println("Data "+postData);
		int responseCode = con.getResponseCode();
		System.out.println(responseCode);
		ObjectMapper mapper = new ObjectMapper();
		LinkedHashMap<String, Object> finalJson = new LinkedHashMap<String, Object>();
		finalJson.put("content",message);
		finalJson.put("activity","update");
		System.out.println("Json is "+finalJson);
		String response = mapper.writeValueAsString(finalJson);
		System.out.println("Response is "+response);
		OutputStreamWriter output = new OutputStreamWriter(con.getOutputStream());
		output.write(response);
		String line, outputString = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		while ((line = reader.readLine()) != null) {
			outputString += line;
		}
		System.out.println(outputString);
		
		if(responseCode==401){
			accessToken=requestNewAccessToken(refreshToken);
		}

	}

}
