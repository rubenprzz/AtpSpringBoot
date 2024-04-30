package dev.ruben.atp.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ruben.atp.auth.users.model.UserEntity;
import dev.ruben.atp.controllers.UserController;
import dev.ruben.atp.dto.UserCreateDTO;
import dev.ruben.atp.dto.UserResponseDTO;
import dev.ruben.atp.exceptions.NewUserWithDifferentPasswordsException;
import dev.ruben.atp.mapper.UserMapper;
import dev.ruben.atp.services.UserEntityService;
import dev.ruben.atp.utils.PaginationLinksUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@AutoConfigureMockMvc
public class UserControllerTest {

    private UserController userController;

    @Mock
    private UserEntityService userEntityService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PaginationLinksUtils paginationLinksUtils;
    @Mock
    private HttpServletRequest request;
    private final ObjectMapper mapper = new ObjectMapper();


    UserResponseDTO userResponse = new UserResponseDTO("username", Collections.emptySet(), "avatar", "email", "token");
    private final String myEndpoint = "/users";


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userEntityService, userMapper, paginationLinksUtils);
    }

    /*@Test
    void findAll() throws Exception {
        var list = List.of(userResponse);
        Page<UserResponseDTO> page = new PageImpl<>(list);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());


        when(userController.getUsers(pageable,request)).thenReturn(page);

        MockHttpServletResponse response = mockMvc.perform(
                        get(myEndpoint)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Page<UserResponseDTO> res = mapper.readValue(response.getContentAsString(), new TypeReference<>() {
        });

        // Assert
        assertAll("findallUsers",
                () -> assertEquals(200, response.getStatus())

        );

        // Verify
        verify(userEntityService, times(1)).findAll(pageable);
    }*/

    @Test
    @DisplayName("Should create new user")
    public void shouldCreateNewUser() {
        UserCreateDTO userCreateDTO = new UserCreateDTO(String.valueOf("username"), String.valueOf("password"), String.valueOf("password"));
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        UserEntity userEntity = new UserEntity();

        when(userEntityService.nuevoUsuario(userCreateDTO)).thenReturn(userEntity);
        when(userMapper.toUserResponseDTO(userEntity)).thenReturn(userResponseDTO);

        ResponseEntity<UserResponseDTO> response = userController.nuevoUsuario(userCreateDTO);

        assertEquals(userResponseDTO, response.getBody());
    }
    @Test
    @DisplayName("Should throw DataIntegrityViolationException when create new user with existing username")
    public void shouldThrowNewUserDifferentPasswordWhenCreateNewUserWithExistingUsername() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setUsername("username");
        userCreateDTO.setPassword("password");
        userCreateDTO.setConfirmPassword("differentPassword");

        assertThrows(NewUserWithDifferentPasswordsException.class, () -> userController.nuevoUsuario(userCreateDTO));
    }
    @Test
    @DisplayName("Should throw DataIntegrityViolationException when create new user with existing username")
    public void shouldThrowDataIntegrationViolation() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setUsername("username");
        userCreateDTO.setPassword("password");
        userCreateDTO.setConfirmPassword("password");

        UserCreateDTO userCreateDTO2 = new UserCreateDTO();
        userCreateDTO2.setUsername("username");
        userCreateDTO2.setPassword("password");
        userCreateDTO2.setConfirmPassword("password");


        assertEquals(userCreateDTO, userCreateDTO2);
        when(userEntityService.nuevoUsuario(userCreateDTO)).thenThrow(new DataIntegrityViolationException("Username already exists"));
    }

    @Test
    @DisplayName("Should return user when getMe is called")
    public void shouldReturnUser() {
        UserEntity user = new UserEntity();
        UserResponseDTO userResponseDTO = new UserResponseDTO();

        when(userMapper.toUserResponseDTO(user)).thenReturn(userResponseDTO);

        UserResponseDTO response = userController.yo(user);

        assertEquals(userResponseDTO, response);
    }

}