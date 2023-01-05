package ao.it.chandsoft.world.config;

import ao.it.chandsoft.world.handler.AccessoNegadoHandler;
import ao.it.chandsoft.world.repository.UtilizadorRepository;
import ao.it.chandsoft.world.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfigure extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UtilizadorRepository utilizadorRepository;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/hello-world").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/countries").permitAll()
                //.antMatchers(HttpMethod.GET, "/v1/countries/*").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/users").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/v1/countries/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //.authenticationProvider(new SmsRecebidaAuthenticationProvider())
                .addFilter(new WorldAuthenticationFilter(authenticationManager(), utilizadorRepository))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userDetailsService))
                .exceptionHandling().accessDeniedHandler(new AccessoNegadoHandler())
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

}
