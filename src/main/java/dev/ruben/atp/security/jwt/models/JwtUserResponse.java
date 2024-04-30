package dev.ruben.atp.security.jwt.models;

import dev.ruben.atp.auth.users.model.UserRole;
import dev.ruben.atp.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@Builder
public class JwtUserResponse  {
    private String username;
    private Set<UserRole> roles;
    private String avatar;
    private String email;
    private String token;
    @Builder(builderMethodName = "jwtUserResponseBuilder")
    public JwtUserResponse(String username, Set<UserRole> roles, String avatar , String email, String token) {
        this.username = username;
        this.roles = roles;
        this.avatar = avatar;
        this.email = email;
        this.token = token;
    }
}
