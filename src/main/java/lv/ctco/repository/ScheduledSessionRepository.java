package lv.ctco.repository;

import lv.ctco.entities.KnowledgeSession;
import lv.ctco.entities.ScheduledSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduledSessionRepository extends JpaRepository<ScheduledSession,Long> {
}
