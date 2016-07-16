package lv.ctco.controllers;

import lv.ctco.entities.KnowledgeSession;
import lv.ctco.entities.Person;
import lv.ctco.entities.UserRole;
import lv.ctco.enums.SessionStatus;
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
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static lv.ctco.Consts.*;
import static lv.ctco.enums.SessionStatus.APPROVED;
import static lv.ctco.enums.SessionStatus.DONE;
import static lv.ctco.enums.SessionStatus.PENDING;

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

    @RequestMapping(path = TAG_PATH + "/tag", method = RequestMethod.GET)
     public ResponseEntity<?> getSessionByTag(@RequestParam String name) {
        List<KnowledgeSession> sessions;
        sessions = sessionRepository.findByTag(name);
        if (sessions != null) {
            return new ResponseEntity<>(sessions, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

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
    public ResponseEntity<?> addPendingSession(@RequestBody KnowledgeSession session, UriComponentsBuilder b) {
        session.setStatus(PENDING);
        sessionRepository.save(session);
        UriComponents uriComponents =
                b.path(SESSION_PATH + "/{id}").buildAndExpand(session.getId());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(uriComponents.toUri());
        return new ResponseEntity<String>(responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(path = "bystatus/{status}", method = RequestMethod.GET)
    public ResponseEntity<?> getSessionsByStatus(@PathVariable("status") String status) {
        List<KnowledgeSession> sessions = new ArrayList<>();
        if (status.equals("all"))
            sessions = sessionRepository.findAll();
        if (status.equals("pending"))
            sessions = sessionRepository.findSessionByStatus(PENDING);
        if (status.equals("approved"))
            sessions = sessionRepository.findSessionByStatus(APPROVED);
        if (status.equals("done"))
            sessions = sessionRepository.findSessionByStatus(DONE);
        return new ResponseEntity<>(sessions, HttpStatus.OK);
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

    @Transactional
    @RequestMapping(path = "/{id}/changestatus/{status}", method = RequestMethod.PUT)
    public ResponseEntity<?> changeSessionStatus(@PathVariable("id") long id,
                                                 @PathVariable("status") String statusWanted) {

        if (sessionRepository.exists(id)) {
            KnowledgeSession session = sessionRepository.findOne(id);
            if (statusWanted.equals("approved") && session.getStatus().equals(PENDING)) {
                session.setStatus(APPROVED);
            }
            if (statusWanted.equals("done") && session.getStatus().equals(APPROVED)) {
                session.setStatus(DONE);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "/{session_id}/admin", method = RequestMethod.GET)
    public ResponseEntity<?> changeSessionStatusToApprovedByAdmin(@PathVariable("session_id") long sessionId,
                                                Principal principal) {
        Person loggedPerson = personRepository.findUserByLogin(principal.getName());
        List<UserRole> roles = loggedPerson.getUserRoles();
        for(UserRole role : roles){
            if (role.getRole().compareTo("ADMIN") == 0){
                if (sessionRepository.exists(sessionId)) {
                    KnowledgeSession editedSession = sessionRepository.findOne(sessionId);
                    editedSession.setStatus(SessionStatus.APPROVED);
                    sessionRepository.save(editedSession);
                    return new ResponseEntity<>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSessionById(@PathVariable("id") long id) {
        if (sessionRepository.exists(id)) {
            sessionRepository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "/{sessionId}/user", method = RequestMethod.POST)
    public ResponseEntity<?> addPersonToSession(@PathVariable("sessionId") long sessionId,
                                                Principal principal){
        if(sessionRepository.exists(sessionId)){
            KnowledgeSession session = sessionRepository.findOne(sessionId);
            Person inputPerson = personRepository.findUserByLogin(principal.getName());
            for(Person person : session.getPersons()){
                if(person.getId() == inputPerson.getId()){
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
            List<Person> sessionPersons = session.getPersons();
            sessionPersons.add(inputPerson);
            session.setPersons(sessionPersons);
            sessionRepository.save(session);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

