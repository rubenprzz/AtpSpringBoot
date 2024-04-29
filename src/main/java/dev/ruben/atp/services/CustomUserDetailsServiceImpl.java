package dev.ruben.atp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private final UserEntityService userEntityService;
@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userEntityService.findByUsername(username).orElseThrow();
    }
    public UserDetails loadUserById(Long id) {
        return userEntityService.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
