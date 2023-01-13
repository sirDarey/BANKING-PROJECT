package sirdarey.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class UserDetailsForUser {
	
	private Long userId;
	private String fullName;
	private Long bvn;
	private byte userEnabled;	
	private List<UserAccountDetails> accounts;		
}
