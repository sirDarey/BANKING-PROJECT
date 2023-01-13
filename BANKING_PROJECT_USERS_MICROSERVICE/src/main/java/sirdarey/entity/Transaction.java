package sirdarey.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor
public class Transaction {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Long tId;
	
	@Column(length = 12)
	Long transactionId;
	
	@Column(length = 10)
	Long accountNo;
	
	String transactionType;  //DR/CR
	
	@NotBlank(message = "Enter Transaction Amount")
	double transactionAmount;
	
	@NotBlank(message = "Transaction Description Cannot be empty")
	@Size(min = 10, message = "Enter a valid Transaction Description")
	String transactionDesc;
	
	@Column(length = 10)	
	Long thirdPartyAccountNo;
	
	double balance;	
	
	Date transactionDateAndTime;
	
	List<String> notificationMedia;

}
