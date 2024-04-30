package dev.ruben.atp.security;


import dev.ruben.atp.security.jwt.models.JwtUserResponse;
import dev.ruben.atp.security.jwt.models.LoginRequest;
import dev.ruben.atp.security.jwt.models.SignUpRequest;
import dev.ruben.atp.security.jwt.services.AuthenticationService;
import dev.ruben.atp.security.jwt.services.AuthenticationServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/auth")
@PreAuthorize("permitAll()")
public class AuthenticationRestController {
    private final AuthenticationServiceImpl authenticationService;

    @Autowired
    public AuthenticationRestController(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/signup")
    @PreAuthorize("hasRole('ANONYMOUS')")


    public ResponseEntity<JwtUserResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        log.info("Registrando usuario: {}", request);
        return ResponseEntity.ok(authenticationService.signUp(request));
    }


    @PostMapping("/signin")
    @PreAuthorize("hasRole('ANONYMOUS')")
    public ResponseEntity<JwtUserResponse> signIn(@Valid @RequestBody LoginRequest request) {
        log.info("Iniciando sesión de usuario: {}", request);
        return ResponseEntity.ok(authenticationService.signIn(request));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
