package sirdarey.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class AirtimeRequestDTO {

	private Long accountNo;
	private Double transactionAmount;
	private Long phoneNo;
	private Integer transactionPIN;
}
