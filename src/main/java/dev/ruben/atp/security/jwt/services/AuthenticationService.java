package dev.ruben.atp.security.jwt.services;

import dev.ruben.atp.security.jwt.models.JwtUserResponse;
import dev.ruben.atp.security.jwt.models.LoginRequest;
import dev.ruben.atp.security.jwt.models.SignUpRequest;

public interface AuthenticationService {
    JwtUserResponse signUp(SignUpRequest request);

    JwtUserResponse signIn(LoginRequest request);
}