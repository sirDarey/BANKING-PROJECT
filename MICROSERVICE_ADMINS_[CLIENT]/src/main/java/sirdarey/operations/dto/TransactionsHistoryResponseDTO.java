package sirdarey.operations.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class TransactionsHistoryResponseDTO {

	private String MESSAGE;
	private AccountHeader HEADER;
	private List<TransactionsHistory> TRANSACTIONS;
	
}
