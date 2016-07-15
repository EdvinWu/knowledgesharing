package lv.ctco.repository;

import lv.ctco.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonRepository extends JpaRepository<Person,Long>{
    @Query("select p from Person p where p.login = ?1")
    Person findUserByLogin(String login);
}
