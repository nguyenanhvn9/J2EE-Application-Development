// src/main/java/com/example/demo/config/WebConfig.java
package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.example.demo.converter.CategoryByIdConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.upload-dir}")
    private String uploadDir; // Sẽ là "./uploaded_images"

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Các handler mặc định (nếu có)
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");

        // Handler cho các file ảnh đã upload
        // Điều này ánh xạ URL /uploads/** tới thư mục vật lý ./uploaded_images/
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadDir + "/");
    }
    private final CategoryByIdConverter categoryByIdConverter;
    // Tiêm converter vào lớp cấu hình
    public WebConfig(CategoryByIdConverter categoryByIdConverter) {
        this.categoryByIdConverter = categoryByIdConverter;
    }

    // Trong WebConfig.java
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(categoryByIdConverter);
    }
}