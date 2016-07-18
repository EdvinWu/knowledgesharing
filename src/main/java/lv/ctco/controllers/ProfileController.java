package lv.ctco.controllers;

import lv.ctco.entities.Person;
import lv.ctco.entities.UserRole;
import lv.ctco.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.soap.SOAPBinding;
import java.security.Principal;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    PersonRepository personRepository;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/profile"})
    public String profile(Model model, Principal principal) {
        Person person = personRepository.findUserByLogin(principal.getName());
        boolean isAdmin = false;
        List<UserRole> userRoles = person.getUserRoles();
        for(UserRole role : userRoles){
            if(role.getRole().compareTo("ADMIN") == 0){
                isAdmin = true;
            }
        }
        model.addAttribute("username", person.getFullName());
        model.addAttribute("user", person);
        model.addAttribute("isAdmin", isAdmin);
        return "profile";
    }
}