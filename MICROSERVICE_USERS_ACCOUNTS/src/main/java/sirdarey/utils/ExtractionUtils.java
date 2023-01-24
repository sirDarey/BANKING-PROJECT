package sirdarey.utils;

import java.util.List;

import sirdarey.account.Account;
import sirdarey.account.AccountService;
import sirdarey.account.dto.AccountDetailsDTO;
import sirdarey.card.Card;
import sirdarey.card.CardService;
import sirdarey.card.dto.CardDetailsDTO;
import sirdarey.notification.NotificationMedia;
import sirdarey.user.User;
import sirdarey.user.dto.UserDetailsForAdminsDTO;
import sirdarey.user.dto.UserDetailsForUserDTO;

public class ExtractionUtils {

	public UserDetailsForUserDTO extractUserDetailsForUser (User rawUser, AccountService accountService) {
		
		List<AccountDetailsDTO> getAccounts = accountService.getUserAccounts(rawUser.getACCOUNTS());
		
		return new UserDetailsForUserDTO(
				rawUser.getUserId(), 
				rawUser.getFullName(),   
				rawUser.getBVN(), 
				rawUser.getUserEnabled(),
				getAccounts
				);
	}
	
	public UserDetailsForAdminsDTO extractUserDetailsForAdmin (User rawUser, AccountService accountService) {
		
		List<AccountDetailsDTO> getAccounts = accountService.getUserAccounts(rawUser.getACCOUNTS());
		
		return new UserDetailsForAdminsDTO(
				rawUser.getUserId(), 
				rawUser.getFullName(),   
				rawUser.getBVN(), 
				rawUser.getUserEnabled(),
				rawUser.getRole(),
				rawUser.getRegisteredBy(),
				getAccounts
				);
	}
	
	public AccountDetailsDTO extractAccountDetails (Account account, CardService cardService) {
		
		List <NotificationMedia> getNotificationMedia = account.getNotificationMedia();
		List<CardDetailsDTO> getCards = cardService.getAccountCards(account.getCards());
		
		return new AccountDetailsDTO(
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
	
	public CardDetailsDTO extractCardDetails (Card card) {
		
		return new CardDetailsDTO(
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
