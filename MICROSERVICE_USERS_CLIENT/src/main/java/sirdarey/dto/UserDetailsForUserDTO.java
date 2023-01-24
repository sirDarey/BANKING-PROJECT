package sirdarey.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class UserDetailsForUserDTO {
	
	private Long userId;
	private String fullName;
	private Long bvn;
	private Byte userEnabled;	
	private List<AccountDetailsDTO> accounts;		
}
