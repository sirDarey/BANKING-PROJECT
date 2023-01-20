package sirdarey.TransactionsLayer.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sirdarey.TransactionsLayer.AirtimeRequestDTO;
import sirdarey.TransactionsLayer.GenericResponse;
import sirdarey.TransactionsLayer.TransactionDetails;
import sirdarey.TransactionsLayer.TransactionsResponseDTO;
import sirdarey.repo.AccountRepo;
import sirdarey.utils.Utils;

@Service
@Transactional(rollbackFor = Exception.class)
public class AirtimeRequestService {

	@Autowired private AccountRepo accountRepo;
	@Autowired private Utils utils;
	
	public ResponseEntity<TransactionsResponseDTO> requestAirtime(AirtimeRequestDTO airAirtimeRequest) throws Exception {
		
		Long accountNo = airAirtimeRequest.getAccountNo();
		Double transactionAmount = airAirtimeRequest.getTransactionAmount();
		Long entryPhoneNo = airAirtimeRequest.getPhoneNo();
		Integer transactionPIN = airAirtimeRequest.getTransactionPIN();
		
		GenericResponse requiredAccountDetails = accountRepo.getDetailsForAirtimeRequest(accountNo);
		
		if (requiredAccountDetails == null) 
			return ResponseEntity.status(200).body(new TransactionsResponseDTO("Account NOT FOUND", null));
		
		if(!entryPhoneNo.equals(requiredAccountDetails.getPhoneNo())) {
			
			if (transactionPIN == null)
				return ResponseEntity.status(200)
						.body(new TransactionsResponseDTO("Transaction PIN CANNOT be EMPTY for a third party Phone No",  null));
			
			if (!transactionPIN.equals(requiredAccountDetails.getTransactionPIN())) 
				return ResponseEntity.status(200)
						.body(new TransactionsResponseDTO("Transaction Failed, INCORRECT PIN",  null));
		}  

		Double newBalance = requiredAccountDetails.getBalance() - transactionAmount;
		if (newBalance < 0.0) 
			return ResponseEntity.status(200)
					.body(new TransactionsResponseDTO("Transaction Failed, INSUFFICIENT FUNDS", null));
			
		accountRepo.updateAccountBalance(newBalance, accountNo);
		TransactionsResponseDTO response = new TransactionsResponseDTO (
				"AIRTIME RECHARGE SUCCESSFUL",
				
				new TransactionDetails(
					accountNo,
					requiredAccountDetails.getAccountName(),
					"DEBIT",
					transactionAmount,
					"AIRTIME RECHARGE - "+requiredAccountDetails.getPhoneNo(),
					newBalance,
					new Date(System.currentTimeMillis()))				
				);
		
		HttpStatus savedStatus = utils.saveTransaction(response);
		
		if (savedStatus.equals(HttpStatus.CREATED))
			return ResponseEntity.status(201).body(response);
		else
			throw new Exception("UNABLE TO SAVE TRANSACTION");
		
	}
	
}
