package sirdarey.TransactionsLayer.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sirdarey.TransactionsLayer.BalanceRequestDTO;
import sirdarey.TransactionsLayer.GenericResponse;
import sirdarey.TransactionsLayer.TransactionDetails;
import sirdarey.TransactionsLayer.TransactionsResponseDTO;

import sirdarey.repo.AccountRepo;

@Service
@Transactional
public class BalanceRequestService {

	@Autowired private AccountRepo accountRepo;
	
	
	public ResponseEntity<TransactionsResponseDTO> checkAccountBalance(BalanceRequestDTO balanceRequest) {
		
		Long accountNo = balanceRequest.getAccountNo();
		Integer transactionPIN = balanceRequest.getTransactionPIN();
		
		GenericResponse requiredAccountDetails = accountRepo.getDetailsForBalanceCheck(accountNo);		
		
		if (requiredAccountDetails == null) 
			return ResponseEntity.status(200).body(new TransactionsResponseDTO("ERROR: ACCOUNT NOT FOUND", null));
		
		
		if (!transactionPIN.equals(requiredAccountDetails.getTransactionPIN())) 
			return ResponseEntity.status(200).body(new TransactionsResponseDTO("ERROR: INCORRECT PIN", null));
		
		Double newBalance = requiredAccountDetails.getBalance() - 5.0;
		if (newBalance < 0.0) 
			return ResponseEntity.status(200).body(new TransactionsResponseDTO("ERROR: INSUFFICIENT FUNDS", null));
		
		accountRepo.updateAccountBalance(newBalance, accountNo);
		TransactionsResponseDTO response = new TransactionsResponseDTO (
				"BALANCE ENQUIRY SUCCESSFUL",
				
				new TransactionDetails(
				accountNo,
				requiredAccountDetails.getAccountName(),
				"DEBIT",
				5.0,
				"BALANCE ENQUIRY",
				newBalance,
				new Date(System.currentTimeMillis()))				
				);
		
		return ResponseEntity.status(201).body(response);
	}

}
