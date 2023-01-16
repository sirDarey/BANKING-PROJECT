package sirdarey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sirdarey.dto.UpdateAccountRequest;
import sirdarey.dto.UserAccountDetails;
import sirdarey.exceptions.CustomExceptions;
import sirdarey.service.AccountService;

@RestController
@RequestMapping("/bank/accounts/{accountNo}")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	//VIEW DETAILS OF AN ACCOUNT
	
	@GetMapping
	public ResponseEntity<UserAccountDetails> 
		getAnAccountDetails (@PathVariable Long accountNo) throws Exception{
		
		return ResponseEntity.status(200).body(accountService.getAnAccountDetails(accountNo));
	}
	
	//VIEW DETAILS OF AN ACCOUNT
	
	@PutMapping
	public ResponseEntity<UserAccountDetails> updateAccountDetails (
			@RequestBody UpdateAccountRequest updateAccountRequest, @PathVariable Long accountNo) throws Exception{
		
		return ResponseEntity.status(200).body(accountService.updateAccountDetails(updateAccountRequest, accountNo));
	}
	
	//UPDATE ACCOUNT BALANCE
	
	@RequestMapping
	public double updateAccountBalance (@RequestHeader Double amount, @PathVariable Long accountNo) throws CustomExceptions {
		return accountService.updateAccountBalance(amount, accountNo);
	}
	
	//UPDATE ACCOUNT_LOCKED STATUS
	
	@RequestMapping("/updateaccountstatus")
	public String updateAccountLockedStatus (@RequestParam Boolean isLocked, @PathVariable Long accountNo) throws Exception{
		return accountService.updateAccountLockedStatus(isLocked, accountNo);
	}
}
