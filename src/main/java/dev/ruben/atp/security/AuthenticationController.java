package dev.ruben.atp.security;


import dev.ruben.atp.auth.users.model.UserEntity;
import dev.ruben.atp.dto.UserResponseDTO;
import dev.ruben.atp.mapper.UserMapper;
import dev.ruben.atp.security.jwt.JwtProvider;
import dev.ruben.atp.security.jwt.models.JwtUserResponse;
import dev.ruben.atp.security.jwt.models.LoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtTokenProvider;
    private final UserMapper userMapper;

     @PostMapping("auth/login")
    public ResponseEntity<JwtUserResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
         SecurityContextHolder.getContext().setAuthentication(authentication);
         UserEntity userEntity = (UserEntity) authentication.getPrincipal();
            String jwtToken = jwtTokenProvider.generateToken(authentication);

           return ResponseEntity.status(200).body(userMapper.toUserResponseDtoToken(userEntity, jwtToken));

    }
    public UserResponseDTO me(@AuthenticationPrincipal UserEntity userEntity ) {
        return userMapper.toUserResponseDTO((UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

}
