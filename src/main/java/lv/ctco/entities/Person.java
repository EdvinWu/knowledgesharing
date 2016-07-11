package lv.ctco.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;
@Entity
public class Person {
    @Id
    @GeneratedValue
    long id;
    String fullName;
    String userName;
    String password;
    List<KnowledgeSession> attended;
    //TODO
    //Calendar

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
