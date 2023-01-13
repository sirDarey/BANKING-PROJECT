package sirdarey.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor
public class Card {
	
	@Id
	@Column(length = 16, unique = true)
	private Long cardNo;
	
	@Column(length = 3)
	private int cvv;
	
	private Long fk_account_no;
	
	private String cardHolder;
	
	@Column(length = 4)
	private int cardPIN;
	
	private String cardType;
	
	@JsonFormat(pattern="dd-MM-yyyy", shape = Shape.STRING)
	private Date expiryDate;
	
	private byte isBlocked;
	private byte isExpired;

	public Card(Long cardNo, int cvv, Long fk_account_no, String cardType, Date expiryDate,
			byte isBlocked, byte isExpired) {

		this.cardNo = cardNo;
		this.cvv = cvv;
		this.fk_account_no = fk_account_no;
		this.cardType = cardType;
		this.expiryDate = expiryDate;
		this.isBlocked = isBlocked;
		this.isExpired = isExpired;
	}
}
