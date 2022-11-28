package captain.cybot.adventure.backend.service;

import captain.cybot.adventure.backend.exception.InvalidRoleException;
import captain.cybot.adventure.backend.exception.PasswordInvalidException;
import captain.cybot.adventure.backend.exception.UserAlreadyExistsException;
import captain.cybot.adventure.backend.model.user.AllowedQuestions;
import captain.cybot.adventure.backend.model.user.User;
import captain.cybot.adventure.backend.model.user.UserStars;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    User saveUser(User user) throws UserAlreadyExistsException, InvalidRoleException, PasswordInvalidException;
    User getUser(String username);
    void addRoleToUser(String username, String roleName) throws UsernameNotFoundException, InvalidRoleException;

    List<AllowedQuestions> getAllowedQuestions(String username) throws UsernameNotFoundException;

    void deleteUser(User user);

    void incrementLevelsCompleted(String username, String planet);

    void incrementIncorrectAttempts(String username, String planet, int levelNumber);

    void updateStars(String username, String planet, int levelNumber, int stars);

    int getIncorrectAttempts(String username, String planet, int levelNumber);

    UserStars getUserStars(String username);
}
