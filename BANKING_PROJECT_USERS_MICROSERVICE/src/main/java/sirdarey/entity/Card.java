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
	private Integer cvv;
	
	private Long fk_account_no;
	
	private String cardHolder;
	
	@Column(length = 4)
	private Integer cardPIN;
	
	private String cardType;
	
	@JsonFormat(pattern="dd-MM-yyyy", shape = Shape.STRING)
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
