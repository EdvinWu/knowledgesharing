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
public class AdminPageController {
    @Autowired
    PersonRepository personRepository;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/adminPage"})
    public String index(Model model, Principal principal) {
        Person loggedPerson = personRepository.findUserByLogin(principal.getName());
        model.addAttribute("user", loggedPerson);
        return "adminPage";
    }
}
