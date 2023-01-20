package sirdarey.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import sirdarey.dto.AirtimeRequestDTO;
import sirdarey.dto.BalanceRequestDTO;
import sirdarey.dto.DepositRequestDTO;
import sirdarey.dto.TransactionsHistoryRequestDTO;
import sirdarey.dto.TransactionsHistoryResponseDTO;
import sirdarey.dto.TransactionsResponseDTO;
import sirdarey.dto.TransferRequestDTO;
import sirdarey.dto.WithdrawalRequestDTO;
import sirdarey.exceptions.CustomExceptions;
import sirdarey.service.TransactionService;

@RestController
@RequestMapping("/bank/transactions")
public class TransactionController {

	@Autowired private TransactionService transactionService;
	@Autowired private RestTemplate restTemplate;
	
	private final String VALIDATE_AIRTIME_REQUEST = "http://localhost:8001/bank/transactions/airtime";
	private final String CHECK_ACCOUNT_BALANCE = "http://localhost:8001/bank/transactions/balance";
	private final String DEPOSIT_REQUEST = "http://localhost:8001/bank/transactions/deposit";
	private final String TRANSFER_REQUEST = "http://localhost:8001/bank/transactions/transfer";
	private final String WITHDRAWAL_REQUEST = "http://localhost:8001/bank/transactions/withdrawal";
	
	
	//PURCHASE AIRTIME (SELF OR THIRD PARTY)
	
	@PostMapping("/airtime")
	public ResponseEntity<TransactionsResponseDTO> requestAirtime (@RequestBody AirtimeRequestDTO airtimeRequest) throws CustomExceptions {
		
		if (airtimeRequest.getAccountNo() == null ||airtimeRequest.getPhoneNo() == null
				||  airtimeRequest.getTransactionAmount() == null) {
			
			return ResponseEntity.badRequest().body(new TransactionsResponseDTO("Enter All Required Fields "
					+ "'accountNo', 'phoneNo', 'transactionAmount'", null));
		}
		
		ResponseEntity<TransactionsResponseDTO> response;
		try {
			response =  restTemplate.postForEntity(
					VALIDATE_AIRTIME_REQUEST, airtimeRequest, TransactionsResponseDTO.class);
		} catch (Exception e) {
			return ResponseEntity.status(200)
					.body(new TransactionsResponseDTO(e.getMessage(), null));
		}
		
//		if(response.getStatusCode().value() == 201) 
//			transactionService.saveNewTransaction(response.getBody()); 

		return response;
	}
	
	
	//REQUEST ACCOUNT BALANCE
	
	@PostMapping ("/balance")
	public ResponseEntity<TransactionsResponseDTO> getAccountBalance (@RequestBody BalanceRequestDTO request) throws CustomExceptions {
		
		if (request.getAccountNo() == null || request.getTransactionPIN() == null) {			
			return ResponseEntity.badRequest().body(new TransactionsResponseDTO("Enter All Required Fields "
					+ "'accountNo', 'transactionPIN'", null));
		}
		
		ResponseEntity<TransactionsResponseDTO> response;
		try {
			response =  restTemplate.postForEntity(
					CHECK_ACCOUNT_BALANCE, request, TransactionsResponseDTO.class);
		} catch (Exception e) {
			System.out.println("***************************************");
			throw new CustomExceptions(e.getMessage());
		}
		
		if(response.getStatusCode().value() == 201) 
			transactionService.saveNewTransaction(response.getBody()); 

		return response;
		
	}
	
	
	//DEPOSIT FUNDS
	
