package lv.ctco.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Tag {
    @Id
    @GeneratedValue
    private long id;
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "tags")
    private List<KnowledgeSession> sessions = new ArrayList<>();

    public List<KnowledgeSession> getSessions() {
        return sessions;
    }

    public void setSessions(List<KnowledgeSession> sessions) {
        this.sessions = sessions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
