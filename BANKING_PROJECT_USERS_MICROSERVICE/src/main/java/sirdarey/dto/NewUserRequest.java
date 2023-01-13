package sirdarey.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import sirdarey.entity.User;

@AllArgsConstructor @NoArgsConstructor  
public class NewUserRequest extends AddAccountToUserRequest{

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
