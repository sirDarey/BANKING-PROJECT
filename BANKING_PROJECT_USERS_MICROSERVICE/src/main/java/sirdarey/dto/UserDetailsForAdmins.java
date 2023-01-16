package sirdarey.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class UserDetailsForAdmins {
	
	private Long userId;
	private String fullName;
	private Long bvn;
	private Byte userEnabled;
	private String role;
	private String registeredBy;
	private List<UserAccountDetails> accounts;		
}
