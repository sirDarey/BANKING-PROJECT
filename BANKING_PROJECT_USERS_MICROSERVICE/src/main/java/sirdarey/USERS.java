package sirdarey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import sirdarey.utils.AdditionSetterUtils;
import sirdarey.utils.ExtractionUtils;
import sirdarey.utils.Utils;

@SpringBootApplication
public class USERS {

	public static void main(String[] args) {
		SpringApplication.run(USERS.class, args);
	}

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
}
