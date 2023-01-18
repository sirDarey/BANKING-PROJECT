package sirdarey.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sirdarey.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Long>{

	Role findByName(String name);
}
