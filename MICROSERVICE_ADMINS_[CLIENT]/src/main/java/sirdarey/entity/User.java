package sirdarey.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class User {
	
	private final String passwordRegex = 
			"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

	private Long userId;
	private String fullName;
	private String userPassword;
	private String role;	 
	private String registeredBy; //(adminCode_name)
	private Long BVN;
	private List <Account> ACCOUNTS = new ArrayList<>();	
	private Byte userEnabled;

}
