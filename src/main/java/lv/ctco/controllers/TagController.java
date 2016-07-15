package lv.ctco.controllers;

import lv.ctco.entities.KnowledgeSession;
import lv.ctco.entities.Tag;
import lv.ctco.repository.SessionRepository;
import lv.ctco.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @RequestMapping(path = "/{id}" + TAG_PATH + "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getTagsByID(@PathVariable("id") long id, @PathVariable("id") long tagID) {
        if (sessionRepository.exists(id)) {
            if (tagRepository.exists(tagID)) {
                Tag tag = tagRepository.findOne(tagID);
                return new ResponseEntity<>(tag, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
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
    }

    @Transactional
    @RequestMapping(path = "/{id}" + TAG_PATH + "/{sId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTag(@PathVariable("id") long id, @PathVariable("sId") long tagID) {
        if (sessionRepository.exists(id)) {
            KnowledgeSession session = sessionRepository.findOne(id);
            List<Tag> tags = session.getTags();
            Optional<Tag> tag = tags.stream()
                    .filter(t -> t.getId() == id)
                    .findAny();
            if (tag != null) {
                if (session.removeTag(tagID)) {
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @RequestMapping(path = "/{id}" + TAG_PATH + "/{tId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateTag(@PathVariable("id") long id, @PathVariable("tId") long tagID,
                                            @RequestBody Tag tag) {
        tag.setId(tagID);
        if (sessionRepository.exists(id)) {
            KnowledgeSession session = sessionRepository.findOne(id);
            tagRepository.save(tag);
            sessionRepository.save(session);
            return new ResponseEntity<>("text", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
