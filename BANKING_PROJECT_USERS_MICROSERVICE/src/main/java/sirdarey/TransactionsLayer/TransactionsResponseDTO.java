package sirdarey.TransactionsLayer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor @AllArgsConstructor
public class TransactionsResponseDTO {

	private String message;
	private TransactionDetails transactionDetails;	
}
