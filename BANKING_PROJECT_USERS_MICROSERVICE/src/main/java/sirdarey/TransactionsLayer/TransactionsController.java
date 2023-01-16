package sirdarey.TransactionsLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sirdarey.TransactionsLayer.services.AirtimeRequestService;
import sirdarey.TransactionsLayer.services.BalanceRequestService;
import sirdarey.TransactionsLayer.services.DepositRequestService;
import sirdarey.TransactionsLayer.services.TransferRequestService;
import sirdarey.TransactionsLayer.services.WithdrawalRequestService;

@RestController
@RequestMapping("/bank/transactions")
public class TransactionsController {
	
	@Autowired private AirtimeRequestService airtimeRequestService;
	@Autowired private BalanceRequestService balanceRequestService;
	@Autowired private DepositRequestService depositRequestService;
	@Autowired private TransferRequestService transferRequestService;
	@Autowired private WithdrawalRequestService withdrawalRequestService;
	
	//PURCHASE AIRTIME (SELF OR THIRD PARTY)
	
	@PostMapping("/airtime")
	public ResponseEntity<TransactionsResponseDTO> requestAirtime (@RequestBody AirtimeRequestDTO airAirtimeRequest)  {
		return airtimeRequestService.requestAirtime(airAirtimeRequest);
	}
	
	
	//CHECK ACCOUNT BALANCE
	
	@PostMapping("/balance") 
	public ResponseEntity<TransactionsResponseDTO> checkAccountBalance (@RequestBody BalanceRequestDTO balanceRequest)  {
		return balanceRequestService.checkAccountBalance(balanceRequest);
	}
	
	
	//MAKE A DEPOSIT
	
	@PostMapping("/deposit") 
	public ResponseEntity<TransactionsResponseDTO> deposit (@RequestBody DepositRequestDTO depositRequest)  {
		return depositRequestService.deposit(depositRequest);
	}
	
	
	// TRANSFER FUNDS 
	
	@PostMapping("/transfer") 
	public ResponseEntity<TransactionsResponseDTO []> transfer (@RequestBody TransferRequestDTO transferRequest)  {
		return transferRequestService.transfer(transferRequest);
	}
	
	
	//WITHDRAW FUNDS
	
	@PostMapping("/withdrawal") 
	public ResponseEntity<TransactionsResponseDTO> withdrawal (@RequestBody WithdrawalRequestDTO withdrawalRequest)  {
		return withdrawalRequestService.withdrawal(withdrawalRequest);
	}
}
