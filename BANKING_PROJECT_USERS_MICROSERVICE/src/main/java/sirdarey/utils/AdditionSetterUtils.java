package sirdarey.utils;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.http.HttpServletResponse;
import sirdarey.entity.Account;
import sirdarey.entity.Card;
import sirdarey.entity.NotificationMedia;
import sirdarey.exceptions.CustomExceptions;

public class AdditionSetterUtils {
	
	@Autowired
	private Utils utils;

	public void newCardSetters (Card card, String accountName) {
		card.setCardNo(utils.generateRandom(15));
		card.setCvv((int) utils.generateRandom(2));
		card.setCardHolder(accountName);
		card.setCardPIN(0000);
		card.setExpiryDate(new Date(System.currentTimeMillis() + (1000*60*60)));
		card.setIsBlocked((byte)1);
		card.setIsExpired((byte)0);
	} 
	
	public void newAccountSetters (
			Account accountDetails, 
			Long userId, 
			String accountName,
			List<NotificationMedia> notificationMedia,
			List<Card> cards,
			HttpServletResponse response) throws CustomExceptions, IOException {
		
		
		if (notificationMedia.isEmpty())
			throw new CustomExceptions(400, 
					"You must enter AT LEAST ONE Notification Medium",
					response);
		
		cards.forEach(card -> {
			newCardSetters(card, accountName);
		});		
		
		accountDetails.setAccountName(accountName);
		accountDetails.setAccLocked((byte) 0);
		accountDetails.setBalance(0.0);
		if (userId != null)
			accountDetails.setFk_user_id(userId);
		accountDetails.setAccountNo(utils.generateRandom(9));
		accountDetails.setNotificationMedia(notificationMedia);
		accountDetails.setCards(cards);
	}
}
