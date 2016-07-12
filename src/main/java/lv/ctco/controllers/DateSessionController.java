package lv.ctco.controllers;

import lv.ctco.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static lv.ctco.Consts.SESSION_PATH;


@RestController
@RequestMapping
public class DateSessionController {

    @Autowired
    SessionRepository sessionRepository;

    @RequestMapping(path = "/{id}" + SESSION_PATH + "/{fId}", method = RequestMethod.POST)
    public ResponseEntity<?> addDate(@PathVariable("id") long id) {
        if (sessionRepository.exists(id)){
            Date date = new Date();
            sessionRepository.findOne(id).addDate(date);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "/{id}" + SESSION_PATH + "/{fId}", method = RequestMethod.GET)
    public ResponseEntity<?> addDate(@PathVariable("id") long id) {
        if (sessionRepository.exists(id)){
            sessionRepository.findOne(id).getDate();
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "/{id}" + SESSION_PATH + "/{fId}", method = RequestMethod.PUT)
    public ResponseEntity<?> addDate(@PathVariable("id") long id, Date date) {
        if (sessionRepository.exists(id)){
            sessionRepository.findOne(id).setDate(date);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "/{id}" + SESSION_PATH + "/{fId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> addDate(@PathVariable("id") long id) {
        if (sessionRepository.exists(id)){
            sessionRepository.findOne(id).setDate(null);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
