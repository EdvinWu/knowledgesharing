package lv.ctco.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_roles")
public class UserRoles {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "user_role")
    private String role;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "userRoles")
    private List<Person> persons = new ArrayList<>();

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
