package dev.ruben.atp.security.jwt.models;

import dev.ruben.atp.dto.UserCreateDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest extends UserCreateDTO {
    private String email;
    private String avatar;


}
