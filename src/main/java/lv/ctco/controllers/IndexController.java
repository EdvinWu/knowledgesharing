package lv.ctco.controllers;

import lv.ctco.entities.Person;
import lv.ctco.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class IndexController {
    @Autowired
    PersonRepository personRepository;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/", "/index"})
    public String index(Model model, HttpServletRequest request, Principal principal) {
        Person person = personRepository.findUserByLogin(principal.getName());
        model.addAttribute("username", person.getFullName());
        model.addAttribute("user", person);
//        model.addAttribute("isAdmin", request.isUserInRole("ADMIN"));
        return "index";
    }
}
