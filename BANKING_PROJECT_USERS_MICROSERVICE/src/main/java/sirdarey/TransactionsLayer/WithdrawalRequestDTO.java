package sirdarey.TransactionsLayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class WithdrawalRequestDTO {

	private Long cardNo;
	private String accountType;
	private Double withdrawalAmount;
	private Integer cardPIN;
	private String merchant; //ATM,POS
}
