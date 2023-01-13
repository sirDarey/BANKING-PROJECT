package sirdarey.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor
public class Account {
	
	@Id
	@Column(unique = true, length = 10)
	private Long accountNo;
	
	String accountName;
	
	@Email (message = "Enter a valid Email", regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
	private String email;
	
	
	private Long fk_user_id;
	
	private Long phoneNo;
	
	
	private String registeredBy; //(adminCode_name)
	
	@NumberFormat(style = Style.CURRENCY)
	private Double balance;
	
	private String accountManagerName;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_account_no", referencedColumnName = "accountNo")
	private List<NotificationMedia> notificationMedia = new ArrayList<>();;
	
	private byte accLocked;  //UPDATABLE BY >=ADMINS 
	
	
	private String branchRegistered;
	
	
	private String accountType;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_account_no", referencedColumnName = "accountNo")
	private List<Card> cards = new ArrayList<>();;
	
}