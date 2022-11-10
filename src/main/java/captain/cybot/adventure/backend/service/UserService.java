package captain.cybot.adventure.backend.service;

import captain.cybot.adventure.backend.exception.InvalidRoleException;
import captain.cybot.adventure.backend.exception.UserAlreadyExistsException;
import captain.cybot.adventure.backend.model.user.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    User saveUser(User user) throws UserAlreadyExistsException;
    User getUser(String username) throws UsernameNotFoundException;
    void addRoleToUser(String username, String roleName) throws UsernameNotFoundException, InvalidRoleException;
}
