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
import sirdarey.card.CardRepo;
import sirdarey.exceptions.CustomExceptions;
import sirdarey.transactions.dto.GenericResponseDTO;
import sirdarey.transactions.dto.TransactionDetailsDTO;
import sirdarey.transactions.dto.TransactionsResponseDTO;
import sirdarey.transactions.dto.WithdrawalRequestDTO;
import sirdarey.utils.Utils;

@Service
@Transactional(rollbackFor = CustomExceptions.class)
public class WithdrawalRequestService {

	@Autowired private AccountRepo accountRepo;
	@Autowired private CardRepo cardRepo;
	@Autowired private Utils utils;
	@Autowired private HttpServletResponse httpServletResponse;
	

	public ResponseEntity<TransactionsResponseDTO> withdrawal(WithdrawalRequestDTO withdrawalRequest) throws CustomExceptions, IOException {
		Long cardNo = withdrawalRequest.getCardNo();
		Integer cardPIN = withdrawalRequest.getCardPIN();
		Double withdrawalAmount = withdrawalRequest.getWithdrawalAmount();
		String merchant = withdrawalRequest.getMerchant();
		String accountType = withdrawalRequest.getAccountType();
		
		if (cardNo == null || withdrawalAmount == null || cardPIN == null 
				|| merchant == null || accountType == null) {	
			return ResponseEntity.status(400).body(new TransactionsResponseDTO("Enter All Required Fields "
					+ "'cardNo', 'cardPIN', 'withdrawalAmount', 'merchant', 'accountType'", null));
		}
		
		GenericResponseDTO requiredCardDetails = cardRepo.getCardDetailsForWithdrawal(cardNo);	
		
		if (requiredCardDetails == null)
			return ResponseEntity.status(409)
					.body(new TransactionsResponseDTO("UNABLE TO VERIFY ACCOUNT", null));
		
		if (!requiredCardDetails.getCardPIN().equals(cardPIN))
			return ResponseEntity.status(401)
					.body(new TransactionsResponseDTO("INCORRECT PIN", null));
		
		if (requiredCardDetails.getIsBlocked() == 1 || requiredCardDetails.getIsExpired() == 1)
			return ResponseEntity.status(403)
					.body(new TransactionsResponseDTO("INVALID CARD", null));
		
		
		//balance, accountType
		Long accountNo = requiredCardDetails.getFk_account_no();
		GenericResponseDTO requiredAccountDetails = 
				accountRepo.getAccountDetailsForWithdrawal(accountNo);
		
		//THIS IS WRONG THOUGH, YOU CAN'T BE GIVING SPECIFIC ERROR RESPONSE FOR EACH WRONG ENTRY BY A USER
		if (!requiredAccountDetails.getAccountType().equals(accountType))
			return ResponseEntity.status(400)
					.body(new TransactionsResponseDTO("ACCOUNT TYPE MISMATCH", null));
		
		Double newBalance = requiredAccountDetails.getBalance() - withdrawalAmount;
		if (newBalance < 0.0)
			return ResponseEntity.status(409)
					.body(new TransactionsResponseDTO("INSUFFICIENT FUNDS", null));
		
		accountRepo.updateAccountBalance(newBalance, accountNo);
		TransactionsResponseDTO response = new TransactionsResponseDTO (
				"WITHDRAWAL SUCCESSFUL",
				
				new TransactionDetailsDTO(
				accountNo,
				requiredCardDetails.getCardHolder(),
				"DEBIT",
				withdrawalAmount,
				"CASH WITHDRAWAL VIA "+merchant,
				newBalance,
				new Date(System.currentTimeMillis()))
				);
		
		HttpStatus savedStatus = utils.saveTransaction(response);
		
		if (savedStatus.equals(HttpStatus.CREATED))
			return ResponseEntity.status(200).body(response); 
		else
			throw new CustomExceptions(409, "WITHDRAWAL TRANSACTION DECLINED", httpServletResponse);
	}

}
