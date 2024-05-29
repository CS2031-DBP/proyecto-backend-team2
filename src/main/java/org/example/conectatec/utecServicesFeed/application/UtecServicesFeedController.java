package org.example.conectatec.utecServicesFeed.application;


import org.example.conectatec.utecServicesFeed.domain.UtecServicesFeed;
import org.example.conectatec.utecServicesFeed.domain.UtecServicesFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/UtecServicesFeed")
public class UtecServicesFeedController {

    @Autowired
    private UtecServicesFeedService utecServicesFeedService;

    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @GetMapping
    public ResponseEntity<List<UtecServicesFeed>> getAllUtecServicesFeeds() {
        List<UtecServicesFeed> utecFeeds = utecServicesFeedService.findAllPublications();
        return new ResponseEntity<>(utecFeeds, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('UTEC')")
    @PostMapping
    public ResponseEntity<UtecServicesFeed> createUtecServicesFeed(@RequestBody UtecServicesFeed utecServicesFeed) {
        UtecServicesFeed newUtecServicesFeed = utecServicesFeedService.saveUtecServicesPublications(utecServicesFeed);
        return new ResponseEntity<>(newUtecServicesFeed, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('UTEC')")
    @PutMapping("/{id}")
    public ResponseEntity<UtecServicesFeed> updateUtecServicesFeed(@PathVariable Long id, @RequestBody UtecServicesFeed updatedUtecServicesFeed) {
        UtecServicesFeed updatedFeed = utecServicesFeedService.updateUtecServicesFeed(id, updatedUtecServicesFeed);
        if (updatedFeed != null) {
            return new ResponseEntity<>(updatedFeed, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('UTEC')")
    @PatchMapping("/{id}")
    public ResponseEntity<UtecServicesFeed> partialUpdateUtecServicesFeed(@PathVariable Long id, @RequestBody UtecServicesFeed partialUtecServicesFeed) {
        UtecServicesFeed updatedFeed = utecServicesFeedService.partialUpdateUtecServicesFeed(id, partialUtecServicesFeed);
        if (updatedFeed != null) {
            return new ResponseEntity<>(updatedFeed, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('UTEC')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtecServicesFeed(@PathVariable Long id) {
        utecServicesFeedService.deleteUtecServicesPublication(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @GetMapping("/search")
    public ResponseEntity<List<UtecServicesFeed>> getPublicationsByHashtag(@RequestParam String hashtag) {
        List<UtecServicesFeed> posts = utecServicesFeedService.findUtecServicesPublicationsByHashtag(hashtag);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
