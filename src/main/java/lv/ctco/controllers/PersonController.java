package lv.ctco.controllers;

import lv.ctco.entities.Person;
import lv.ctco.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class PersonController {

    @Autowired
    PersonRepository personRepository;


    @RequestMapping(path = "register",method = RequestMethod.POST)
    public ResponseEntity<?> registerPerson(@RequestBody Person person) {
    if (personRepository.exists(person.getId())){
        personRepository.save(person);
        return new ResponseEntity<>(HttpStatus.OK);
    }else
        return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
    List<Person> personList = personRepository.findAll();
        return new ResponseEntity<>(personList,HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(path = "person/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePersonById(@PathVariable("id") long id) {
        if (personRepository.exists(id)) {
            personRepository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @RequestMapping(path = " /{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStudentByID(@PathVariable("id") long id,
                                               @RequestBody Person person) {

        if (personRepository.exists(id)) {
            Person editedPerson = personRepository.findOne(id);
            editedPerson.setFullName(person.getFullName());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
