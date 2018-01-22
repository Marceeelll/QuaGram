package others;

import java.util.ArrayList;

import org.apache.http.client.utils.URIBuilder;

import com.php.Quagram.model.Card;

public class InstagramRequestService {
	
	public ArrayList<Card> getAllUserPictures(String instagramAccessToken) {
		// DOKU-LINK: https://www.instagram.com/developer/endpoints/users/#get_users_media_recent_self
		// https://api.instagram.com/v1/users/self/media/recent/?access_token=ACCESS-TOKEN
		
		ArrayList<Card> allCards = new ArrayList<Card>();
		
		URIBuilder builder = new URIBuilder()
				.setScheme("https")
				.setHost("api.instagram.com")
				.setPath("/v1/users/self/media/recent/")
				.setParameter("access_token", instagramAccessToken);
		
		try {
			String instagrammAllPictureResponse = URLConnectionReader.getText(builder.toString());
			System.out.println(instagrammAllPictureResponse);
			JSONService parser = new JSONService();
			allCards = parser.getInstagramCards(instagrammAllPictureResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return allCards;
	}
	
	public void getUserProfile(String instagramAccessToken) {
		// DOKU-LINK: https://www.instagram.com/developer/endpoints/users/#get_users_self
		// https://api.instagram.com/v1/users/self/?access_token=ACCESS-TOKEN
		
		URIBuilder builder = new URIBuilder()
				.setScheme("https")
				.setHost("api.instagram.com")
				.setPath("/v1/users/self/")
				.setParameter("access_token", instagramAccessToken);
		
		
		
		System.out.println("\nAll User Profile: " + builder.toString() + "\n");
		
		try {
			String instagramProfileResponse = URLConnectionReader.getText(builder.toString());
			
			JSONService parser = new JSONService();
			parser.getInstagramUser(instagramProfileResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
