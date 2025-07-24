package org.example.booking;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()   // Cho phép tất cả truy cập
                )
                .csrf(AbstractHttpConfigurer::disable)  // Tắt CSRF (nên tắt khi dùng API)
                .formLogin(AbstractHttpConfigurer::disable) // Tắt form login
                .httpBasic(AbstractHttpConfigurer::disable); // Tắt HTTP Basic auth

        return http.build();
    }
}
