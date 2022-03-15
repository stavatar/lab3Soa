package lab2.stavatar.serveronelab2;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfiguration {
        //implements WebMvcConfigurer {

   /* @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("POST, GET, OPTIONS, DELETE, PUT")
                .allowedHeaders("Origin, X-Requested-With, Content-Type, Accept")
                .allowedOrigins("https://127.0.0.1:8181")
                .maxAge(3600);

    }*/
}