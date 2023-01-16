package sirdarey.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sirdarey.dto.AccountHeader;
import sirdarey.dto.TransactionDetails;
import sirdarey.dto.TransactionsHistory;
import sirdarey.dto.TransactionsHistoryResponseDTO;
import sirdarey.dto.TransactionsResponseDTO;
import sirdarey.entity.TransactionEntity;
import sirdarey.repo.TransactionRepo;
import sirdarey.utils.Utils;

@Service
public class TransactionService {

	@Autowired private TransactionRepo transactionRepo;
	@Autowired private Utils utils;
	
	public void saveNewTransaction (TransactionsResponseDTO response) {
		
		TransactionDetails transactionDetails = response.getTransactionDetails();
		
		TransactionEntity newTransaction = new TransactionEntity(
				utils.generateRandom(7), 
				transactionDetails.getAccountNo(), 
				transactionDetails.getAccountName(), 
				transactionDetails.getTransactionType(), 
				transactionDetails.getTransactionAmount(), 
				transactionDetails.getTransactionDesc(), 
				transactionDetails.getBalance(), 
				transactionDetails.getTransactionDateAndTime());
		
		transactionRepo.save(newTransaction);	
	}

	
	
	public ResponseEntity<TransactionsHistoryResponseDTO> 
		getAllTransactions(Long accountNo, Date startDate, Date endDate, String accountName) {
		
		String getEndDate = endDate.toString();
		endDate.setTime(endDate.getTime()+ (1000*60*60*24));
		
		List<TransactionsHistory> transanctionsList = 
				transactionRepo.getAllTransactions(accountNo, startDate, endDate);
		
		AccountHeader accountHeader = new AccountHeader(accountNo, accountName, 
						startDate.toString() +" -> "+ getEndDate);
		
		TransactionsHistoryResponseDTO response = 
				new TransactionsHistoryResponseDTO("SUCCESS", accountHeader, transanctionsList);
		return ResponseEntity.ok().body(response);
	}


	public String getAccountName(Long accountNo) {
		return transactionRepo.getAccountName(accountNo);
	}

}
