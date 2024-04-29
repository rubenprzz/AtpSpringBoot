package dev.ruben.atp.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {

    private  String username;
    private  String password;
    private  String confirmPassword;
}
