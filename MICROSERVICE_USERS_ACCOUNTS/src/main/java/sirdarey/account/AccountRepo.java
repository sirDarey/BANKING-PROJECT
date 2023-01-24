package sirdarey.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import sirdarey.transactions.dto.GenericResponseDTO;

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
	void updateAccountBalance(Double finalBalance, Long accountNo);
	

	@Modifying
	@Query(nativeQuery = true, 
			value = "UPDATE account SET acc_locked = ?1 WHERE account_no = ?2")
	int updateAccountLockedStatus(Byte setStatus, Long accountNo);

	@Query(nativeQuery = true, 
			value = "SELECT account_name FROM account WHERE account_no = ?1")
	String getAccountName(Long accountNo);
	
	

	/***************** QUERIES FOR TRANSACTION LAYER ********************************/
	
	@Query (value = "select new sirdarey.transactions.dto.GenericResponseDTO"
			+ "(a.accountName, a.balance, a.transactionPIN, a.phoneNo) "
			+ "from Account a where a.accountNo=?1")
	GenericResponseDTO getDetailsForAirtimeRequest(Long accountNo);

	
	@Query (value = "select new sirdarey.transactions.dto.GenericResponseDTO"
			+ "(a.accountName, a.balance, a.transactionPIN) "
			+ "from Account a where a.accountNo=?1")
	GenericResponseDTO getDetailsForBalanceCheck(Long accountNo);

	
	@Query (value = "select new sirdarey.transactions.dto.GenericResponseDTO"
			+ "(a.balance, a.accountName) from Account a where a.accountNo=?1")
	GenericResponseDTO getDetailsForDeposit(Long accountNo);


	@Query (value = "select new sirdarey.transactions.dto.GenericResponseDTO"
			+ "(a.accountName, a.balance, a.transactionPIN) "
			+ "from Account a where a.accountNo=?1")
	GenericResponseDTO getSenderDetailsForTransfer(Long senderAccountNo);

	
	@Query (value = "select new sirdarey.transactions.dto.GenericResponseDTO"
			+ "(a.balance, a.accountName) from Account a where a.accountNo=?1")
	GenericResponseDTO getReceiverDetailsForTransfer(Long receiverAccountNo);

	@Query (value = "select new sirdarey.transactions.dto.GenericResponseDTO"
			+ "(a.accountType, a.balance) from Account a where a.accountNo=?1")
	GenericResponseDTO getAccountDetailsForWithdrawal(Long fk_account_no);
}
