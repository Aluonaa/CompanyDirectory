package hibernate.repos;

import hibernate.entity.Hierarchy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HierarchyRepos extends JpaRepository<Hierarchy, Integer> {
}
