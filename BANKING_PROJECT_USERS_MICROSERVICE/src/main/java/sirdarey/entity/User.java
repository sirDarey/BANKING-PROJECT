package sirdarey.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor
public class User {
	
	@Transient
	private final String passwordRegex = 
			"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

	@Id 
//	@GeneratedValue(generator = "genUUID")
//	@GenericGenerator(name = "genUUID", strategy = "org.hibernate.id.UUIDGenerator")
	private Long userId;
	
	@Size(min = 7,  message = "MINIMUM Length of 'fullName' is 7")
	 
	private String fullName;
	
	@Pattern (message = "Password must contain [a-z], [A-Z], [0-9] and at least one special character such as (\"!@#&()\")", 
			regexp = passwordRegex)
	@Size(min = 8, message = "MINIMUM Password Length is 8 characters")
	private String userPassword;
	
	 
	private String role;
	 
	private String registeredBy; //(adminCode_name)
	
	@Column(unique = true, length = 11)
	private Long BVN;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_user_id", referencedColumnName = "userId")
	private List <Account> ACCOUNTS = new ArrayList<>();
	
	private byte userEnabled;

}
