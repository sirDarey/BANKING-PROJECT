package sirdarey.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletResponse;
import sirdarey.dto.AddAccountToUserRequest;
import sirdarey.dto.UserDetailsForUser;
import sirdarey.dto.UserDetailsResponse;
import sirdarey.entity.Account;
import sirdarey.entity.Card;
import sirdarey.entity.NotificationMedia;
import sirdarey.entity.User;
import sirdarey.exceptions.CustomExceptions;
import sirdarey.repo.AccountRepo;
import sirdarey.repo.UserRepo;
import sirdarey.service.AccountService;
import sirdarey.service.UserService;
import sirdarey.utils.AdditionSetterUtils;
import sirdarey.utils.ExtractionUtils;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ExtractionUtils extractionUtils;
	@Autowired
	private AccountService accountService;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private AdditionSetterUtils additionSetterUtils;
	@Autowired
	private AccountRepo accountRepo;
	
	@Override
	public UserDetailsForUser getUserByUserIdForUser(Long userId) {
		
		User user = userRepo.findById(userId).get();		
		return extractionUtils.extractUserDetailsForUser(user, accountService);
	}


	@Override
	public ResponseEntity<UserDetailsResponse> getUserByUserIdForAdmin(Long userId) {
		User user  = null;
		try {
			user = userRepo.findById(userId).get();
		}catch (Exception e) {
			return ResponseEntity.status(200).body(new UserDetailsResponse(e.getMessage(), null));
		}
		
		return ResponseEntity.ok()
				.body(new UserDetailsResponse("User Details Retrieved SUCCESSFULLY",
						extractionUtils.extractUserDetailsForAdmin(user, accountService)));
	}
	
	@Override
	public ResponseEntity<UserDetailsResponse> addUser(User userDetails) {	
		
		User user  = null;
		try {
			user = userRepo.save(userDetails);
		}catch (Exception e) {
			return ResponseEntity.status(200).body(new UserDetailsResponse(e.getMessage(), null));
		}
		
		return ResponseEntity.ok()
				.body(new UserDetailsResponse("Registered New User SUCCESSFULLY",
						extractionUtils.extractUserDetailsForAdmin(user, accountService)));
	}
	
	@Override
	public ResponseEntity<UserDetailsResponse> updateOnlyName(String newName, Long userId) {
		User user = null;
		
		try {
			userRepo.updateOnlyName(newName, userId);
			accountRepo.updateAllAccountsNames (newName, userId);
			user = userRepo.findById(userId).get();
		}catch (Exception e) {
			return ResponseEntity.status(200).body(new UserDetailsResponse(e.getMessage(), null));
		}
		
		return ResponseEntity.ok()
				.body(new UserDetailsResponse("User's Name Updated SUCCESSFULLY",
						extractionUtils.extractUserDetailsForAdmin(user, accountService)));
	}

	@Override
	public ResponseEntity<UserDetailsResponse> updateEnableStatus(Boolean enable, Long userId) {
		int setStatus = 0;
		String response;
		User user = null;
		if (enable) {
			setStatus = 1;
			response = "User ENABLED SUCCESSFULLY";
		} else
			response = "User DISabled SUCCESSFULLY";
		
		try {
			userRepo.updateEnableStatus(setStatus, userId);
			user = userRepo.findById(userId).get();
		}catch (Exception e) {
			return ResponseEntity.ok().body(new UserDetailsResponse(e.getMessage(), null));
		}
		
		return ResponseEntity.ok()
				.body(new UserDetailsResponse(response,
						extractionUtils.extractUserDetailsForAdmin(user, accountService)));
	}


	@Override
	public ResponseEntity<UserDetailsResponse> addNewAccountToUser(
			AddAccountToUserRequest addAccountToUserRequest, Long userId) throws CustomExceptions, IOException {
		
		User userDetails = null;
		
		try {
			userDetails = userRepo.findById(userId).get();	
			List <NotificationMedia> notificationMedia = addAccountToUserRequest.getNotificationMedia();
			List<Card> cards = addAccountToUserRequest.getCards();
			Account accountDetails = addAccountToUserRequest.getAccount();
			
			additionSetterUtils.newAccountSetters(
					accountDetails, userId, userDetails.getFullName(), notificationMedia, cards, response);
			
			accountRepo.save(accountDetails);
			userDetails.getACCOUNTS().forEach(account -> {
				account.getCards().forEach(card -> {
					card.setFk_account_no(account.getAccountNo());
				});
			});
			
		} catch (Exception e) {
			return ResponseEntity.status(200).body(new UserDetailsResponse(e.getMessage(), null));
		}
		
		
		
		return ResponseEntity.ok()
				.body(new UserDetailsResponse("Acount Added to User SUCCESSFULLY",
						extractionUtils.extractUserDetailsForAdmin(userDetails, accountService)));
	}	

}
