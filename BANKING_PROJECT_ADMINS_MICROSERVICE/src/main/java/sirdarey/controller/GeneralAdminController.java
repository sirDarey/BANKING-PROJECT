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
import org.springframework.web.client.RestTemplate;

import operations.dto.AddAccountToUserRequest;
import operations.dto.DepositRequestDTO;
import operations.dto.NewUserRequest;
import operations.dto.TransactionsHistoryRequestDTO;
import operations.dto.TransactionsHistoryResponseDTO;
import operations.dto.TransactionsResponseDTO;
import operations.dto.UserDetailsResponse;
import operations.dto.WithdrawalRequestDTO;
import sirdarey.dto.AdminDetailsResponse;
import sirdarey.service.GeneralAdminService;

@RestController
@RequestMapping("/bank/gadmins/{adminId}")
public class GeneralAdminController {

	@Autowired GeneralAdminService generalAdminService;
	@Autowired private RestTemplate restTemplate;
	
	private final String DEPOSIT_REQUEST = "http://localhost:8002/bank/transactions/deposit";
	private final String TRANSACTIONS_HISTORY_REQUEST = "http://localhost:8002/bank/transactions/history?limit={limit}&page={page}";
	private final String WITHDRAW_REQUEST = "http://localhost:8002/bank/transactions/withdraw";
	private final String USER_DETAILS_REQUEST = "http://localhost:8001/bank/users/{userId}/admin";
	private final String NEW_USER_REQUEST = "http://localhost:8001/bank/users";
	private final String UPDATE_USER_NAME_REQUEST = "http://localhost:8001/bank/users/{userId}/updateonlyname?newName={newName}";
	private final String UPDATE_USER_ENABLE_STATUS_REQUEST = "http://localhost:8001/bank/users/{userId}/enablestatus?enable={enable}";
	private final String ADD_ACCOUNT_TO_USER_REQUEST = "http://localhost:8001/bank/users/{userId}/newaccount";
	
	
	//GET SELF DETAILS/PROFILE
	
	@GetMapping("/myprofile")
	public ResponseEntity<AdminDetailsResponse> getSelfDetails (@PathVariable Long adminId) {
		return generalAdminService.getSelfDetails(adminId);
	}
	
	/**********CHANGE PASSWORD ****************TODO*****/
	
	
	//DEPOSIT FUNDS FOR A USER
	
	@PostMapping ("/deposit")
	public ResponseEntity<TransactionsResponseDTO> deposit (@RequestBody DepositRequestDTO request) {
		return restTemplate.postForEntity(DEPOSIT_REQUEST, request, TransactionsResponseDTO.class);		
	}
	
	
	//GET TRANSACTIONS HISTORY OF A USER
	
	@PostMapping ("/transactionshistory")
	public ResponseEntity<TransactionsHistoryResponseDTO> 
		transactionHistory (@RequestBody TransactionsHistoryRequestDTO request,
				@RequestParam(defaultValue = "2") int limit, 
				@RequestParam(defaultValue = "2") int page) {
		
		return restTemplate.postForEntity(TRANSACTIONS_HISTORY_REQUEST, request, TransactionsHistoryResponseDTO.class, limit, page);		
	}
	
	
	//WITHDRAW FUNDS  FOR A USER
	
	@PostMapping ("/withdraw")
	public ResponseEntity<TransactionsResponseDTO> withdraw (@RequestBody WithdrawalRequestDTO request){
		return restTemplate.postForEntity(WITHDRAW_REQUEST, request, TransactionsResponseDTO.class);		
	}
	
	
	//GET A USER DETAILS/PROFILE
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<UserDetailsResponse> getUserDetails (
				@PathVariable Long adminId, @PathVariable Long userId) {
		
		return restTemplate.getForEntity(USER_DETAILS_REQUEST, UserDetailsResponse.class, userId);	
	}
	
	

	//ADD A NEW USER
	
	@PostMapping ("/users")
	public ResponseEntity<UserDetailsResponse> addNewUser (@RequestBody NewUserRequest request) {
		return restTemplate.postForEntity(NEW_USER_REQUEST, request, UserDetailsResponse.class);	
	}
	
	
	//UPDATE NAME in UserDetails and in ALL Accounts
	
	@PutMapping ("/users/{userId}/updateonlyname")
	public ResponseEntity<UserDetailsResponse> updateOnlyName (
			@RequestParam String newName, @PathVariable Long userId) {

		return restTemplate.getForEntity(UPDATE_USER_NAME_REQUEST, UserDetailsResponse.class, userId, newName);	
	}
	
	
	//DISABLE/ENABLE USER 
	
	@PutMapping ("/users/{userId}/enablestatus")
	public ResponseEntity<UserDetailsResponse> updateEnableStatus (
			@RequestParam Boolean enable, @PathVariable Long userId) {
		
		return restTemplate.getForEntity(UPDATE_USER_ENABLE_STATUS_REQUEST, UserDetailsResponse.class, userId, enable);
	}
	
	//ADD NEW ACCOUNT TO  A USER
	
	@PostMapping("/users/{userId}/newaccount")
	public ResponseEntity<UserDetailsResponse> addNewAccountToUser (
			@RequestBody AddAccountToUserRequest addAccountToUserRequest, @PathVariable Long userId) {
		
		return restTemplate.postForEntity(ADD_ACCOUNT_TO_USER_REQUEST, addAccountToUserRequest, UserDetailsResponse.class, userId);
	}
}
