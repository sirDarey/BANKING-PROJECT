package sirdarey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sirdarey.dto.AddRolesToAdminDTO;
import sirdarey.dto.AdminDetailsResponse;
import sirdarey.dto.AllAdminsDTO;
import sirdarey.dto.AnAdminDTO;
import sirdarey.entity.Admin;
import sirdarey.service.SuperAdminService;

@RestController
@RequestMapping("/bank/sadmins")
public class SuperAdminController {

	@Autowired SuperAdminService adminService;
	
	//ADD NEW ADMIN
	
	@PostMapping
	public ResponseEntity<AnAdminDTO> addNewAdmin (@RequestBody Admin newAdmin) {
		return adminService.addNewAdmin(newAdmin);
	}
	
	//GET ALL ADMINS FROM ALL BRANCHES
	/**************IMPLEMENT SORTING TO GET ADMINS BY BRANCH AND ROLES *************/
	@GetMapping
	public ResponseEntity<AllAdminsDTO> getAllAdmins () {
		return adminService.getAllAdmins();
	}
	
	//GET AN ADMIN FROM ANY BRANCH
	
	@GetMapping("/{adminId}")
	public ResponseEntity<AdminDetailsResponse> getAnAdmin (@PathVariable Long adminId) {
		return adminService.getAnAdmin(adminId);
	}
	
	//ADD ROLE TO ADMIN
	
	@PostMapping ("/addroletoadmin")
	public ResponseEntity<AdminDetailsResponse> addRoleToAdmin (@RequestBody AddRolesToAdminDTO addRoleToAdmin) {
		return adminService.addRoleToAdmin(addRoleToAdmin);
	}
	
	//UPDATE AN ADMIN
	
	@PutMapping("/{adminId}")
	public ResponseEntity<AdminDetailsResponse> updateAnAdmin (@RequestBody Admin updatedAdmin, @PathVariable Long adminId) {
		return adminService.updateAnAdmin(updatedAdmin, adminId);
	}
	
	//UPDATE ONLY ADMIN ENABLED STATUS
	
	@PutMapping("/{adminId}/isenabled")
	public String updateAdminEnabledStatus (@RequestParam Boolean enable, @PathVariable Long adminId) {
		return adminService.updateAdminEnabledStatus(enable, adminId);
	}
}
