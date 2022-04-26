package hibernate.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "hierarchy")
@Getter
@Setter
@NoArgsConstructor
public class Hierarchy {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "parent_id")
    private Integer parentId;
    @Column(name = "degree")
    private String degree;
    @Column(name = "name")
    private String name;

    public Hierarchy(int id, int parentId, String degree, String name) {
        this.id = id;
        this.parentId = parentId;
        this.degree = degree;
        this.name = name;
    }

    public Hierarchy(Hierarchy hierarchy) {
        this.id = hierarchy.id;
        this.parentId = hierarchy.parentId;
        this.degree = hierarchy.degree;
        this.name = hierarchy.name;
    }
}

