package sirdarey.authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sirdarey.exceptions.CustomExceptions;
import sirdarey.security.JWTService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	private final JWTService jwtService;
	private final UserDetailsService userDetailsService;
	
	@PostMapping ("/authenticate")
	public ResponseEntity<AuthenticationResponse> register (@RequestBody AuthenticationRequest authenticationRequest) throws CustomExceptions {
		return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
	}
	
	@GetMapping ("/validate/{token}")
	public Boolean validateToken (@PathVariable String token) {
		
		final String username = jwtService.extractUsername(token);
		if (username != null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			if (jwtService.isTokenValid(token, userDetails))
				return true;
			return false;
		}
		
		return false;
	}
}
