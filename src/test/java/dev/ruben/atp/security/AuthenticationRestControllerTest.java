package dev.ruben.atp.security;

import dev.ruben.atp.security.jwt.models.JwtUserResponse;
import dev.ruben.atp.security.jwt.models.LoginRequest;
import dev.ruben.atp.security.jwt.models.SignUpRequest;
import dev.ruben.atp.security.jwt.services.AuthenticationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthenticationRestControllerTest {

    @Mock
    private AuthenticationServiceImpl authenticationService;

    @InjectMocks
    private AuthenticationRestController authenticationRestController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return JwtUserResponse when signUp is called with valid request")
    public void shouldReturnJwtUserResponseWhenSignUpIsCalled() {
        SignUpRequest request = new SignUpRequest();
        JwtUserResponse response = new JwtUserResponse();
        when(authenticationService.signUp(any())).thenReturn(response);

        ResponseEntity<JwtUserResponse> result = authenticationRestController.signUp(request);

        assertEquals(response, result.getBody());
    }
    @Test
    @DisplayName("Should throw MethodArgumentNotValidException when signUp is called with invalid request")
    public void shouldThrowMethodArgumentNotValidExceptionWhenSignUpIsCalledWithInvalidRequest() {
        SignUpRequest request = new SignUpRequest();
        request.setUsername("user");
        request.setPassword("pass");
    }

    @Test
    @DisplayName("Should return JwtUserResponse when signIn is called with valid request")
    public void shouldReturnJwtUserResponseWhenSignInIsCalled() {
        LoginRequest request = new LoginRequest();
        JwtUserResponse response = new JwtUserResponse();
        when(authenticationService.signIn(any())).thenReturn(response);

        ResponseEntity<JwtUserResponse> result = authenticationRestController.signIn(request);

        assertEquals(response, result.getBody());
    }
    @Test
    @DisplayName("Should throw MethodArgumentNotValidException when signIn is called with invalid request")
    public void shouldThrowMethodArgumentNotValidExceptionWhenSignInIsCalledWithInvalidRequest() {
        LoginRequest request = new LoginRequest();
        request.setUsername("user");
        request.setPassword("pass");
    }
}