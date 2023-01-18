package sirdarey.service.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import sirdarey.dto.NewUserRequest;
import sirdarey.dto.UserDetailsResponse;
import sirdarey.entity.Account;
import sirdarey.entity.Card;
import sirdarey.entity.NotificationMedia;
import sirdarey.entity.User;
import sirdarey.exceptions.CustomExceptions;
import sirdarey.service.UserService;
import sirdarey.utils.AdditionSetterUtils;
import sirdarey.utils.Utils;

@Service
public class NewUserRequestService {
	
	@Autowired
	private UserService userService;
	@Autowired
	private Utils utils;
	@Autowired
	private AdditionSetterUtils additionSetterUtils;
	@Autowired
	private HttpServletResponse response;
	
	public ResponseEntity<UserDetailsResponse> addUser(NewUserRequest newUser) throws CustomExceptions, IOException, SQLException {
		
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
		
		ResponseEntity<UserDetailsResponse> registrationResponse = userService.addUser(userDetails);
		
		registrationResponse.getBody().getUserDetails().getAccounts().forEach(account -> {
			account.getCards().forEach(card -> {
				card.setFk_account_no(accountDetails.getAccountNo());
			});
		});
		
		return registrationResponse;
		
	}

	
}
