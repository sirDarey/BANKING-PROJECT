package sirdarey.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sirdarey.dto.CardDetails;
import sirdarey.dto.CardDetailsResponse;
import sirdarey.entity.Card;
import sirdarey.repo.AccountRepo;
import sirdarey.repo.CardRepo;
import sirdarey.service.CardService;
import sirdarey.utils.AdditionSetterUtils;
import sirdarey.utils.ExtractionUtils;

@Service
@Transactional
public class CardServiceImpl implements CardService{

	@Autowired
	private CardRepo cardRepo;
	@Autowired
	private AccountRepo accountRepo;
	@Autowired
	private AdditionSetterUtils additionSetterUtils;
	@Autowired
	private ExtractionUtils extractionUtils;
	
	
	@Override
	public List<CardDetails> getAccountCards(List<Card> getCards) {
		List<CardDetails> cardsDetails = new ArrayList<>();
		
		for (Card card : getCards) {
			cardsDetails.add(extractionUtils.extractCardDetails(card));
		} 
		return cardsDetails;
	}

	@Override
	public ResponseEntity<CardDetailsResponse> addCardToAccount(Card newCard, Long accountNo) {
		
		String accountName  = null;
		try {
			accountName = accountRepo.getAccountName(accountNo);
			if (accountName == null)
				return ResponseEntity.status(404)
						.body(new CardDetailsResponse("Account NOT FOUND", null));
			
		}catch (Exception e) {
			return ResponseEntity.status(404).body(new CardDetailsResponse(e.getMessage(), null));
		}
		
		additionSetterUtils.newCardSetters(newCard, accountName);
		newCard.setFk_account_no(accountNo);
		Card savedCard =  cardRepo.save(newCard);
		
		return ResponseEntity.status(201)
				.body(new CardDetailsResponse("Card Added to Account SUCCESSFULLY",
						extractionUtils.extractCardDetails(savedCard)));
	}

	@Override
	public ResponseEntity<CardDetailsResponse> getACardDetails(Long cardNo){
		
		CardDetails cardDetails = null;
		try {
			cardDetails = cardRepo.findCardDetails(cardNo);
			if (cardDetails == null)
				return ResponseEntity.status(404)
						.body(new CardDetailsResponse("Card NOT FOUND", null));
			
		}catch (Exception e) {
			return ResponseEntity.status(404).body(new CardDetailsResponse(e.getMessage(), null));
		}
		
		return ResponseEntity.status(201)
				.body(new CardDetailsResponse("Card Details Retrieved SUCCESSFULLY",
						cardDetails));	
	}

	@Override
	public ResponseEntity<CardDetailsResponse> updateCardExpiryStatus(Long cardNo) {
		
		try {
			int rowUpdated = cardRepo.updateCardExpiryStatus(cardNo);
			if (rowUpdated == 0)
				return ResponseEntity.status(404).body(new CardDetailsResponse("Card NOT FOUND", null));
			
		}catch (Exception e) {
			return ResponseEntity.status(404).body(new CardDetailsResponse(e.getMessage(), null));
		}
		
		return ResponseEntity.status(201)
				.body(new CardDetailsResponse("SUCCESS",
						"Expiry Status of Card Updated SUCCESSFULLY"));	
	}

	@Override
	public ResponseEntity<CardDetailsResponse> updateCardBlockedStatus(Boolean block, Long cardNo) {
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
				return ResponseEntity.status(404).body(new CardDetailsResponse("Card NOT FOUND", null));
			
		}catch (Exception e) {
			return ResponseEntity.status(404).body(new CardDetailsResponse(e.getMessage(), null));
		}
		
		return ResponseEntity.status(201)
				.body(new CardDetailsResponse("SUCCESS", response));	
	}
	

}
