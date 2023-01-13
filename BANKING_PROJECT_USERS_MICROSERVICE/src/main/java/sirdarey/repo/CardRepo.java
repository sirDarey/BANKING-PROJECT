package sirdarey.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sirdarey.dto.CardDetails;
import sirdarey.entity.Card;

public interface CardRepo extends JpaRepository<Card, Long>{

	@Query(value = "select new sirdarey.dto.CardDetails("
					+ "c.cardNo, c.fk_account_no, c.cvv, c.cardHolder, c.cardType, c.expiryDate, "
					+ "c.isBlocked, c.isExpired"
					+ ") from Card c where c.cardNo=?1")
	CardDetails findCardDetails(Long cardNo);
	
}
