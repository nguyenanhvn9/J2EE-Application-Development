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
                                                .requestMatchers("/test/**").permitAll()
                                                // Quản lý Người dùng - chỉ ADMIN
                                                .requestMatchers("/admin/users/**").hasRole("ADMIN")
                                                // Quản lý Danh mục - chỉ ADMIN
                                                .requestMatchers("/admin/categories/**").hasRole("ADMIN")
                                                // Xem, Thêm, Sửa Sản phẩm - ADMIN và MANAGER
                                                .requestMatchers("/admin/products", "/admin/products/add",
                                                                "/admin/products/edit/**")
                                                .hasAnyRole("ADMIN", "MANAGER")
                                                // Xóa Sản phẩm - chỉ ADMIN
                                                .requestMatchers("/admin/products/delete/**").hasRole("ADMIN")
                                                // Xem, Sửa Đơn hàng - ADMIN, MANAGER, LEADER
                                                .requestMatchers("/admin/orders", "/admin/orders/edit/**")
                                                .hasAnyRole("ADMIN", "MANAGER", "LEADER")
                                                // Các chức năng khác của admin - ADMIN, MANAGER, LEADER
                                                .requestMatchers("/admin/dashboard", "/admin/vouchers/**")
                                                .hasAnyRole("ADMIN", "MANAGER", "LEADER")
                                                // Chức năng user thông thường
                                                .requestMatchers("/profile/**", "/order-history", "/orders/**",
                                                                "/cart/**")
                                                .hasAnyRole("USER", "MANAGER", "LEADER", "ADMIN")
                                                .anyRequest().permitAll())
                                .exceptionHandling(ex -> ex
                                                .accessDeniedPage("/error/403"))
                                .csrf(csrf -> csrf.ignoringRequestMatchers("/cart/apply-voucher"))
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .defaultSuccessUrl("/")
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/")
                                                .permitAll());
                return http.build();
        }
}