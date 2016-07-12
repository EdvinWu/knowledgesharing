package lv.ctco.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Person {
    @Id
    @GeneratedValue
    private long id;
    private String fullName;
    @Column(unique=true,nullable = false)
    private String userName;
    @Column(nullable = false)
    private String password;
    @ManyToMany(mappedBy = "users")
    private List<KnowledgeSession> attended;
    @OneToOne
    private Calendar calendar;

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

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
}
