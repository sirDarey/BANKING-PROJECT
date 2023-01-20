package sirdarey.utils;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import sirdarey.TransactionsLayer.TransactionsResponseDTO;

public class Utils {

	@Autowired private RestTemplate restTemplate;
	private String SAVE_TRANSACTION_URL = "http://localhost:8002/bank/transactions/save";
	
	public long generateRandom(int length) {
		Random rand = new Random();
		return rand.nextLong((long)Math.pow(10, length), (long)Math.pow(10, length+1));
	}
	
	
	public HttpStatus saveTransaction(@RequestBody TransactionsResponseDTO response) throws Exception {
		
		return restTemplate.postForObject(SAVE_TRANSACTION_URL, response, HttpStatus.class);
		
	}
}
