    package dev.ruben.atp.security.jwt;

    import dev.ruben.atp.auth.users.model.UserEntity;
    import dev.ruben.atp.auth.users.model.UserRole;
    import dev.ruben.atp.services.CustomUserDetailsService;
    import dev.ruben.atp.services.CustomUserDetailsServiceImpl;
    import jakarta.servlet.FilterChain;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import lombok.RequiredArgsConstructor;
    import org.springframework.context.annotation.Lazy;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.web.authentication.WebAuthenticationDetails;
    import org.springframework.stereotype.Component;
    import org.springframework.web.filter.OncePerRequestFilter;

    import java.io.IOException;

    @Component
    @RequiredArgsConstructor
    public class JwtAuthorizationFilter  extends OncePerRequestFilter {

        private final JwtProvider jwtTokenProvider;
        private final  CustomUserDetailsServiceImpl userDetailsService;
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            try {
                String token = getToken(request);
                if (token != null && jwtTokenProvider.validateToken(token)) {
                    Long id = jwtTokenProvider.getUserIdFromJWT(token);
                    UserEntity userDetails = (UserEntity) userDetailsService.loadUserById(id);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getRoles(), userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            } catch (Exception e) {
                logger.error("Error en el método doFilterInternal", e);
            }
            filterChain.doFilter(request, response);

        }
        private String getToken(HttpServletRequest request) {
            String header = request.getHeader(JwtProvider.TOKEN_HEADER);
            if (header != null && header.startsWith(JwtProvider.TOKEN_PREFIX)) {
                return header.substring(JwtProvider.TOKEN_HEADER.length(), header.length());
            }
            return null;
        }
    }
