package org.example.conectatec.utecServicesFeed.application;

import org.example.conectatec.utecServices.domain.UtecServices;
import org.example.conectatec.utecServices.domain.UtecServicesService;
import org.example.conectatec.utecServicesFeed.domain.UtecServicesFeed;
import org.example.conectatec.utecServicesFeed.domain.UtecServicesFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/UtecServicesFeed")
public class UtecServicesFeedController {

    @Autowired
    private UtecServicesFeedService utecServicesFeedService;
    @Autowired
    private UtecServicesService utecServicesService;

    @GetMapping("/{id}")
    public ResponseEntity<UtecServicesFeed> getUtecServicesFeedById(@PathVariable Long id) {
        Optional<UtecServicesFeed> sutecFeed = utecServicesFeedService.findUtecServicesPublicationsById(id);
        return sutecFeed.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<UtecServicesFeed>> getAllUtecServicesFeeds() {
        List<UtecServicesFeed> sutecFeeds = utecServicesFeedService.findAllPublications();
        return new ResponseEntity<>(sutecFeeds, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UtecServicesFeed> createUtecServicesFeed(@RequestBody UtecServicesFeed utecServicesFeed) {
        UtecServicesFeed newUtecServicesFeed = utecServicesFeedService.saveUtecServicesPublications(utecServicesFeed);
        return new ResponseEntity<>(newUtecServicesFeed, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtecServicesFeed> updateUtecServicesFeed(@PathVariable Long id, @RequestBody UtecServicesFeed updatedUtecServicesFeed) {
        Optional<UtecServicesFeed> existingUtecServicesFeed = utecServicesFeedService.findUtecServicesPublicationsById(id);
        if (existingUtecServicesFeed.isPresent()) {
            UtecServicesFeed sutecFeed = existingUtecServicesFeed.get();
            sutecFeed.setHashtag(updatedUtecServicesFeed.getHashtag());
            sutecFeed.setMedia(updatedUtecServicesFeed.getMedia());
            sutecFeed.setAnswer(updatedUtecServicesFeed.getAnswer());
            UtecServicesFeed savedUtecServicesFeed = utecServicesFeedService.saveUtecServicesPublications(sutecFeed);
            return new ResponseEntity<>(savedUtecServicesFeed, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtecServicesFeed(@PathVariable Long id) {
        utecServicesFeedService.deleteUtecServicesPublication(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UtecServicesFeed>> getPublicationsByHashtag(@RequestParam String hashtag) {
        List<UtecServicesFeed> publications = utecServicesFeedService.findUtecServicesPublicationsByHashtag(hashtag);
        return new ResponseEntity<>(publications, HttpStatus.OK);
    }

    @GetMapping("/utecServices/{utecServicesId}")
    public ResponseEntity<List<UtecServicesFeed>> getPublicationsByUtecServicesId(@PathVariable Long utecServicesId) {
        Optional<UtecServices> utecServices = utecServicesService.findUtecServicesById(utecServicesId);
        if (utecServices.isPresent()) {
            List<UtecServicesFeed> publications = utecServicesFeedService.findPublicationsByUtecServices(utecServices.get());
            return new ResponseEntity<>(publications, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
