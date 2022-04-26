package hibernate.service;

import hibernate.entity.Position;
import hibernate.repos.PositionRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PositionService {
    @Autowired
    private PositionRepos repo;
    public List<Position> listAll() {
        return repo.findAll();
    }
    public void save(Position position) {
        repo.save(position);
    }
    public Position get(int id) {
        return repo.findById(id).get();
    }
    public void delete(int id) {
        repo.deleteById(id);
    }
}
