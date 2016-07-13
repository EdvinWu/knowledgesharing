package lv.ctco.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    @Column(name = "username")
    private String userName;
    @Column(name = "pass", nullable = false)
    private String password;
    @ManyToMany(mappedBy = "users")
    private List<KnowledgeSession> attended;
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private List<UserRoles> userRoles = new ArrayList<>();

    public List<UserRoles> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRoles> userRoles) {
        if (userRoles == null) return;
        this.userRoles.clear();
        this.userRoles.addAll(userRoles);
        userRoles.forEach(u -> u.setPerson(this));
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<KnowledgeSession> getAttended() {
        return attended;
    }

    public void setAttended(List<KnowledgeSession> attended) {
        this.attended = attended;
    }


}
