package hibernate.service;

import hibernate.entity.Hierarchy;
import hibernate.entity.Position;
import hibernate.entity.PositionHierarchy;

import hibernate.repos.PositionHierarchyRepos;
import hibernate.repos.PositionRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PositionHierarchyService {

    @Autowired
    private PositionHierarchyRepos repo;
    @Autowired
    private PositionRepos positionRepos;

    public List<PositionHierarchy> listAll() {
        return repo.findAll();
    }

    public void save(PositionHierarchy positionHierarchy) {
        repo.save(positionHierarchy);
    }

    public PositionHierarchy get(int id) {
        if (repo.findById(id).isPresent()) {
            return repo.findById(id).get();
        } else return null;
    }

    public void delete(int id) {
        repo.deleteById(id);
    }

    public List<Position> getAllJobList() {
        return positionRepos.findAll().stream().sorted(Comparator.comparing(Position::getId)).collect(Collectors.toList());
    }
}
