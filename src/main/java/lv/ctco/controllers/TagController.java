package lv.ctco.controllers;

import lv.ctco.entities.KnowledgeSession;
import lv.ctco.entities.Tag;
import lv.ctco.repository.SessionRepository;
import lv.ctco.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static lv.ctco.Consts.TAG_PATH;
import static lv.ctco.Consts.SESSION_PATH;

@RestController
@RequestMapping(SESSION_PATH)
public class TagController {

    @Autowired
    TagRepository tagRepository;
    @Autowired
    SessionRepository sessionRepository;

    @RequestMapping(path = "/{id}" + TAG_PATH, method = RequestMethod.GET)
    public ResponseEntity<?> getAllSessionTags(@PathVariable("id") long id) {
        KnowledgeSession session = sessionRepository.findOne(id);
        List<Tag> tagList = session.getTags();
        return new ResponseEntity<>(tagList, HttpStatus.OK);
    }

   /* @Transactional
    @RequestMapping(path = "/{id}"+ TAG_PATH, method = RequestMethod.POST)
    public ResponseEntity<?> addTag(@PathVariable("id") long id,
                                         @RequestBody Tag tag){
        if (sessionRepository.exists(id)) {
            KnowledgeSession session = sessionRepository.findOne(id);
            List<Tag> tags = session.getTags();
            tags.add(tag);
            session.setTags(tags);
            sessionRepository.save(session);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/
}
