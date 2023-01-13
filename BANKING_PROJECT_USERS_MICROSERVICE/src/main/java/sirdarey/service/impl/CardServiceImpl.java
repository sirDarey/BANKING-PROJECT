package sirdarey.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sirdarey.dto.CardDetails;
import sirdarey.entity.Card;
import sirdarey.repo.AccountRepo;
import sirdarey.repo.CardRepo;
import sirdarey.service.CardService;
import sirdarey.utils.AdditionSetterUtils;
import sirdarey.utils.ExtractionUtils;

@Service
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
	public CardDetails getACardDetails(Long cardNo) throws SQLException {		
		return cardRepo.findCardDetails(cardNo);		
	}
	

}
