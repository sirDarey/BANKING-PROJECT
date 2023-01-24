package sirdarey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import sirdarey.dto.AirtimeRequestDTO;
import sirdarey.dto.BalanceRequestDTO;
import sirdarey.dto.TransactionsResponseDTO;
import sirdarey.dto.TransferRequestDTO;
import sirdarey.dto.UserDetailsForUserDTO;
import sirdarey.dto.WithdrawalRequestDTO;


@RestController
@RequestMapping("/client/users")
public class UsersController {
	
	@Autowired private RestTemplate restTemplate;
	
	private final String SELF_DETAILS_REQUEST = "http://localhost:8001/bank/users/{userId}";
	private final String AIRTIME_REQUEST = "http://localhost:8001/bank/users/airtime";
	private final String BALANCE_REQUEST = "http://localhost:8001/bank/users/balance";
	private final String TRANSFER_REQUEST = "http://localhost:8001/bank/users/transfer";
	private final String WITHDRAWAL_REQUEST = "http://localhost:8001/bank/users/withdrawal";
	
	//GET User By UserID For User
	
	@GetMapping("/{userId}")
	public ResponseEntity<?> getUserByUserIdForUser (@PathVariable Long userId) {
		try {			
			return restTemplate.getForEntity(SELF_DETAILS_REQUEST, UserDetailsForUserDTO.class, userId);	
		
		}catch (RestClientResponseException e) {
			
			String errorResponse = e.getResponseBodyAs(String.class);		
			return ResponseEntity.status(e.getStatusCode().value()).body(errorResponse);	
		}	
	}
	
	//PURCHASE AIRTIME (SELF OR THIRD PARTY)
	
	@PostMapping("/airtime")
	public ResponseEntity<TransactionsResponseDTO> requestAirtime (@RequestBody AirtimeRequestDTO airtimeRequest) {
		try {			
			return restTemplate.postForEntity(AIRTIME_REQUEST, airtimeRequest, TransactionsResponseDTO.class);	
		
		}catch (RestClientResponseException e) {
			
			TransactionsResponseDTO errorResponse = e.getResponseBodyAs(TransactionsResponseDTO.class);		
			return ResponseEntity.status(e.getStatusCode().value()).body(errorResponse);	
		}	
	}
	
	//CHECK ACCOUNT BALANCE
	
	@PostMapping("/balance") 
	public ResponseEntity<TransactionsResponseDTO> checkAccountBalance (@RequestBody BalanceRequestDTO balanceRequest) {
		try {			
			return restTemplate.postForEntity(BALANCE_REQUEST, balanceRequest, TransactionsResponseDTO.class);	
		
		}catch (RestClientResponseException e) {
			
			TransactionsResponseDTO errorResponse = e.getResponseBodyAs(TransactionsResponseDTO.class);		
			return ResponseEntity.status(e.getStatusCode().value()).body(errorResponse);	
		}
	}
	
	// TRANSFER FUNDS 
	
	@PostMapping("/transfer") 
	public ResponseEntity<TransactionsResponseDTO> transfer (@RequestBody TransferRequestDTO transferRequest) {
		try {			
			return restTemplate.postForEntity(TRANSFER_REQUEST, transferRequest, TransactionsResponseDTO.class);	
		
		}catch (RestClientResponseException e) {
			
			TransactionsResponseDTO errorResponse = e.getResponseBodyAs(TransactionsResponseDTO.class);		
			return ResponseEntity.status(e.getStatusCode().value()).body(errorResponse);	
		}
	}
	
	//WITHDRAW FUNDS
	
	@PostMapping("/withdrawal") 
	public ResponseEntity<TransactionsResponseDTO> withdrawalViaMerchant (@RequestBody WithdrawalRequestDTO withdrawalRequest) {
		try {			
			return restTemplate.postForEntity(WITHDRAWAL_REQUEST, withdrawalRequest, TransactionsResponseDTO.class);	
		
		}catch (RestClientResponseException e) {
			
			TransactionsResponseDTO errorResponse = e.getResponseBodyAs(TransactionsResponseDTO.class);		
			return ResponseEntity.status(e.getStatusCode().value()).body(errorResponse);	
		}
	}
		
}
