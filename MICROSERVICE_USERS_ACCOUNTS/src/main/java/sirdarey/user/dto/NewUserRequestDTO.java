package sirdarey.user.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import sirdarey.user.User;

@AllArgsConstructor @NoArgsConstructor  
public class NewUserRequestDTO extends AddAccountToUserRequestDTO{

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private User user;	
}
