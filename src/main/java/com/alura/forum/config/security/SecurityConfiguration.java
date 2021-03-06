package com.alura.forum.config.security;

import com.alura.forum.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@Profile(value = {"prod", "test"})
public class SecurityConfiguration {

    private static final String TOPICOS_API_URL = "/topicos";

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> {
                            try {
                                authz
                                        .antMatchers(HttpMethod.GET, TOPICOS_API_URL).permitAll()
                                        .antMatchers(HttpMethod.GET, TOPICOS_API_URL + "/*").permitAll()
                                        .antMatchers(HttpMethod.POST, "/auth").permitAll()
                                        .antMatchers(HttpMethod.GET, "/actuator").permitAll()
                                        .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                                        .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                        .antMatchers(HttpMethod.DELETE, TOPICOS_API_URL + "/*").hasRole("MODERADOR")
                                        .anyRequest().authenticated()
                                        .and().csrf().disable()
                                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                        .and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
                            } catch (Exception e) {
                                System.out.println("Authentication Exception");
                            }
                        }
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers("/**.html", "/v3/api-docs", "/webjars/**","/configuration/**", "/swagger-resources/**");
    }

}
