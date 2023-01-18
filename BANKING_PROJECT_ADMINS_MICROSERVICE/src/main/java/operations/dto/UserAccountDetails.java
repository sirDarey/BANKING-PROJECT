package operations.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sirdarey.entity.NotificationMedia;

@Getter @AllArgsConstructor @NoArgsConstructor
public class UserAccountDetails {

	private Long accountNo;
	private String accountName;
	private String email;
	private Long phoneNo;
	private String registeredBy;
	private Double balance;
	private String accountManagerName;
	private List<NotificationMedia> notificationMedia;
	private Byte accLocked;
	private String branchRegistered;
	private String accountType;
	private List<CardDetails> cards;
}
