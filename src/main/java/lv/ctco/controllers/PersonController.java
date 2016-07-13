package lv.ctco.controllers;

import lv.ctco.entities.Person;
import lv.ctco.entities.UserRoles;
import lv.ctco.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @RequestMapping(path = "register/", method = RequestMethod.POST)
    public ResponseEntity<?> registerPerson(@RequestBody Person person, UriComponentsBuilder b) {
        List<Person> persons = personRepository.findAll();
        Optional<Person> ifExists = persons.stream()
                .filter(p -> p.getUserName().equals(person.getUserName()))
                .findFirst();
        if (ifExists.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            UserRoles userRoles = new UserRoles();
            userRoles.setRole("USER");
            person.setUserRoles(Arrays.asList(userRoles));
           person.setPassword(passwordEncoder.encode(person.getPassword()));
            personRepository.save(person);
            UriComponents uriComponents =
                    b.path("/person/{id}").buildAndExpand(person.getId());

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setLocation(uriComponents.toUri());
            return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
        }
    }

    @RequestMapping(path = "person/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPerson(@PathVariable("id") long id) {
        if (personRepository.exists(id)) {
            Person person = personRepository.findOne(id);
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(path = "person", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPersons() {
        List<Person> personList = personRepository.findAll();
        return new ResponseEntity<>(personList, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(path = "person/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePersonById(@PathVariable("id") long id) {
        if (personRepository.exists(id)) {
            personRepository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @RequestMapping(path = "/person/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStudentByID(@PathVariable("id") long id,
                                               @RequestBody Person person) {

        if (personRepository.exists(id)) {
            Person editedPerson = personRepository.findOne(id);
            editedPerson.setFullName(person.getFullName());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


}
