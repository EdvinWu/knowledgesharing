package lv.ctco.controllers;

import lv.ctco.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegistrationController {
    @Autowired
    PersonRepository personRepository;

//    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/registration"})
    public String index(    ) {
//        model.addAttribute("user", personService.getAuthenticatedPerson());
        return "registration";
    }
}
