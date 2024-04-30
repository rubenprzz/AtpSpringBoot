package dev.ruben.atp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String jwtSigningKey;
    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @Bean
    public String jwtSigningKey() {
        return jwtSigningKey;
    }
    @Bean
    public Long jwtExpiration() {
        return jwtExpiration;
    }
}