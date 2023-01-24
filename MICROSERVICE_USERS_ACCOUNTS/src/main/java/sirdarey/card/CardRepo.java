package sirdarey.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import sirdarey.card.dto.CardDetailsDTO;
import sirdarey.transactions.dto.GenericResponseDTO;

public interface CardRepo extends JpaRepository<Card, Long>{

	@Query(value = "select new sirdarey.card.dto.CardDetailsDTO("
					+ "c.cardNo, c.fk_account_no, c.cvv, c.cardHolder, c.cardType, c.expiryDate, "
					+ "c.isBlocked, c.isExpired"
					+ ") from Card c where c.cardNo=?1")
	CardDetailsDTO findCardDetails(Long cardNo);

	
	@Modifying
	@Query(nativeQuery = true,
			value = "UPDATE card SET is_expired = 1 WHERE card_no =?1")
	int updateCardExpiryStatus(Long cardNo);

	
	@Modifying
	@Query(nativeQuery = true,
			value = "UPDATE card SET is_blocked = ?1 WHERE card_no =?2")
	int  updateCardBlockedStatus(byte setStatus, Long cardNo);


	
	
	
	/**************************QUERIES FOR TRANSACTIONS LAYER ***************/
	
	
	@Query (value = "select new sirdarey.transactions.dto.GenericResponseDTO"
			+ "(c.fk_account_no, c.cardPIN, c.cardHolder, c.isBlocked, c.isExpired) "
			+ "from Card c where c.cardNo=?1")
	GenericResponseDTO getCardDetailsForWithdrawal(Long cardNo);
	
}
