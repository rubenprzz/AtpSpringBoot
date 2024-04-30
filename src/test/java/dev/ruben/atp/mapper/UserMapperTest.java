package dev.ruben.atp.mapper;

import dev.ruben.atp.auth.users.model.UserEntity;
import dev.ruben.atp.dto.UserResponseDTO;
import dev.ruben.atp.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import dev.ruben.atp.security.jwt.models.JwtUserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return UserResponseDTO when toUserResponseDTO is called with valid UserEntity")
    public void shouldReturnUserResponseDTOWhenToUserResponseDTOIsCalled() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUser");
        userEntity.setEmail("test@test.com");

        UserResponseDTO result = userMapper.toUserResponseDTO(userEntity);

        assertEquals(userEntity.getUsername(), result.getUsername());
        assertEquals(userEntity.getEmail(), result.getEmail());
    }

    @Test
    @DisplayName("Should return JwtUserResponse when toUserResponseDtoToken is called with valid UserEntity and token")
    public void shouldReturnJwtUserResponseWhenToUserResponseDtoTokenIsCalled() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUser");
        userEntity.setEmail("test@test.com");
        String token = "testToken";

        JwtUserResponse result = userMapper.toUserResponseDtoToken(userEntity, token);

        assertEquals(userEntity.getUsername(), result.getUsername());
        assertEquals(userEntity.getEmail(), result.getEmail());
        assertEquals(token, result.getToken());
    }

    @Test
    @DisplayName("Should return UserEntity when toUserEntity is called with valid UserResponseDTO")
    public void shouldReturnUserEntityWhenToUserEntityIsCalled() {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUsername("testUser");
        userResponseDTO.setEmail("test@test.com");

        UserEntity result = userMapper.toUserEntity(userResponseDTO);

        assertEquals(userResponseDTO.getUsername(), result.getUsername());
        assertEquals(null, result.getEmail());
    }

    @Test
    @DisplayName("Should return JwtUserResponse when convertUserEntityAndTokenToJwtUserResponse is called with valid UserEntity and token")
    public void shouldReturnJwtUserResponseWhenConvertUserEntityAndTokenToJwtUserResponseIsCalled() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUser");
        userEntity.setEmail("test@test.com");
        String token = "testToken";

        JwtUserResponse result = userMapper.convertUserEntityAndTokenToJwtUserResponse(userEntity, token);

        assertEquals(userEntity.getUsername(), result.getUsername());
        assertEquals(userEntity.getEmail(), result.getEmail());
        assertEquals(token, result.getToken());
    }
}