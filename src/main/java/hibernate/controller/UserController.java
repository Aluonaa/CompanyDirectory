package hibernate.controller;

import hibernate.entity.Position;
import hibernate.entity.PositionHierarchy;
import hibernate.entity.Users;
import hibernate.service.PositionHierarchyService;
import hibernate.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Данный контролёр предназначен для работы с объектом типа Users(пользователь)
 * Так же на фоазе логина делается запрос, а вдруг ли такой пользователь есть
 * И если есть - считывается пароль и делается сравнение их MD5 ключей
 * если идентичны то к @user присваивается то что мы получили для
 * дальнейшего отображения displayName в navbar
 */
@Controller
public class UserController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private PositionHierarchyService positionHierarchyService;
    /*    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${email.send.to}")
    private String to;
    @Value("${email.send.subject}")
    private String subject;
    @Value("${email.send.text}")
    private String text; */
    private static Users user = null;

    @RequestMapping("/login")
    public String login(@RequestParam(required = false, name = "username") String username,
                        @RequestParam(required = false, name = "password") String password,
                        Model model) {
        user = usersService.findUsersByUserNameAndUserPwd(username, password);
        model.addAttribute("user_attr", user);
        return "index";
    }

    @RequestMapping("/index")
    public String backToIndex(Model model) {
        if (user == null) {
            return "redirect:/";
        } else {
            model.addAttribute("user_attr", user);
            return "index";
        }
    }

    //Возвращает весь список юзеров
    @RequestMapping("/allUsers")
    public String allUsers(Model model) {
        if (user == null) {
            return "redirect:/";
        }
        //Сортирую по ID
        List<Users> listUsers = usersService.getAll().stream()
                .sorted(Comparator.comparing(Users::getUserId))
                .collect(Collectors.toList());
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("user_attr", user);
        return "users_list";
    }

    //Открывает страницу для добавления нового пользователя
    @RequestMapping("/new")
    public String showNewUsersPage(Model model) {
        if (user == null) {
            return "redirect:/";
        }
        Users users = new Users();
        List<PositionHierarchy> positionHierarchy = positionHierarchyService.listAll();
        List<Position> allJobList = positionHierarchyService.getAllJobList();
        model.addAttribute("jobList", allJobList);
        model.addAttribute("positionHierarchy", positionHierarchy);
        model.addAttribute("users", users);
        model.addAttribute("user_attr", user);
//        model.addAttribute("user_attr", user);
        return "new_users";
    }

    //Сохраняет нового пользователя
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("users") Users users) {
        if (user == null) {
            return "redirect:/";
        }
        usersService.save(users);
        return "redirect:/allUsers";
    }

    //Сохраняет новые данные пользователя
    @PostMapping(value = "/save/{userId}")
    public String updateUser(@PathVariable("userId") int userId, @Valid Users users, BindingResult result, Model model) {
        if (user == null) {
            return "redirect:/";
        }
        if (result.hasErrors()) {
            users.setUserId(userId);
            return "redirect:/";
        }
        usersService.save(users);
/*        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        javaMailSender.send(simpleMailMessage); */
        return "redirect:/allUsers";
    }

    //Возвращает пользователя по Айдишнику для редактирования
    @RequestMapping("/edit/{userId}")
    public ModelAndView showEditUsersPage(@PathVariable(name = "userId") int userId, Model model) {
        if (user == null) {
            return new ModelAndView("redirect:/");
        }
        ModelAndView mav = new ModelAndView("edit_users");
        Users users = usersService.get(userId);
        List<PositionHierarchy> positionHierarchy = positionHierarchyService.listAll();
        List<Position> allJobList = positionHierarchyService.getAllJobList();
        model.addAttribute("positionHierarchy", positionHierarchy);
        mav.addObject("users", users).addObject("jobList", allJobList)
                .addObject("user_attr", user);
        return mav;
    }

    //Удаляет пользователя через его айдишник
    @RequestMapping("/delete/{userId}")
    public String deleteUsers(@PathVariable(name = "userId") int userId, Model model) {
        if (user == null) {
            return "redirect:/";
        }
        usersService.delete(userId);
        model.addAttribute("user_attr", user);
        return "redirect:/allUsers";
    }

    public static Users getUserData() {
        return user;
    }
}
