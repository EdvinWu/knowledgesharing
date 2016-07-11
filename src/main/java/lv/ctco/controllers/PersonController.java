package lv.ctco.controllers;

import lv.ctco.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PersonController {

    @Autowired
    PersonRepository personRepository;


    @RequestMapping(path = "register",method = RequestMethod.POST)
    public ResponseEntity<?> registerPerson() {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "person/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getPerson(@PathVariable("id") long id) {
        if (personRepository.exists(id)) {
            personRepository.findOne(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(path = "person/",method = RequestMethod.GET)
    public ResponseEntity<?> getAllPersons() {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "person/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePersonById() {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "person/{id}",method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser() {

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
