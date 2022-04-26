package hibernate.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class HierarchyTree extends hibernate.entity.Hierarchy {
    private int id;
    private Integer parentId;
    private String degree;
    private String name;
    private List<HierarchyTree> list;

    public HierarchyTree() {
        list = new ArrayList<>();
    }

    public HierarchyTree(HierarchyTree hierarchy) {
        this.id = hierarchy.id;
        this.parentId = hierarchy.parentId;
        this.degree = hierarchy.degree;
        this.name = hierarchy.name;
    }

    public HierarchyTree(int id, Integer parentId, String degree, String name) {
        this.id = id;
        this.parentId = parentId;
        this.degree = degree;
        this.name = name;
    }

    public HierarchyTree(int id, Integer parentId, String degree, String name, List<HierarchyTree> list) {
        this.id = id;
        this.parentId = parentId;
        this.degree = degree;
        this.name = name;
        this.list = list;
    }

    @Override
    public String toString() {
        return "Hierarchy{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", degree='" + degree + '\'' +
                ", name='" + name + '\'' +
                ", list=" + list +
                '}';
    }
}
