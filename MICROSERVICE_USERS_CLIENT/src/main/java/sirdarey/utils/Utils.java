package sirdarey.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

public class Utils {
	
	@Autowired
	private RestTemplate restTemplate;
	private final String VALIDATE_TOKEN_REQUEST = "http://localhost:8001/auth/validate/{token}";

	public boolean validateJWT (String token) {
		if (token == null || !isTokenValid(token))
			return false;
		return true;	
	}

	private Boolean isTokenValid(@PathVariable String token) {
		try {
			return restTemplate.getForObject(VALIDATE_TOKEN_REQUEST, Boolean.class, token.substring(7));
		}catch (Exception e) {
			return false;
		}
	}
}
