package com.andre.reportai.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    // Em desenvolvimento, o Vite pode subir em portas diferentes (5173, 5174, ...)
    // se a porta padrão estiver ocupada — por isso liberamos qualquer porta do
    // localhost aqui. Em produção, trocar por allowedOrigins("https://seu-dominio.com").
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOriginPatterns("http://localhost:*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}

