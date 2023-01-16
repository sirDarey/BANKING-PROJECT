package sirdarey.TransactionsLayer;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter  @NoArgsConstructor
public class GenericResponse {

	private Integer transactionPIN;
	private Long phoneNo;
	private String accountName;
	@NumberFormat(style = Style.CURRENCY)
	private Double balance;	


	public GenericResponse(String accountName, Double balance, Integer transactionPIN, Long phoneNo) {
	
		this.accountName = accountName;
		this.balance = balance;
		this.transactionPIN = transactionPIN;
		this.phoneNo = phoneNo;
	}

	public GenericResponse(String accountName, Double balance, Integer transactionPIN) {
		this.accountName = accountName;
		this.balance = balance;
		this.transactionPIN = transactionPIN;
	}

	public GenericResponse(Double balance, String accountName) {
		this.accountName = accountName;
		this.balance = balance;
	}
	
}
