package lv.ctco.controllers;

import lv.ctco.entities.Feedback;
import lv.ctco.repository.FeedbackRepository;
import lv.ctco.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
public class FeedbackController {

    @Autowired
    FeedbackRepository feedbackRepository;
    @Autowired
    PersonRepository personRepository;

    @RequestMapping(path = "/{id}/feedback/{f_id}", method = RequestMethod.GET)
    public ResponseEntity<?> getSessionFeedback(@PathVariable("id") long id, @PathVariable("f_id") long feedbackID) {
        if (personRepository.exists(id)) {
            if (feedbackRepository.exists(feedbackID)) {
                Feedback feedback = feedbackRepository.findOne(feedbackID);
                return new ResponseEntity<>(feedback, HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "/{id}/feedback}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllSessionFeedback(@PathVariable("id") long id) {
        if (personRepository.exists(id)) {
            feedbackRepository.findAll();
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
