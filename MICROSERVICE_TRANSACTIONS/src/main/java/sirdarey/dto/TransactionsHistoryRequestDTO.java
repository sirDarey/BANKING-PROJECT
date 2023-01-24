package sirdarey.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class TransactionsHistoryRequestDTO {

	@JsonFormat(pattern="dd-MM-yyyy", shape = Shape.STRING)
	private Date startDate;
	@JsonFormat(pattern="dd-MM-yyyy", shape = Shape.STRING)
	private Date endDate;
	private Long accountNo;
}
