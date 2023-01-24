package sirdarey.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class TransactionsResponseDTO {

	private String message;
	private TransactionDetailsDTO transactionDetails;	
}
