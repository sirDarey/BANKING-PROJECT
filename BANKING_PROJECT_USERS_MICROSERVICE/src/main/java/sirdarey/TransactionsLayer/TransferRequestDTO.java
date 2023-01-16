package sirdarey.TransactionsLayer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor @NoArgsConstructor
public class TransferRequestDTO {

	private Long senderAccountNo;
	private Long receiverAccountNo;
	private String receiverAccountName;
	private Double amount;
	private Integer transactionPIN;
}
