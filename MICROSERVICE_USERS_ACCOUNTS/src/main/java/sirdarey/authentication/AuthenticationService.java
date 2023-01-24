package sirdarey.authentication;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.var;
import sirdarey.exceptions.CustomExceptions;
import sirdarey.security.JWTService;
import sirdarey.user.UserRepo;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserRepo userRepo;	
	private final JWTService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws CustomExceptions {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
						authenticationRequest.getUserId().toString(),
						authenticationRequest.getUserPassword()
					)
				);
				
				var user = userRepo.findByUserId(authenticationRequest.getUserId());
				var JWT = jwtService.generateToken(user, null);
				return AuthenticationResponse.builder()
						.tokens(JWT)
						.build();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomExceptions("THIS USER DOESN'T EXIST");
		}
		
	}

}
