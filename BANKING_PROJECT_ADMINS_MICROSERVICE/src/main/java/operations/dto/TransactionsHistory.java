package operations.dto;

import java.util.Date;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor 
public class TransactionsHistory {
	
	private String transactionType; 
	private Double transactionAmount;
	private String transactionDesc;
	@NumberFormat(style = Style.CURRENCY)
	private Double balance;	
	@JsonFormat(pattern="dd-MM-yyyy HH:mm:ss", shape = Shape.STRING)
	private Date transactionDateAndTime;
	
	public TransactionsHistory(String transactionType, Double transactionAmount, String transactionDesc, Double balance,
			Date transactionDateAndTime) {
		
		this.transactionType = transactionType;
		this.transactionAmount = transactionAmount;
		this.transactionDesc = transactionDesc;
		this.balance = balance;
		this.transactionDateAndTime = transactionDateAndTime;
	}

	
}
