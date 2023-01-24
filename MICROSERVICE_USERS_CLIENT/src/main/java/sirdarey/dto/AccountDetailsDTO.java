package sirdarey.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class AccountDetailsDTO {

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
	private List<CardDetailsDTO> cards;
}
