package sirdarey.user;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import sirdarey.account.Account;
import sirdarey.card.Card;
import sirdarey.exceptions.CustomExceptions;
import sirdarey.notification.NotificationMedia;
import sirdarey.user.dto.NewUserRequestDTO;
import sirdarey.user.dto.UserDetailsResponseDTO;
import sirdarey.utils.AdditionSetterUtils;
import sirdarey.utils.Utils;

@Service
public class NewUserRequestService {
	
	@Autowired private UserService userService;
	@Autowired private Utils utils;
	@Autowired private AdditionSetterUtils additionSetterUtils;
	@Autowired private HttpServletResponse response;
	
	public ResponseEntity<UserDetailsResponseDTO> addUser(NewUserRequestDTO newUser) throws CustomExceptions, IOException {
		
		String validatePasswordMessage = utils.isValidPassword(newUser.getUser().getUserPassword());
		
		if(validatePasswordMessage != null)
			return ResponseEntity
					.status(400)
					.body(new UserDetailsResponseDTO(
							validatePasswordMessage, 
							null));
		
		/*******IN real life, there should be a separate class to vet all entries in the request ********/
		
		User userDetails = newUser.getUser();
		List <NotificationMedia> notificationMedia = newUser.getNotificationMedia();
		List<Card> cards = newUser.getCards();
		Account accountDetails = newUser.getAccount();
		
		additionSetterUtils.newAccountSetters(
				accountDetails, null, userDetails.getFullName(), notificationMedia, cards, response);
		
		userDetails.setUserId(utils.generateRandom(7));
		userDetails.setRole("USER");
		userDetails.setUserEnabled((byte) 0);
		userDetails.setACCOUNTS(Arrays.asList(accountDetails));
		
		ResponseEntity<UserDetailsResponseDTO> registrationResponse = userService.addUser(userDetails);
		
		return registrationResponse;
		
	}

	
}
