package dev.ruben.atp.services;

import dev.ruben.atp.auth.users.model.UserEntity;
import dev.ruben.atp.dto.UserResponseDTO;
import dev.ruben.atp.mapper.UserMapper;
import dev.ruben.atp.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private final UserEntityRepository authUsersRepository;

    


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authUsersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario con username " + username + " no encontrado"));
    }



}
