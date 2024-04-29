package dev.ruben.atp.mapper;

import dev.ruben.atp.auth.users.model.UserEntity;
import dev.ruben.atp.auth.users.model.UserRole;
import dev.ruben.atp.dto.UserResponseDTO;
import dev.ruben.atp.security.jwt.models.JwtUserResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component

public class UserMapper {

    public UserResponseDTO toUserResponseDTO(UserEntity userEntity) {
        return UserResponseDTO.builder()
                .username(userEntity.getUsername())
                .roles(userEntity.getRoles().stream().map(UserRole::name).collect(Collectors.toSet()))
                .build();
    }

    public JwtUserResponse toUserResponseDtoToken(UserEntity userEntity, String token) {
        return JwtUserResponse.jwtUserResponseBuilder()
                .username(userEntity.getUsername())
                .roles(userEntity.getRoles().stream().map(UserRole::name).collect(Collectors.toSet()))
                .avatar(userEntity.getAvatar())
                .email(userEntity.getEmail())
                .token(token)

                .build();

    }
}
