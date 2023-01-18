package sirdarey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import sirdarey.utils.Utils;

@SpringBootApplication
public class TRANSACTIONS {

	public static void main(String[] args) {
		SpringApplication.run(TRANSACTIONS.class, args);
	}

	@Bean
	public RestTemplate restTemplate () {
		return new RestTemplate();
	}
	
	@Bean
	public Utils utils () {
		return new Utils();
	}
}
