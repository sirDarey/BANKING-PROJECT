package sirdarey.user.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sirdarey.account.dto.AccountDetailsDTO;

@Getter @AllArgsConstructor
public class UserDetailsForUserDTO {
	
	private Long userId;
	private String fullName;
	private Long bvn;
	private Byte userEnabled;	
	private List<AccountDetailsDTO> accounts;		
}
