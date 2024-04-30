package dev.ruben.atp.mapper;

import dev.ruben.atp.auth.users.model.UserEntity;
import dev.ruben.atp.auth.users.model.UserRole;
import dev.ruben.atp.dto.UserResponseDTO;
import dev.ruben.atp.security.jwt.models.JwtUserResponse;
import dev.ruben.atp.security.jwt.models.SignUpRequest;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component

public class UserMapper {

    public UserResponseDTO toUserResponseDTO(UserEntity userEntity) {
        return UserResponseDTO.builder()
                .username(userEntity.getUsername())
                .roles(userEntity.getRoles())
                .avatar(userEntity.getAvatar())
                .email(userEntity.getEmail())
                .build();
    }
    public JwtUserResponse toUserResponseDtoToken(UserEntity userEntity, String token) {
        return JwtUserResponse.jwtUserResponseBuilder()
                .username(userEntity.getUsername())
                .roles(userEntity.getRoles())
                .avatar(userEntity.getAvatar())
                .email(userEntity.getEmail())
                .token(token)

                .build();

    }
    public UserEntity toUserEntity(UserResponseDTO userResponseDTO) {
        return UserEntity.builder()
                .username(userResponseDTO.getUsername())
                .roles(userResponseDTO.getRoles())
                .build();
    }

    public JwtUserResponse convertUserEntityAndTokenToJwtUserResponse(UserEntity userEntity, String token) {
        return JwtUserResponse.jwtUserResponseBuilder()
                .username(userEntity.getUsername())
                .roles(userEntity.getRoles())
                .avatar(userEntity.getAvatar())
                .email(userEntity.getEmail())
                .token(token)
                .build();
    }


}
