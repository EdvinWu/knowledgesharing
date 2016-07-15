package lv.ctco.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
    @Column(name = "pass")
    private String password;

    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "person_roles",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "usre_role_id"))
    private List<UserRoles> userRoles = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, mappedBy="users")
    private List<KnowledgeSession> sessions = new ArrayList<>();

    public List<KnowledgeSession> getSessions() {
        return sessions;
    }

    public void setSessions(List<KnowledgeSession> sessions) {
        this.sessions = sessions;
    }

    public List<UserRoles> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRoles> userRoles) {
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

}
