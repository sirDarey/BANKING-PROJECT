package sirdarey.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletResponse;
import sirdarey.account.Account;
import sirdarey.account.AccountRepo;
import sirdarey.account.AccountService;
import sirdarey.card.Card;
import sirdarey.exceptions.CustomExceptions;
import sirdarey.notification.NotificationMedia;
import sirdarey.user.dto.AddAccountToUserRequestDTO;
import sirdarey.user.dto.UserDetailsResponseDTO;
import sirdarey.utils.AdditionSetterUtils;
import sirdarey.utils.ExtractionUtils;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{

	@Autowired private UserRepo userRepo;
	@Autowired private ExtractionUtils extractionUtils;
	@Autowired private AccountService accountService;
	@Autowired private HttpServletResponse response;
	@Autowired private AdditionSetterUtils additionSetterUtils;
	@Autowired private AccountRepo accountRepo;
	@Autowired private PasswordEncoder passwordEncoder;	
	
	@Override
	public ResponseEntity<?> getUserByUserIdForUser(Long userId) {
		try {
			User user = userRepo.findById(userId).get();
			return ResponseEntity.ok()
					.body(extractionUtils.extractUserDetailsForUser(user, accountService));
		} catch (Exception e) {
			return new ResponseEntity<>("User NOT FOUND", HttpStatus.NOT_FOUND);
		}
	}


	@Override
	public ResponseEntity<UserDetailsResponseDTO> getUserByUserIdForAdmin(Long userId) {
		User user  = null;
		try {
			user = userRepo.findById(userId).get();
		}catch (Exception e) {
			return ResponseEntity.status(404).body(new UserDetailsResponseDTO(e.getMessage(), null));
		}
		
		return ResponseEntity.ok()
				.body(new UserDetailsResponseDTO("User Details Retrieved SUCCESSFULLY",
						extractionUtils.extractUserDetailsForAdmin(user, accountService)));
	}
	
	@Override
	public ResponseEntity<UserDetailsResponseDTO> addUser(User userDetails) {	
		
		User user  = null;
		try {
			userDetails.setUserPassword(passwordEncoder.encode(userDetails.getUserPassword()));
			user = userRepo.save(userDetails);
		}catch (Exception e) {
			return ResponseEntity.status(400).body(new UserDetailsResponseDTO(e.getMessage(), null));
		}
		
		return ResponseEntity.ok()
				.body(new UserDetailsResponseDTO("Registered New User SUCCESSFULLY",
						extractionUtils.extractUserDetailsForAdmin(user, accountService)));
	}
	
	@Override
	@Transactional
	public ResponseEntity<UserDetailsResponseDTO> updateOnlyName(String newName, Long userId) {
		User user = null;
		
		try {
			userRepo.updateOnlyName(newName, userId);
			accountRepo.updateAllAccountsNames (newName, userId);
			user = userRepo.findById(userId).get();
		}catch (Exception e) {
			return ResponseEntity.status(404).body(new UserDetailsResponseDTO(e.getMessage(), null));
		}
		
		return ResponseEntity.ok()
				.body(new UserDetailsResponseDTO("User's Name Updated SUCCESSFULLY",
						extractionUtils.extractUserDetailsForAdmin(user, accountService)));
	}

	@Override
	@Transactional
	public ResponseEntity<UserDetailsResponseDTO> updateEnableStatus(Boolean enable, Long userId) {
		int setStatus = 0;
		String response;
		User user = null;
		if (enable) {
			setStatus = 1;
			response = "User ENABLED SUCCESSFULLY";
		} else
			response = "User DISabled SUCCESSFULLY";
		
		try {
			userRepo.updateEnableStatus(setStatus, userId);
			user = userRepo.findById(userId).get();
		}catch (Exception e) {
			return ResponseEntity.status(404).body(new UserDetailsResponseDTO(e.getMessage(), null));
		}
		
		return ResponseEntity.ok()
				.body(new UserDetailsResponseDTO(response,
						extractionUtils.extractUserDetailsForAdmin(user, accountService)));
	}


	@Override
	public ResponseEntity<UserDetailsResponseDTO> addNewAccountToUser(
			AddAccountToUserRequestDTO addAccountToUserRequest, Long userId) throws CustomExceptions, IOException {
		
		User userDetails = null;
		
		try {
			userDetails = userRepo.findById(userId).get();	
			List <NotificationMedia> notificationMedia = addAccountToUserRequest.getNotificationMedia();
			List<Card> cards = addAccountToUserRequest.getCards();
			Account accountDetails = addAccountToUserRequest.getAccount();
			
			additionSetterUtils.newAccountSetters(
					accountDetails, userId, userDetails.getFullName(), notificationMedia, cards, response);
			
			accountRepo.save(accountDetails);
			userDetails.getACCOUNTS().forEach(account -> {
				account.getCards().forEach(card -> {
					card.setFk_account_no(account.getAccountNo());
				});
			});
			
		} catch (Exception e) {
			return ResponseEntity.status(400).body(new UserDetailsResponseDTO(e.getMessage(), null));
		}
		
		
		
		return ResponseEntity.ok()
				.body(new UserDetailsResponseDTO("Acount Added to User SUCCESSFULLY",
						extractionUtils.extractUserDetailsForAdmin(userDetails, accountService)));
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUserId(Long.parseLong(username));
		if (user == null) {
			throw new UsernameNotFoundException("User NOT FOUND in the Database");
		}
		
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getAuthorities().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		});
		
		return new org.springframework.security.core.userdetails.User (
					user.getUsername(),
					user.getPassword(),
					authorities
				);
	}	

}
