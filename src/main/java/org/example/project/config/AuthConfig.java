package org.example.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@Profile("mysql")
public class AuthConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .formLogin()
                .and()
                .logout();

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        return username -> jdbcTemplate.queryForObject(
                """
                SELECT u.username, u.password, u.enabled, r.name AS role
                FROM user u
                JOIN role r ON u.role_id = r.id
                WHERE u.username = ?
                """,
                (rs, rowNum) -> User.withUsername(rs.getString("username"))
                        .password(rs.getString("password"))
                        .disabled(!rs.getBoolean("enabled"))
                        .authorities(rs.getString("role"))
                        .build(),
                username
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}