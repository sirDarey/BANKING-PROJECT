package sirdarey.TransactionsLayer.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sirdarey.TransactionsLayer.AirtimeRequestDTO;
import sirdarey.TransactionsLayer.GenericResponse;
import sirdarey.TransactionsLayer.TransactionDetails;
import sirdarey.TransactionsLayer.TransactionsResponseDTO;
import sirdarey.repo.AccountRepo;

@Service
@Transactional
public class AirtimeRequestService {

	@Autowired private AccountRepo accountRepo;
	
	
	public ResponseEntity<TransactionsResponseDTO> requestAirtime(AirtimeRequestDTO airAirtimeRequest) {
		
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
		
		return ResponseEntity.status(201).body(response);
	}

}
