package org.example.conectatec.student.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

public class HelloEmailEvent extends ApplicationEvent {
    private final String email;

    public HelloEmailEvent(String email) {
        super(email);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}

