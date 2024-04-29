package dev.ruben.atp.exceptions;

import dev.ruben.atp.exceptions.StorageException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


//Devolvemos una excepción del tipo BAD_REQUEST (400)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StorageInternal extends StorageException {

    public StorageInternal(String mensaje) {
        super(mensaje);
    }
}