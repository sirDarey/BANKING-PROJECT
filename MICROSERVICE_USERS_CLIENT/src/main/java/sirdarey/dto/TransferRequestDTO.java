package sirdarey.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class TransferRequestDTO {

	private Long senderAccountNo;
	private Long receiverAccountNo;
	private String receiverAccountName;
	private Double amount;
	private Integer transactionPIN;
}
