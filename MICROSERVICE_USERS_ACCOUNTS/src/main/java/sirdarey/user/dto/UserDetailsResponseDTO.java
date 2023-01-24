package sirdarey.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor @NoArgsConstructor
public class UserDetailsResponseDTO {

	private String message;
	private UserDetailsForAdminsDTO userDetails;
}
