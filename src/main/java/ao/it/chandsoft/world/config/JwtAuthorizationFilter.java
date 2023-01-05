package ao.it.chandsoft.world.config;

import ao.it.chandsoft.world.domain.constants.SecurityConstants;
import ao.it.chandsoft.world.exceptions.JwtTokenException;
import ao.it.chandsoft.world.service.UserDetailsServiceImpl;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);

        if(Objects.isNull(header) || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        try {
            String token = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);
            if (Objects.isNull(token)) {
                return null;
            }

            String username = getUsernameFromToken(token.substring(7));
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            return Objects.nonNull(username) ? new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities()) : null;

        }catch (Exception e) {
            if (e.getMessage().contains("expired")) {
                throw new JwtTokenException("Expired token");
            } else if (e.getMessage().contains("JWT signature does not match")) { //JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted.
                throw new JwtTokenException("Invalid token");
            } else if (e.getMessage().contains("JWT strings must contain")) { //JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted.
                throw new JwtTokenException("Invalid token");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    private String getUsernameFromToken(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET_KEY)
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }
}
