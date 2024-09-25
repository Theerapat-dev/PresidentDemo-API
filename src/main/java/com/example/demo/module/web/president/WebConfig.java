package com.example.demo.module.web.president;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // สำหรับทุก endpoint
                .allowedOrigins("https://acec-61-7-146-25.ngrok-free.app") // แหล่งที่มาที่อนุญาต
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // วิธี HTTP ที่อนุญาต
                .allowedHeaders("*") // หัวข้อที่อนุญาต
                .allowCredentials(true); // อนุญาตให้ใช้ cookies
    }
}