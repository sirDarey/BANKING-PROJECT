package sirdarey.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import sirdarey.entity.Account;

public interface AccountRepo extends JpaRepository<Account, Long>{	

	@Modifying
	@Query(nativeQuery = true, 
			value = "UPDATE account SET"
					+ " email=?1,"
					+ " phone_no=?2,"
					+ " account_manager_name=?3"
					+ " WHERE account_no=?4")
	void updateAccountDetails(String email, Long phoneNo, String accountManagerName, Long accountNo);
	

	@Modifying
	@Query(nativeQuery = true, 
			value = "UPDATE account SET account_name = ?1 WHERE fk_user_id = ?2")
	void updateAllAccountsNames(String newName, Long userId);

	
	@Query(nativeQuery = true, 
			value = "SELECT balance FROM account WHERE account_no = ?1")
	double getInitialBalance(Long accountNo);
	

	@Modifying
	@Query(nativeQuery = true, 
			value = "UPDATE account SET balance = ?1 WHERE account_no = ?2")
	void updateAccountBalance(double finalBalance, Long accountNo);
	

	@Modifying
	@Query(nativeQuery = true, 
			value = "UPDATE account SET acc_locked = ?1 WHERE account_no = ?2")
	void updateAccountLockedStatus(byte setStatus, Long accountNo);

	@Query(nativeQuery = true, 
			value = "SELECT account_name FROM account WHERE account_no = ?1")
	String getAccountName(Long accountNo);
}
