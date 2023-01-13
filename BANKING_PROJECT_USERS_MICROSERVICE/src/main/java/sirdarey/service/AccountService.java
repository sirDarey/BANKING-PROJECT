package sirdarey.service;

import java.util.List;

import sirdarey.dto.UpdateAccountRequest;
import sirdarey.dto.UserAccountDetails;
import sirdarey.entity.Account;
import sirdarey.exceptions.CustomExceptions;

public interface AccountService {

	List <UserAccountDetails> getUserAccounts (List<Account> getAccounts);

	UserAccountDetails getAnAccountDetails(Long accountNo);

	UserAccountDetails updateAccountDetails(UpdateAccountRequest updateAccountRequest, Long accountNo);

	double updateAccountBalance(double amount, Long accountNo) throws CustomExceptions;

	String updateAccountLockedStatus(boolean isLocked, Long accountNo);

}