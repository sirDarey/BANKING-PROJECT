package sirdarey.TransactionsLayer.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sirdarey.TransactionsLayer.GenericResponse;
import sirdarey.TransactionsLayer.TransactionDetails;
import sirdarey.TransactionsLayer.TransactionsResponseDTO;
import sirdarey.TransactionsLayer.WithdrawalRequestDTO;
import sirdarey.repo.AccountRepo;
import sirdarey.repo.CardRepo;

@Service
@Transactional
public class WithdrawalRequestService {

	@Autowired private AccountRepo accountRepo;
	@Autowired private CardRepo cardRepo;
	

	public ResponseEntity<TransactionsResponseDTO> withdrawal(WithdrawalRequestDTO withdrawalRequest) {
		Long cardNo = withdrawalRequest.getCardNo();
		Integer cardPIN = withdrawalRequest.getCardPIN();
		Double withdrawalAmount = withdrawalRequest.getWithdrawalAmount();
		String merchant = withdrawalRequest.getMerchant();
		String accountType = withdrawalRequest.getAccountType();
		
		//cardPIN, cardHolder, isExpired, isBlocked
		GenericResponse requiredCardDetails = cardRepo.getCardDetailsForWithdrawal(cardNo);	
		
		if (requiredCardDetails == null)
			return ResponseEntity.status(200)
					.body(new TransactionsResponseDTO("UNABLE TO VERIFY ACCOUNT", null));
		
		if (!requiredCardDetails.getCardPIN().equals(cardPIN))
			return ResponseEntity.status(200)
					.body(new TransactionsResponseDTO("INCORRECT PIN", null));
		
		if (requiredCardDetails.getIsBlocked() == 1 || requiredCardDetails.getIsExpired() == 1)
			return ResponseEntity.status(200)
					.body(new TransactionsResponseDTO("INVALID CARD", null));
		
		
		//balance, accountType
		Long accountNo = requiredCardDetails.getFk_account_no();
		GenericResponse requiredAccountDetails = 
				accountRepo.getAccountDetailsForWithdrawal(accountNo);
		
		//THIS IS WRONG THOUGH, YOU CAN'T BE GIVING SPECIFIC ERROR RESPONSE FOR EACH WRONG ENTRY BY A USER
		if (!requiredAccountDetails.getAccountType().equals(accountType))
			return ResponseEntity.status(200)
					.body(new TransactionsResponseDTO("ACCOUNT TYPE MISMATCH", null));
		
		Double newBalance = requiredAccountDetails.getBalance() - withdrawalAmount;
		if (newBalance < 0.0)
			return ResponseEntity.status(200)
					.body(new TransactionsResponseDTO("INSUFFICIENT FUNDS", null));
		
		accountRepo.updateAccountBalance(newBalance, accountNo);
		TransactionsResponseDTO response = new TransactionsResponseDTO (
				"WITHDRAWAL SUCCESSFUL",
				
				new TransactionDetails(
				accountNo,
				requiredCardDetails.getCardHolder(),
				"DEBIT",
				withdrawalAmount,
				"CASH WITHDRAWAL VIA "+merchant,
				newBalance,
				new Date(System.currentTimeMillis()))
				);
		
		return ResponseEntity.status(201).body(response);
	}

}
