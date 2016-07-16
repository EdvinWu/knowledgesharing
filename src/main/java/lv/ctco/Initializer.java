package lv.ctco;

import lv.ctco.entities.KnowledgeSession;
import lv.ctco.entities.Person;
import lv.ctco.entities.Tag;
import lv.ctco.entities.UserRole;
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

import java.util.Arrays;


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
        UserRole userRole = new UserRole();
        userRole.setRole("USER");
        UserRole adminRole = new UserRole();
        adminRole.setRole("ADMIN");

        Person person = new Person();
        person.setFullName("Kif Kroker");
        person.setPassword("0000");
        person.setLogin("Kif");
        person.setUserRoles(Arrays.asList(userRole));
//        personRepository.save(person);

        Person person2 = new Person();
        person2.setFullName("Zapp Brannigan");
        person2.setLogin("Zapp");
        person2.setPassword("1234");
        person2.setUserRoles(Arrays.asList(adminRole));
        personRepository.save(Arrays.asList(person, person2));

        KnowledgeSession session = new KnowledgeSession();
        session.setAuthor("Zapp Brannigan");
        session.setTitle("Learning");
        session.setStatus(SessionStatus.PENDING);
        session.setDate(null);
        session.setVotes(5);

        Tag tag = new Tag();
        tag.setName("java");
        session.setTags(Arrays.asList(tag));


//        Feedback feedback = new Feedback();
//        feedback.setComment("Disgusting");
//        feedback.setPersonName("Leela");
//        feedback.setRating(2);

//        session.setFeedbacks(Arrays.asList(feedback));
//        session.setPersons(Arrays.asList(person));

        sessionRepository.save(session);
    }
}

