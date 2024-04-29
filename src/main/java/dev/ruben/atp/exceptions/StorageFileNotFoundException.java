package dev.ruben.atp.exceptions;

public class StorageFileNotFoundException extends RuntimeException {
    public StorageFileNotFoundException(String filename) {
        super("No se pudo encontrar el archivo: " + filename);


    }
}

