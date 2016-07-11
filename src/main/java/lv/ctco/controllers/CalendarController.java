package lv.ctco.controllers;

import lv.ctco.entities.Person;
import lv.ctco.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class CalendarController {
    @Autowired
    PersonRepository personRepository;

    @RequestMapping(path = "/{id}/calendar", method = RequestMethod.GET)
    public ResponseEntity<?> getCalendar(@PathVariable("id") long id) {
        if (personRepository.exists(id)) {
            Person person = personRepository.findOne(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
