package sirdarey.service;

import java.io.IOException;

import sirdarey.dto.AddAccountToUserRequest;
import sirdarey.dto.UserDetailsForAdmins;
import sirdarey.dto.UserDetailsForUser;
import sirdarey.entity.User;
import sirdarey.exceptions.CustomExceptions;

public interface UserService {

	UserDetailsForUser getUserByUserIdForUser(Long userId);

	UserDetailsForAdmins addUser(User newUser);

	UserDetailsForAdmins updateOnlyName(String newName, Long userId);

	UserDetailsForAdmins updateEnableStatus(boolean enable, Long userId);

	UserDetailsForAdmins getUserByUserIdForAdmin(Long userId);
	
	UserDetailsForAdmins addNewAccountToUser(AddAccountToUserRequest addAccountToUserRequest, Long userId) throws CustomExceptions, IOException;

}
