package sirdarey.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class TransferRequestDTO {

	private Long senderAccountNo;
	private Long receiverAccountNo;
	private String receiverAccountName;
	private String receiverBank;
	private Double amount;
	private Integer transactionPIN;
}
