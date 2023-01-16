package sirdarey.TransactionsLayer;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class BalanceRequestDTO {

	private Long accountNo;
	private Integer transactionPIN;
	
}
