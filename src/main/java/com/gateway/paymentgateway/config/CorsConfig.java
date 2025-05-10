package com.gateway.paymentgateway.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*") // Allow all HTTP methods (GET, POST, etc.)
                .allowedHeaders("*") // Allow all headers
                .allowedOrigins("*") // Allow all origins
                .allowCredentials(false);  // Disable credentials
    }
}
