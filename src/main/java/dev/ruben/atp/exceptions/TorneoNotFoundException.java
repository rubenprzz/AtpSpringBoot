package dev.ruben.atp.exceptions;

import org.springframework.http.HttpStatus;
public class TorneoNotFoundException extends RuntimeException{

    public TorneoNotFoundException(String message){
        super(message);
    }

    public TorneoNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
