package sirdarey.entity;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class Card {
	
	private Long cardNo;
	private Integer cvv;
	private Long fk_account_no;
	private String cardHolder;
	private Integer cardPIN;
	private String cardType;
	private Date expiryDate;	
	private Byte isBlocked;
	private Byte isExpired;

	public Card(Long cardNo, Integer cvv, Long fk_account_no, String cardType, Date expiryDate,
			Byte isBlocked, Byte isExpired) {

		this.cardNo = cardNo;
		this.cvv = cvv;
		this.fk_account_no = fk_account_no;
		this.cardType = cardType;
		this.expiryDate = expiryDate;
		this.isBlocked = isBlocked;
		this.isExpired = isExpired;
	}
}
