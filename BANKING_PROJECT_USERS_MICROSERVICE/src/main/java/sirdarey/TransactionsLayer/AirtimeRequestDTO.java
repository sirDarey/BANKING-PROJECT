package sirdarey.TransactionsLayer;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class AirtimeRequestDTO {

	private Long accountNo;
	private Double transactionAmount;
	private Long phoneNo;
	private Integer transactionPIN;
	
}
