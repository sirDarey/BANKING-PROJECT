package sirdarey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import sirdarey.operations.dto.TransactionsHistoryRequestDTO;
import sirdarey.operations.dto.TransactionsHistoryResponseDTO;

@RestController
@RequestMapping("/client/badmins")
public class BankManagerController {
	
	@Autowired private RestTemplate restTemplate;
	@Autowired private HttpServletRequest httpServletRequest;
	
	private final String TRANSACTIONS_HISTORY_REQUEST = "http://localhost:8080/api/badmin-service/bank/transactions/history?limit={limit}&page={page}";

	//GET TRANSACTIONS HISTORY OF A USER
	
	@GetMapping ("/transactionshistory")
	public ResponseEntity<TransactionsHistoryResponseDTO> 
		transactionHistory (@RequestBody TransactionsHistoryRequestDTO request,
				@RequestParam(defaultValue = "2") int limit, 
				@RequestParam(defaultValue = "2") int page) {
		
		try {	
			HttpHeaders headers = new HttpHeaders();			
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("gatewayKey", httpServletRequest.getHeader("gatewayKey"));
			
			HttpEntity<TransactionsHistoryRequestDTO> httpEntity = new HttpEntity<>(request, headers);
			return restTemplate.postForEntity(TRANSACTIONS_HISTORY_REQUEST, httpEntity, TransactionsHistoryResponseDTO.class, limit, page);
		
		}catch (RestClientResponseException e) {
			
			TransactionsHistoryResponseDTO errorResponse = e.getResponseBodyAs(TransactionsHistoryResponseDTO.class);		
			return ResponseEntity.status(e.getStatusCode().value()).body(errorResponse);	
		}
	}
}
