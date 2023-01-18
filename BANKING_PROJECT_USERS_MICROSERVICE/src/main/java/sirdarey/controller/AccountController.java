package sirdarey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sirdarey.dto.AccountDetailsResponse;
import sirdarey.dto.UpdateAccountRequest;
import sirdarey.service.AccountService;

@RestController
@RequestMapping("/bank/accounts/{accountNo}")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	//VIEW DETAILS OF AN ACCOUNT
	
	@GetMapping
	public ResponseEntity<AccountDetailsResponse> 
		getAnAccountDetails (@PathVariable Long accountNo) throws Exception{
		
		return accountService.getAnAccountDetails(accountNo);
	}
	
	//UPDATE DETAILS OF AN ACCOUNT
	
	@PutMapping
	public ResponseEntity<AccountDetailsResponse> updateAccountDetails (
			@RequestBody UpdateAccountRequest updateAccountRequest, @PathVariable Long accountNo) throws Exception{
		
		return accountService.updateAccountDetails(updateAccountRequest, accountNo);
	}
	
//	//UPDATE ACCOUNT BALANCE
//	
//	@RequestMapping
//	public Double updateAccountBalance (@RequestHeader Double amount, @PathVariable Long accountNo) throws CustomExceptions {
//		return accountService.updateAccountBalance(amount, accountNo);
//	}
	
	//UPDATE ACCOUNT_LOCKED STATUS
	
	@RequestMapping("/updateaccountstatus")
	public ResponseEntity<AccountDetailsResponse> updateAccountLockedStatus (@RequestParam Boolean isLocked, @PathVariable Long accountNo) throws Exception{
		return accountService.updateAccountLockedStatus(isLocked, accountNo);
	}
}
