package lv.ctco.controllers;

import lv.ctco.entities.Person;
import lv.ctco.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    PersonService personService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/", "/index"})
    public String index(Model model, HttpServletRequest request) {
        Person person = personService.getAuthenticatedPerson();
        model.addAttribute("username", person.getFullName());
        model.addAttribute("user", person);
//        model.addAttribute("isAdmin", request.isUserInRole("ADMIN"));
        return "index";
    }
}
