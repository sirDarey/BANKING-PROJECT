package sirdarey.TransactionsLayer;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class DepositRequestDTO {

	private Long accountNo;
	private String accountName;
	private Double transactionAmount;
}
