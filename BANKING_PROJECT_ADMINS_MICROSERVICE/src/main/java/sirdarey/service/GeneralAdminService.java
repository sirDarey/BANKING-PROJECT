package sirdarey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sirdarey.dto.AdminDetailsResponse;
import sirdarey.utils.Utils;

@Service
@Transactional
public class GeneralAdminService {

	@Autowired private Utils utils;

	public ResponseEntity<AdminDetailsResponse> getSelfDetails(Long adminId) {
		return utils.getAnAdmin(adminId);
	}

}
