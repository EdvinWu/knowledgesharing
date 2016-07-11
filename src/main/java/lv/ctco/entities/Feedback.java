package lv.ctco.entities;

import javax.persistence.*;


@Entity
public class Feedback {
    @Id
    @GeneratedValue
    private long id;
    private int rating;
    @ManyToOne
    private Person user;
    private String comment;

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

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
