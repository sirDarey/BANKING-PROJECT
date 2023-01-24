package sirdarey.operations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class TransactionsResponseDTO {

	private String message;
	private TransactionDetails transactionDetails;	
}


