package sirdarey.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sirdarey.entity.Account;
import sirdarey.entity.Card;
import sirdarey.entity.NotificationMedia;

@Data @AllArgsConstructor @NoArgsConstructor
public class AddAccountToUserRequest {

	private Account account;
	private List<NotificationMedia> notificationMedia = new ArrayList<>();
	private List<Card> cards = new ArrayList<>();
}
