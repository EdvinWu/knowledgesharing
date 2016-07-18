package lv.ctco.entities;

import lv.ctco.enums.SessionStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity(name = "Session")
@Table(name = "session")
public class KnowledgeSession {
    @Id
    @GeneratedValue
    private long id;
    private String title;
    private String author;
    private String description;
    private int votes;
    private SessionStatus status;
    private LocalDateTime date;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "session_tag",
            joinColumns = @JoinColumn(name = "session_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Feedback> feedbacks = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "session_user_attending",
            joinColumns = @JoinColumn(name = "attending_session_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "attending_user_id", referencedColumnName = "id"))
    private List<Person> personsAttending = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "session_user_voted",
            joinColumns = @JoinColumn(name = "vote_session_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "voted_user_id", referencedColumnName = "id"))
    private List<Person> personsVoted = new ArrayList<>();

    public List<Person> getPersonsAttending() {
        return personsAttending;
    }

    public void setPersonsAttending(List<Person> personsAttending) {
        this.personsAttending = personsAttending;
    }

    public List<Person> getPersonsVoted() {
        return personsVoted;
    }

    public void setPersonsVoted(List<Person> personsVoted) {
        this.personsVoted = personsVoted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public void setStatus(SessionStatus status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

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

    public boolean removeFeedback(long feedbackID) {
        Optional<Feedback> feedback = feedbacks.stream()
                .filter(f -> f.getId() == id)
                .findAny();
        return feedback.isPresent() && feedbacks.remove(feedback.get());
    }

    public void addFeedback(Feedback feedback) {
        this.feedbacks.add(feedback);
    }

    public boolean updateFeedback(Feedback leavedFeedback) {
        Optional<Feedback> feedback = feedbacks.stream()
                .filter(f -> f.getId() == leavedFeedback.getId())
                .findAny();
        if (feedback.isPresent()){
            feedback.get().setId(leavedFeedback.getId());
            return true;
        }
        return false;
    }
    public void deleteDate(){
       date = null;
    }

    public boolean removeTag(long tagID) {
        Optional<Tag> tag = tags.stream()
                .filter(t -> t.getId() == id)
                .findAny();
        return tag.isPresent() && tags.remove(tag.get());
    }
}
