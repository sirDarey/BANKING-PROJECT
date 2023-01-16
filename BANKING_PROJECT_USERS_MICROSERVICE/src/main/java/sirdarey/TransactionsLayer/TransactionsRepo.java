package sirdarey.TransactionsLayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sirdarey.entity.Account;

public interface TransactionsRepo extends JpaRepository<Account, Long>{

	@Query (value = "select new sirdarey.TransactionsLayer.GenericResponse"
			+ "(a.accountName, a.balance, a.transactionPIN, a.phoneNo) "
			+ "from Account a where a.accountNo=?1")
	GenericResponse getDetailsForAirtimeRequest(Long accountNo);

	
	@Query (value = "select new sirdarey.TransactionsLayer.GenericResponse"
			+ "(a.accountName, a.balance, a.transactionPIN) "
			+ "from Account a where a.accountNo=?1")
	GenericResponse getDetailsForBalanceCheck(Long accountNo);

	
	@Query (value = "select new sirdarey.TransactionsLayer.GenericResponse"
			+ "(a.balance, a.accountName) from Account a where a.accountNo=?1")
	GenericResponse getDetailsForDeposit(Long accountNo);

}
