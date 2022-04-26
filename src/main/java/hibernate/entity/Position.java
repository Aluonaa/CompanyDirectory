package hibernate.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Position {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="job_name")
    private String jobName;

    public Position(int id, String jobName) {
        this.id = id;
        this.jobName = jobName;
    }
    public Position(Position position) {
        this.id = position.id;
        this.jobName = position.jobName;
    }
}

