package dev.ruben.atp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StorageBadRequest extends RuntimeException {
    public StorageBadRequest(String message) {
        super(message);
    }

}

