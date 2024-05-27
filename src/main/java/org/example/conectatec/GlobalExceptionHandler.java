package org.example.conectatec;

import org.example.conectatec.auth.exceptions.UserAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class GlobalExceptionHandler extends ResponseEntifyExceptionHandler{

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserAlreadyExistException(UserAlreadyExistException e){
        return e.getMessage();}
}
