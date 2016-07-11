package lv.ctco.repository;

import lv.ctco.entities.KnowledgeSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionInterface extends JpaRepository<KnowledgeSession,Long> {
}
