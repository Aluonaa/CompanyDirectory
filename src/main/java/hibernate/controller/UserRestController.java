package hibernate.controller;

import hibernate.entity.PositionHierarchy;
import hibernate.entity.Users;
import hibernate.model.HierarchyTree;
import hibernate.service.HierarchyService;
import hibernate.service.PositionHierarchyService;
import hibernate.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class UserRestController {
    @Autowired
    private UsersService service;
    @Autowired
    private PositionHierarchyService positionHierarchyService;
    @Autowired
    private HierarchyService hierarchyService;

    @GetMapping(value = "/users", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Users> getAllUsers() {
        return service.listAll();
    }

    @GetMapping(value = "/positionsHierarchy", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<PositionHierarchy> getAllPositionHierarchy() {
        return positionHierarchyService.listAll();
    }

    @GetMapping(value = "/showTree", produces = APPLICATION_JSON_VALUE)
    public List<HierarchyTree> showTree() {
        return hierarchyService.getAllTree();
    }
}
