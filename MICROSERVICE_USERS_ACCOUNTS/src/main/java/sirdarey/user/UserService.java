package sirdarey.user;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

import sirdarey.exceptions.CustomExceptions;
import sirdarey.user.dto.AddAccountToUserRequestDTO;
import sirdarey.user.dto.UserDetailsResponseDTO;

public interface UserService {

	ResponseEntity<?> getUserByUserIdForUser(Long userId);

	ResponseEntity<UserDetailsResponseDTO> addUser(User newUser);

	ResponseEntity<UserDetailsResponseDTO> updateOnlyName(String newName, Long userId);

	ResponseEntity<UserDetailsResponseDTO> updateEnableStatus(Boolean enable, Long userId);

	ResponseEntity<UserDetailsResponseDTO> getUserByUserIdForAdmin(Long userId);
	
	ResponseEntity<UserDetailsResponseDTO> addNewAccountToUser(AddAccountToUserRequestDTO addAccountToUserRequest, Long userId) throws CustomExceptions, IOException;

}
