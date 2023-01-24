package sirdarey.user.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sirdarey.account.dto.AccountDetailsDTO;

@Getter @AllArgsConstructor
public class UserDetailsForAdminsDTO {
	
	private Long userId;
	private String fullName;
	private Long bvn;
	private Byte userEnabled;
	private String role;
	private String registeredBy;
	private List<AccountDetailsDTO> accounts;		
}
