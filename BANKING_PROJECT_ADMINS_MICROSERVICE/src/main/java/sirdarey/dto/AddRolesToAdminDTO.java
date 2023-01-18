package sirdarey.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sirdarey.entity.Role;

@Getter @NoArgsConstructor @AllArgsConstructor
public class AddRolesToAdminDTO {

	private Long adminId;
	private List<Role> roles;
	
}
