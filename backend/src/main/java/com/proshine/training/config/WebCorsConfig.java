// backend/src/main/java/com/proshine/training/config/WebCorsConfig.java
package com.proshine.training.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.*;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class WebCorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // 前端 dev 端口
        cfg.setAllowedMethods(Arrays.asList("*"));      // GET/POST/PUT/DELETE/OPTIONS
        cfg.setAllowedHeaders(Arrays.asList("*"));      // Authorization, Content-Type…
        cfg.setAllowCredentials(true);
        cfg.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);   // 注意：用 /** 捕获所有
        return new CorsFilter(source);
    }
}
