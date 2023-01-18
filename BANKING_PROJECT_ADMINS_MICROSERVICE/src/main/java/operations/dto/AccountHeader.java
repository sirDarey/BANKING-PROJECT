package operations.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class AccountHeader {
	private Long accountNo;
	private String accountName;
	private String RESULTS_TIMEFRAME;

	public AccountHeader(Long accountNo, String accountName, String rESULTS_TIMEFRAME) {

		this.accountNo = accountNo;
		this.accountName = accountName;
		RESULTS_TIMEFRAME = rESULTS_TIMEFRAME;
	}
}