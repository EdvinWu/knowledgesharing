package lv.ctco.controllers;

import lv.ctco.entities.ScheduledSession;
import lv.ctco.repository.ScheduledSessionRepository;
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
@RequestMapping(SCHEDULED_PATH)
public class ScheduledSessionController {

    @Autowired
    ScheduledSessionRepository scheduledSessionRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllSessions() {
        List<ScheduledSession> sessions = scheduledSessionRepository.findAll();
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getSessionById(@PathVariable("id") long id) {
        if (scheduledSessionRepository.exists(id)) {
            ScheduledSession session = scheduledSessionRepository.findOne(id);
            return new ResponseEntity<>(session, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addSession(@RequestBody ScheduledSession scheduledSession, UriComponentsBuilder b) {
        scheduledSessionRepository.save(scheduledSession);

        UriComponents uriComponents =
                b.path(SCHEDULED_PATH + "/{id}").buildAndExpand(scheduledSession.getId());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(uriComponents.toUri());

        return new ResponseEntity<String>(responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSessionById(@PathVariable("id") long id) {
        if (scheduledSessionRepository.exists(id)) {
            scheduledSessionRepository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @RequestMapping(path = " /{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSessionByID(@PathVariable("id") long id,
                                               @RequestBody ScheduledSession session) {

        if (scheduledSessionRepository.exists(id)) {
            ScheduledSession editedSession = scheduledSessionRepository.findOne(id);
            editedSession.setDate(session.getDate());
            editedSession.setPlace(session.getPlace());
            editedSession.setSession(session.getSession());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
