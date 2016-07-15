package lv.ctco.entities;

import javax.persistence.*;


@Entity
public class Feedback {
    @Id
    @GeneratedValue
    private long id;
    private int rating;
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "session_id")
    private KnowledgeSession session;

    public KnowledgeSession getSession() {
        return session;
    }

    public void setSession(KnowledgeSession session) {
        this.session = session;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
