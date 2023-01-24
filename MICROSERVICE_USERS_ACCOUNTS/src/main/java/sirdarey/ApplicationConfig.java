package sirdarey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import sirdarey.user.UserRepo;
import sirdarey.utils.AdditionSetterUtils;
import sirdarey.utils.ExtractionUtils;
import sirdarey.utils.Utils;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
	
	private final UserRepo userRepo;
	
	@Bean
	public Utils utils () {
		return new Utils();
	}
	
	@Bean
	public ExtractionUtils extractionUtils () {
		return new ExtractionUtils();
	}
	
	@Bean
	public AdditionSetterUtils additionUtils () {
		return new AdditionSetterUtils();
	}
	
	@Bean
	public RestTemplate restTemplate () {
		return new RestTemplate();
	}
	
	
	
	/************** APP CONFIG FOR SECURITY STARTS HERE *********************/

	@Bean
	public UserDetailsService userDetailsService () {
		return (userId) -> userRepo.findByUserId(Long.parseLong(userId));
		
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager (AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	} 
}
