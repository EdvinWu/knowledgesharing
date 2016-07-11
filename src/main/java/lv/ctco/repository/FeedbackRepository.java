package lv.ctco.repository;

import lv.ctco.entities.KnowledgeSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<KnowledgeSession,Long> {
}
