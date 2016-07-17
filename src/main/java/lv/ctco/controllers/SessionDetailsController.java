package lv.ctco.controllers;

import lv.ctco.entities.KnowledgeSession;
import lv.ctco.repository.SessionRepository;
import lv.ctco.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SessionDetailsController {

    @Autowired
    PersonService personService;
    @Autowired
    SessionRepository sessionRepository;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"/sessionDetails"})
    public String profile(@RequestParam long id, Model model) {
        model.addAttribute("user", personService.getAuthenticatedPerson());
        KnowledgeSession session = sessionRepository.findOne(id);
        model.addAttribute("knowledgeSession", session );
        return "sessionDetails";
    }
}