package sirdarey.operations.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CardDetails {
	
	private Long cardNo;
	private Long fk_account_no;
	private Integer cvv;
	private String cardHolder;
	private String cardType;
	private Date expiryDate;
	private Byte isBlocked;
	private Byte isExpired;
	
	public CardDetails(Long cardNo, Long fk_account_no, Integer cvv, Date expiryDate, Byte isBlocked, Byte isExpired) {
		
		this.cardNo = cardNo;
		this.fk_account_no = fk_account_no;
		this.cvv = cvv;
		this.expiryDate = expiryDate;
		this.isBlocked = isBlocked;
		this.isExpired = isExpired;
	}
	
	
}
