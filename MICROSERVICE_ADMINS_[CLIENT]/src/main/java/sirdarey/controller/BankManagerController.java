package sirdarey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import sirdarey.operations.dto.TransactionsHistoryRequestDTO;
import sirdarey.operations.dto.TransactionsHistoryResponseDTO;

@RestController
@RequestMapping("/bank/badmins")
public class BankManagerController {
	
	@Autowired private RestTemplate restTemplate;
	
	private final String TRANSACTIONS_HISTORY_REQUEST = "http://localhost:8002/bank/transactions/history?limit={limit}&page={page}";

	//GET TRANSACTIONS HISTORY OF A USER
	
	@GetMapping ("/transactionshistory")
	public ResponseEntity<TransactionsHistoryResponseDTO> 
		transactionHistory (@RequestBody TransactionsHistoryRequestDTO request,
				@RequestParam(defaultValue = "2") int limit, 
				@RequestParam(defaultValue = "2") int page) {
		
		try {		
			return restTemplate.postForEntity(TRANSACTIONS_HISTORY_REQUEST, request, TransactionsHistoryResponseDTO.class, limit, page);
		
		}catch (RestClientResponseException e) {
			
			TransactionsHistoryResponseDTO errorResponse = e.getResponseBodyAs(TransactionsHistoryResponseDTO.class);		
			return ResponseEntity.status(e.getStatusCode().value()).body(errorResponse);	
		}
	}
}
