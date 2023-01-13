package sirdarey.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sirdarey.dto.CardDetails;
import sirdarey.entity.Card;
import sirdarey.service.CardService;

@RestController
@RequestMapping("/bank/cards")
public class CardController {

	@Autowired
	private CardService cardService;
	
	//ADD A NEW CARD TO AN ACCOUNT
	
	@PostMapping("/{accountNo}")
	public ResponseEntity<CardDetails> addCardToAccount(@RequestBody Card newCard, @PathVariable Long accountNo) {
		return ResponseEntity.status(201).body(cardService.addCardToAccount(newCard, accountNo));
	}
	
	//VIEW A CARD DETAILS
	
	@GetMapping("/{cardNo}")
	public CardDetails getACardDetails (@PathVariable Long cardNo) throws SQLException {
		return cardService.getACardDetails(cardNo);
	}
	
	
	//UPDATE EXPIRY STATUS
	
//	@GetMapping("/{cardNo}")
//	public CardDetails getACardDetails (@PathVariable Long cardNo) throws SQLException {
//		return cardService.getACardDetails(cardNo);
//	}
//	
	
	//UPDATE BLOCKED STATUS
	
}
