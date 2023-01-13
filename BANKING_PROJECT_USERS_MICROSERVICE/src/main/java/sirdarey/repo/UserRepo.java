package sirdarey.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import sirdarey.entity.User;

public interface UserRepo extends JpaRepository<User, Long>{

	@Modifying
	@Query(nativeQuery = true, 
			value = "UPDATE user SET full_name = ?1 WHERE user_id = ?2")
	void updateOnlyName(String newName, Long userId);

	@Modifying
	@Query(nativeQuery = true, 
			value = "UPDATE user SET user_enabled = ?1 WHERE user_id = ?2")
	void updateEnableStatus(int setStatus, Long userId);
	
}
