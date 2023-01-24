package sirdarey.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter  @AllArgsConstructor @NoArgsConstructor
public class AllAdminsDTO {
	private List<AnAdminDTO> allAdmins;
}
