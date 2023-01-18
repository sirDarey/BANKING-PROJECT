package sirdarey.utils;

import java.util.Collection;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import sirdarey.dto.AdminDetailsResponse;
import sirdarey.dto.AnAdminDTO;
import sirdarey.entity.Admin;
import sirdarey.entity.Role;
import sirdarey.repo.AdminRepo;

public class Utils {
	
	@Autowired private AdminRepo adminRepo;

	public long generateRandom(int length) {
		Random rand = new Random();
		return rand.nextLong((long)Math.pow(10, length), (long)Math.pow(10, length+1));
	}
	
	public AnAdminDTO extractAnAdminDTO (Admin admin) {
		
		Collection<Role> roles = admin.getRoles();
		if (roles != null) {
			roles.forEach(role -> {
				role.setFk_admin_id(admin.getAdminId());
			});
		}
				
		return new AnAdminDTO(
				admin.getAdminId(), 
				admin.getName(), 
				admin.getAdminEnabled(), 
				admin.getRegisteredBy(), 
				admin.getBranch(), 
				admin.getRoles());
	}
	
	public ResponseEntity<AdminDetailsResponse> getAnAdmin(Long adminId) {
		Admin admin = null;
		try {
			admin = adminRepo.findById(adminId).get();
		}catch (Exception e) {
			return ResponseEntity.status(404).body(new AdminDetailsResponse(e.getMessage(), null));
		}
				
		AnAdminDTO adminDTO = extractAnAdminDTO(admin);
		return ResponseEntity.ok()
				.body(new AdminDetailsResponse("Admin's Details Retrieved SUCCESSFULLY", adminDTO));
	}
}
