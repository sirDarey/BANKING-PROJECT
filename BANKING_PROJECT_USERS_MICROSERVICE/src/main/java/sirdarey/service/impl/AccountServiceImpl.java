package sirdarey.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sirdarey.dto.UpdateAccountRequest;
import sirdarey.dto.UserAccountDetails;
import sirdarey.entity.Account;
import sirdarey.exceptions.CustomExceptions;
import sirdarey.repo.AccountRepo;
import sirdarey.service.AccountService;
import sirdarey.service.CardService;
import sirdarey.utils.ExtractionUtils;

@Service
@Transactional
public class AccountServiceImpl implements AccountService{

	@Autowired
	private CardService cardService;
	@Autowired
	private AccountRepo accountRepo;
	@Autowired
	private ExtractionUtils extractionUtils;
	
	@Override
	public List<UserAccountDetails> getUserAccounts(List<Account> getAccounts) {	
		
		List<UserAccountDetails> returnAccountDetails = new ArrayList<>();
		
		for (Account account : getAccounts) {
			
			returnAccountDetails.add(extractionUtils
					.extractAccountDetails(account, cardService));
					
		}
		return returnAccountDetails;
	}

	@Override
	public UserAccountDetails getAnAccountDetails(Long accountNo) {
		
			Account accountDetails = accountRepo.findById(accountNo).get();
			return extractionUtils.extractAccountDetails(
					accountDetails, cardService);
		
	}

	@Override
	public UserAccountDetails updateAccountDetails(UpdateAccountRequest updateAccountRequest, Long accountNo) {
	
		accountRepo.updateAccountDetails(
							updateAccountRequest.getEmail(),
							updateAccountRequest.getPhoneNo(),
							updateAccountRequest.getAccountManagerName(),
							accountNo);
		
		Account accountDetails = accountRepo.findById(accountNo).get();
		return extractionUtils.extractAccountDetails(
				accountDetails, cardService);
	}

	@Override
	public double updateAccountBalance(double amount, Long accountNo) throws CustomExceptions {
		
		double initialBalance = accountRepo.getInitialBalance(accountNo);
		double finalBalance = amount + initialBalance;
		
		if (finalBalance < 0.0) 
			throw new CustomExceptions("You have Insufficient Funds for this transaction");
		
		accountRepo.updateAccountBalance(finalBalance, accountNo);
		return finalBalance;
	}

	@Override
	public String updateAccountLockedStatus(boolean isLocked, Long accountNo) {
		int setStatus; 
		String response;
		
		if (isLocked) {
			setStatus = 1;
			response = "Account Locked SUCCESSFULLY";
		} else {
			setStatus = 0;
			response = "Account UNlocked SUCCESSFULLY";
		}
		
		accountRepo.updateAccountLockedStatus((byte)setStatus, accountNo);
		return response;
	}


}
