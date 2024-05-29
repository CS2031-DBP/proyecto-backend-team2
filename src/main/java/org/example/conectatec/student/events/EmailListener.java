package org.example.conectatec.student.events;

import org.example.conectatec.student.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailListener {

    @Autowired
    private EmailService emailService;

    @EventListener
    @Async
    public void handleHelloEmailEvent(HelloEmailEvent event) {
        emailService.sendSimpleMessage(event.getEmail(), "Verficacion de sesion", "Este es un correo de prueba para verificar su sesion");
    }
}