package sirdarey.repo;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import sirdarey.account.Account;
import sirdarey.card.Card;
import sirdarey.card.CardRepo;
import sirdarey.card.dto.CardDetailsDTO;
import sirdarey.notification.NotificationMedia;
import sirdarey.transactions.dto.GenericResponseDTO;
import sirdarey.user.User;
import sirdarey.user.UserRepo;

@DataJpaTest
@TestInstance(Lifecycle.PER_CLASS)
class CardRepoTest {
	
	@Autowired
	private CardRepo cardRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	private Long cardNo;
	private byte blockedStatus;
	
	@BeforeAll
	void createNewUser () {
		Card newCard = new Card(1234667890487145l, 211, null, "CardHolder", 
				1234, "VISA", new Date(System.currentTimeMillis()), (byte) 1, (byte)0);
		List<Card> cards = Arrays.asList(newCard);
		
		NotificationMedia newMedium = new NotificationMedia(null, "SMS");
		List<NotificationMedia> media = Arrays.asList(newMedium);
		
		Account newAccount = new Account(1234567890l, "Test User1", "test1@gmail.com", null, 
				70108546l, "Sir Darey", 0.00, "Manager1", media, (byte)0, 1234, "Yaba", "CURRENT", cards);
		List<Account> accounts = Arrays.asList(newAccount);
		
		User newUser = new User(2215463l, "Test User1", "bb123*AE&7", "USER", "Admin", 25364987548l, accounts, (byte)1);
		
		cardNo = 1234667890487145l;
		blockedStatus = 1;
		userRepo.save(newUser);
	}
	
	@Test
	void testFindCardDetails() { 
		CardDetailsDTO findCard = cardRepo.findCardDetails(cardNo);
		assertNotNull(findCard);
	}

	@Test
	void testUpdateCardExpiryStatus() {
		cardRepo.updateCardExpiryStatus(cardNo);
		CardDetailsDTO findCard = cardRepo.findCardDetails(cardNo);
		byte expiryStatus = findCard.getIsExpired();
		assertEquals(1, expiryStatus);
	}

	
	@Test
	@RepeatedTest(value = 4)
	void testUpdateCardBlockedStatus() {
		blockedStatus = (byte) ((blockedStatus == 1)? 0 : 1);
		cardRepo.updateCardBlockedStatus(blockedStatus, cardNo);
		CardDetailsDTO findCard = cardRepo.findCardDetails(cardNo);
		byte newBlockedStatus = findCard.getIsBlocked();
		
		assertEquals(blockedStatus, newBlockedStatus);
	}

	@Test
	void testGetCardDetailsForWithdrawal() {
		
		Card findCard = cardRepo.findById(cardNo).get();
		
		GenericResponseDTO expectedCardDetailsForWithdrawal = 
				new GenericResponseDTO(findCard.getFk_account_no(), findCard.getCardPIN(), findCard.getCardHolder(), 
						findCard.getIsBlocked(), findCard.getIsExpired());
		
		GenericResponseDTO actualCardDetailsForWithdrawal = cardRepo.getCardDetailsForWithdrawal(cardNo);
		
		assertAll(()-> assertEquals(expectedCardDetailsForWithdrawal.getFk_account_no(), actualCardDetailsForWithdrawal.getFk_account_no()),
				  () -> assertEquals(expectedCardDetailsForWithdrawal.getCardPIN(), actualCardDetailsForWithdrawal.getCardPIN()),
				  () -> assertEquals(expectedCardDetailsForWithdrawal.getCardHolder(), actualCardDetailsForWithdrawal.getCardHolder()),
				  () -> assertEquals(expectedCardDetailsForWithdrawal.getIsBlocked(), actualCardDetailsForWithdrawal.getIsBlocked()),
				  () -> assertEquals(expectedCardDetailsForWithdrawal.getIsExpired(), actualCardDetailsForWithdrawal.getIsExpired())
				);
	}
	
	

}
