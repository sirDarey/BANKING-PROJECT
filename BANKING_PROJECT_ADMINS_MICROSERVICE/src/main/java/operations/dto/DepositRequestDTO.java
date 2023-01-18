package operations.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class DepositRequestDTO {

	private Long accountNo;
	private String accountName;
	private Double transactionAmount;
}
