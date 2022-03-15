package lab2.stavatar.serveronelab2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServerOneLab2Application {

    public static void main(String[] args) {
		SpringApplication.run(ServerOneLab2Application.class, args);
	}

}
