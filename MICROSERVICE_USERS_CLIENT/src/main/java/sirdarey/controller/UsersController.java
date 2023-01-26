package sirdarey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import sirdarey.dto.AirtimeRequestDTO;
import sirdarey.dto.BalanceRequestDTO;
import sirdarey.dto.TransactionsResponseDTO;
import sirdarey.dto.TransferRequestDTO;
import sirdarey.dto.UserDetailsForUserDTO;
import sirdarey.dto.WithdrawalRequestDTO;
import sirdarey.utils.Utils;


@RestController
@RequestMapping("/client/users")
public class UsersController {
	
	@Autowired private RestTemplate restTemplate;
	@Autowired private Utils utils;
	@Autowired private HttpServletRequest httpServletRequest;
	
	private final String SELF_DETAILS_REQUEST = "http://localhost:8080/api/user-service/bank/users/{userId}";
	private final String AIRTIME_REQUEST = "http://localhost:8080/api/user-service/bank/users/airtime";
	private final String BALANCE_REQUEST = "http://localhost:8080/api/user-service/bank/users/balance";
	private final String TRANSFER_REQUEST = "http://localhost:8080/api/user-service/bank/users/transfer";
	private final String WITHDRAWAL_REQUEST = "http://localhost:8080/api/user-service/bank/users/withdrawal";
	
	//GET User By UserID For User
	
	@GetMapping("/{userId}")
	public ResponseEntity<?> getUserByUserIdForUser (@PathVariable Long userId) {
		try {
			if (!utils.validateJWT(httpServletRequest.getHeader("Authorization")))
				return ResponseEntity.status(401).build();
	
			HttpHeaders headers = new HttpHeaders();			
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("gatewayKey", httpServletRequest.getHeader("gatewayKey"));
			
			HttpEntity <?>  httpEntity = new HttpEntity<>(headers);
			return restTemplate.exchange(SELF_DETAILS_REQUEST, HttpMethod.GET, httpEntity, UserDetailsForUserDTO.class, userId);
			
		}catch (RestClientResponseException e) {
			
			String errorResponse = e.getResponseBodyAs(String.class);		
			return ResponseEntity.status(e.getStatusCode().value()).body(errorResponse);	
		}	
	}
	
	//PURCHASE AIRTIME (SELF OR THIRD PARTY)
	
	@PostMapping("/airtime")
	public ResponseEntity<TransactionsResponseDTO> requestAirtime (@RequestBody AirtimeRequestDTO airtimeRequest) {
		try {	
			if (!utils.validateJWT(httpServletRequest.getHeader("Authorization")))
				return ResponseEntity.status(401).build();
			
			HttpHeaders headers = new HttpHeaders();
			
			headers.setContentType(MediaType.APPLICATION_JSON);
			//headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.add("gatewayKey", httpServletRequest.getHeader("gatewayKey"));
			
			HttpEntity<AirtimeRequestDTO> httpEntity = new HttpEntity<>(airtimeRequest, headers);
			
			return restTemplate.postForEntity(AIRTIME_REQUEST, httpEntity, TransactionsResponseDTO.class);	
		
		}catch (RestClientResponseException e) {
			
			TransactionsResponseDTO errorResponse = e.getResponseBodyAs(TransactionsResponseDTO.class);		
			return ResponseEntity.status(e.getStatusCode().value()).body(errorResponse);	
		}	
	}
	
	//CHECK ACCOUNT BALANCE
	
	@PostMapping("/balance") 
	public ResponseEntity<TransactionsResponseDTO> checkAccountBalance (@RequestBody BalanceRequestDTO balanceRequest) {
		try {	
			if (!utils.validateJWT(httpServletRequest.getHeader("Authorization")))
				return ResponseEntity.status(401).build();
			
			HttpHeaders headers = new HttpHeaders();			
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("gatewayKey", httpServletRequest.getHeader("gatewayKey"));
			
			HttpEntity<BalanceRequestDTO> httpEntity = new HttpEntity<>(balanceRequest, headers);
			return restTemplate.postForEntity(BALANCE_REQUEST, httpEntity, TransactionsResponseDTO.class);	
		
		}catch (RestClientResponseException e) {
			
			TransactionsResponseDTO errorResponse = e.getResponseBodyAs(TransactionsResponseDTO.class);		
			return ResponseEntity.status(e.getStatusCode().value()).body(errorResponse);	
		}
	}
	
	// TRANSFER FUNDS 
	
	@PostMapping("/transfer") 
	public ResponseEntity<TransactionsResponseDTO> transfer (@RequestBody TransferRequestDTO transferRequest) {
		try {	
			if (!utils.validateJWT(httpServletRequest.getHeader("Authorization")))
				return ResponseEntity.status(401).build();
			
			HttpHeaders headers = new HttpHeaders();			
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("gatewayKey", httpServletRequest.getHeader("gatewayKey"));
			
			HttpEntity<TransferRequestDTO> httpEntity = new HttpEntity<>(transferRequest, headers);
			return restTemplate.postForEntity(TRANSFER_REQUEST, httpEntity, TransactionsResponseDTO.class);	
		
		}catch (RestClientResponseException e) {
			
			TransactionsResponseDTO errorResponse = e.getResponseBodyAs(TransactionsResponseDTO.class);		
			return ResponseEntity.status(e.getStatusCode().value()).body(errorResponse);	
		}
	}
	
	//WITHDRAW FUNDS
	
	@PostMapping("/withdrawal") 
	public ResponseEntity<TransactionsResponseDTO> withdrawalViaMerchant (@RequestBody WithdrawalRequestDTO withdrawalRequest) {
		try {		
			if (!utils.validateJWT(httpServletRequest.getHeader("Authorization")))
				return ResponseEntity.status(401).build();
			
			HttpHeaders headers = new HttpHeaders();			
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("gatewayKey", httpServletRequest.getHeader("gatewayKey"));
			
			HttpEntity<WithdrawalRequestDTO> httpEntity = new HttpEntity<>(withdrawalRequest, headers);
			return restTemplate.postForEntity(WITHDRAWAL_REQUEST, httpEntity, TransactionsResponseDTO.class);	
		
		}catch (RestClientResponseException e) {
			
			TransactionsResponseDTO errorResponse = e.getResponseBodyAs(TransactionsResponseDTO.class);		
			return ResponseEntity.status(e.getStatusCode().value()).body(errorResponse);	
		}
	}
		
}
