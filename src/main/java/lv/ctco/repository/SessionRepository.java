package lv.ctco.repository;

import lv.ctco.entities.KnowledgeSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SessionRepository extends JpaRepository<KnowledgeSession,Long> {
//    @Query("select s from Session s join s.tags t where UPPER(t.name) LIKE CONCAT('%', CONCAT(UPPER(?1), '%'))")
//    List<KnowledgeSession> findByTag(String tag);
}
