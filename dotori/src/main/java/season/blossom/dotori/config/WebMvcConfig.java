package season.blossom.dotori.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
<<<<<<< HEAD
                .allowedOrigins("http://localhost:3000", "http://localhost:5500", "http://localhost:52338")
                .allowCredentials(true)
                .allowedHeaders("*")
                .exposedHeaders("Set-Cookie")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE", "HEAD");
=======
                .allowedOrigins("http://localhost:3000", "http://localhost:5500", "http://localhost:8080")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE", "HEAD")
                .allowedHeaders("content-type")
                .exposedHeaders("Set-Cookie")
                .maxAge(1000)
                .allowCredentials(true);
>>>>>>> 434e70a (fix: 작성 오류 수정)
    }
}