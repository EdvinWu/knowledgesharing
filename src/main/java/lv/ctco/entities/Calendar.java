package lv.ctco.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Optional;

@Entity
public class Calendar {
    @Id
    @GeneratedValue
    private long id;
    @OneToMany
    private List<ScheduledSession> scheduledSessions;

    public List<ScheduledSession> getScheduledSessions() {
        return scheduledSessions;
    }

    public void setScheduledSessions(List<ScheduledSession> scheduledSessions) {
        this.scheduledSessions = scheduledSessions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void addSession(ScheduledSession session) {
        scheduledSessions.add(session);
    }

    public boolean removeSession(long id) {
        Optional<ScheduledSession> sessionToRemove = scheduledSessions.stream()
                .filter(s -> s.getId() == id)
                .findAny();
        return sessionToRemove.isPresent() && scheduledSessions.remove(sessionToRemove.get());
    }

    public boolean updateSession(ScheduledSession scheduledSession) {
        Optional<ScheduledSession> sessionToUpdate = scheduledSessions.stream()
                .filter(s -> s.getId() == id)
                .findAny();
        if (sessionToUpdate.isPresent()) {
            sessionToUpdate.get().setSessionName(scheduledSession.getSessionName());
            return true;
        }
        return false;
    }
}
