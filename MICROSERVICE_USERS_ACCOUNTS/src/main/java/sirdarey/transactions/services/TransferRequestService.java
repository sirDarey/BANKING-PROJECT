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
import sirdarey.transactions.dto.GenericResponseDTO;
import sirdarey.transactions.dto.TransactionDetailsDTO;
import sirdarey.transactions.dto.TransactionsResponseDTO;
import sirdarey.transactions.dto.TransferRequestDTO;
import sirdarey.utils.Utils;

@Service
@Transactional(rollbackFor = CustomExceptions.class)
public class TransferRequestService {

	@Autowired private AccountRepo accountRepo;
	@Autowired private Utils utils;
	@Autowired private HttpServletResponse httpServletResponse;
	
	
	public ResponseEntity<TransactionsResponseDTO> transfer(TransferRequestDTO transferRequest) throws CustomExceptions, IOException {

		Long senderAccountNo = transferRequest.getSenderAccountNo();
		Long receiverAccountNo = transferRequest.getReceiverAccountNo();
		String receiverAccountName = transferRequest.getReceiverAccountName();
		Double transferAmount = transferRequest.getAmount();
		Integer transactionPIN = transferRequest.getTransactionPIN();
		
		TransactionsResponseDTO [] response = new TransactionsResponseDTO [2];
		
		if (senderAccountNo == null || receiverAccountNo == null || receiverAccountName == null
				|| transferAmount == null || transactionPIN == null) {	
			
			response[0] = new TransactionsResponseDTO ("Enter All Required Fields: 'senderAccountNo', 'receiverAccountNo',"
					+ " 'receiverAccountName', 'amount', 'transactionPIN'", null);
			
			return ResponseEntity.status(400).body(response[0]);
		}
		
		GenericResponseDTO senderAccountDetails = accountRepo.getSenderDetailsForTransfer(senderAccountNo);
		GenericResponseDTO receiverAccountDetails = accountRepo.getReceiverDetailsForTransfer(receiverAccountNo);	
		
		
		
		if (senderAccountDetails == null) {
			response[0] = new TransactionsResponseDTO ("ERROR: YOUR ACCOUNT DOESN'T EXIST", null);
			return ResponseEntity.status(404).body(response[0]);
		}
		
		if (receiverAccountDetails == null || !receiverAccountName.equals(receiverAccountDetails.getAccountName())) {
			response[0] = new TransactionsResponseDTO ("ERROR: UNABLE TO VERIFY RECEIVER'S ACCOUNT", null);
			return ResponseEntity.status(409).body(response[0]);
		}
		
		if (!transactionPIN.equals(senderAccountDetails.getTransactionPIN())) {
			response[0] = new TransactionsResponseDTO ("ERROR: INCORRECT PIN", null);
			return ResponseEntity.status(401).body(response[0]);
		}
		
		Double senderNewBalance = senderAccountDetails.getBalance() - transferAmount;
		if (senderNewBalance < 0.0)  {
			response[0] = new TransactionsResponseDTO ("Transaction Failed, INSUFFICIENT FUNDS", null);
			return ResponseEntity.status(409).body(response[0]);
		}
		
		Double receiverNewBalance = receiverAccountDetails.getBalance() + transferAmount;
		
		accountRepo.updateAccountBalance(senderNewBalance, senderAccountNo);
		accountRepo.updateAccountBalance(receiverNewBalance, receiverAccountNo); 
		
		response [0] = new TransactionsResponseDTO (
				"TRANSFER SUCCESSFUL",
				
				new TransactionDetailsDTO(
						senderAccountNo,
						senderAccountDetails.getAccountName(),
						"DEBIT",
						transferAmount,
						"TRANSFER TO "+receiverAccountNo+ "_" +receiverAccountDetails.getAccountName(),
						senderNewBalance,
						new Date(System.currentTimeMillis()))
				);
		
		response [1] = new TransactionsResponseDTO (
				null,				
				new TransactionDetailsDTO(
						receiverAccountNo,
						receiverAccountName,
						"CREDIT",
						transferAmount,
						"TRANSFER FROM "+senderAccountDetails.getAccountName(),
						receiverNewBalance,
						new Date(System.currentTimeMillis()))
				);
		
		HttpStatus savedStatus = utils.saveTransaction(response);
		
		if (savedStatus.equals(HttpStatus.CREATED))
			return ResponseEntity.status(200).body(response[0]); 
		else
			throw new CustomExceptions(409, "TRANSFER TRANSACTION DECLINED", httpServletResponse);
	}

}
