package sirdarey.dto;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sirdarey.entity.Role;

@Getter @NoArgsConstructor @AllArgsConstructor
public class AnAdminDTO {
	private Long adminId;
	private String name; 
	private Byte adminEnabled;
	private String registeredBy;
	private String branch;	
	private Collection<Role> roles;
	
}
