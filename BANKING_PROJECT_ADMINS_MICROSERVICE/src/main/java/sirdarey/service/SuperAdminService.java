package sirdarey.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sirdarey.dto.AddRolesToAdminDTO;
import sirdarey.dto.AdminDetailsResponse;
import sirdarey.dto.AllAdminsDTO;
import sirdarey.dto.AnAdminDTO;
import sirdarey.entity.Admin;
import sirdarey.entity.Role;
import sirdarey.repo.AdminRepo;
import sirdarey.repo.RoleRepo;
import sirdarey.utils.Utils;

@Service
public class SuperAdminService {

	@Autowired private AdminRepo adminRepo;
	@Autowired private RoleRepo roleRepo;
	@Autowired private Utils utils;
	
	public ResponseEntity<AnAdminDTO> addNewAdmin(Admin newAdmin) {
		newAdmin.setAdminId(utils.generateRandom(7));
		newAdmin.setRegisteredBy("**TODO**");
		
		Admin savedAdmin = adminRepo.save(newAdmin); 
		
		AnAdminDTO response = utils.extractAnAdminDTO(savedAdmin);
				
		return ResponseEntity.status(201).body(response);
	}

	public ResponseEntity<AllAdminsDTO> getAllAdmins() {
		//Sorting by adminId in DESC order
		List<Admin> allAdmins = adminRepo.findAll(Sort.by(Direction.DESC, "adminId"));		
		List<AnAdminDTO> response = new ArrayList<>();
		
		allAdmins.forEach(admin -> {
			response.add(utils.extractAnAdminDTO(admin));
		});
		
		return ResponseEntity.ok().body(new AllAdminsDTO(response));
	}

	public ResponseEntity<AdminDetailsResponse> getAnAdmin(Long adminId) {
		return utils.getAnAdmin(adminId);
	}

	public ResponseEntity<AdminDetailsResponse> addRoleToAdmin(AddRolesToAdminDTO addRoleToAdmin) {
		Long adminId = addRoleToAdmin.getAdminId();
		List<Role> roles = addRoleToAdmin.getRoles();
		
		Admin admin = null;
		try {
			admin = adminRepo.findById(adminId).get();
		}catch (Exception e) {
			return ResponseEntity.status(404).body(new AdminDetailsResponse(e.getMessage(), null));
		}
		
		roles.forEach(role -> {
			role.setFk_admin_id(adminId);
			roleRepo.save(role);
		});
		
		admin.getRoles().addAll(roles);
		AnAdminDTO adminDTO = utils.extractAnAdminDTO(admin);
		return ResponseEntity.ok()
				.body(new AdminDetailsResponse("Role(s) Added to Admin SUCCESSFULLY", adminDTO));
	}

	public ResponseEntity<AdminDetailsResponse> updateAnAdmin(Admin updatedAdmin, Long adminId) {
		Admin admin = null;
		try {
			admin = adminRepo.findById(adminId).get();
		}catch (Exception e) {
			return ResponseEntity.status(404).body(new AdminDetailsResponse(e.getMessage(), null));
		}
		
		admin.setBranch(updatedAdmin.getBranch());
		admin.setName(updatedAdmin.getName());
		
		AnAdminDTO adminDTO = utils.extractAnAdminDTO(adminRepo.save(admin));
		return ResponseEntity.ok()
				.body(new AdminDetailsResponse("Admin Updated SUCCESSFULLY", adminDTO));
	}

	public String updateAdminEnabledStatus(Boolean enable, Long adminId) {
		Byte setStatus = 0; 
		String response;
		
		if (enable) {
			setStatus = 1;
			response = "Admin ENABLED Successfully";
		} else 
			response = "Admin DISabled Successfully";
		
		int updatedRow = adminRepo.updateAdminEnabledStatus(setStatus, adminId);
		if (updatedRow == 1)
			return response;
		return "An ERROR Occured";
	}

}
