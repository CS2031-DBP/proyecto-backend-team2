package org.example.conectatec.utecServices.application;

import org.example.conectatec.utecServices.domain.UtecServices;
import org.example.conectatec.utecServices.domain.UtecServicesService;
import org.example.conectatec.utecServices.dto.UtecServicesDto;
import org.example.conectatec.utecServicesFeed.domain.UtecServicesFeed;
import org.modelmapper.ModelMapper;
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
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UtecServicesDto> getUtecServicesById(@PathVariable Long id) {
        UtecServices utecService = utecServicesService.findUtecServicesById(id);
        UtecServicesDto utecServicesDto = modelMapper.map(utecService, UtecServicesDto.class);
        return new ResponseEntity<>(utecServicesDto, HttpStatus.OK);
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
        UtecServices utecServices = utecServicesService.findUtecServicesById(id);
        if (utecServices != null) {
            utecServicesService.deleteUtecServices_porId(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
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