package sirdarey.card;

import java.util.List;

import org.springframework.http.ResponseEntity;

import sirdarey.card.dto.CardDetailsDTO;
import sirdarey.card.dto.CardDetailsResponseDTO;

public interface CardService {
 
	List <CardDetailsDTO> getAccountCards (List<Card> getCards);

	ResponseEntity<CardDetailsResponseDTO> addCardToAccount(Card newCard, Long accountNo);

	ResponseEntity<CardDetailsResponseDTO> getACardDetails(Long cardNo);

	ResponseEntity<CardDetailsResponseDTO> updateCardExpiryStatus(Long cardNo);

	ResponseEntity<CardDetailsResponseDTO> updateCardBlockedStatus(Boolean block, Long cardNo);
}
