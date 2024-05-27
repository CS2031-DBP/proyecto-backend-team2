package org.example.conectatec.sUTEC.application;

import org.example.conectatec.career.domain.Career;
import org.example.conectatec.sUTEC.domain.SUTEC;
import org.example.conectatec.sUTEC.domain.SUTECService;
import org.example.conectatec.student.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/UtecServices")
public class SUTECController {

    @Autowired
    SUTECService sutecService;


    @GetMapping("/{id}")
    public ResponseEntity<SUTEC> getUtecServicesById(@PathVariable Long id) {
        Optional<SUTEC> sutec = sutecService.findUtecServicesById(id);
        return sutec.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<SUTEC>> getAllUtecServices() {
        List<SUTEC> sutec = sutecService.findAllUtecServices();
        return new ResponseEntity<>(sutec, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtecServices_porId(@PathVariable Long id) {
        sutecService.deleteUtecServices_porId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
