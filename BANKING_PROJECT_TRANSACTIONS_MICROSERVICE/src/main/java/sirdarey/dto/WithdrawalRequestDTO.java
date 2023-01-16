package sirdarey.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class WithdrawalRequestDTO {

	private Long cardNo;
	private Integer cardPIN;
	private Double amount;
	private String merchant; //ATM,POS
}
