package sirdarey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import sirdarey.dto.AdminDetailsResponse;
import sirdarey.operations.dto.AddAccountToUserRequest;
import sirdarey.operations.dto.DepositRequestDTO;
import sirdarey.operations.dto.NewUserRequest;
import sirdarey.operations.dto.TransactionsResponseDTO;
import sirdarey.operations.dto.UserDetailsResponse;
import sirdarey.service.GeneralAdminService;

@RestController
@RequestMapping("/bank/gadmins")
public class GeneralAdminController {

	@Autowired GeneralAdminService generalAdminService;
	@Autowired private RestTemplate restTemplate;
	
	private final String DEPOSIT_REQUEST = "http://localhost:8001/bank/admins/deposit";
	private final String USER_DETAILS_REQUEST = "http://localhost:8001/bank/admins/getuser/{userId}";
	private final String NEW_USER_REQUEST = "http://localhost:8001/bank/admins/newuser";
	private final String UPDATE_USER_NAME_REQUEST = "http://localhost:8001/bank/admins/updateusername/{userId}?newName={newName}";
	private final String UPDATE_USER_ENABLE_STATUS_REQUEST = "http://localhost:8001/bank/admins/updateuserenablestatus/{userId}?enable={enable}";
	private final String ADD_ACCOUNT_TO_USER_REQUEST = "http://localhost:8001/bank/admins/newaccount/{userId}";
	
	
	//GET SELF DETAILS/PROFILE
	
	@GetMapping("/{adminId}/myprofile")
	public ResponseEntity<AdminDetailsResponse> getSelfDetails (@PathVariable Long adminId) {
		return generalAdminService.getSelfDetails(adminId);
	}
	
	/**********CHANGE PASSWORD ****************TODO*****/
	
	
	//DEPOSIT FUNDS FOR A USER
	
	@PostMapping ("/deposit")
	public ResponseEntity<TransactionsResponseDTO> deposit (@RequestBody DepositRequestDTO request) {
		
		try {
			
			return restTemplate.postForEntity(DEPOSIT_REQUEST, request, TransactionsResponseDTO.class);	
		
		}catch (RestClientResponseException e) {
			
			TransactionsResponseDTO errorResponse = e.getResponseBodyAs(TransactionsResponseDTO.class);		
			return ResponseEntity.status(e.getStatusCode().value()).body(errorResponse);	
		}			
	}
	
	
	//GET A USER DETAILS/PROFILE
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<UserDetailsResponse> getUserDetails (@PathVariable Long userId) {
		
		try {			
			return restTemplate.getForEntity(USER_DETAILS_REQUEST, UserDetailsResponse.class, userId);		
		
		}catch (RestClientResponseException e) {
			
			UserDetailsResponse errorResponse = e.getResponseBodyAs(UserDetailsResponse.class);		
			return ResponseEntity.status(e.getStatusCode().value()).body(errorResponse);	
		}		
	}
		

	//ADD A NEW USER
	
	@PostMapping ("/users")
	public ResponseEntity<UserDetailsResponse> addNewUser (@RequestBody NewUserRequest request) {
		
		try {			
			return restTemplate.postForEntity(NEW_USER_REQUEST, request, UserDetailsResponse.class);	
		
		}catch (RestClientResponseException e) {
			
			UserDetailsResponse errorResponse = e.getResponseBodyAs(UserDetailsResponse.class);		
			return ResponseEntity.status(e.getStatusCode().value()).body(errorResponse);	
		}		
	}
	
	
	//UPDATE NAME in UserDetails and in ALL Accounts
	
	@PutMapping ("/users/{userId}/updateonlyname")
	public ResponseEntity<UserDetailsResponse> updateOnlyName (
			@RequestParam String newName, @PathVariable Long userId) {
		
		try {			
			return restTemplate.getForEntity(UPDATE_USER_NAME_REQUEST, UserDetailsResponse.class, userId, newName);	
		
		}catch (RestClientResponseException e) {
			
			UserDetailsResponse errorResponse = e.getResponseBodyAs(UserDetailsResponse.class);		
			return ResponseEntity.status(e.getStatusCode().value()).body(errorResponse);	
		}		
	}
	
	
	//DISABLE/ENABLE USER 
	
	@PutMapping ("/users/{userId}/enablestatus")
	public ResponseEntity<UserDetailsResponse> updateEnableStatus (
			@RequestParam Boolean enable, @PathVariable Long userId) {
		
		try {
			return restTemplate.getForEntity(UPDATE_USER_ENABLE_STATUS_REQUEST, UserDetailsResponse.class, userId, enable);
	
		}catch (RestClientResponseException e) {
			
			UserDetailsResponse errorResponse = e.getResponseBodyAs(UserDetailsResponse.class);		
			return ResponseEntity.status(e.getStatusCode().value()).body(errorResponse);	
		}		
	}
	
	//ADD NEW ACCOUNT TO  A USER
	
	@PostMapping("/users/{userId}/newaccount")
	public ResponseEntity<UserDetailsResponse> addNewAccountToUser (
			@RequestBody AddAccountToUserRequest addAccountToUserRequest, @PathVariable Long userId) {
		
		try {		
			return restTemplate.postForEntity(ADD_ACCOUNT_TO_USER_REQUEST, addAccountToUserRequest, UserDetailsResponse.class, userId);
		
		}catch (RestClientResponseException e) {
			
			UserDetailsResponse errorResponse = e.getResponseBodyAs(UserDetailsResponse.class);		
			return ResponseEntity.status(e.getStatusCode().value()).body(errorResponse);	
		}
	}
	
}
