package hibernate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Мейн контроллер который перехватывает localhost:8080/
 * после перенаправляет на страницу логина
 */
@RestController
public class MainController {
    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("login");
    }

    @RequestMapping("/logout")
    public ModelAndView logout() {
        return new ModelAndView("redirect:/");
    }
}
