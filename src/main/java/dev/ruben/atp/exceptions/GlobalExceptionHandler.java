package dev.ruben.atp.exceptions;

import dev.ruben.atp.exceptions.TorneoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TorneoNotFoundException.class)
    public ResponseEntity<Object> handleTorneoNotFoundException(TorneoNotFoundException ex) {
        TorneoNotFoundException torneoNoEncontrado = new TorneoNotFoundException("Torneo no encontrado", ex);
        return new ResponseEntity<>(torneoNoEncontrado, HttpStatus.NOT_FOUND);
    }
}
