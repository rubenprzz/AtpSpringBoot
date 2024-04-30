package dev.ruben.atp.dto;

import dev.ruben.atp.auth.users.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private String username;
    private Set<UserRole> roles;
    private String avatar;
    private String email;
    private String token;

}
