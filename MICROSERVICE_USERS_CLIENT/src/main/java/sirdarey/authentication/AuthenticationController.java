package sirdarey.authentication;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import sirdarey.exceptions.CustomExceptions;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final RestTemplate restTemplate;
	private final String AUTH_REQUEST = "http://localhost:8001/auth/authenticate";
	
	@PostMapping ("/authenticate")
	public ResponseEntity<AuthenticationResponse> register (@RequestBody AuthenticationRequest authenticationRequest) throws CustomExceptions {

		HttpEntity<AuthenticationRequest> httpEntity = new HttpEntity<>(authenticationRequest);
		return restTemplate.exchange(AUTH_REQUEST, HttpMethod.POST, httpEntity, AuthenticationResponse.class);
	}	
}
