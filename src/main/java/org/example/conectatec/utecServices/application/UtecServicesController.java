package org.example.conectatec.utecServices.application;

import org.example.conectatec.utecServices.domain.UtecServices;
import org.example.conectatec.utecServices.domain.UtecServicesService;
import org.example.conectatec.utecServicesFeed.domain.UtecServicesFeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/UtecServices")
public class UtecServicesController {

    @Autowired
    private UtecServicesService utecServicesService;

    @GetMapping("/{id}")
    public ResponseEntity<UtecServices> getUtecServicesById(@PathVariable Long id) {
        Optional<UtecServices> sutec = utecServicesService.findUtecServicesById(id);
        return sutec.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<UtecServices>> getAllUtecServices() {
        List<UtecServices> sutec = utecServicesService.findAllUtecServices();
        return new ResponseEntity<>(sutec, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UtecServices> createUtecServices(@RequestBody UtecServices utecServices) {
        UtecServices newUtecServices = utecServicesService.saveUtecServices(utecServices);
        return new ResponseEntity<>(newUtecServices, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtecServices> updateUtecServices(@PathVariable Long id, @RequestBody UtecServices utecServices) {
        UtecServices updatedUtecServices = utecServicesService.updateUtecServices(id, utecServices);
        if (updatedUtecServices != null) {
            return new ResponseEntity<>(updatedUtecServices, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtecServices_porId(@PathVariable Long id) {
        utecServicesService.deleteUtecServices_porId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/publications")
    public ResponseEntity<List<UtecServicesFeed>> getPublicationsByUtecServiceId(@PathVariable Long id) {
        Optional<UtecServices> sutec = utecServicesService.findUtecServicesById(id);
        if (sutec.isPresent()) {
            return new ResponseEntity<>(sutec.get().getPublications(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/publications")
    public ResponseEntity<UtecServicesFeed> addPublicationToUtecService(@PathVariable Long id, @RequestBody UtecServicesFeed publication) {
        UtecServicesFeed newPublication = utecServicesService.addPublicationToUtecService(id, publication);
        if (newPublication != null) {
            return new ResponseEntity<>(newPublication, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/publications/search")
    public ResponseEntity<List<UtecServicesFeed>> getPublicationsByHashtag(@RequestParam String hashtag) {
        List<UtecServicesFeed> publications = utecServicesService.findPublicationsByHashtag(hashtag);
        return new ResponseEntity<>(publications, HttpStatus.OK);
    }
}