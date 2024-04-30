package dev.ruben.atp.controllers;

import dev.ruben.atp.auth.users.model.UserEntity;
import dev.ruben.atp.dto.UserCreateDTO;
import dev.ruben.atp.dto.UserResponseDTO;
import dev.ruben.atp.exceptions.NewUserWithDifferentPasswordsException;
import dev.ruben.atp.mapper.UserMapper;
import dev.ruben.atp.services.UserEntityService;
import dev.ruben.atp.utils.PaginationLinksUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserEntityService userEntityService;
    private final UserMapper userMapper;
    private final PaginationLinksUtils paginationLinksUtils;


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponseDTO>> getUsers(Pageable pageable, HttpServletRequest request) {
        Page<UserResponseDTO> users = userEntityService.findAll(pageable).map(userMapper::toUserResponseDTO);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
        return ResponseEntity.ok().header("link", paginationLinksUtils.createLinkHeader(users, uriBuilder))
                .body(users);
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> nuevoUsuario(@RequestBody UserCreateDTO userNew) {
        if(!userNew.getPassword().equals(userNew.getConfirmPassword())){
            throw new NewUserWithDifferentPasswordsException();
        }else if(userNew.getUsername().equals(userEntityService.findByUsername(userNew.getUsername()))){
            throw new DataIntegrityViolationException(HttpStatus.BAD_REQUEST + "Ya existe un usuario con ese nombre");
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(userMapper.toUserResponseDTO(userEntityService.nuevoUsuario(userNew)));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(HttpStatus.BAD_REQUEST + " " + e.getMessage());
        }

    }
    @GetMapping("/me")
    public UserResponseDTO yo(@AuthenticationPrincipal UserEntity user){
        return userMapper.toUserResponseDTO(user);

    }

}
