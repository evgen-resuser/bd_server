package org.evgen.bd_server;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(Consts.FRONT)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .allowCredentials(true)
                .allowedHeaders("*");
    }
}
