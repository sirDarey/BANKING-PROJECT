package sirdarey.transactions.dto;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter  @NoArgsConstructor
public class GenericResponseDTO {

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

	public GenericResponseDTO(String accountName, Double balance, Integer transactionPIN, Long phoneNo) {
	
		this.accountName = accountName;
		this.balance = balance;
		this.transactionPIN = transactionPIN;
		this.phoneNo = phoneNo;
	}

	public GenericResponseDTO(String accountName, Double balance, Integer transactionPIN) {
		this.accountName = accountName;
		this.balance = balance;
		this.transactionPIN = transactionPIN;
	}

	public GenericResponseDTO(Double balance, String accountName) {
		this.accountName = accountName;
		this.balance = balance;
	}

	public GenericResponseDTO(Integer transactionPIN, Double balance) {
		this.transactionPIN = transactionPIN;
		this.balance = balance;
	}

	public GenericResponseDTO(Long fk_account_no, Integer cardPIN, String cardHolder, Byte isBlocked, Byte isExpired) {
		this.fk_account_no = fk_account_no;
		this.cardPIN = cardPIN;
		this.cardHolder = cardHolder;
		this.isBlocked = isBlocked;
		this.isExpired = isExpired;
	}

	public GenericResponseDTO(String accountType, Double balance) {
		this.balance = balance;
		this.accountType = accountType;
	}	
	
	
}
