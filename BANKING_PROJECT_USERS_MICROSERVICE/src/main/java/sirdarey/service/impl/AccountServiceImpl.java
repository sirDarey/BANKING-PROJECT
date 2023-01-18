package sirdarey.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sirdarey.dto.AccountDetailsResponse;
import sirdarey.dto.UpdateAccountRequest;
import sirdarey.dto.UserAccountDetails;
import sirdarey.entity.Account;
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
	public ResponseEntity<AccountDetailsResponse> getAnAccountDetails(Long accountNo) {
		
		Account accountDetails  = null;
		try {
			accountDetails = accountRepo.findById(accountNo).get();
		}catch (Exception e) {
			return ResponseEntity.status(404).body(new AccountDetailsResponse(e.getMessage(), null));
		}
		
		return ResponseEntity.ok()
				.body(new AccountDetailsResponse("Account Details Retrieved SUCCESSFULLY",
						extractionUtils.extractAccountDetails(accountDetails, cardService)));		
	}

	@Override
	public ResponseEntity<AccountDetailsResponse> updateAccountDetails(UpdateAccountRequest updateAccountRequest, Long accountNo) {
	
		Account accountDetails  = null;
		try {
			accountRepo.updateAccountDetails(
					updateAccountRequest.getEmail(),
					updateAccountRequest.getPhoneNo(),
					updateAccountRequest.getAccountManagerName(),
					accountNo);

			accountDetails = accountRepo.findById(accountNo).get();
			
		}catch (Exception e) {
			return ResponseEntity.status(404).body(new AccountDetailsResponse(e.getMessage(), null));
		}
		
		return ResponseEntity.ok()
				.body(new AccountDetailsResponse("Account Updated SUCCESSFULLY",
						extractionUtils.extractAccountDetails(accountDetails, cardService)));
	}

//	@Override
//	public Double updateAccountBalance(Double amount, Long accountNo) throws CustomExceptions {
//		
//		double initialBalance = accountRepo.getInitialBalance(accountNo);
//		double finalBalance = amount + initialBalance;
//		
//		if (finalBalance < 0.0) 
//			throw new CustomExceptions("You have Insufficient Funds for this transaction");
//		
//		accountRepo.updateAccountBalance(finalBalance, accountNo);
//		return finalBalance;
//	}

	@Override
	public ResponseEntity<AccountDetailsResponse> updateAccountLockedStatus(Boolean isLocked, Long accountNo) {
		
		Byte setStatus; 
		String response;
		
		if (isLocked) {
			setStatus = 1;
			response = "Account Locked SUCCESSFULLY";
		} else {
			setStatus = 0;
			response = "Account UNlocked SUCCESSFULLY";
		}
		
		int rowUpdated = accountRepo.updateAccountLockedStatus(setStatus, accountNo);
		if (rowUpdated == 0)
			return ResponseEntity.status(404)
					.body(new AccountDetailsResponse("Account NOT FOUND", null));
		
		return ResponseEntity.ok()
				.body(new AccountDetailsResponse("SUCCESS", response));
	}


}
