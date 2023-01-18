package sirdarey.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import sirdarey.dto.AccountDetailsResponse;
import sirdarey.dto.UpdateAccountRequest;
import sirdarey.dto.UserAccountDetails;
import sirdarey.entity.Account;

public interface AccountService {

	List <UserAccountDetails> getUserAccounts (List<Account> getAccounts);

	ResponseEntity<AccountDetailsResponse> getAnAccountDetails(Long accountNo);

	ResponseEntity<AccountDetailsResponse> updateAccountDetails(UpdateAccountRequest updateAccountRequest, Long accountNo);

	//Double updateAccountBalance(Double amount, Long accountNo) throws CustomExceptions;

	ResponseEntity<AccountDetailsResponse> updateAccountLockedStatus(Boolean isLocked, Long accountNo);

}
