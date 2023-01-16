package sirdarey.TransactionsLayer;

import java.util.Date;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor  @NoArgsConstructor
public class TransactionDetails {
	
	private Long accountNo;
	private String accountName;
	private String transactionType; 
	private Double transactionAmount;
	private String transactionDesc;
	@NumberFormat(style = Style.CURRENCY)
	private Double balance;	
	@JsonFormat(pattern="dd-MM-yyyy HH:mm:ss", shape = Shape.STRING)
	private Date transactionDateAndTime;		
}
