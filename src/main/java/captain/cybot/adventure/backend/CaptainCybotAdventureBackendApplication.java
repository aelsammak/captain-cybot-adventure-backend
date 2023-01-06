package captain.cybot.adventure.backend;

import captain.cybot.adventure.backend.config.RSAKeyProperties;
import captain.cybot.adventure.backend.config.SecurityConfig;
import captain.cybot.adventure.backend.constants.ROLES;
import captain.cybot.adventure.backend.model.user.User;
import captain.cybot.adventure.backend.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

@EnableConfigurationProperties(RSAKeyProperties.class)
@SpringBootApplication
public class CaptainCybotAdventureBackendApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(CaptainCybotAdventureBackendApplication.class, args);
        UserService userService = applicationContext.getBean(UserService.class);
        if (userService.getUser("admin") == null) {
            try {
                userService.saveUser(new User("admin", "admin@gmail.com", "Admin2023?"));
                userService.addRoleToUser("admin", ROLES.ROLE_ADMIN.toString());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
