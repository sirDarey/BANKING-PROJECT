package sirdarey.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sirdarey.dto.TransactionsHistory;
import sirdarey.entity.TransactionEntity;

public interface TransactionRepo extends JpaRepository<TransactionEntity, Long>{

	@Query (value = "select new sirdarey.dto.TransactionsHistory"
			+ "(t.transactionType, t.transactionAmount, t.transactionDesc, "
			+ "t.balance, t.transactionDateAndTime) from TransactionEntity t where t.accountNo=?1 and "
			+ "t.transactionDateAndTime between ?2 and ?3 order by t.transactionDateAndTime desc")
	List<TransactionsHistory> getAllTransactions(Long accountNo, Date startDate, Date endDate);

	
	@Query(nativeQuery = true,
			value = "SELECT account_name FROM transaction_entity WHERE account_no = ?1 LIMIT 1")
	String getAccountName(Long accountNo);

}
