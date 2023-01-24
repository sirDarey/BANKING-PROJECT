package sirdarey.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, Long>{

	User findByUserId(Long userId); 
	
	@Modifying
	@Query(nativeQuery = true, 
			value = "UPDATE users SET full_name = ?1 WHERE user_id = ?2")
	void updateOnlyName(String newName, Long userId);

	@Modifying
	@Query(nativeQuery = true, 
			value = "UPDATE users SET user_enabled = ?1 WHERE user_id = ?2")
	void updateEnableStatus(int setStatus, Long userId);
	
}
