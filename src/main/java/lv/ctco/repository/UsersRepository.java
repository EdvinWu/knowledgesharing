package lv.ctco.repository;

import lv.ctco.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Person,Long>{
}
