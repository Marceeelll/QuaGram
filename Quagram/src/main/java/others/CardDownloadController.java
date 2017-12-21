package others;

import java.util.ArrayList;

import com.php.Quagram.database.DatabaseQuagramCards;
import com.php.Quagram.model.Card;

public class CardDownloadController {
	private ArrayList<Card> downloadCardsForUser(String instagramAccessToken) {
		ArrayList<Card> cardsFromInstagram = new ArrayList<>();
		
		InstagramRequestService instagramService = new InstagramRequestService();
		cardsFromInstagram = instagramService.getAllUserPictures(instagramAccessToken);
		
		for(Card card: cardsFromInstagram) {
			GeonamesRequestService geonamesService = new GeonamesRequestService();
			Double latitude = card.getLocation().getLatitude();
			Double longitude = card.getLocation().getLongitude();
			Card geonameCard = geonamesService.getLocationData(latitude, longitude);
			if (geonameCard != null) {
				card.setHeightMeter(geonameCard.getHeightMeter());
				card.setTemperature(geonameCard.getTemperature());
				card.setWindspeed(geonameCard.getWindspeed());
				card.setHumidity(geonameCard.getHumidity());
			}
		}
		return cardsFromInstagram;
	}
	
	public void downloadCardsForUserAndSafeToDB(String instagramAccessToken, String instagramID) {
		ArrayList<Card> cardsFromInstagram = new ArrayList<>();
		cardsFromInstagram = downloadCardsForUser(instagramAccessToken);
		
		DatabaseQuagramCards cardsDB = new DatabaseQuagramCards();
		for(Card card: cardsFromInstagram) {
			cardsDB.addCard(card, instagramID);
		}
	}
}
