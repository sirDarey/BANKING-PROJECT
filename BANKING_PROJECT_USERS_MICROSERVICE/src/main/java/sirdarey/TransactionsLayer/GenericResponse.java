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
	private Long cardNo;
	private String accountType;
	private Long fk_account_no;
	private Integer cardPIN;
	private String cardHolder;
	private Byte isBlocked;
	private Byte isExpired;

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

	public GenericResponse(Integer transactionPIN, Double balance) {
		this.transactionPIN = transactionPIN;
		this.balance = balance;
	}

	public GenericResponse(Long fk_account_no, Integer cardPIN, String cardHolder, Byte isBlocked, Byte isExpired) {
		this.fk_account_no = fk_account_no;
		this.cardPIN = cardPIN;
		this.cardHolder = cardHolder;
		this.isBlocked = isBlocked;
		this.isExpired = isExpired;
	}

	public GenericResponse(String accountType, Double balance) {
		this.balance = balance;
		this.accountType = accountType;
	}	
	
	
}
