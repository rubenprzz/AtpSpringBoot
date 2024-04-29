package dev.ruben.atp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TorneoNotFoundException extends RuntimeException{

    public TorneoNotFoundException(String message){
        super(message);
    }

    public TorneoNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
