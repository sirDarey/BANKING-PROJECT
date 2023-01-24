package sirdarey.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import sirdarey.transactions.dto.TransactionsResponseDTO;

public class Utils {
	
	private final String passwordRegex = 
			"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

	@Autowired private RestTemplate restTemplate;
	private String SAVE_TRANSACTION_URL = "http://localhost:8002/bank/transactions/save";
	private String SAVE_TRANSFER_TRANSACTION_URL = "http://localhost:8002/bank/transactions/savetransfer";
	
	public long generateRandom(int length) {
		Random rand = new Random();
		return rand.nextLong((long)Math.pow(10, length), (long)Math.pow(10, length+1));
	}
	
	
	public HttpStatus saveTransaction(@RequestBody TransactionsResponseDTO response) {
		return restTemplate.postForObject(SAVE_TRANSACTION_URL, response, HttpStatus.class);		
	}
	
	
	public HttpStatus saveTransaction(TransactionsResponseDTO[] response) {
		return restTemplate.postForObject(SAVE_TRANSFER_TRANSACTION_URL, response, HttpStatus.class);
	}
	
	
	public String isValidPassword(String password) {
		if (password.length() < 8) 
			return "Minimum Length of Password is 8 Characters";
		
		Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        if (matcher.matches()) 
        	return null;
        
        return "Password must contain [a-z], [A-Z], [0-9] and at least one special character such as (\"!@#&()\")";
    }
	
}
