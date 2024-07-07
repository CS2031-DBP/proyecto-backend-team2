package org.example.conectatec.utecServicesPost.application;


import org.example.conectatec.utecServicesPost.domain.UtecServicesPost;
import org.example.conectatec.utecServicesPost.domain.UtecServicesPostService;
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
    private UtecServicesPostService utecServicesPostService;

    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @GetMapping
    public ResponseEntity<List<UtecServicesPost>> getAllUtecServicesPosts() {
        List<UtecServicesPost> utecFeeds = utecServicesPostService.findAllPublications();
        return new ResponseEntity<>(utecFeeds, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('UTEC')")
    @PostMapping
    public ResponseEntity<UtecServicesPost> createUtecServicesFeed(@RequestBody UtecServicesPost utecServicesPost) {
        UtecServicesPost newUtecServicesPost = utecServicesPostService.saveUtecServicesPublications(utecServicesPost);
        return new ResponseEntity<>(newUtecServicesPost, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('UTEC')")
    @PutMapping("/{id}")
    public ResponseEntity<UtecServicesPost> updateUtecServicesFeed(@PathVariable Long id, @RequestBody UtecServicesPost updatedUtecServicesPost) {
        UtecServicesPost updatedPost = utecServicesPostService.updateUtecServicesPost(id, updatedUtecServicesPost);
        if (updatedPost != null) {
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('UTEC')")
    @PatchMapping("/{id}")
    public ResponseEntity<UtecServicesPost> partialUpdateUtecServicesPost(@PathVariable Long id, @RequestBody UtecServicesPost partialUtecServicesPost) {
        UtecServicesPost updatedPost = utecServicesPostService.partialUpdateUtecServicesPost(id, partialUtecServicesPost);
        if (updatedPost != null) {
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('UTEC')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtecServicesPost(@PathVariable Long id) {
        utecServicesPostService.deleteUtecServicesPublication(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @GetMapping("/search")
    public ResponseEntity<List<UtecServicesPost>> getPublicationsByHashtag(@RequestParam String hashtag) {
        List<UtecServicesPost> posts = utecServicesPostService.findUtecServicesPublicationsByHashtag(hashtag);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
