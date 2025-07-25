package com.techshop.config;

import com.techshop.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Autowired
        private CustomUserDetailsService userDetailsService;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .userDetailsService(userDetailsService)
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/cart/apply-voucher").permitAll()
                                                .requestMatchers("/admin/users/**").hasRole("ADMIN")
                                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                                .requestMatchers("/profile/**", "/order-history", "/orders/**",
                                                                "/cart/**")
                                                .hasRole("USER")
                                                .anyRequest().permitAll())
                                .csrf(csrf -> csrf.ignoringRequestMatchers("/cart/apply-voucher"))
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .defaultSuccessUrl("/")
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/")
                                                .permitAll())
                                .exceptionHandling(eh -> eh
                                                .accessDeniedPage("/login?error=forbidden"));
                return http.build();
        }
}