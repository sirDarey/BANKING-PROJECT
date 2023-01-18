package sirdarey.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import sirdarey.TransactionsLayer.GenericResponse;
import sirdarey.dto.CardDetails;
import sirdarey.entity.Card;

public interface CardRepo extends JpaRepository<Card, Long>{

	@Query(value = "select new sirdarey.dto.CardDetails("
					+ "c.cardNo, c.fk_account_no, c.cvv, c.cardHolder, c.cardType, c.expiryDate, "
					+ "c.isBlocked, c.isExpired"
					+ ") from Card c where c.cardNo=?1")
	CardDetails findCardDetails(Long cardNo);

	
	@Modifying
	@Query(nativeQuery = true,
			value = "UPDATE card SET is_expired = 1 WHERE card_no =?1")
	int updateCardExpiryStatus(Long cardNo);

	
	@Modifying
	@Query(nativeQuery = true,
			value = "UPDATE card SET is_blocked = ?1 WHERE card_no =?2")
	int  updateCardBlockedStatus(byte setStatus, Long cardNo);


	
	
	
	/**************************QUERIES FOR TRANSACTIONS LAYER ***************/
	
	
	@Query (value = "select new sirdarey.TransactionsLayer.GenericResponse"
			+ "(c.fk_account_no, c.cardPIN, c.cardHolder, c.isBlocked, c.isExpired) "
			+ "from Card c where c.cardNo=?1")
	GenericResponse getCardDetailsForWithdrawal(Long cardNo);
	
}
