package org.example.conectatec.utecServicesFeed.application;

import org.example.conectatec.utecServices.domain.UtecServices;
import org.example.conectatec.utecServices.domain.UtecServicesService;
import org.example.conectatec.utecServicesFeed.domain.UtecServicesFeed;
import org.example.conectatec.utecServicesFeed.domain.UtecServicesFeedService;
import org.example.conectatec.utecServicesFeed.dto.UtecServicesFeedDto;
import org.modelmapper.ModelMapper;
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
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UtecServicesFeedDto> getUtecServicesFeedById(@PathVariable Long id) {
        UtecServicesFeed sutecFeed = utecServicesFeedService.findUtecServicesPublicationsById(id);
        UtecServicesFeedDto dto = modelMapper.map(sutecFeed, UtecServicesFeedDto.class);
        return new ResponseEntity<>(dto, HttpStatus.OK);
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
    public ResponseEntity<UtecServicesFeed> updateUtecServicesFeed(@RequestBody UtecServicesFeed updatedUtecServicesFeed) {
        if (updatedUtecServicesFeed.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            updatedUtecServicesFeed.setHashtag(updatedUtecServicesFeed.getHashtag());
            updatedUtecServicesFeed.setMedia(updatedUtecServicesFeed.getMedia());
            updatedUtecServicesFeed.setCaption(updatedUtecServicesFeed.getCaption());
            UtecServicesFeed savedUtecServicesFeed = utecServicesFeedService.saveUtecServicesPublications(updatedUtecServicesFeed);
            return new ResponseEntity<>(savedUtecServicesFeed, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtecServicesFeed(@PathVariable Long id) {
        utecServicesFeedService.deleteUtecServicesPublication(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UtecServicesFeed>> getPublicationsByHashtag(@RequestParam String hashtag) {
        List<UtecServicesFeed> posts = utecServicesFeedService.findUtecServicesPublicationsByHashtag(hashtag);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/utecServices/{utecServicesId}")
    public ResponseEntity<List<UtecServicesFeed>> getPublicationsByUtecServicesId(@PathVariable UtecServices utecServices) {
        List<UtecServicesFeed> posts = utecServicesFeedService.findPublicationsByUtecServices(utecServices);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
