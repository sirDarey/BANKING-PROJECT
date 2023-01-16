package sirdarey.TransactionsLayer.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sirdarey.TransactionsLayer.GenericResponse;
import sirdarey.TransactionsLayer.TransactionDetails;
import sirdarey.TransactionsLayer.TransactionsResponseDTO;
import sirdarey.TransactionsLayer.TransferRequestDTO;
import sirdarey.repo.AccountRepo;

@Service
@Transactional
public class TransferRequestService {

	@Autowired private AccountRepo accountRepo;
	
	
	public ResponseEntity<TransactionsResponseDTO []> transfer(TransferRequestDTO transferRequest) {

		Long senderAccountNo = transferRequest.getSenderAccountNo();
		Long receiverAccountNo = transferRequest.getReceiverAccountNo();
		String receiverAccountName = transferRequest.getReceiverAccountName();
		Double transferAmount = transferRequest.getAmount();
		Integer transactionPIN = transferRequest.getTransactionPIN();
		
		GenericResponse senderAccountDetails = accountRepo.getSenderDetailsForTransfer(senderAccountNo);
		GenericResponse receiverAccountDetails = accountRepo.getReceiverDetailsForTransfer(receiverAccountNo);	
		
		TransactionsResponseDTO [] response = new TransactionsResponseDTO [2];
		
		if (senderAccountDetails == null) {
			response[0] = new TransactionsResponseDTO ("ERROR: YOUR ACCOUNT DOESN'T EXIST", null);
			return ResponseEntity.status(200).body(response);
		}
		
		if (receiverAccountDetails == null || !receiverAccountName.equals(receiverAccountDetails.getAccountName())) {
			response[0] = new TransactionsResponseDTO ("ERROR: UNABLE TO VERIFY RECEIVER'S ACCOUNT", null);
			return ResponseEntity.status(200).body(response);
		}
		
		if (!transactionPIN.equals(senderAccountDetails.getTransactionPIN())) {
			response[0] = new TransactionsResponseDTO ("ERROR: INCORRECT PIN", null);
			return ResponseEntity.status(200).body(response);
		}
		
		Double senderNewBalance = senderAccountDetails.getBalance() - transferAmount;
		if (senderNewBalance < 0.0)  {
			response[0] = new TransactionsResponseDTO ("Transaction Failed, INSUFFICIENT FUNDS", null);
			return ResponseEntity.status(200).body(response);
		}
		
		Double receiverNewBalance = receiverAccountDetails.getBalance() + transferAmount;
		
		accountRepo.updateAccountBalance(senderNewBalance, senderAccountNo);
		accountRepo.updateAccountBalance(receiverNewBalance, receiverAccountNo); 
		
		response [0] = new TransactionsResponseDTO (
				"TRANSFER SUCCESSFUL",
				
				new TransactionDetails(
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
				new TransactionDetails(
						receiverAccountNo,
						receiverAccountName,
						"CREDIT",
						transferAmount,
						"TRANSFER FROM "+senderAccountDetails.getAccountName(),
						receiverNewBalance,
						new Date(System.currentTimeMillis()))
				);
		
		return ResponseEntity.status(201).body(response);
	}

}
