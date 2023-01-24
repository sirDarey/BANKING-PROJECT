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
import sirdarey.transactions.dto.DepositRequestDTO;
import sirdarey.transactions.dto.GenericResponseDTO;
import sirdarey.transactions.dto.TransactionDetailsDTO;
import sirdarey.transactions.dto.TransactionsResponseDTO;
import sirdarey.utils.Utils;

@Service
@Transactional(rollbackFor = CustomExceptions.class)
public class DepositRequestService {

	@Autowired private AccountRepo accountRepo;
	@Autowired private Utils utils;
	@Autowired private HttpServletResponse httpServletResponse;
	
	
	public ResponseEntity<TransactionsResponseDTO> deposit (DepositRequestDTO depositRequest) throws CustomExceptions, IOException {
		
		Long accountNo = depositRequest.getAccountNo();
		String accountName = depositRequest.getAccountName();
		Double depositAmount = depositRequest.getTransactionAmount();
		
		if (accountNo == null || accountName == null || depositAmount == null) {	
			return ResponseEntity.status(400).body(new TransactionsResponseDTO("Enter All Required Fields "
					+ "'accountNo', 'accountName', 'transactionAmount'", null));
		}
		
		GenericResponseDTO requiredAccountDetails = accountRepo.getDetailsForDeposit(accountNo);		
		
		if (requiredAccountDetails == null) 
			return ResponseEntity.status(404).body(new TransactionsResponseDTO("ERROR: ACCOUNT NOT FOUND", null));
		
		
		if (!accountName.equals(requiredAccountDetails.getAccountName())) 
			return ResponseEntity.status(400).body(new TransactionsResponseDTO("ERROR: INCORRECT ACCOUNT NAME", null));
		
		Double newBalance = requiredAccountDetails.getBalance() + depositAmount;
		
		accountRepo.updateAccountBalance(newBalance, accountNo);
		TransactionsResponseDTO response = new TransactionsResponseDTO (
				"DEPOSIT SUCCESSFUL",
				
				new TransactionDetailsDTO(
				accountNo,
				requiredAccountDetails.getAccountName(),
				"CREDIT",
				depositAmount,
				"DEPOSIT",
				newBalance,
				new Date(System.currentTimeMillis()))
				);
		
		HttpStatus savedStatus = utils.saveTransaction(response);
		
		if (savedStatus.equals(HttpStatus.CREATED))
			return ResponseEntity.status(200).body(response); 
		else
			throw new CustomExceptions(409, "UNABLE TO PROCESS DEPOSIT TRANSACTION", httpServletResponse);
	}

}
