package sirdarey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sirdarey.dto.CardDetailsResponse;
import sirdarey.entity.Card;
import sirdarey.service.CardService;

@RestController
@RequestMapping("/bank/cards")
public class CardController {

	@Autowired
	private CardService cardService;
	
	//ADD A NEW CARD TO AN ACCOUNT
	
	@PostMapping("/{accountNo}")
	public ResponseEntity<CardDetailsResponse> addCardToAccount(@RequestBody Card newCard, @PathVariable Long accountNo) throws Exception{
		return cardService.addCardToAccount(newCard, accountNo);
	}
	
	//VIEW A CARD DETAILS
	
	@GetMapping("/{cardNo}")
	public ResponseEntity<CardDetailsResponse> getACardDetails (@PathVariable Long cardNo) throws Exception{
		return cardService.getACardDetails(cardNo);
	}
	
	
	//UPDATE EXPIRY STATUS
	
	@PutMapping("/{cardNo}/expiry")
	public ResponseEntity<CardDetailsResponse> updateCardExpiryStatus (@PathVariable Long cardNo)throws Exception{
		return cardService.updateCardExpiryStatus(cardNo);
	}
	
	
	//UPDATE BLOCKED STATUS
	
	@PutMapping("/{cardNo}/block")
	public ResponseEntity<CardDetailsResponse> updateCardBlockedStatus (@RequestParam Boolean block, @PathVariable Long cardNo) throws Exception {
		return cardService.updateCardBlockedStatus(block, cardNo);
	}
}
