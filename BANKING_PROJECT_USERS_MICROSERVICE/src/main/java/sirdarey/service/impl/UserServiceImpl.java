package sirdarey.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletResponse;
import sirdarey.dto.AddAccountToUserRequest;
import sirdarey.dto.UserDetailsForAdmins;
import sirdarey.dto.UserDetailsForUser;
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
	public UserDetailsForAdmins getUserByUserIdForAdmin(Long userId) {
		
		User user = userRepo.findById(userId).get();		
		return extractionUtils.extractUserDetailsForAdmin(user, accountService);
	}
	
	@Override
	public UserDetailsForAdmins addUser(User userDetails) {		
		
		User user = userRepo.save(userDetails);		
		return extractionUtils.extractUserDetailsForAdmin(user, accountService);
	}
	
	@Override
	public UserDetailsForAdmins updateOnlyName(String newName, Long userId) {
		userRepo.updateOnlyName(newName, userId);
		accountRepo.updateAllAccountsNames (newName, userId);
		User user = userRepo.findById(userId).get();
		return extractionUtils.extractUserDetailsForAdmin(user, accountService);
	}

	@Override
	public UserDetailsForAdmins updateEnableStatus(boolean enable, Long userId) {
		int setStatus;
		
		if (enable)
			setStatus = 1;
		else
			setStatus = 0;
		
		userRepo.updateEnableStatus(setStatus, userId);
		User user = userRepo.findById(userId).get();
		return extractionUtils.extractUserDetailsForAdmin(user, accountService);
	}


	@Override
	public UserDetailsForAdmins addNewAccountToUser(
			AddAccountToUserRequest addAccountToUserRequest, Long userId) throws CustomExceptions, IOException {
		
		User userDetails = userRepo.findById(userId).get();		
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
		
		return extractionUtils.extractUserDetailsForAdmin(userDetails, accountService);
	}	

}
