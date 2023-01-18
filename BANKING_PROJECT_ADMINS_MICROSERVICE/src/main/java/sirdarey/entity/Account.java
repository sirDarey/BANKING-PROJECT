package sirdarey.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class Account {
	
	private Long accountNo;	
	private String accountName;	
	private String email;
	private Long fk_user_id;
	private Long phoneNo;
	private String registeredBy; //(adminCode_name)
	private Double balance;
	private String accountManagerName;
	private List<NotificationMedia> notificationMedia = new ArrayList<>();;
	private Byte accLocked;  //UPDATABLE BY >=ADMINS 	
	private Integer transactionPIN;	
	private String branchRegistered;
	private String accountType;
	private List<Card> cards = new ArrayList<>();;
	
}
