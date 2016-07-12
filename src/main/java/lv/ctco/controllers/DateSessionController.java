package lv.ctco.controllers;

import lv.ctco.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static lv.ctco.Consts.SESSION_PATH;


@RestController
@RequestMapping(SESSION_PATH)
public class DateSessionController {

    @Autowired
    SessionRepository sessionRepository;

    @RequestMapping(path ="/{id}/date", method = RequestMethod.POST)
    public ResponseEntity<?> addDate(@PathVariable("id") long id,
                                     @RequestBody LocalDateTime date) {
        if (sessionRepository.exists(id)){
            sessionRepository.findOne(id).setDate(date);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "/{id}/date", method = RequestMethod.GET)
    public ResponseEntity<?> getDate(@PathVariable("id") long id) {
        if (sessionRepository.exists(id)){
            LocalDateTime date = sessionRepository.findOne(id).getDate();
            return new ResponseEntity<>(date,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @RequestMapping(path = "/{id}/date", method = RequestMethod.PUT)
    public ResponseEntity<?> updateDate(@PathVariable("id") long id,
                                        @RequestBody LocalDateTime date) {
        if (sessionRepository.exists(id)){
            sessionRepository.findOne(id).setDate(date);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @RequestMapping(path = "/{id}/date", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDate(@PathVariable("id") long id) {
        if (sessionRepository.exists(id)){
            sessionRepository.findOne(id).deleteDate();
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
