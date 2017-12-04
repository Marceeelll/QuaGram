package com.php.Quagram.service;

import org.apache.http.client.utils.URIBuilder;

public class GeonamesRequestService {
	public void getLocationData(double latitude, double longitude) {
		//String url = http://api.geonames.org/citiesJSON?lat=50.325238&lng=11.941379&lang=de&username=marceeelll
		
		String latitudeString = String.format("%.6f", latitude);
		String longitudeString = String.format("%.6f", longitude);
		
		URIBuilder builder = new URIBuilder()
				.setScheme("http")
				.setHost("api.geonames.org")
				.setPath("/findNearByWeatherJSON")
				.setParameter("lat", latitudeString)
				.setParameter("lng", longitudeString)
				.addParameter("username", "marceeelll");
		
		System.out.println(builder.toString());
		
		try {
			String geoResponse = URLConnectionReader.getText(builder.toString());
			System.out.println(geoResponse);
			JSONService parser = new JSONService();
			parser.getGeoNamesData(geoResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
