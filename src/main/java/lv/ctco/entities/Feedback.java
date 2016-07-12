package lv.ctco.entities;

import javax.persistence.*;


@Entity
public class Feedback {
    @Id
    @GeneratedValue
    private long id;
    private int rating;
    private long personID;
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

    public long getPersonID() {
        return personID;
    }

    public void setPersonID(long personID) {
        this.personID = personID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
