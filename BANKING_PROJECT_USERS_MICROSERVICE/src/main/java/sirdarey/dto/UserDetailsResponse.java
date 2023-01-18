package sirdarey.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor @NoArgsConstructor
public class UserDetailsResponse {

	private String message;
	private UserDetailsForAdmins userDetails;
}
