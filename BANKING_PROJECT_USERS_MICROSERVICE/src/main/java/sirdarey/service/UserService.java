package sirdarey.service;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

import sirdarey.dto.AddAccountToUserRequest;
import sirdarey.dto.UserDetailsForUser;
import sirdarey.dto.UserDetailsResponse;
import sirdarey.entity.User;
import sirdarey.exceptions.CustomExceptions;

public interface UserService {

	UserDetailsForUser getUserByUserIdForUser(Long userId);

	ResponseEntity<UserDetailsResponse> addUser(User newUser) throws SQLException;

	ResponseEntity<UserDetailsResponse> updateOnlyName(String newName, Long userId);

	ResponseEntity<UserDetailsResponse> updateEnableStatus(Boolean enable, Long userId);

	ResponseEntity<UserDetailsResponse> getUserByUserIdForAdmin(Long userId);
	
	ResponseEntity<UserDetailsResponse> addNewAccountToUser(AddAccountToUserRequest addAccountToUserRequest, Long userId) throws CustomExceptions, IOException;

}
