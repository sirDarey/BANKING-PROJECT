package sirdarey.entity;

import java.util.Date;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class TransactionEntity {

	@Id
	@Column(unique = true, length = 10)
	private Long transactionId;
	
	@Column(length = 10)
	private Long accountNo;
	
	private String accountName;
	
	private String transactionType;  //DR/CR
	
	private Double transactionAmount;
	
	private String transactionDesc;
	
	@NumberFormat(style = Style.CURRENCY)	
	private Double balance;	
	
	@JsonFormat(pattern="dd-MM-yyyy HH:mm:ss", shape = Shape.STRING)
	private Date transactionDateAndTime;
}
