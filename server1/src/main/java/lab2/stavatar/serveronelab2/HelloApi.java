package lab2.stavatar.serveronelab2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloApi {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot";
    }
}
