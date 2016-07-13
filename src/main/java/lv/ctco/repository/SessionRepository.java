package lv.ctco.repository;

import lv.ctco.entities.KnowledgeSession;
import lv.ctco.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SessionRepository extends JpaRepository<KnowledgeSession,Long> {
}
