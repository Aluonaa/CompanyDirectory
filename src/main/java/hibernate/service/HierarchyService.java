package hibernate.service;

import hibernate.entity.Hierarchy;
import hibernate.model.HierarchyTree;
import hibernate.repos.HierarchyRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HierarchyService {
    @Autowired
    private HierarchyRepos repo;

    public List<Hierarchy> listAll() {
        return repo.findAll();
    }

    public void save(Hierarchy hierarchy) {
        repo.save(hierarchy);
    }

    public Hierarchy get(int id) {
        if (repo.findById(id).isPresent()) {
            return repo.findById(id).get();
        } else return null;
    }

    public void delete(int id) {
        repo.deleteById(id);
    }

    public List<HierarchyTree> getAllTree() {
        List<Hierarchy> hierarchyList = repo.findAll().stream().sorted(Comparator.comparing(Hierarchy::getParentId,
                Comparator.nullsFirst(Comparator.naturalOrder()))).collect(Collectors.toList());
        List<HierarchyTree> hierarchies = new LinkedList<>();
        //Цикл копирования данных из Ентити Иерархии в новую модельную Иерархию
        for (Hierarchy hierarchy : hierarchyList) {
            HierarchyTree h = new HierarchyTree(
                    hierarchy.getId(), hierarchy.getParentId(), hierarchy.getDegree(), hierarchy.getName()
            );
            hierarchies.add(h);
        }
        //Создание списка содержащие элементы с parentId = null или 0
        List<HierarchyTree> depList = new LinkedList<>();
        for (HierarchyTree hierarchy : hierarchies) {
            if (hierarchy.getParentId() == null || hierarchy.getParentId() == 0) {
                depList.add(hierarchy);
            }
        }
        //Создание списка содержащие элементы с parentId
        List<HierarchyTree> hList = new LinkedList<>();
        for (HierarchyTree hierarchy : hierarchies) {
            if (hierarchy.getParentId() != null || hierarchy.getParentId() != 0) {
                hList.add(hierarchy);
            }
        }

        //Сортировка списка по ID, содержащие элементы с parentId
        List<HierarchyTree> sortedTree = hList.stream()
                .sorted(Comparator.comparing(HierarchyTree::getId))
                .collect(Collectors.toList());

        int idx = 0;
        //Основной цикл из двух департаментов
        for (HierarchyTree hierarchyTree : depList) {
            List<HierarchyTree> temp = new LinkedList<>();

            for (int hierarchyFullList = 0; hierarchyFullList < sortedTree.size(); hierarchyFullList++) {
                if (sortedTree.get(hierarchyFullList).getParentId() == hierarchyTree.getId()) {
                    HierarchyTree h = new HierarchyTree(
                            sortedTree.get(hierarchyFullList).getId(),
                            sortedTree.get(hierarchyFullList).getParentId(),
                            sortedTree.get(hierarchyFullList).getDegree(),
                            sortedTree.get(hierarchyFullList).getName()
                    );
                    temp.add(h);
                    //Запоминаю индекс элемента в котором совпадают parentId элемента
                    //с ID департамента
                    idx = sortedTree.indexOf(sortedTree.get(hierarchyFullList));
                }
                //Проверяю parentId с ID с элемента который совпал в предидущей проверке
                if (sortedTree.get(hierarchyFullList).getParentId() == sortedTree.get(idx).getId()) {
                    HierarchyTree h = new HierarchyTree(
                            sortedTree.get(hierarchyFullList).getId(),
                            sortedTree.get(hierarchyFullList).getParentId(),
                            sortedTree.get(hierarchyFullList).getDegree(),
                            sortedTree.get(hierarchyFullList).getName()
                    );
                    temp.add(h);
                    idx = sortedTree.indexOf(sortedTree.get(hierarchyFullList));
                }
            }
            hierarchyTree.setList(temp);
        }
        return depList;
    }
}
