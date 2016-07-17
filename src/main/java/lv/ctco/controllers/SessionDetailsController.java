package lv.ctco.controllers;

import lv.ctco.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SessionDetailsController {

    @Autowired
    PersonService personService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/sessionDetails"})
    public String profile(Model model) {
        model.addAttribute("user", personService.getAuthenticatedPerson());
        return "sessionDetails";
    }
}