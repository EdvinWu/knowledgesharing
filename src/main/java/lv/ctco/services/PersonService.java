package lv.ctco.services;

import lv.ctco.entities.Person;
import lv.ctco.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public Person getAuthenticatedPerson() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = ((User)auth.getPrincipal()).getUsername();

        return personRepository.findUserByLogin(userName);
    }


}
