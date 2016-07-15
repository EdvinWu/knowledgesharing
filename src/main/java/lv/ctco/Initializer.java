package lv.ctco;

import lv.ctco.entities.KnowledgeSession;
import lv.ctco.entities.Person;
import lv.ctco.entities.Tag;
import lv.ctco.entities.UserRoles;
import lv.ctco.enums.SessionStatus;
import lv.ctco.repository.FeedbackRepository;
import lv.ctco.repository.PersonRepository;
import lv.ctco.repository.SessionRepository;
import lv.ctco.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Component
public class Initializer implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    FeedbackRepository feedbackRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        Person person = new Person();

        //person.setFullName(UUID.randomUUID().toString());
        person.setFullName("Kif Kroker");
        person.setPassword("0000");
        //person.setUserName(UUID.randomUUID().toString());
        person.setUserLogin("a");
        personRepository.save(person);

        KnowledgeSession session = new KnowledgeSession();
        session.setAuthor("Author");
        session.setTitle("Title");
        session.setDate(null);
        session.setVotes(5);
        session.setStatus(SessionStatus.DONE);
        List<Person> personsList = session.getUsers();
        personsList.add(person);
        session.setUsers(personsList);
        sessionRepository.save(session);


        Person testPerson = new Person();
        person.setFullName("SnoopySnoop");
        person.setUserLogin("SnoopDogg");
        person.setPassword(passwordEncoder.encode("123456789"));
        UserRoles userRoles = new UserRoles();
        userRoles.setRole("USER");
        person.setUserRoles(Arrays.asList(userRoles));
        personRepository.save(testPerson);

    }
}

