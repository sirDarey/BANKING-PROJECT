package sirdarey.card;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sirdarey.account.AccountRepo;
import sirdarey.card.dto.CardDetailsDTO;
import sirdarey.card.dto.CardDetailsResponseDTO;
import sirdarey.utils.AdditionSetterUtils;
import sirdarey.utils.ExtractionUtils;

@Service
public class CardServiceImpl implements CardService{

	@Autowired private CardRepo cardRepo;
	@Autowired private AccountRepo accountRepo;
	@Autowired private AdditionSetterUtils additionSetterUtils;
	@Autowired private ExtractionUtils extractionUtils;
	
	
	@Override
	public List<CardDetailsDTO> getAccountCards(List<Card> getCards) {
		List<CardDetailsDTO> cardsDetails = new ArrayList<>();
		
		for (Card card : getCards) {
			cardsDetails.add(extractionUtils.extractCardDetails(card));
		} 
		return cardsDetails;
	}

	@Override
	public ResponseEntity<CardDetailsResponseDTO> addCardToAccount(Card newCard, Long accountNo) {
		
		String accountName  = null;
		try {
			accountName = accountRepo.getAccountName(accountNo);
			if (accountName == null)
				return ResponseEntity.status(409)
						.body(new CardDetailsResponseDTO("Account NOT FOUND", null));
			
		}catch (Exception e) {
			return ResponseEntity.status(404).body(new CardDetailsResponseDTO(e.getMessage(), null));
		}
		
		additionSetterUtils.newCardSetters(newCard, accountName);
		newCard.setFk_account_no(accountNo);
		Card savedCard =  cardRepo.save(newCard);
		
		return ResponseEntity.status(201)
				.body(new CardDetailsResponseDTO("Card Added to Account SUCCESSFULLY",
						extractionUtils.extractCardDetails(savedCard)));
	}

	@Override
	public ResponseEntity<CardDetailsResponseDTO> getACardDetails(Long cardNo){
		
		CardDetailsDTO cardDetails = null;
		try {
			cardDetails = cardRepo.findCardDetails(cardNo);
			if (cardDetails == null)
				return ResponseEntity.status(404)
						.body(new CardDetailsResponseDTO("Card NOT FOUND", null));
			
		}catch (Exception e) {
			return ResponseEntity.status(409).body(new CardDetailsResponseDTO(e.getMessage(), null));
		}
		
		return ResponseEntity.status(201)
				.body(new CardDetailsResponseDTO("Card Details Retrieved SUCCESSFULLY",
						cardDetails));	
	}

	@Override
	@Transactional
	public ResponseEntity<CardDetailsResponseDTO> updateCardExpiryStatus(Long cardNo) {
		
		try {
			int rowUpdated = cardRepo.updateCardExpiryStatus(cardNo);
			if (rowUpdated == 0)
				return ResponseEntity.status(404).body(new CardDetailsResponseDTO("Card NOT FOUND", null));
			
		}catch (Exception e) {
			return ResponseEntity.status(409).body(new CardDetailsResponseDTO(e.getMessage(), null));
		}
		
		return ResponseEntity.status(201)
				.body(new CardDetailsResponseDTO("SUCCESS",
						"Expiry Status of Card Updated SUCCESSFULLY"));	
	}

	@Override
	@Transactional
	public ResponseEntity<CardDetailsResponseDTO> updateCardBlockedStatus(Boolean block, Long cardNo) {
		Byte setStatus = 0;
		String response;
		
		if (block) {
			setStatus = 1;
			response = "Card BLOCKED SUCCESSFULLY";
		} else
			response = "Card UNblocked SUCCESSFULLY";
		
		try {
			int rowUpdated = cardRepo.updateCardBlockedStatus(setStatus, cardNo);
			if (rowUpdated == 0)
				return ResponseEntity.status(404).body(new CardDetailsResponseDTO("Card NOT FOUND", null));
			
		}catch (Exception e) {
			return ResponseEntity.status(409).body(new CardDetailsResponseDTO(e.getMessage(), null));
		}
		
		return ResponseEntity.status(201)
				.body(new CardDetailsResponseDTO("SUCCESS", response));	
	}
	

}
