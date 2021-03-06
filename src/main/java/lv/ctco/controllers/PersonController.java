package lv.ctco.controllers;

import lv.ctco.entities.Person;
import lv.ctco.entities.UserRole;
import lv.ctco.repository.PersonRepository;
import lv.ctco.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
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
    @Autowired
    SessionRepository sessionRepository;

    @Transactional
    @RequestMapping(path = "register", method = RequestMethod.POST)
    public ResponseEntity<?> registerPerson(@RequestBody Person person, UriComponentsBuilder b) {
        List<Person> persons = personRepository.findAll();
        Optional<Person> ifExists = persons.stream()
                .filter(p -> p.getLogin().equals(person.getLogin()))
                .findFirst();
        if (ifExists.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            UserRole userRole = new UserRole();
            userRole.setRole("USER");
            person.setUserRoles(Arrays.asList(userRole));
            person.setPassword(passwordEncoder.encode(person.getPassword()));
            personRepository.save(person);
            UriComponents uriComponents =
                    b.path("/person/{id}").buildAndExpand(person.getId());

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setLocation(uriComponents.toUri());
            return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
        }
    }

    @Transactional
    @RequestMapping(path = "adduser", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<?> userAdd(@RequestParam String username,
                                     String fullname,
                                     String password) {
        Person person = new Person();
        Person newUser = personRepository.findUserByLogin(username);
        if (newUser == null) {
            person.setLogin(username);
            person.setFullName(fullname);
            person.setPassword(password);
            UserRole userRole = new UserRole();
            userRole.setRole("USER");
            person.setUserRoles(Arrays.asList(userRole));
            personRepository.save(person);
//            URI linkLogin = new URI("htttp://localhost:8080/login");
//            HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.setLocation(linkLogin);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "person", method = RequestMethod.GET)
    public ResponseEntity<?> getPerson(Principal principal) {
        Person currentPerson = personRepository.findUserByLogin(principal.getName());
        return new ResponseEntity<>(currentPerson, HttpStatus.OK);
    }

    @RequestMapping(path = "persons", method = RequestMethod.GET)
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
    public ResponseEntity<?> updatePersonByID(@PathVariable("id") long id,
                                              @RequestBody Person person) {
        if (personRepository.exists(id)) {
            Person editedPerson = personRepository.findOne(id);
            editedPerson.setFullName(person.getFullName());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @RequestMapping(path = "/person/isadmin", method = RequestMethod.GET)
    public boolean getAdminPrivileges(Principal principal) {
        Person loggedPerson = personRepository.findUserByLogin(principal.getName());
        List<UserRole> roles = loggedPerson.getUserRoles();
        for (UserRole role : roles) {
            if (role.getRole().equals("ADMIN")) {
                return true;
            }
        }
        return false;
    }
}
