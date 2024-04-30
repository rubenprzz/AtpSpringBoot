package dev.ruben.atp.services;

import dev.ruben.atp.auth.users.model.UserEntity;
import dev.ruben.atp.dto.UserCreateDTO;
import dev.ruben.atp.dto.UserResponseDTO;
import dev.ruben.atp.exceptions.NewUserWithDifferentPasswordsException;
import dev.ruben.atp.mapper.UserMapper;
import dev.ruben.atp.repository.UserEntityRepository;
import dev.ruben.atp.services.UserEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserEntityServiceTest {

    @Mock
    private UserEntityRepository userEntityRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    private UserEntityService userEntityService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userEntityService = new UserEntityService(passwordEncoder, userMapper);
    }

    @Test
    @DisplayName("Should return user when findByUsername is called with existing username")
    public void shouldReturnUserWhenFindByUsernameCalledWithExistingUsername() {
        UserEntity userEntity = new UserEntity();
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        when(userEntityRepository.findByUsername("username")).thenReturn(Optional.of(userEntity));
        when(userMapper.toUserResponseDTO(userEntity)).thenReturn(userResponseDTO);

        Optional<UserResponseDTO> result = Optional.ofNullable(userMapper.toUserResponseDTO(userEntityRepository.findByUsername("username").orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        )));

        assertTrue(result.isPresent());
        assertEquals(userResponseDTO, result.get());
    }

    @Test
    @DisplayName("Should return empty when findByUsername is called with non-existing username")
    public void shouldReturnEmptyWhenFindByUsernameCalledWithNonExistingUsername() {
        when(userEntityRepository.findByUsername("fsdsdfdsf")).thenReturn(Optional.empty());

        Optional<UserResponseDTO> result = Optional.ofNullable(userMapper.toUserResponseDTO(userEntityRepository.findByUsername("username").orElse(null)));;

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should create new user when nuevoUsuario is called with valid UserCreateDTO")
    public void shouldCreateNewUserWhenNuevoUsuarioCalledWithValidUserCreateDTO() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setUsername("username");
        userCreateDTO.setPassword("password");
        userCreateDTO.setConfirmPassword("password");

        UserEntity userEntity = new UserEntity();
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userEntityRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserEntity result =userEntityRepository.save(userEntity);

        assertEquals(userEntity, result);
    }

    @Test
    @DisplayName("Should throw NewUserWithDifferentPasswordsException when nuevoUsuario is called with different passwords")
    public void shouldThrowExceptionWhenNuevoUsuarioCalledWithDifferentPasswords() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setUsername("username");
        userCreateDTO.setPassword("password");
        userCreateDTO.setConfirmPassword("differentPassword");

        assertThrows(NewUserWithDifferentPasswordsException.class, () -> userEntityService.nuevoUsuario(userCreateDTO));
    }

    @Test
    @DisplayName("Should throw ResponseStatusException when nuevoUsuario is called and username already exists")
    public void shouldThrowExceptionWhenNuevoUsuarioCalledAndUsernameExists() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setUsername("username");
        userCreateDTO.setPassword("password");
        userCreateDTO.setConfirmPassword("password");

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userEntityRepository.save(any(UserEntity.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(NullPointerException.class, () -> userEntityService.nuevoUsuario(userCreateDTO));
    }
}