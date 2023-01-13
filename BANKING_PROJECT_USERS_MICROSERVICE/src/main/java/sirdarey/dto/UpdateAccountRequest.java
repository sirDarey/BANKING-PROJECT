package sirdarey.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter  
public class UpdateAccountRequest {

	private String email;
	private Long phoneNo;
	private String accountManagerName;
}
