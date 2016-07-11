package lv.ctco.entities;


import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "Session")
public class KnowledgeSession {

    @Id
    @GeneratedValue
    private long id;
    private String title;
    private String author;
    private int votes;
    @OneToMany
    private List<Tag> tags;
    @OneToMany
    private List<Feedback> feedbacks;
    @ManyToMany
    @JoinTable(name = "session_user",
            joinColumns = @JoinColumn(name = "session_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<Person> users;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks.clear();
        if (feedbacks != null)
            this.feedbacks.addAll(feedbacks);
    }

    public List<Person> getUsers() {
        return users;
    }

    public void setUsers(List<Person> users) {
        this.users = users;
    }

    //TODO
    public boolean removeFeedback(long feedbackID) {
        return false;
    }

    //TODO
    public void addFeedback(Feedback feedback) {

    }
    //TODO
    public boolean updateFeedback(Feedback feedback) {
        return false;
    }
}
