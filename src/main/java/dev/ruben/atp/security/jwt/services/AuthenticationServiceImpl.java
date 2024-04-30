package dev.ruben.atp.security.jwt.services;

import dev.ruben.atp.auth.users.model.UserEntity;
import dev.ruben.atp.auth.users.model.UserRole;
import dev.ruben.atp.exceptions.NewUserWithDifferentPasswordsException;
import dev.ruben.atp.repository.UserEntityRepository;
import dev.ruben.atp.security.jwt.models.JwtUserResponse;
import dev.ruben.atp.security.jwt.models.LoginRequest;
import dev.ruben.atp.security.jwt.models.SignUpRequest;
import dev.ruben.atp.security.jwt.services.AuthenticationService;
import dev.ruben.atp.security.jwt.services.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementación de nuestro servicio de autenticación
 */
@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserEntityRepository authUsersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(UserEntityRepository authUsersRepository, PasswordEncoder passwordEncoder,
                                     JwtService jwtService, AuthenticationManager authenticationManager) {
        this.authUsersRepository = authUsersRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public JwtUserResponse signUp(SignUpRequest request) {
        log.info("Creando usuario: {}", request);
        if (request.getPassword().contentEquals(request.getConfirmPassword())) {
            UserEntity user = UserEntity.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .email(request.getEmail())
                    .roles(Stream.of(UserRole.USER).collect(Collectors.toSet()))
                    .build();
            try {
                var userStored = authUsersRepository.save(user);
                return JwtUserResponse.builder().token(jwtService.generateToken(userStored)).build();
            } catch (DataIntegrityViolationException ex) {
                throw new RuntimeException("El usuario ya existe");
            }
        } else {
            throw new NewUserWithDifferentPasswordsException();

        }
    }

    @Override
    public JwtUserResponse signIn(LoginRequest request) {
        log.info("Autenticando usuario: {}", request);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = authUsersRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Usuario o contraseña incorrectos"));
        var jwt = jwtService.generateToken(user);
        JwtUserResponse jwtUserResponse = JwtUserResponse.builder()
                .username(user.getUsername())
                .roles(user.getRoles())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .token(jwt)
                .build();

        return jwtUserResponse;
    }

}