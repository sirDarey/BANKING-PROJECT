package sirdarey.account;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sirdarey.account.dto.AccountDetailsResponseDTO;
import sirdarey.account.dto.UpdateAccountRequestDTO;
import sirdarey.card.Card;
import sirdarey.card.CardService;
import sirdarey.card.dto.CardDetailsResponseDTO;
import sirdarey.exceptions.CustomExceptions;
import sirdarey.transactions.dto.DepositRequestDTO;
import sirdarey.transactions.dto.TransactionsResponseDTO;
import sirdarey.transactions.services.DepositRequestService;

@RestController
@RequestMapping("/bank/admins")
public class AccountController {
	
	@Autowired private AccountService accountService;
	@Autowired private CardService cardService;
	@Autowired private DepositRequestService depositRequestService;

	
	/************************ ACTIONS ON A USER'S ACCOUNT BY AN ADMIN ******************************/
	
	//ADD A NEW CARD TO AN ACCOUNT
	
	@PostMapping("/newcard/{accountNo}")
	public ResponseEntity<CardDetailsResponseDTO> addCardToAccount(@RequestBody Card newCard, @PathVariable Long accountNo) {
		return cardService.addCardToAccount(newCard, accountNo);
	}
		
	
	//VIEW DETAILS OF AN ACCOUNT
	
	@GetMapping ("/getaccount/{accountNo}")
	public ResponseEntity<AccountDetailsResponseDTO> 
		getAnAccountDetails (@PathVariable Long accountNo) {
		
		return accountService.getAnAccountDetails(accountNo);
	}
	
	//UPDATE DETAILS OF AN ACCOUNT
	
	@PutMapping ("/updateaccountdetails/{accountNo}")
	public ResponseEntity<AccountDetailsResponseDTO> updateAccountDetails (
			@RequestBody UpdateAccountRequestDTO updateAccountRequest, @PathVariable Long accountNo) {
		
		return accountService.updateAccountDetails(updateAccountRequest, accountNo);
	}
	
	//UPDATE ACCOUNT_LOCKED STATUS
	
	@PutMapping("/updateaccountstatus/{accountNo}")
	public ResponseEntity<AccountDetailsResponseDTO> updateAccountLockedStatus (@RequestParam Boolean isLocked, @PathVariable Long accountNo) {
		return accountService.updateAccountLockedStatus(isLocked, accountNo);
	}
	
	//MAKE A DEPOSIT
	
	@PostMapping("/deposit") 
	public ResponseEntity<TransactionsResponseDTO> deposit (@RequestBody DepositRequestDTO depositRequest) throws CustomExceptions, IOException  {
		return depositRequestService.deposit(depositRequest);
	}
	
}
