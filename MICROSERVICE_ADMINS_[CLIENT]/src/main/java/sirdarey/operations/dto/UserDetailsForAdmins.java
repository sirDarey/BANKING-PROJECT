package sirdarey.operations.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor @NoArgsConstructor
public class UserDetailsForAdmins {
	
	private Long userId;
	private String fullName;
	private Long bvn;
	private Byte userEnabled;
	private String role;
	private String registeredBy;
	private List<UserAccountDetails> accounts;		
}
