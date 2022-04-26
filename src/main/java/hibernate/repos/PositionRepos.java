package hibernate.repos;

import hibernate.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepos extends JpaRepository<Position, Integer> {
}
