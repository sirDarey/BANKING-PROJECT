package sirdarey.utils;

import java.util.List;

import sirdarey.dto.CardDetails;
import sirdarey.dto.UserAccountDetails;
import sirdarey.dto.UserDetailsForAdmins;
import sirdarey.dto.UserDetailsForUser;
import sirdarey.entity.Account;
import sirdarey.entity.Card;
import sirdarey.entity.NotificationMedia;
import sirdarey.entity.User;
import sirdarey.service.AccountService;
import sirdarey.service.CardService;

public class ExtractionUtils {

	public UserDetailsForUser extractUserDetailsForUser (User rawUser, AccountService accountService) {
		
		List<UserAccountDetails> getAccounts = accountService.getUserAccounts(rawUser.getACCOUNTS());
		
		return new UserDetailsForUser(
				rawUser.getUserId(), 
				rawUser.getFullName(),   
				rawUser.getBVN(), 
				rawUser.getUserEnabled(),
				getAccounts
				);
	}
	
	public UserDetailsForAdmins extractUserDetailsForAdmin (User rawUser, AccountService accountService) {
		
		List<UserAccountDetails> getAccounts = accountService.getUserAccounts(rawUser.getACCOUNTS());
		
		return new UserDetailsForAdmins(
				rawUser.getUserId(), 
				rawUser.getFullName(),   
				rawUser.getBVN(), 
				rawUser.getUserEnabled(),
				rawUser.getRole(),
				rawUser.getRegisteredBy(),
				getAccounts
				);
	}
	
	public UserAccountDetails extractAccountDetails (Account account, CardService cardService) {
		
		List <NotificationMedia> getNotificationMedia = account.getNotificationMedia();
		List<CardDetails> getCards = cardService.getAccountCards(account.getCards());
		
		return new UserAccountDetails(
				account.getAccountNo(), 
				account.getAccountName(),
				account.getEmail(), 
				account.getPhoneNo(), 
				account.getRegisteredBy(), 
				account.getBalance(), 
				account.getAccountManagerName(), 
				getNotificationMedia, 
				account.getAccLocked(), 
				account.getBranchRegistered(), 
				account.getAccountType(), 
				getCards);
	}
	
	public CardDetails extractCardDetails (Card card) {
		
		return new CardDetails(
				card.getCardNo(), 
				card.getFk_account_no(),
				card.getCvv(), 
				card.getCardHolder(), 
				card.getCardType(), 
				card.getExpiryDate(), 
				card.getIsBlocked(), 
				card.getIsExpired());
	}
}
