package com.example.demo.config;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.slf4j.Logger; // Import Logger
import org.slf4j.LoggerFactory; // Import LoggerFactory

import java.util.Optional;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    // Khai báo Logger để ghi log một cách chuyên nghiệp hơn
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Cho phép truy cập CÔNG KHAI vào các đường dẫn sau:
                        // - Trang chủ, đăng ký, đăng nhập
                        // - Tài nguyên tĩnh (CSS, JS, Images, Videos)
                        // - Trang chi tiết sản phẩm và danh mục
                        // - API công khai để lấy dữ liệu sản phẩm (quan trọng cho việc hiển thị khi chưa đăng nhập)
                        .requestMatchers("/", "/signup", "/login",
                                "/css/**", "/js/**", "/images/**", "/videos/**", // Thêm /videos/** để video banner hiển thị
                                "/product/**", "/category/**", // Trang chi tiết sản phẩm và danh mục sản phẩm
                                "/api/products/**").permitAll() // RẤT QUAN TRỌNG: Cho phép truy cập API sản phẩm (bao gồm cả lọc theo danh mục)
                        // Quyền ADMIN và MANAGER cho các đường dẫn admin chung (xem, thêm, sửa sản phẩm, quản lý đơn hàng)
                        .requestMatchers("/admin/products", "/admin/add-product", "/admin/edit-product/**", "/admin/orders").hasAnyRole("ADMIN", "MANAGER")
                        // Chỉ ADMIN mới có quyền xóa sản phẩm
                        .requestMatchers("/admin/delete-product/**").hasRole("ADMIN")
                        // Các đường dẫn yêu cầu người dùng đã xác thực (đăng nhập)
                        .requestMatchers("/cart", "/order/**", "/my-orders/**", "/profile/**").authenticated()
                        // Mọi request khác không khớp với các quy tắc trên đều yêu cầu xác thực
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/?logoutSuccess=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .csrf(csrf -> csrf.disable()); // Tạm thời vô hiệu hóa CSRF để debug (nhớ bật lại khi deploy production)
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Optional<User> userOptional = userRepository.findByUsername(username);
            User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
            if (!user.isActive()) {
                throw new UsernameNotFoundException("User is inactive: " + username);
            }
            // Sử dụng logger thay vì System.out.println để ghi log
            logger.info("User roles for {}: {}", username, user.getAuthorities());
            return user;
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
