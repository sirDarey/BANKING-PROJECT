package sirdarey.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sirdarey.account.Account;

@Entity @Table(name = "users") @Data @NoArgsConstructor @AllArgsConstructor
public class User implements UserDetails{
	
	private static final long serialVersionUID = 1L;	

	@Id 
//	@GeneratedValue(generator = "genUUID")
//	@GenericGenerator(name = "genUUID", strategy = "org.hibernate.id.UUIDGenerator")
	private Long userId;
	
	@Size(min = 7,  message = "MINIMUM Length of 'fullName' is 7")
	private String fullName;
	
	@Size(min = 8, message = "MINIMUM Password Length is 8 characters")
	private String userPassword;
	 
	private String role;
	 
	private String registeredBy; //(adminCode_name)
	
	@Column(unique = true, length = 11)
	private Long BVN;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_user_id", referencedColumnName = "userId")
	private List <Account> ACCOUNTS = new ArrayList<>();
	
	private Byte userEnabled;

	
	
	
	/*************** SECURITY STUFFS START HERE ****************************/
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getUsername() {
		return userId.toString();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return (userEnabled == 1)? true : false;
	}

	@Override
	public String getPassword() {
		return userPassword;
	}

}
