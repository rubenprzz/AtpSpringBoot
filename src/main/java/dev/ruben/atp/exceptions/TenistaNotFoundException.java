package dev.ruben.atp.exceptions;

import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TenistaNotFoundException extends RuntimeException{

    public TenistaNotFoundException(String message){
        super(message);
    }

    public TenistaNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
