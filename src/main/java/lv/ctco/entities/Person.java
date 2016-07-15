package lv.ctco.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue
    private long id;
    private String fullName;
    @Column (name = "LOGIN", unique = true, nullable = false)
    private String login;
    @Column (name = "PASSWORD", nullable = false)
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "person_roles",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "user_role_id"))
    private List<UserRole> userRoles = new ArrayList<>();

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String userName) {
        this.login = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
