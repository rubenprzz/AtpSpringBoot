package dev.ruben.atp.security.jwt.models;

import dev.ruben.atp.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class JwtUserResponse extends UserResponseDTO {
    private String token;
    @Builder(builderMethodName = "jwtUserResponseBuilder")
    public JwtUserResponse(String username, Set<String> roles, String avatar , String email, String token) {
        super(username, roles, avatar, email, token);
        this.token = token;
    }
}
