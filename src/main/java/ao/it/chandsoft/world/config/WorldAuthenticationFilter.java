package ao.it.chandsoft.world.config;

import ao.it.chandsoft.world.domain.Login;
import ao.it.chandsoft.world.domain.UserModel;
import ao.it.chandsoft.world.domain.constants.SecurityConstants;
import ao.it.chandsoft.world.repository.UtilizadorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Slf4j
public class WorldAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UtilizadorRepository utilizadorRepository;
    private AuthenticationManager authenticationManager;

    public WorldAuthenticationFilter(AuthenticationManager authenticationManager, UtilizadorRepository utilizadorRepository) {
        this.authenticationManager = authenticationManager;
        this.utilizadorRepository = utilizadorRepository;

        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Login login = new ObjectMapper().readValue(request.getInputStream(), Login.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        } catch (IOException ex) {
            log.error("Erro de autenticacao", ex);
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = ((User)authResult.getPrincipal()).getUsername();
        String jwtToken = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET_KEY)
                .compact();

        createResponse(jwtToken, response);
    }

    private void createResponse(String jwtToken, HttpServletResponse response) throws IOException {
        Map<String, Object> reponseBody = Collections.singletonMap("token", jwtToken);
        String jsonResponseBody = new ObjectMapper().writeValueAsString(reponseBody);
        response.getWriter().write(jsonResponseBody);
        response.addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
    }
}
