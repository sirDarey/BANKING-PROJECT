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

@RestController
@RequestMapping("/bank/transactions")
public class TransactionsController {
	
	@Autowired private AirtimeRequestService airtimeRequestService;
	@Autowired private BalanceRequestService balanceRequestService;
	@Autowired private DepositRequestService depositRequestService;
	
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
	
}
