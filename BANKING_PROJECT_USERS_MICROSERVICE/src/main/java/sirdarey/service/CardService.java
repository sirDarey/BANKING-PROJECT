package sirdarey.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import sirdarey.dto.CardDetails;
import sirdarey.dto.CardDetailsResponse;
import sirdarey.entity.Card;

public interface CardService {
 
	List <CardDetails> getAccountCards (List<Card> getCards);

	ResponseEntity<CardDetailsResponse> addCardToAccount(Card newCard, Long accountNo);

	ResponseEntity<CardDetailsResponse> getACardDetails(Long cardNo);

	ResponseEntity<CardDetailsResponse> updateCardExpiryStatus(Long cardNo);

	ResponseEntity<CardDetailsResponse> updateCardBlockedStatus(Boolean block, Long cardNo);
}
