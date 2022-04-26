package hibernate.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "position_hierarchy")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class PositionHierarchy {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "hierarchy_id")
    private int hierarchyId;
    @Column(name = "position_id")
    private int positionId;

    public PositionHierarchy(int id, int hierarchyId, int positionId) {
        this.id = id;
        this.hierarchyId = hierarchyId;
        this.positionId = positionId;
    }

    public PositionHierarchy(PositionHierarchy positionHierarchy) {
        this.id = positionHierarchy.id;
        this.hierarchyId = positionHierarchy.hierarchyId;
        this.positionId = positionHierarchy.positionId;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "position_hierarchy",
            joinColumns = @JoinColumn(name = "position_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id", referencedColumnName = "position_id"))
    private List<Users> users = new ArrayList<>();

    /* @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hierarchy_id", updatable = false, insertable = false)
    private Hierarchy hierarchy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", insertable = false, updatable = false)
    private Position position; */
}

