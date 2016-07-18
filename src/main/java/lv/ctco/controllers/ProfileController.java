package lv.ctco.controllers;

import lv.ctco.entities.Person;
import lv.ctco.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProfileController {

    @Autowired
    PersonService personService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/profile"})
    public String profile(Model model) {
        Person person = personService.getAuthenticatedPerson();
        model.addAttribute("username", person.getFullName());
        model.addAttribute("user", person);
        return "profile";
    }
}