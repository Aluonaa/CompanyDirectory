package hibernate.repos;

import hibernate.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepos extends JpaRepository<Users, Integer> {
    //Подпись метода, его имплементация в сервисе
    Users findUsersByUserName(String username);
}
