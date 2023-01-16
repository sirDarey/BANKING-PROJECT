package sirdarey.service;

import java.util.List;

import sirdarey.dto.CardDetails;
import sirdarey.entity.Card;

public interface CardService {
 
	List <CardDetails> getAccountCards (List<Card> getCards);

	CardDetails addCardToAccount(Card newCard, Long accountNo);

	CardDetails getACardDetails(Long cardNo);

	String updateCardExpiryStatus(Long cardNo);

	String updateCardBlockedStatus(Boolean block, Long cardNo);
}
