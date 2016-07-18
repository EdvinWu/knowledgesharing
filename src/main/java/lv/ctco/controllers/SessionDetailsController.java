package lv.ctco.controllers;

import lv.ctco.entities.KnowledgeSession;
import lv.ctco.entities.Person;
import lv.ctco.repository.PersonRepository;
import lv.ctco.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class SessionDetailsController {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    SessionRepository sessionRepository;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/sessionDetails"})
    public String profile(@RequestParam long id, Model model, Principal principal) {
        Person person = personRepository.findUserByLogin(principal.getName());
        model.addAttribute("user", person);
        KnowledgeSession session = sessionRepository.findOne(id);
        model.addAttribute("knowledgeSession", session );
        return "sessionDetails";
    }
}