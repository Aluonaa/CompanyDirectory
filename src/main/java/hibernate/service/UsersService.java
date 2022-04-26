package hibernate.service;

import hibernate.entity.UserRoles;
import hibernate.entity.Users;
import hibernate.handler.PasswordHandler;
import hibernate.repos.UserRolesRepos;
import hibernate.repos.UsersRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Transactional
public class UsersService {
    @Autowired
    private UsersRepos repo;
    @Autowired
    private UserRolesRepos userRolesRepos;

    public List<Users> listAll() {
        return repo.findAll();
    }

    public void save(Users users) {
        repo.save(users);
    }

    public Users get(int id) {
        //Проверяем если пользователь по ID есть- если есть возвращаем, если нету - возвращаем NULL
        if (repo.findById(id).isPresent()) {
            return repo.findById(id).get();
        }
        return null;
    }

    public void delete(int id) {
        repo.deleteById(id);
    }

    //Метод который, по идее ищёт пользователя по его имени (логину)
    public Users findUsersByUserNameAndUserPwd(String username, String password) {
        Users user = repo.findUsersByUserName(username);
        if (user != null) {
            //если пользователь есть, вытаскиваю его пароль (зашиврованный)
            String userPwd = user.getUserPwd();
            //Сравнение пароля (в открытом виде) который приходит проходит шифрование уровня MD5 с тем что есть в БД (зашифрованный)
            //если они схожи - возвращаю данные пользователя, в противном случае возвращаю пользователя - а там по идее будет NULL
            if (Objects.equals(PasswordHandler.MD5(password), userPwd)) {
                return user;
            }
        }
        return user;
    }

    public List<Users> getAll() {
        List<Users> usersList = repo.findAll();
        List<Users> users = new ArrayList<>();
        for (Users u : usersList) {
            String roleName = null;
            if (Integer.parseInt(u.getUserRoleRef()) != 0) {
                if (userRolesRepos.findById(Integer.parseInt(u.getUserRoleRef())).isPresent()) {
                    roleName = userRolesRepos.findById(Integer.parseInt(u.getUserRoleRef())).get().getRoleName();
                }
            }
            Users user = new Users(u.getUserId(), u.getUserName(), roleName, u.getUserPwd(), u.getDisplayName()
                    , u.getPositionRef(), u.getSalary(), u.getBirthDate());
            users.add(user);
        }
        return users;
    }
}
