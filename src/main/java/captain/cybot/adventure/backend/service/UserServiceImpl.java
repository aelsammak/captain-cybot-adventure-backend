package captain.cybot.adventure.backend.service;

import captain.cybot.adventure.backend.constants.ROLES;
import captain.cybot.adventure.backend.exception.InvalidRoleException;
import captain.cybot.adventure.backend.exception.UserAlreadyExistsException;
import captain.cybot.adventure.backend.model.user.Role;
import captain.cybot.adventure.backend.model.user.User;
import captain.cybot.adventure.backend.repository.user.RoleRepository;
import captain.cybot.adventure.backend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public User saveUser(User user) throws UserAlreadyExistsException {
        if(usernameAlreadyExists(user.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists.");
        } else if(emailAlreadyExists(user.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists.");
        }

        user.setPassword(passwordEncoder().encode(user.getPassword()));
        User newUser = userRepository.save(user);
        log.info(newUser.getUsername() + "'s account has been created.");
        return newUser;
    }

    @Override
    public User getUser(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException(username + " not found in the database.");
        }

        log.info(user.getUsername() + " has been retrieved from the database.");
        return user;
    }

    private boolean usernameAlreadyExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    private boolean emailAlreadyExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Override
    public void addRoleToUser(String username, String roleName) throws UsernameNotFoundException, InvalidRoleException {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        if (user != null && role != null) {
            user.getRoles().add(role);
            log.info(role.getName() + " has been added to " + user.getUsername());
        } else if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        } else {
            throw new InvalidRoleException("Invalid Role name: " + roleName +
                    " Valid Role names: " + ROLES.ROLE_USER.toString() + " AND " + ROLES.ROLE_ADMIN.toString());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
