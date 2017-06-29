package com.session.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.google.api.server.spi.Client;



public class AnyWhereService {

	static ObjectMapper mapper = new ObjectMapper();

	public static String getAccessToken(String authcode, StringBuffer reqUrl) throws IOException {
		// https://api-dot-staging-fullspectrum.appspot.com
		URL url = new URL("https://staging-fullcreative-dot-full-auth.appspot.com/o/oauth2/v1/token");
		String params = "&code=" + authcode
				+ "&client_id=2a9ac-2baf139b82055cc1e9d6974edf536f2c&client_secret=T7_Buj9BRe2odJuV-iASsgowJ4xFFmxDS1-7DaC2&redirect_uri="
				+ reqUrl + "&grant_type=authorization_code";
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
		System.out.println(outputString);
		AnyWhereInfo anywhereToken = mapper.readValue(outputString, AnyWhereInfo.class);
		if (anywhereToken != null) {
			return anywhereToken.getAccess_token();
		} else {
			return null;
		}
	}

	public static AnyWhereUser getUserInfo(String accesstoken) throws IOException {
		AnyWhereUser aj;
		URL url = new URL("https://api-dot-staging-fullspectrum.appspot.com/api/v1/user/me");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Authorization", "Bearer " + accesstoken);
		con.setDoOutput(true);
		
		String line, outputString = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		while ((line = reader.readLine()) != null) {
			outputString += line;
		}
		System.out.println(outputString);

		AnyWhereUser ino = mapper.readValue(outputString, AnyWhereUser.class);
//		Map<String,Object> map = mapper.readValue(mapper.writeValueAsString(outputString),new TypeReference<Map<String,Object>>(){});
//		Map<String,String> datas = mapper.readValue(mapper.writeValueAsString(map.get("data")), new TypeReference<Map<String,Object>>(){});
//        JsonData user = mapper.readValue(mapper.writeValueAsString(datas.get("user")),new TypeReference<JsonData>(){} );
//		System.out.println(ino);
//		System.out.println(ino.getData().getUser().);
		return ino;

	}
}
