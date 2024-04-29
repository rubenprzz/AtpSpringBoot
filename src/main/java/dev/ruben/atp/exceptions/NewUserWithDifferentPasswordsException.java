package dev.ruben.atp.exceptions;

public class NewUserWithDifferentPasswordsException extends RuntimeException {


    public NewUserWithDifferentPasswordsException() {
        super("Las contraseñas no coinciden");
    }
}
