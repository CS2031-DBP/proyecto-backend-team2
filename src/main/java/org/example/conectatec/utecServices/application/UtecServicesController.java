package org.example.conectatec.utecServices.application;

import org.example.conectatec.student.dto.StudentDto;
import org.example.conectatec.utecServices.domain.UtecServices;
import org.example.conectatec.utecServices.domain.UtecServicesService;
import org.example.conectatec.utecServices.dto.UtecServicesDto;
import org.example.conectatec.utecServicesFeed.domain.UtecServicesFeed;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<UtecServicesDto> getUtecServices(@PathVariable Long id) {
        UtecServicesDto utecServicesDto = utecServicesService.findUtecServicesInfo(id);
        return new ResponseEntity<>(utecServicesDto, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<UtecServicesDto> getUtecServicesByEmail(@PathVariable String email) {
        utecServicesService.findUtecServicesByEmail(email);
        UtecServicesDto utecServicesDto = modelMapper.map(utecServicesService.findUtecServicesByEmail(email), UtecServicesDto.class);
        return new ResponseEntity<>(utecServicesDto, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @GetMapping
    public ResponseEntity<List<UtecServices>> getAllUtecServices() {
        List<UtecServices> sutec = utecServicesService.findAllUtecServices();
        return new ResponseEntity<>(sutec, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('UTEC')")
    @PostMapping
    public ResponseEntity<UtecServices> createUtecServices(@RequestBody UtecServices utecServices) {
        UtecServices newUtecServices = utecServicesService.saveUtecServices(utecServices);
        return new ResponseEntity<>(newUtecServices, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('UTEC') ")
    @PutMapping("/{id}")
    public ResponseEntity<UtecServices> updateUtecServices(@PathVariable Long id, @RequestBody UtecServices utecServices) {
        UtecServices updatedUtecServices = utecServicesService.updateUtecServices(id, utecServices);
        if (updatedUtecServices != null) {
            return new ResponseEntity<>(updatedUtecServices, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('UTEC')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtecServices_porId(@PathVariable Long id) {
        utecServicesService.deleteUtecServices_porId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}