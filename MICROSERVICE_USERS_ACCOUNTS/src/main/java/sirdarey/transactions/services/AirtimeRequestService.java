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
import sirdarey.transactions.dto.AirtimeRequestDTO;
import sirdarey.transactions.dto.GenericResponseDTO;
import sirdarey.transactions.dto.TransactionDetailsDTO;
import sirdarey.transactions.dto.TransactionsResponseDTO;
import sirdarey.utils.Utils;

@Service
@Transactional(rollbackFor = CustomExceptions.class)
public class AirtimeRequestService {

	@Autowired private AccountRepo accountRepo;
	@Autowired private Utils utils;
	@Autowired private HttpServletResponse httpServletResponse;
	
	public ResponseEntity<TransactionsResponseDTO> requestAirtime(AirtimeRequestDTO airtimeRequest) throws CustomExceptions, IOException {
			
		Long accountNo = airtimeRequest.getAccountNo();
		Double transactionAmount = airtimeRequest.getTransactionAmount();
		Long entryPhoneNo = airtimeRequest.getPhoneNo();
		Integer transactionPIN = airtimeRequest.getTransactionPIN();
		
		if (accountNo == null || entryPhoneNo == null
				||  transactionAmount == null) {
			
			return ResponseEntity.status(400).body(new TransactionsResponseDTO("Enter All Required Fields "
					+ "'accountNo', 'phoneNo', 'transactionAmount'", null));
		}
		
		GenericResponseDTO requiredAccountDetails = accountRepo.getDetailsForAirtimeRequest(accountNo);
		
		if (requiredAccountDetails == null) 
			return ResponseEntity.status(404).body(new TransactionsResponseDTO("Account NOT FOUND", null));
		
		if(!entryPhoneNo.equals(requiredAccountDetails.getPhoneNo())) {
			
			if (transactionPIN == null)
				return ResponseEntity.status(400)
						.body(new TransactionsResponseDTO("Transaction PIN CANNOT be EMPTY for a third party Phone No",  null));
			
			if (!transactionPIN.equals(requiredAccountDetails.getTransactionPIN())) 
				return ResponseEntity.status(401)
						.body(new TransactionsResponseDTO("Transaction Failed, INCORRECT PIN",  null));
		}  

		Double newBalance = requiredAccountDetails.getBalance() - transactionAmount;
		if (newBalance < 0.0) 
			return ResponseEntity.status(409)
					.body(new TransactionsResponseDTO("Transaction Failed, INSUFFICIENT FUNDS", null));
			
		accountRepo.updateAccountBalance(newBalance, accountNo);
		TransactionsResponseDTO response = new TransactionsResponseDTO (
				"AIRTIME RECHARGE SUCCESSFUL",
				
				new TransactionDetailsDTO(
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
			return ResponseEntity.status(200).body(response); 
		else
			throw new CustomExceptions(409, "UNABLE TO PROCESS AIRTIME REQUEST", httpServletResponse);
		
	}
	
}
