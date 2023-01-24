package sirdarey.account;

import java.util.List;

import org.springframework.http.ResponseEntity;

import sirdarey.account.dto.AccountDetailsDTO;
import sirdarey.account.dto.AccountDetailsResponseDTO;
import sirdarey.account.dto.UpdateAccountRequestDTO;

public interface AccountService {

	List <AccountDetailsDTO> getUserAccounts (List<Account> getAccounts);

	ResponseEntity<AccountDetailsResponseDTO> getAnAccountDetails(Long accountNo);

	ResponseEntity<AccountDetailsResponseDTO> updateAccountDetails(UpdateAccountRequestDTO updateAccountRequest, Long accountNo);

	ResponseEntity<AccountDetailsResponseDTO> updateAccountLockedStatus(Boolean isLocked, Long accountNo);

}
