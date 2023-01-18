package sirdarey.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sirdarey.dto.UserDetailsForUser;
import sirdarey.dto.UserDetailsResponse;
import sirdarey.exceptions.CustomExceptions;
import sirdarey.dto.AddAccountToUserRequest;
import sirdarey.dto.NewUserRequest;
import sirdarey.service.UserService;
import sirdarey.service.impl.NewUserRequestService;

@RestController
@RequestMapping("/bank/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private NewUserRequestService newUserRequestService;
	
	//GET User By UserID For User
	
	@GetMapping("/{userId}")
	public UserDetailsForUser getUserByUserIdForUser (@PathVariable Long userId) throws Exception{
		return userService.getUserByUserIdForUser(userId);
	}
	
	//GET User By UserID For Admin  
	
	@GetMapping("/{userId}/admin")
	public ResponseEntity<UserDetailsResponse>  getUserByUserIdForAdmin (@PathVariable Long userId) {
		return userService.getUserByUserIdForAdmin(userId); 
	}

	//CREATE a New User
	
	@PostMapping
	public ResponseEntity<UserDetailsResponse> addNewUser (
			@RequestBody NewUserRequest newUser) throws CustomExceptions, IOException, SQLException {
		return newUserRequestService.addUser(newUser);
	}
	
	//UPDATE NAME in UserDetails and in ALL Accounts
	
	@GetMapping ("/{userId}/updateonlyname")
	public ResponseEntity<UserDetailsResponse> updateOnlyName (
			@RequestParam String newName, @PathVariable Long userId) throws Exception{
		return userService.updateOnlyName(newName, userId);
	}
	
	
	//DISABLE/ENABLE USER 
	
	@GetMapping ("/{userId}/enablestatus")
	public ResponseEntity<UserDetailsResponse> updateEnableStatus (
			@RequestParam Boolean enable, @PathVariable Long userId) throws Exception{
		return userService.updateEnableStatus(enable, userId);
	}
	
	//ADD NEW ACCOUNT TO  A USER
	
	@PostMapping("/{userId}/newaccount")
	public ResponseEntity<UserDetailsResponse> addNewAccountToUser (
			@RequestBody AddAccountToUserRequest addAccountToUserRequest, @PathVariable Long userId) throws CustomExceptions, IOException {
		
		return userService.addNewAccountToUser(addAccountToUserRequest, userId);
	}
	
}
