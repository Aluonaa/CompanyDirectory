package hibernate.controller;

import hibernate.entity.Hierarchy;
import hibernate.entity.PositionHierarchy;
import hibernate.service.HierarchyService;
import hibernate.service.PositionHierarchyService;
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


@Controller
public class HierarchyController {
    @Autowired
    private HierarchyService hierarchyService;
    @Autowired
    private PositionHierarchyService positionHierarchyService;

    @RequestMapping("/allHierarchy")
    public String allHierarchy(Model model) {
        if (UserController.getUserData() == null) {
            return "redirect:/";
        }
        //Сортировка по ID
        List<Hierarchy> listHierarchy = hierarchyService.listAll()
                .stream().sorted(Comparator.comparing(Hierarchy::getParentId))
                .collect(Collectors.toList());
        model.addAttribute("user_attr", UserController.getUserData());
        model.addAttribute("listHierarchy", listHierarchy);
        return "hierarchy_list";
    }

    @RequestMapping("/newHierarchy")
    public String showNewUsersPage(Model model) {
        if (UserController.getUserData() == null) {
            return "redirect:/";
        }
        Hierarchy hierarchy = new Hierarchy();
        List<Hierarchy> positionHierarchy = hierarchyService.listAll().stream()
                .sorted(Comparator.comparing(Hierarchy::getParentId)).collect(Collectors.toList());
        model.addAttribute("user_attr", UserController.getUserData());
        model.addAttribute("positionHierarchy", positionHierarchy);
        model.addAttribute("hierarchy", hierarchy);
        return "new_hierarchy";
    }

    @RequestMapping(value = "/saveHierarchy", method = RequestMethod.POST)
    public String saveHierarchy(@ModelAttribute("hierarchy") Hierarchy hierarchy) {
        if (UserController.getUserData() == null) {
            return "redirect:/";
        }
        hierarchyService.save(hierarchy);
        return "redirect:/allHierarchy";
    }

    //Сохраняет новые данные
    @PostMapping(value = "/saveHierarchy/{id}")
    public String updateHierarchy(@PathVariable("id") int id, @Valid Hierarchy hierarchy, BindingResult result, Model model) {
        if (hierarchy == null) {
            return "redirect:/";
        }
        if (result.hasErrors()) {
            hierarchy.setId(id);
            return "redirect:/";
        }
        hierarchyService.save(hierarchy);

        return "redirect:/allHierarchy";
    }

    @RequestMapping("/editHierarchy/{id}")
    public ModelAndView showEditHierarchyPage(@PathVariable(name = "id") int id, Model model) {
        if (UserController.getUserData() == null) {
            return new ModelAndView("redirect:/");
        }
        ModelAndView mav = new ModelAndView("edit_hierarchy");
        Hierarchy hierarchy = hierarchyService.get(id);
        List<PositionHierarchy> positionHierarchy = positionHierarchyService.listAll();
        model.addAttribute("positionHierarchy", positionHierarchy);
        model.addAttribute("user_attr", UserController.getUserData()); // ВТФ2
        mav.addObject("hierarchy", hierarchy);
        return mav;
    }

    @RequestMapping("/deleteHierarchy/{id}")
    public String deleteHierarchy(@PathVariable(name = "id") int id) {
        if (UserController.getUserData() == null) {
            return "redirect:/";
        }
        hierarchyService.delete(id);
        return "redirect:/allHierarchy";
    }

//    @GetMapping("/showTree")
//    public String showTree(Model model) {
//        if (UserController.getUserData() == null) {
//            return "redirect:/";
//        }
//        model.addAttribute("tree", hierarchyService.getAllTree());
//        model.addAttribute("user_attr", UserController.getUserData());
//        return "tree";
//    }
}

