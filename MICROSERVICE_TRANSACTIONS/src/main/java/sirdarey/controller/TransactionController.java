package sirdarey.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sirdarey.dto.TransactionsHistoryRequestDTO;
import sirdarey.dto.TransactionsHistoryResponseDTO;
import sirdarey.dto.TransactionsResponseDTO;
import sirdarey.exceptions.CustomExceptions;
import sirdarey.service.TransactionService;

@RestController
@RequestMapping("/bank/transactions")
public class TransactionController {

	@Autowired private TransactionService transactionService;
	
		
	//REQUEST TRANSACTION HISTORY

	@PostMapping ("/history")
	public ResponseEntity<TransactionsHistoryResponseDTO> 
	transactionHistory (@RequestBody TransactionsHistoryRequestDTO request,
			@RequestParam(defaultValue = "2") int limit, @RequestParam(defaultValue = "2") int page) throws CustomExceptions {
		
		Long accountNo = request.getAccountNo();
		Date startDate = request.getStartDate();
		Date endDate = request.getEndDate();
		/*****TO BE TRANSFERRED TO USER CONSUMER *********/
		if (accountNo == null || startDate == null)	{
			
			return ResponseEntity.badRequest()
					.body(new TransactionsHistoryResponseDTO(
							"Enter All Required Fields: 'accountNo', 'startDate', 'endDate(Optional)'",
							null, null
						));
		}
		/*******END OT TRANSFER*******/
		
		String accountName = transactionService.getAccountName(accountNo);
		if (accountName == null) {
			return ResponseEntity.status(404)
					.body(new TransactionsHistoryResponseDTO(
							"ERROR: ACCOUNT DOESN'T EXIST",
							null, null
						));
		}
		
		if (endDate == null)
			endDate =  new Date(System.currentTimeMillis());
		
		if (startDate.after(endDate)) {
			return ResponseEntity.badRequest()
					.body(new TransactionsHistoryResponseDTO(
							"START Date must PRECEDE END Date",
							null, null
						));
		}
		
		ResponseEntity<TransactionsHistoryResponseDTO> response;
		try {
			response =  transactionService.getAllTransactions(accountNo, startDate, endDate, accountName,
					limit, page);
		} catch (Exception e) {
			throw new CustomExceptions(e.getMessage());
		}
		
		return response;
		
	}

	//SAVE TRANSFER TRANSACTION (BI-DIRECTIONAL)

	@PostMapping ("/savetransfer")
	@Transactional
	public HttpStatus saveTransfer (@RequestBody TransactionsResponseDTO [] response) {
	
		try {
			transactionService.saveNewTransaction(response[0]);
			transactionService.saveNewTransaction(response[1]); 
			return HttpStatus.CREATED;
			
		} catch (Exception e) {
			e.printStackTrace();
			return HttpStatus.EXPECTATION_FAILED;
		}
		
	}
	
	//SAVE OTHER TRANSACTIONS (UNI-DIRECTIONAL)
	
	@PostMapping("/save") 
	public HttpStatus saveNewTransaction (@RequestBody TransactionsResponseDTO response) {
		
		try {
			transactionService.saveNewTransaction(response); 
			return HttpStatus.CREATED;
		} catch (Exception e) {
			e.printStackTrace();
			return HttpStatus.EXPECTATION_FAILED;
		}
	}
		
}
