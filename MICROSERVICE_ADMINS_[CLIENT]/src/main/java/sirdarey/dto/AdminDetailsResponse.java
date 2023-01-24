package sirdarey.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor @AllArgsConstructor
public class AdminDetailsResponse {

	private String message;
	private AnAdminDTO adminDetails;
}
