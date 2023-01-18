package sirdarey.entity;

import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data  @AllArgsConstructor @NoArgsConstructor
public class Admin {

	@Transient
	private final String passwordRegex = 
			"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
	
	@Id
	private Long adminId;
	
	@Size(min = 7,  message = "MINIMUM Length of 'fullName' is 7")
	private String name; 
	
	@Pattern (message = "Password must contain [a-z], [A-Z], [0-9] and at least one special character such as (\"!@#&()\")", 
			regexp = passwordRegex)
	@Size(min = 8, message = "MINIMUM Password Length is 8 characters")
	private String password;
	
	private String registeredBy;
	
	private Byte adminEnabled;
	
	private String branch;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_admin_id", referencedColumnName = "adminId")
	private Collection<Role> roles;
}
