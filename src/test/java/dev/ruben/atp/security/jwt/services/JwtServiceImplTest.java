package dev.ruben.atp.security.jwt.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.ruben.atp.security.jwt.services.JwtServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class JwtServiceImplTest {

    @Mock
    private UserDetails userDetails;

    private JwtServiceImpl jwtService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        jwtService = new JwtServiceImpl("3Kjuhp9GfKv0kjzMql6blj1KRgR5EBFj0zcMVQvF9uJceQEimh3Kjuhp9GfKv0kjzMql6blj1KRgR5EBFj0zcMVQvF9uJceQEimh", 3600000L);

    }

    @Test
    @DisplayName("Should return username when extractUserName is called with valid token")
    public void shouldReturnUsernameWhenExtractUserNameCalledWithValidToken() {
        String token = JWT.create().withSubject("username").sign(Algorithm.HMAC512(Base64.getEncoder().encode("secret".getBytes())));
        String username = jwtService.extractUserName(token);
        assertEquals("username", username);
    }

    @Test
    @DisplayName("Should return generated token when generateToken is called with valid UserDetails")
    public void shouldReturnGeneratedTokenWhenGenerateTokenCalledWithValidUserDetails() {
        when(userDetails.getUsername()).thenReturn("username");
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
    }

    @Test
    @DisplayName("Should return true when isTokenValid is called with valid token and UserDetails")
    public void shouldReturnTrueWhenIsTokenValidCalledWithValidTokenAndUserDetails() {
        when(userDetails.getUsername()).thenReturn("username");
        String token = jwtService.generateToken(userDetails);
        boolean isValid = jwtService.isTokenValid(token, userDetails);
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Should return false when isTokenValid is called with expired token and UserDetails")
    public void shouldReturnFalseWhenIsTokenValidCalledWithExpiredTokenAndUserDetails() {
        when(userDetails.getUsername()).thenReturn("username");
        String token = JWT.create().withSubject("username").withExpiresAt(new Date(System.currentTimeMillis() - 1000)).sign(Algorithm.HMAC512(Base64.getEncoder().encode("secret".getBytes())));
        boolean isValid = jwtService.isTokenValid(token, userDetails);
        assertFalse(isValid);
    }
}