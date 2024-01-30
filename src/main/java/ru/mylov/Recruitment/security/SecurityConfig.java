package ru.mylov.Recruitment.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.mylov.Recruitment.model.Role;
import ru.mylov.Recruitment.service.UserService;

/**
 * Spring Security configuration class
 *
 * @author Mylov Sergey <mylov91@yandex.ru>
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Inject this object for transfer user credentials to authentication manager
     *
     * @see UserService
     * @see AuthenticationManager
     */
    private final UserService userService;
    private final JwtFilter jwtFilter;

    @Autowired
    public SecurityConfig(UserService userService, JwtFilter jwtFilter) {
        this.userService = userService;
        this.jwtFilter = jwtFilter;
    }

    /**
     * Processes the authentication request
     *
     * @return authentication result
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Allows to use encryption in passwords store
     *
     * @return standard BCrypt password encoder
     * @see BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Setting up a sequence of filters to display a custom authorization form and determining which endpoints
     * which role has access to, as well as setting up deleting cookies and closing the session when logging out
     *
     * @param http allows configuring web based security
     * @return an object of the HttpSecurity class on which all filters are applied
     * @throws Exception if something goes wrong
     * @see HttpSecurity
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers("/auth/**")
                        .permitAll()
                        .requestMatchers("/recruiter/**").hasAnyAuthority(Role.RECRUITER.name(), Role.ADMIN_RECRUITER.name())
                        .requestMatchers("/customer/**").hasAnyAuthority(Role.CUSTOMER_MANAGER.name())
                        .requestMatchers("/customer/applications/**").hasAnyAuthority(Role.CUSTOMER.name())
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}


