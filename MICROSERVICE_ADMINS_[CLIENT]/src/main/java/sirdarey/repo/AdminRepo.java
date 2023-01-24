package sirdarey.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import sirdarey.entity.Admin;

public interface AdminRepo extends JpaRepository<Admin, Long>{

	@Modifying
	@Query (nativeQuery = true, 
			value = "UPDATE admin SET admin_enabled = ?1 WHERE admin_id = ?2")
	int updateAdminEnabledStatus(Byte setStatus, Long adminId);
	
	List<Admin> findAllByBranch(String branch);

}
