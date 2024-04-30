package dev.ruben.atp.services;

import dev.ruben.atp.auth.users.model.UserEntity;
import dev.ruben.atp.auth.users.model.UserRole;
import dev.ruben.atp.dto.UserCreateDTO;
import dev.ruben.atp.dto.UserResponseDTO;
import dev.ruben.atp.exceptions.NewUserWithDifferentPasswordsException;
import dev.ruben.atp.mapper.UserMapper;
import dev.ruben.atp.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, Long, UserEntityRepository>{

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public Optional<UserResponseDTO> findByUsername(String username) {
        var user= this.repositorio.findByUsername(username);
        return user.map(userMapper::toUserResponseDTO);

    }
    public UserEntity nuevoUsuario(UserCreateDTO userCreateDTO) {
        if(!userCreateDTO.getPassword().contentEquals(userCreateDTO.getConfirmPassword())) {
            throw new NewUserWithDifferentPasswordsException();
        }
        UserEntity userEntity = UserEntity.builder()
                .username(userCreateDTO.getUsername())
                .password(passwordEncoder.encode(userCreateDTO.getPassword()))
                .roles(Set.of(UserRole.USER))
                .build();
        try {
            return save(userEntity);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

    }
    

}
