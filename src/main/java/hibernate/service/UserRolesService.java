package hibernate.service;

import hibernate.entity.UserRoles;
import hibernate.repos.UserRolesRepos;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserRolesService {
    @Autowired
    private UserRolesRepos repo;

    public List<UserRoles> listAll() {
        return repo.findAll();
    }

    public void save(UserRoles userRoles) {
        repo.save(userRoles);
    }

    public UserRoles get(int id) {
        return repo.findById(id).get();
    }

    public void delete(int id) {
        repo.deleteById(id);
    }
}
