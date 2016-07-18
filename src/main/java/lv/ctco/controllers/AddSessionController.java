package lv.ctco.controllers;

import lv.ctco.entities.Person;
import lv.ctco.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class AddSessionController {
    @Autowired
    PersonRepository personRepository;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/addSession"})
    public String index(Model model, Principal principal) {
        Person person = personRepository.findUserByLogin(principal.getName());
        model.addAttribute("user", person);
        return "addSession";
    }
}
