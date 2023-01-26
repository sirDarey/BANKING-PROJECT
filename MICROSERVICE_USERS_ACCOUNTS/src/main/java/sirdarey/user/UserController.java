package sirdarey.user;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sirdarey.exceptions.CustomExceptions;
import sirdarey.transactions.dto.AirtimeRequestDTO;
import sirdarey.transactions.dto.BalanceRequestDTO;
import sirdarey.transactions.dto.TransactionsResponseDTO;
import sirdarey.transactions.dto.TransferRequestDTO;
import sirdarey.transactions.dto.WithdrawalRequestDTO;
import sirdarey.transactions.services.AirtimeRequestService;
import sirdarey.transactions.services.BalanceRequestService;
import sirdarey.transactions.services.TransferRequestService;
import sirdarey.transactions.services.WithdrawalRequestService;
import sirdarey.user.dto.AddAccountToUserRequestDTO;
import sirdarey.user.dto.NewUserRequestDTO;
import sirdarey.user.dto.UserDetailsResponseDTO;

@RestController
@RequestMapping("/bank")
public class UserController {
	
	@Autowired private UserService userService;
	@Autowired private NewUserRequestService newUserRequestService;
	@Autowired private AirtimeRequestService airtimeRequestService;
	@Autowired private BalanceRequestService balanceRequestService;
	@Autowired private TransferRequestService transferRequestService;
	@Autowired private WithdrawalRequestService withdrawalRequestService;
	
	/************************ ACTIONS BY A USER ******************************/
	
	//GET User By UserID For User
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<?> getUserByUserIdForUser (@PathVariable Long userId) {
		return userService.getUserByUserIdForUser(userId);
	}
	
	//PURCHASE AIRTIME (SELF OR THIRD PARTY)
	
	@PostMapping("/users/airtime")
	public ResponseEntity<TransactionsResponseDTO> requestAirtime (@RequestBody AirtimeRequestDTO airtimeRequest) throws CustomExceptions, IOException {
		return airtimeRequestService.requestAirtime(airtimeRequest);
	}
	
	//CHECK ACCOUNT BALANCE
	
	@PostMapping("/users/balance") 
	public ResponseEntity<TransactionsResponseDTO> checkAccountBalance (@RequestBody BalanceRequestDTO balanceRequest) throws CustomExceptions, IOException  {
		return balanceRequestService.checkAccountBalance(balanceRequest);
	}
	
	// TRANSFER FUNDS 
	
	@PostMapping("/users/transfer") 
	public ResponseEntity<TransactionsResponseDTO> transfer (@RequestBody TransferRequestDTO transferRequest) throws CustomExceptions, IOException  {
		return transferRequestService.transfer(transferRequest);
	}
	
	//WITHDRAW FUNDS
	
	@PostMapping("/users/withdrawal") 
	public ResponseEntity<TransactionsResponseDTO> withdrawalViaMerchant (@RequestBody WithdrawalRequestDTO withdrawalRequest) throws CustomExceptions, IOException  {
		return withdrawalRequestService.withdrawal(withdrawalRequest);
	}
	
	
	/************************ ACTIONS ON A USER BY AN ADMIN ******************************/
	
	//GET User By UserID For Admin  
	
	@GetMapping("/admins/getuser/{userId}")
	public ResponseEntity<UserDetailsResponseDTO>  getUserByUserIdForAdmin (@PathVariable Long userId) {
		return userService.getUserByUserIdForAdmin(userId); 
	}
	
	
	//CREATE a New User
	
	@PostMapping("/admins/newuser")
	public ResponseEntity<UserDetailsResponseDTO> addNewUser (
			@RequestBody NewUserRequestDTO newUser) throws CustomExceptions, IOException {
		return newUserRequestService.addUser(newUser);
	}
	
	//UPDATE NAME in UserDetails and in ALL Accounts
	
	@RequestMapping ("/admins/updateusername/{userId}")
	public ResponseEntity<UserDetailsResponseDTO> updateOnlyName (
			@RequestParam String newName, @PathVariable Long userId) {
		return userService.updateOnlyName(newName, userId);
	}
	
	
	//DISABLE/ENABLE USER 
	
	@RequestMapping ("/admins/updateuserenablestatus/{userId}")
	public ResponseEntity<UserDetailsResponseDTO> updateEnableStatus (
			@RequestParam Boolean enable, @PathVariable Long userId) {
		return userService.updateEnableStatus(enable, userId);
	}
	
	//ADD NEW ACCOUNT TO  A USER
	
	@PostMapping("/admins/newaccount/{userId}")
	public ResponseEntity<UserDetailsResponseDTO> addNewAccountToUser (
			@RequestBody AddAccountToUserRequestDTO addAccountToUserRequest, @PathVariable Long userId) throws CustomExceptions, IOException {
		
		return userService.addNewAccountToUser(addAccountToUserRequest, userId);
	}
	
}
