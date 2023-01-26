package sirdarey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import sirdarey.dto.AdminDetailsResponse;
import sirdarey.operations.dto.AddAccountToUserRequest;
import sirdarey.operations.dto.DepositRequestDTO;
import sirdarey.operations.dto.NewUserRequest;
import sirdarey.operations.dto.TransactionsResponseDTO;
import sirdarey.operations.dto.UserDetailsResponse;
import sirdarey.service.GeneralAdminService;

@RestController
public class GeneralAdminController {

	@Autowired GeneralAdminService generalAdminService;
	@Autowired private RestTemplate restTemplate;
	@Autowired private HttpServletRequest httpServletRequest;
	
	private final String DEPOSIT_REQUEST = "http://localhost:8080/api/gadmin-service/bank/admins/deposit";
	private final String USER_DETAILS_REQUEST = "http://localhost:8080/api/gadmin-service/bank/admins/getuser/{userId}";
	private final String NEW_USER_REQUEST = "http://localhost:8080/api/gadmin-service/bank/admins/newuser";
	private final String UPDATE_USER_NAME_REQUEST = "http://localhost:8080/api/gadmin-service/bank/admins/updateusername/{userId}?newName={newName}";
	private final String UPDATE_USER_ENABLE_STATUS_REQUEST = "http://localhost:8080/api/gadmin-service/bank/admins/updateuserenablestatus/{userId}?enable={enable}";
	private final String ADD_ACCOUNT_TO_USER_REQUEST = "http://localhost:8080/api/gadmin-service/bank/admins/newaccount/{userId}";
	
	
	//GET SELF DETAILS/PROFILE
	
	@GetMapping("/bank/gadmins/{adminId}/myprofile")
	public ResponseEntity<AdminDetailsResponse> getSelfDetails (@PathVariable Long adminId) {
		return generalAdminService.getSelfDetails(adminId);
	}
	
	/**********CHANGE PASSWORD ****************TODO*****/
	
	
	//DEPOSIT FUNDS FOR A USER
	
	@PostMapping ("/client/gadmins/deposit")
	public ResponseEntity<TransactionsResponseDTO> deposit (@RequestBody DepositRequestDTO request) {
		
		try {
			HttpHeaders headers = new HttpHeaders();			
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("gatewayKey", httpServletRequest.getHeader("gatewayKey"));
			
			HttpEntity<DepositRequestDTO> httpEntity = new HttpEntity<>(request, headers);
			return restTemplate.postForEntity(DEPOSIT_REQUEST, httpEntity, TransactionsResponseDTO.class);	
		
		}catch (RestClientResponseException e) {
			
			TransactionsResponseDTO errorResponse = e.getResponseBodyAs(TransactionsResponseDTO.class);		
			return ResponseEntity.status(e.getStatusCode().value()).body(errorResponse);	
		}			
	}
	
	
	//GET A USER DETAILS/PROFILE
	
	@GetMapping("/client/gadmins/users/{userId}")
	public ResponseEntity<UserDetailsResponse> getUserDetails (@PathVariable Long userId) {
		
		try {
			HttpHeaders headers = new HttpHeaders();			
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("gatewayKey", httpServletRequest.getHeader("gatewayKey"));
			
			HttpEntity<?> httpEntity = new HttpEntity<>(headers);
			return restTemplate.exchange(USER_DETAILS_REQUEST, HttpMethod.GET, httpEntity, UserDetailsResponse.class, userId);		
		
		}catch (RestClientResponseException e) {
			
			UserDetailsResponse errorResponse = e.getResponseBodyAs(UserDetailsResponse.class);		
			return ResponseEntity.status(e.getStatusCode().value()).body(errorResponse);	
		}		
	}
		

	//ADD A NEW USER
	
	@PostMapping ("/client/gadmins/users")
	public ResponseEntity<UserDetailsResponse> addNewUser (@RequestBody NewUserRequest request) {
		
		try {		
			HttpHeaders headers = new HttpHeaders();			
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("gatewayKey", httpServletRequest.getHeader("gatewayKey"));
			
			HttpEntity<NewUserRequest> httpEntity = new HttpEntity<>(request, headers);
			return restTemplate.postForEntity(NEW_USER_REQUEST, httpEntity, UserDetailsResponse.class);	
		
		}catch (RestClientResponseException e) {
			
			UserDetailsResponse errorResponse = e.getResponseBodyAs(UserDetailsResponse.class);		
			return ResponseEntity.status(e.getStatusCode().value()).body(errorResponse);	
		}		
	}
	
	
	//UPDATE NAME in UserDetails and in ALL Accounts
	
	@PutMapping ("/client/gadmins/users/{userId}/updateonlyname")
	public ResponseEntity<UserDetailsResponse> updateOnlyName (
			@RequestParam String newName, @PathVariable Long userId) {
		
		try {			
			HttpHeaders headers = new HttpHeaders();			
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("gatewayKey", httpServletRequest.getHeader("gatewayKey"));
			
			HttpEntity<?> httpEntity = new HttpEntity<>(headers);
			return restTemplate.exchange(UPDATE_USER_NAME_REQUEST, HttpMethod.GET, httpEntity, UserDetailsResponse.class, userId, newName);	
		
		}catch (RestClientResponseException e) {
			
			UserDetailsResponse errorResponse = e.getResponseBodyAs(UserDetailsResponse.class);		
			return ResponseEntity.status(e.getStatusCode().value()).body(errorResponse);	
		}		
	}
	
	
	//DISABLE/ENABLE USER 
	
	@PutMapping ("/client/gadmins/users/{userId}/enablestatus")
	public ResponseEntity<UserDetailsResponse> updateEnableStatus (
			@RequestParam Boolean enable, @PathVariable Long userId) {
		
		try {
			HttpHeaders headers = new HttpHeaders();			
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("gatewayKey", httpServletRequest.getHeader("gatewayKey"));
			
			HttpEntity<?> httpEntity = new HttpEntity<>(headers);
			return restTemplate.exchange(UPDATE_USER_ENABLE_STATUS_REQUEST, HttpMethod.GET, httpEntity, UserDetailsResponse.class, userId, enable);
	
		}catch (RestClientResponseException e) {
			
			UserDetailsResponse errorResponse = e.getResponseBodyAs(UserDetailsResponse.class);		
			return ResponseEntity.status(e.getStatusCode().value()).body(errorResponse);	
		}		
	}
	
	//ADD NEW ACCOUNT TO  A USER
	
	@PostMapping("/client/gadmins/users/{userId}/newaccount")
	public ResponseEntity<UserDetailsResponse> addNewAccountToUser (
			@RequestBody AddAccountToUserRequest addAccountToUserRequest, @PathVariable Long userId) {
		
		try {		
			HttpHeaders headers = new HttpHeaders();			
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("gatewayKey", httpServletRequest.getHeader("gatewayKey"));
			
			HttpEntity<AddAccountToUserRequest> httpEntity = new HttpEntity<>(addAccountToUserRequest, headers);
			return restTemplate.postForEntity(ADD_ACCOUNT_TO_USER_REQUEST, httpEntity, UserDetailsResponse.class, userId);
		
		}catch (RestClientResponseException e) {
			
			UserDetailsResponse errorResponse = e.getResponseBodyAs(UserDetailsResponse.class);		
			return ResponseEntity.status(e.getStatusCode().value()).body(errorResponse);	
		}
	}
	
}
