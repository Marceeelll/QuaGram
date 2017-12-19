package others;

import org.apache.http.client.utils.URIBuilder;

import com.php.Quagram.model.Card;

public class GeonamesRequestService {
	public Card getLocationData(double latitude, double longitude) {
		//String url = http://api.geonames.org/findNearByWeatherJSON?lat=50.325238&lng=11.941379&lang=de&username=marceeelll

		String latitudeString = String.format("%.6f", latitude);
		String longitudeString = String.format("%.6f", longitude);
		
		URIBuilder builder = new URIBuilder()
				.setScheme("http")
				.setHost("api.geonames.org")
				.setPath("/findNearByWeatherJSON")
				.setParameter("lat", latitudeString)
				.setParameter("lng", longitudeString)
				.addParameter("username", "marceeelll");
		
		try {
			String geoResponse = URLConnectionReader.getText(builder.toString());
			JSONService parser = new JSONService();
			return parser.getGeoNameCard(geoResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
