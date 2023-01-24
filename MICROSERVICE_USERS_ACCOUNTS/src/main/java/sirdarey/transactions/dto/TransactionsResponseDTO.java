package sirdarey.transactions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor @AllArgsConstructor
public class TransactionsResponseDTO {

	private String message;
	private TransactionDetailsDTO transactionDetails;	
}
