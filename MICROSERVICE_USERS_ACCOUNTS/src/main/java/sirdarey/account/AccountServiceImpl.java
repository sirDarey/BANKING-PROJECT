package sirdarey.account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sirdarey.account.dto.AccountDetailsDTO;
import sirdarey.account.dto.AccountDetailsResponseDTO;
import sirdarey.account.dto.UpdateAccountRequestDTO;
import sirdarey.card.CardService;
import sirdarey.utils.ExtractionUtils;

@Service
@Transactional
public class AccountServiceImpl implements AccountService{

	@Autowired private CardService cardService;
	@Autowired private AccountRepo accountRepo;
	@Autowired private ExtractionUtils extractionUtils;
	
	@Override
	public List<AccountDetailsDTO> getUserAccounts(List<Account> getAccounts) {	
		
		List<AccountDetailsDTO> returnAccountDetails = new ArrayList<>();
		
		for (Account account : getAccounts) {
			
			returnAccountDetails.add(extractionUtils
					.extractAccountDetails(account, cardService));
					
		}
		return returnAccountDetails;
	}

	@Override
	public ResponseEntity<AccountDetailsResponseDTO> getAnAccountDetails(Long accountNo) {
		
		Account accountDetails  = null;
		try {
			accountDetails = accountRepo.findById(accountNo).get();
		}catch (Exception e) {
			return ResponseEntity.status(404).body(new AccountDetailsResponseDTO(e.getMessage(), null));
		}
		
		return ResponseEntity.ok()
				.body(new AccountDetailsResponseDTO("Account Details Retrieved SUCCESSFULLY",
						extractionUtils.extractAccountDetails(accountDetails, cardService)));		
	}

	@Override
	public ResponseEntity<AccountDetailsResponseDTO> updateAccountDetails(UpdateAccountRequestDTO updateAccountRequest, Long accountNo) {
	
		Account accountDetails  = null;
		try {
			accountRepo.updateAccountDetails(
					updateAccountRequest.getEmail(),
					updateAccountRequest.getPhoneNo(),
					updateAccountRequest.getAccountManagerName(),
					accountNo);

			accountDetails = accountRepo.findById(accountNo).get();
			
		}catch (Exception e) {
			return ResponseEntity.status(404).body(new AccountDetailsResponseDTO(e.getMessage(), null));
		}
		
		return ResponseEntity.ok()
				.body(new AccountDetailsResponseDTO("Account Updated SUCCESSFULLY",
						extractionUtils.extractAccountDetails(accountDetails, cardService)));
	}

	@Override
	public ResponseEntity<AccountDetailsResponseDTO> updateAccountLockedStatus(Boolean isLocked, Long accountNo) {
		
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
					.body(new AccountDetailsResponseDTO("Account NOT FOUND", null));
		
		return ResponseEntity.ok()
				.body(new AccountDetailsResponseDTO("SUCCESS", response));
	}


}
