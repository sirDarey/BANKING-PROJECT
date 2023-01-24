package sirdarey.card.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class CardDetailsDTO {
	
	private Long cardNo;
	private Long fk_account_no;
	private Integer cvv;
	private String cardHolder;
	private String cardType;
	private Date expiryDate;
	private Byte isBlocked;
	private Byte isExpired;
	
	public CardDetailsDTO(Long cardNo, Long fk_account_no, Integer cvv, Date expiryDate, Byte isBlocked, Byte isExpired) {
		
		this.cardNo = cardNo;
		this.fk_account_no = fk_account_no;
		this.cvv = cvv;
		this.expiryDate = expiryDate;
		this.isBlocked = isBlocked;
		this.isExpired = isExpired;
	}
	
	
}
