package org.example.conectatec.utecServices.application;

import org.example.conectatec.utecServices.domain.UtecServices;
import org.example.conectatec.utecServices.domain.UtecServicesService;
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
    UtecServicesService utecServicesService;


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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtecServices_porId(@PathVariable Long id) {
        utecServicesService.deleteUtecServices_porId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
