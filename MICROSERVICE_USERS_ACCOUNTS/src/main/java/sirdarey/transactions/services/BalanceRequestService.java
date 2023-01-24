package sirdarey.transactions.services;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletResponse;
import sirdarey.account.AccountRepo;
import sirdarey.exceptions.CustomExceptions;
import sirdarey.transactions.dto.BalanceRequestDTO;
import sirdarey.transactions.dto.GenericResponseDTO;
import sirdarey.transactions.dto.TransactionDetailsDTO;
import sirdarey.transactions.dto.TransactionsResponseDTO;
import sirdarey.utils.Utils;

@Service
@Transactional(rollbackFor = CustomExceptions.class)
public class BalanceRequestService {

	@Autowired private AccountRepo accountRepo;
	@Autowired private Utils utils;
	@Autowired private HttpServletResponse httpServletResponse;
	
	
	public ResponseEntity<TransactionsResponseDTO> checkAccountBalance(BalanceRequestDTO balanceRequest) throws CustomExceptions, IOException {
		
		Long accountNo = balanceRequest.getAccountNo();
		Integer transactionPIN = balanceRequest.getTransactionPIN();
		
		if (accountNo == null || transactionPIN == null) {			
			return ResponseEntity.status(400).body(new TransactionsResponseDTO("Enter All Required Fields "
					+ "'accountNo', 'transactionPIN'", null));
		}
		
		GenericResponseDTO requiredAccountDetails = accountRepo.getDetailsForBalanceCheck(accountNo);		
		
		if (requiredAccountDetails == null) 
			return ResponseEntity.status(404).body(new TransactionsResponseDTO("ERROR: ACCOUNT NOT FOUND", null));
		
		
		if (!transactionPIN.equals(requiredAccountDetails.getTransactionPIN())) 
			return ResponseEntity.status(401).body(new TransactionsResponseDTO("ERROR: INCORRECT PIN", null));
		
		Double newBalance = requiredAccountDetails.getBalance() - 5.0;
		if (newBalance < 0.0) 
			return ResponseEntity.status(409).body(new TransactionsResponseDTO("ERROR: INSUFFICIENT FUNDS", null));
		
		accountRepo.updateAccountBalance(newBalance, accountNo);
		TransactionsResponseDTO response = new TransactionsResponseDTO (
				"BALANCE ENQUIRY SUCCESSFUL",
				
				new TransactionDetailsDTO(
				accountNo,
				requiredAccountDetails.getAccountName(),
				"DEBIT",
				5.0,
				"BALANCE ENQUIRY",
				newBalance,
				new Date(System.currentTimeMillis()))				
				);
		
		HttpStatus savedStatus = utils.saveTransaction(response);
		
		if (savedStatus.equals(HttpStatus.CREATED))
			return ResponseEntity.status(200).body(response); 
		else
			throw new CustomExceptions(409, "UNABLE TO PROCESS BALANCE ENQUIRY", httpServletResponse);
	}

}
