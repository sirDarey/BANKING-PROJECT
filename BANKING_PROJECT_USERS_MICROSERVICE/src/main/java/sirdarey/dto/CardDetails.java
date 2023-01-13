package sirdarey.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class CardDetails {
	
	private Long cardNo;
	private Long fk_account_no;
	private int cvv;
	private String cardHolder;
	private String cardType;
	private Date expiryDate;
	private byte isBlocked;
	private byte isExpired;
	
	public CardDetails(Long cardNo, Long fk_account_no, int cvv, Date expiryDate, byte isBlocked, byte isExpired) {
		
		this.cardNo = cardNo;
		this.fk_account_no = fk_account_no;
		this.cvv = cvv;
		this.expiryDate = expiryDate;
		this.isBlocked = isBlocked;
		this.isExpired = isExpired;
	}
	
	
}
