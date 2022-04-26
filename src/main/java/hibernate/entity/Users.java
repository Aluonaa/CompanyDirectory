package hibernate.entity;

import hibernate.handler.StringToInteger;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "web_users")
@Getter
@Setter
@NoArgsConstructor
public class Users implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(fetch = FetchType.LAZY)
    private int userId;
    @Column(name = "user_name")
    private String userName;
    @Convert(converter = StringToInteger.class)
    @Column(name = "user_role_ref")
    private String userRoleRef;
    @Column(name = "user_pwd")
    @ColumnTransformer(write = "MD5(?)")
    private String userPwd;
    @Column(name = "display_name")
    private String displayName;
    @Column(name = "position_id")
    private int positionRef;
    @Column(name = "salary")
    private double salary;
    @Column(name = "birth_date")
    private Date birthDate;

    public Users(int userId, String userName, String userRoleRef, String userPwd, String displayName
            , int positionRef, double salary, Date birthDate) {
        this.userId = userId;
        this.userName = userName;
        this.userPwd = userPwd;
        this.userRoleRef = userRoleRef;
        this.displayName = displayName;
        this.positionRef = positionRef;
        this.salary = salary;
        this.birthDate = birthDate;
    }

    public Users(Users users) {
        this.userId = users.userId;
        this.userName = users.userName;
        this.userPwd = users.userPwd;
        this.userRoleRef = users.userRoleRef;
        this.displayName = users.displayName;
        this.positionRef = users.positionRef;
        this.salary = users.salary;
        this.birthDate = users.birthDate;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userRoleRef=" + userRoleRef +
                ", userPwd='" + userPwd + '\'' +
                ", displayName='" + displayName + '\'' +
                ", positionRef=" + positionRef +
                ", salary=" + salary +
                ", birthDate=" + birthDate +
                ", position=" + positionRef +
                '}';
    }
}
