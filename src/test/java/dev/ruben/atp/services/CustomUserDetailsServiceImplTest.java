package dev.ruben.atp.services;

import dev.ruben.atp.services.CustomUserDetailsServiceImpl;
import dev.ruben.atp.auth.users.model.UserEntity;
import dev.ruben.atp.repository.UserEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomUserDetailsServiceImplTest {

    @Mock
    private UserEntityRepository userEntityRepository;

    private CustomUserDetailsServiceImpl customUserDetailsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        customUserDetailsService = new CustomUserDetailsServiceImpl(userEntityRepository);
    }

    @Test
    @DisplayName("Should return UserDetails when user with given username exists")
    public void shouldReturnUserDetailsWhenUserWithGivenUsernameExists() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUser");
        when(userEntityRepository.findByUsername("testUser")).thenReturn(Optional.of(userEntity));

        UserDetails result = customUserDetailsService.loadUserByUsername("testUser");

        assertEquals(userEntity.getUsername(), result.getUsername());
    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException when user with given username does not exist")
    public void shouldThrowUsernameNotFoundExceptionWhenUserWithGivenUsernameDoesNotExist() {
        when(userEntityRepository.findByUsername("testUser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername("testUser"));
    }

}