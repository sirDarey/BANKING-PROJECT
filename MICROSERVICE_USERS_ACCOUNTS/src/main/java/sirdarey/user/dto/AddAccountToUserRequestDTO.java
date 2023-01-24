package sirdarey.user.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sirdarey.account.Account;
import sirdarey.card.Card;
import sirdarey.notification.NotificationMedia;

@Data @AllArgsConstructor @NoArgsConstructor
public class AddAccountToUserRequestDTO {

	private Account account;
	private List<NotificationMedia> notificationMedia = new ArrayList<>();
	private List<Card> cards = new ArrayList<>();
}
