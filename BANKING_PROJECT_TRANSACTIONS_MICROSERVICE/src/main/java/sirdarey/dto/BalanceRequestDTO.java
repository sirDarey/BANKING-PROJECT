package sirdarey.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class BalanceRequestDTO {

	private Long accountNo;
	private Integer transactionPIN;
	
}
