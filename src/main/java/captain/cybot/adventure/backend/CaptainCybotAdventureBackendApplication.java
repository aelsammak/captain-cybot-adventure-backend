package captain.cybot.adventure.backend;

import captain.cybot.adventure.backend.config.RSAKeyProperties;
import captain.cybot.adventure.backend.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@EnableConfigurationProperties(RSAKeyProperties.class)
@SpringBootApplication
public class CaptainCybotAdventureBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaptainCybotAdventureBackendApplication.class, args);
    }

}
