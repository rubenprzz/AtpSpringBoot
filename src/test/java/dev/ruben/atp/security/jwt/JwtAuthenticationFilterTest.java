package dev.ruben.atp.security.jwt;

import dev.ruben.atp.security.jwt.JwtAuthenticationFilter;
import dev.ruben.atp.security.jwt.services.JwtService;
import dev.ruben.atp.services.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.*;

public class JwtAuthenticationFilterTest {


    @Mock
    private JwtService jwtService;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, userDetailsService);
    }

    @Test
    @DisplayName("Should process request when Authorization header is present and starts with Bearer")
    public void shouldProcessRequestWhenAuthorizationHeaderIsPresentAndStartsWithBearer() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtService.extractUserName(anyString())).thenReturn("username");
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(jwtService.isTokenValid(anyString(), eq(userDetails))).thenReturn(true);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("Should not process request when Authorization header is not present")
    public void shouldNotProcessRequestWhenAuthorizationHeaderIsNotPresent() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("Should not process request when Authorization header does not start with Bearer")
    public void shouldNotProcessRequestWhenAuthorizationHeaderDoesNotStartWithBearer() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("token");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }
}