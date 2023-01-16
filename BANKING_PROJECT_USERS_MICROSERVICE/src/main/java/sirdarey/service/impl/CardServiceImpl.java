package sirdarey.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sirdarey.dto.CardDetails;
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
	public CardDetails addCardToAccount(Card newCard, Long accountNo) {
		String accountName = accountRepo.getAccountName(accountNo);
		additionSetterUtils.newCardSetters(newCard, accountName);
		newCard.setFk_account_no(accountNo);
		
		return extractionUtils.extractCardDetails(cardRepo.save(newCard));
	}

	@Override
	public CardDetails getACardDetails(Long cardNo){		
		return cardRepo.findCardDetails(cardNo);		
	}

	@Override
	public String updateCardExpiryStatus(Long cardNo) {
		cardRepo.updateCardExpiryStatus(cardNo);
		return "Expiry Status of Card Updated SUCCESSFULLY";
	}

	@Override
	public String updateCardBlockedStatus(Boolean block, Long cardNo) {
		int setStatus = 0;
		String response;
		
		if (block) {
			setStatus = 1;
			response = "Card BLOCKED SUCCESSFULLY";
		} else
			response = "Card UNblocked SUCCESSFULLY";
		
		cardRepo.updateCardBlockedStatus((byte)setStatus, cardNo);
		return response;
	}
	

}
