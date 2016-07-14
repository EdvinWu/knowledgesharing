package lv.ctco.controllers;

import lv.ctco.entities.Feedback;
import lv.ctco.entities.KnowledgeSession;
import lv.ctco.repository.FeedbackRepository;
import lv.ctco.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

import static lv.ctco.Consts.FEEDBACK_PATH;
import static lv.ctco.Consts.SESSION_PATH;

@RestController
@RequestMapping(SESSION_PATH)
public class FeedbackController {

    @Autowired
    FeedbackRepository feedbackRepository;
    @Autowired
    SessionRepository sessionRepository;

    @RequestMapping(path = "/{id}" + FEEDBACK_PATH + "/{fId}", method = RequestMethod.GET)
    public ResponseEntity<?> getSessionFeedback(@PathVariable("id") long id, @PathVariable("fId") long feedbackID) {
        if (sessionRepository.exists(id)) {
            if (feedbackRepository.exists(feedbackID)) {
                Feedback feedback = feedbackRepository.findOne(feedbackID);
                return new ResponseEntity<>(feedback, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "/{id}" + FEEDBACK_PATH, method = RequestMethod.GET)
    public ResponseEntity<?> getAllSessionFeedback(@PathVariable("id") long id) {
        if (sessionRepository.exists(id)) {
            List<Feedback> feedbacks = feedbackRepository.findAll();
            return new ResponseEntity<>(feedbacks, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @RequestMapping(path = "/{id}" + FEEDBACK_PATH, method = RequestMethod.POST)
    public ResponseEntity<?> addFeedback(@PathVariable("id") long id,
                                         @RequestBody Feedback feedback,
                                         UriComponentsBuilder b) {
        if (sessionRepository.exists(id)) {
            KnowledgeSession session = sessionRepository.findOne(id);
            session.addFeedback(feedback);
            feedbackRepository.save(feedback);
            sessionRepository.save(session);
            UriComponents uriComponents =
                    b.path(SESSION_PATH + "/{id}" + FEEDBACK_PATH + "/" + feedback.getId()).buildAndExpand(session.getId());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setLocation(uriComponents.toUri());

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @RequestMapping(path = "/{id}" + FEEDBACK_PATH + "/{fId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteFeedback(@PathVariable("id") long id, @PathVariable("fId") long feedbackID) {
        if (sessionRepository.exists(id)) {
            KnowledgeSession session = sessionRepository.findOne(id);
            List<Feedback> feedbacks = session.getFeedbacks();
            Optional<Feedback> feedback = feedbacks.stream()
                    .filter(t -> t.getId() == id)
                    .findAny();
            if (feedback != null) {
                if (session.removeFeedback(feedbackID)) {
                    feedbackRepository.delete(feedbackID);
                    sessionRepository.save(session);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @RequestMapping(path = "/{id}" + FEEDBACK_PATH + "/{fId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateFeedback(@PathVariable("id") long id, @PathVariable("fId") long feedbackID,
                                            @RequestBody Feedback feedback) {
        feedback.setId(feedbackID);
        if (sessionRepository.exists(id)) {
            KnowledgeSession session = sessionRepository.findOne(id);
            feedbackRepository.save(feedback);
            sessionRepository.save(session);
            return new ResponseEntity<>("text", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
