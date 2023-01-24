package sirdarey.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sirdarey.card.dto.CardDetailsResponseDTO;

@RestController
@RequestMapping("/bank/admins")
public class CardController {

	@Autowired private CardService cardService;
	
	/************************ ACTIONS ON A USER'S CARD ******************************/
	
	
	//VIEW A CARD DETAILS
	
	@GetMapping("/getcard/{cardNo}")
	public ResponseEntity<CardDetailsResponseDTO> getACardDetails (@PathVariable Long cardNo) {
		return cardService.getACardDetails(cardNo);
	}
	
	
	//UPDATE EXPIRY STATUS
	
	@PutMapping("/updatecardexpirystatus/{cardNo}")
	public ResponseEntity<CardDetailsResponseDTO> updateCardExpiryStatus (@PathVariable Long cardNo) {
		return cardService.updateCardExpiryStatus(cardNo);
	}
	
	
	//UPDATE BLOCKED STATUS
	
	@PutMapping("/updatecardblockedstatus/{cardNo}")
	public ResponseEntity<CardDetailsResponseDTO> updateCardBlockedStatus (@RequestParam Boolean block, @PathVariable Long cardNo) {
		return cardService.updateCardBlockedStatus(block, cardNo);
	}
	
}
