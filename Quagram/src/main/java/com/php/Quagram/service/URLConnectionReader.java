package com.php.Quagram.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLConnectionReader {
	public static String getText(String urlString) throws Exception {
		
		System.out.println("-->" + urlString + "<--");
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
		StringBuilder response = new StringBuilder();
		String inputLine = "";
		
		while((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		
		System.out.println("4 - " + response.toString());
		in.close();
		return response.toString();
	}
}
