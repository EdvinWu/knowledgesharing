package lv.ctco.entities;


import org.apache.catalina.Session;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class ScheduledSession {
    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    private KnowledgeSession session;
    private String place;
    @Column(nullable = false)
    private String date = LocalDateTime.now().toString();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public KnowledgeSession getSession() {
        return session;
    }

    public void setSession(KnowledgeSession session) {
        this.session = session;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
