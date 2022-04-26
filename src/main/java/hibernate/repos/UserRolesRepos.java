package hibernate.repos;

import hibernate.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRolesRepos extends JpaRepository<UserRoles,Integer> {
}
