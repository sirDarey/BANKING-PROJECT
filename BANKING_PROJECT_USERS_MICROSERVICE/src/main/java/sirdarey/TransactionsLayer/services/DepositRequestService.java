package sirdarey.TransactionsLayer.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sirdarey.TransactionsLayer.DepositRequestDTO;
import sirdarey.TransactionsLayer.GenericResponse;
import sirdarey.TransactionsLayer.TransactionDetails;
import sirdarey.TransactionsLayer.TransactionsRepo;
import sirdarey.TransactionsLayer.TransactionsResponseDTO;
import sirdarey.repo.AccountRepo;

@Service
@Transactional
public class DepositRequestService {

	@Autowired private TransactionsRepo transactionsRepo;
	@Autowired private AccountRepo accountRepo;
	
	
	public ResponseEntity<TransactionsResponseDTO> deposit (DepositRequestDTO depositRequest) {
		
		Long accountNo = depositRequest.getAccountNo();
		String accounName = depositRequest.getAccountName();
		Double depositAmount = depositRequest.getTransactionAmount();
		
		GenericResponse requiredAccountDetails = transactionsRepo.getDetailsForDeposit(accountNo);		
		
		if (requiredAccountDetails == null) 
			return ResponseEntity.status(200).body(new TransactionsResponseDTO("ERROR: ACCOUNT NOT FOUND", null));
		
		
		if (!accounName.equals(requiredAccountDetails.getAccountName())) 
			return ResponseEntity.status(200).body(new TransactionsResponseDTO("ERROR: INCORRECT ACCOUNT NAME", null));
		
		Double newBalance = requiredAccountDetails.getBalance() + depositAmount;
		
		accountRepo.updateAccountBalance(newBalance, accountNo);
		TransactionsResponseDTO response = new TransactionsResponseDTO (
				"DEPOSIT SUCCESSFUL",
				
				new TransactionDetails(
				accountNo,
				requiredAccountDetails.getAccountName(),
				"CREDIT",
				depositAmount,
				"DEPOSIT",
				newBalance,
				new Date(System.currentTimeMillis()))
				);
		
		return ResponseEntity.status(201).body(response);
	}

}
