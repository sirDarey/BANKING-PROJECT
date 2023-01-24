package sirdarey.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter  
public class UpdateAccountRequestDTO {

	private String email;
	private Long phoneNo;
	private String accountManagerName;
}
