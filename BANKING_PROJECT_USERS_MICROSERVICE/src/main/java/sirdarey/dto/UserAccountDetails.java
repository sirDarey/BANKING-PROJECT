package sirdarey.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sirdarey.entity.NotificationMedia;

@Getter @AllArgsConstructor
public class UserAccountDetails {

	private Long accountNo;
	private String accountName;
	private String email;
	private Long phoneNo;
	private String registeredBy;
	private Double balance;
	private String accountManagerName;
	private List<NotificationMedia> notificationMedia;
	private byte accLocked;
	private String branchRegistered;
	private String accountType;
	private List<CardDetails> cards;
}
