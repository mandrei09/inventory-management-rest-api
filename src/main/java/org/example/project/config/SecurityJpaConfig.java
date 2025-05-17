package org.example.project.config;

import org.example.project.service.security.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("mysql")
public class SecurityJpaConfig {

    private final JpaUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public SecurityJpaConfig(JpaUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()

                        // GET allowed for guests and admin
                        .requestMatchers(HttpMethod.GET, "/inventories/**").hasAnyRole("ADMIN", "GUEST")
                        .requestMatchers(HttpMethod.GET, "/assets/**").hasAnyRole("ADMIN", "GUEST")
                        .requestMatchers(HttpMethod.GET, "/companies/**").hasAnyRole("ADMIN", "GUEST")
                        .requestMatchers(HttpMethod.GET, "/costcenters/**").hasAnyRole("ADMIN", "GUEST")
                        .requestMatchers(HttpMethod.GET, "/departments/**").hasAnyRole("ADMIN", "GUEST")
                        .requestMatchers(HttpMethod.GET, "/divisions/**").hasAnyRole("ADMIN", "GUEST")
                        .requestMatchers(HttpMethod.GET, "/employees/**").hasAnyRole("ADMIN", "GUEST")

                        // POST/PUT/DELETE only for admin
                        .requestMatchers(HttpMethod.POST, "/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsService)
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .permitAll()
                                .loginProcessingUrl("/perform_login")
                )
                .exceptionHandling(ex -> ex.accessDeniedPage("/access_denied"))
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}