	@PostMapping ("/deposit")
	public ResponseEntity<TransactionsResponseDTO> deposit (@RequestBody DepositRequestDTO request) throws CustomExceptions {
		
		if (request.getAccountNo() == null || request.getAccountName() == null
				|| request.getTransactionAmount() == null) {	
			return ResponseEntity.badRequest().body(new TransactionsResponseDTO("Enter All Required Fields "
					+ "'accountNo', 'accountName', 'transactionAmount'", null));
		}
		
		ResponseEntity<TransactionsResponseDTO> response;
		try {
			response =  restTemplate.postForEntity(
					DEPOSIT_REQUEST, request, TransactionsResponseDTO.class);
		} catch (Exception e) {
			throw new CustomExceptions(e.getMessage());
		}
		
		if(response.getStatusCode().value() == 201) 
			transactionService.saveNewTransaction(response.getBody()); 

		return response;
		
	}
		
		
	//REQUEST TRANSACTION HISTORY

	@PostMapping ("/history")
	public ResponseEntity<TransactionsHistoryResponseDTO> 
	transactionHistory (@RequestBody TransactionsHistoryRequestDTO request,
			@RequestParam(defaultValue = "2") int limit, @RequestParam(defaultValue = "2") int page) throws CustomExceptions {
		
		Long accountNo = request.getAccountNo();
		Date startDate = request.getStartDate();
		Date endDate = request.getEndDate();
		
		if (accountNo == null || startDate == null)	{
			
			return ResponseEntity.badRequest()
					.body(new TransactionsHistoryResponseDTO(
							"Enter All Required Fields: 'accountNo', 'startDate', 'endDate(Optional)'",
							null, null
						));
		}
		
		String accountName = transactionService.getAccountName(accountNo);
		if (accountName == null) {
			return ResponseEntity.badRequest()
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
	
	
	//REQUEST TRANSFER

	@PostMapping ("/transfer")
	public ResponseEntity<TransactionsResponseDTO> transfer (@RequestBody TransferRequestDTO request) throws CustomExceptions {
		
		Long senderAccountNo = request.getSenderAccountNo();
		Long receiverAccountNo = request.getReceiverAccountNo();
		String receiverAccountName = request.getReceiverAccountName();
		Double amount = request.getAmount();
		Integer transactionPIN = request.getTransactionPIN();
		
		if (senderAccountNo == null || receiverAccountNo == null || receiverAccountName == null
				|| amount == null || transactionPIN == null) {	
			
			return ResponseEntity.badRequest()
					.body(new TransactionsResponseDTO(
							"Enter All Required Fields: 'senderAccountNo', 'receiverAccountNo',"
							+ " 'receiverAccountName', 'amount', 'transactionPIN'",
							null
						));
		}
		
		ResponseEntity<TransactionsResponseDTO []> response;
		try {
			response =  restTemplate.postForEntity(
					TRANSFER_REQUEST, request, TransactionsResponseDTO[].class);
		} catch (Exception e) {
			throw new CustomExceptions(e.getMessage());
		}
		
		if(response.getStatusCode().value() == 201) {
			transactionService.saveNewTransaction(response.getBody()[0]);
			transactionService.saveNewTransaction(response.getBody()[1]);
		}

		return ResponseEntity.status(200).body(response.getBody()[0]);
		
	}
	
	//WITHDRAW FUNDS
	
	@PostMapping ("/withdraw")
	public ResponseEntity<TransactionsResponseDTO> withdraw (@RequestBody WithdrawalRequestDTO request) throws CustomExceptions {
		
		if (request.getCardNo() == null || request.getWithdrawalAmount() == null || request.getCardPIN() == null 
				|| request.getMerchant() == null || request.getAccountType() == null) {	
			return ResponseEntity.badRequest().body(new TransactionsResponseDTO("Enter All Required Fields "
					+ "'cardNo', 'cardPIN', 'withdrawalAmount', 'merchant', 'accountType'", null));
		}
		
		ResponseEntity<TransactionsResponseDTO> response;
		try {
			response =  restTemplate.postForEntity(
					WITHDRAWAL_REQUEST, request, TransactionsResponseDTO.class);
		} catch (Exception e) {
			throw new CustomExceptions(e.getMessage());
		}
		
		if(response.getStatusCode().value() == 201) 
			transactionService.saveNewTransaction(response.getBody()); 

		return response;		
	}
	
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
