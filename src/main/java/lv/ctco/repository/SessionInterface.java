package lv.ctco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionInterface extends JpaRepository<KnowledgeSession,Long> {
}
