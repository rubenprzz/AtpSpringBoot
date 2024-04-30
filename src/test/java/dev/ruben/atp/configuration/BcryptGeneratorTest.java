package dev.ruben.atp.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BcryptGeneratorTest {

    @Test
    public void testBcryptGeneration() {
        // Given
        String password = "contraseña";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // When
        String hashedPassword = passwordEncoder.encode(password);

        // Then
        assertTrue(passwordEncoder.matches(password, hashedPassword));
    }
}
