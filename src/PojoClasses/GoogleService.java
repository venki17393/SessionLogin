package PojoClasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.codehaus.jackson.map.ObjectMapper;

public class GoogleService {
	static ObjectMapper mapper = new ObjectMapper();

	public static String getAccessToken(String authcode) throws IOException {
		URL url = new URL("https://www.googleapis.com/oauth2/v4/token");
		String params = "&code=" + authcode
				+ "&client_id=905323826918-g2vne7tcluchca01k6giosj88hhbc3el.apps.googleusercontent.com&client_secret=vEzUVdnDTVHBV3F8yy0P0pHN&redirect_uri=http://localhost:8888/googleresp&grant_type=authorization_code";
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
		// System.out.println(outputString);

		Google google = mapper.readValue(outputString, Google.class);
		//System.out.println(google.getAccess_token());
		return google.getAccess_token();
	}

	public static String getUserInfo(String accesstoken) throws IOException {

		URL url = new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + accesstoken);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		String line, outputString = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		while ((line = reader.readLine()) != null) {
			outputString += line;
		}
		// System.out.println(outputString);

		GoogleInfo info = mapper.readValue(outputString, GoogleInfo.class);
		//System.out.println(info.getEmail());
		return info.getEmail();
		
	}
}
