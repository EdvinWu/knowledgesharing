package lv.ctco.controllers;

import lv.ctco.entities.KnowledgeSession;
import lv.ctco.entities.Person;
import lv.ctco.repository.PersonRepository;
import lv.ctco.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.List;

import static lv.ctco.Consts.*;

@RestController
@RequestMapping(SESSION_PATH)
public class SessionController {
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    PersonRepository personRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllSessions() {
        List<KnowledgeSession> sessions = sessionRepository.findAll();
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getSessionById(@PathVariable("id") long id) {
        if (sessionRepository.exists(id)) {
            KnowledgeSession session = sessionRepository.findOne(id);
            return new ResponseEntity<>(session, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @RequestMapping(path = TAG_PATH, method = RequestMethod.GET)
//     public ResponseEntity<?> getSessionByTag(@RequestParam String name) {
//        List<KnowledgeSession> sessions;
//        sessions = sessionRepository.findByTag(name);
//        if (sessions != null) {
//            return new ResponseEntity<>(sessions, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @RequestMapping(path = "/{idUser}/{idSession}",method = RequestMethod.POST)
    public ResponseEntity<?> addUserToSession(@PathVariable ("idUser") long idUser,
                                              @PathVariable ("idSession") long idSession) {
        if(!sessionRepository.exists(idSession)|| !personRepository.exists(idUser)) {
            return new ResponseEntity<>("Session or user not found",HttpStatus.NOT_FOUND);
        }

        Person person = personRepository.findOne(idUser);
        KnowledgeSession session = sessionRepository.findOne(idSession);
        List<Person> personList = session.getPersons();
        personList.add(person);
        session.setPersons(personList);
        sessionRepository.save(session);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @RequestMapping(path = "/{id}/attends", method = RequestMethod.GET)
    public ResponseEntity<?> getPersonsBySession(@PathVariable("id") long id) {
        KnowledgeSession session = sessionRepository.findOne(id);
        List<Person> persons = session.getPersons();
        if (persons != null) {
            return new ResponseEntity<>(persons, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addSession(@RequestBody KnowledgeSession session, UriComponentsBuilder b) {
        sessionRepository.save(session);
        //todo add only pending
        UriComponents uriComponents =
                b.path(SESSION_PATH + "/{id}").buildAndExpand(session.getId());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(uriComponents.toUri());
        return new ResponseEntity<String>(responseHeaders, HttpStatus.CREATED);
    }

    @Transactional
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSessionByID(@PathVariable("id") long id,
                                               @RequestBody KnowledgeSession session) {
        if (sessionRepository.exists(id)) {
            KnowledgeSession editedSession = sessionRepository.findOne(id);
            editedSession.setAuthor(session.getAuthor());
            editedSession.setTitle(session.getTitle());
            editedSession.setVotes(session.getVotes());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //todo accept session by admin

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSessionById(@PathVariable("id") long id) {
        if (sessionRepository.exists(id)) {
            sessionRepository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

