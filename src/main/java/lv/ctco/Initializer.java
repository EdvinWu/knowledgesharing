package lv.ctco;

import lv.ctco.entities.KnowledgeSession;
import lv.ctco.entities.Person;
import lv.ctco.entities.Tag;
import lv.ctco.entities.UserRole;
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
        UserRole userRole = new UserRole();
        userRole.setRole("USER");

        Person person = new Person();
        person.setFullName("Kif Kroker");
        person.setPassword("0000");
        person.setUserLogin("Kif");
        person.setUserRoles(Arrays.asList(userRole));
//        personRepository.save(person);

        Person person2 = new Person();
        person2.setFullName("SnoopySnoop");
        person2.setUserLogin("SnoopDogg");
        person2.setPassword("1234");
        person2.setUserRoles(Arrays.asList(userRole));
        personRepository.save(Arrays.asList(person, person2));

        KnowledgeSession session = new KnowledgeSession();
        session.setAuthor("Zapp Brannigan");
        session.setTitle("Learning");
        session.setDate(null);
        session.setVotes(5);

//        Tag tag = new Tag();
//        tag.setName("Captaining 101");
//        session.setTags(Arrays.asList(tag));
//
//        Feedback feedback = new Feedback();
//        feedback.setComment("Disgusting");
//        feedback.setPersonName("Leela");
//        feedback.setRating(2);
//
//        session.setFeedbacks(Arrays.asList(feedback));
//        session.setPersons(Arrays.asList(person));

        sessionRepository.save(session);
    }
}

