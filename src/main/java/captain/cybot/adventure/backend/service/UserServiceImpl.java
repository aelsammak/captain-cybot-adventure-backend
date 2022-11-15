package captain.cybot.adventure.backend.service;

import captain.cybot.adventure.backend.constants.COSMETICS;
import captain.cybot.adventure.backend.constants.ROLES;
import captain.cybot.adventure.backend.exception.InvalidRoleException;
import captain.cybot.adventure.backend.exception.PasswordInvalidException;
import captain.cybot.adventure.backend.exception.UserAlreadyExistsException;
import captain.cybot.adventure.backend.model.user.Cosmetic;
import captain.cybot.adventure.backend.model.user.Role;
import captain.cybot.adventure.backend.model.user.User;
import captain.cybot.adventure.backend.repository.user.CosmeticRepository;
import captain.cybot.adventure.backend.repository.user.RoleRepository;
import captain.cybot.adventure.backend.repository.user.UserRepository;
import captain.cybot.adventure.backend.validator.PasswordConstraintValidator;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private CosmeticRepository cosmeticRepository;

    @Autowired
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public PasswordConstraintValidator passwordConstraintValidator() {
        return new PasswordConstraintValidator();
    }

    @Override
    public User saveUser(User user) throws UserAlreadyExistsException, InvalidRoleException, PasswordInvalidException {
        if(usernameAlreadyExists(user.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists.");
        } else if(emailAlreadyExists(user.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists.");
        } else if (!passwordConstraintValidator().isValid(user.getPassword())) {
            throw new PasswordInvalidException("Password must contain the following: \n-8 to 24 characters" +
                    "\n-ONE lowercase character \n-ONE uppercase character" +
                    "\n-ONE digit character \n-ONE special character");
        }
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        User newUser = userRepository.save(user);
        log.info(newUser.getUsername() + "'s account has been created.");
        addRoleToUser(newUser.getUsername(), ROLES.ROLE_USER.toString());
        setDefaultCosmetic(newUser.getUsername());
        return newUser;
    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user " + username);
        return userRepository.findByUsername(username);
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
                    " Valid Role names: " + ROLES.ROLE_USER + " AND " + ROLES.ROLE_ADMIN);
        }
    }

    public void setDefaultCosmetic(String username) {
        User user = userRepository.findByUsername(username);
        Cosmetic defaultShield = cosmeticRepository.findByFileName(COSMETICS.DEFAULT_SHIELD.toString());
        if (user != null && defaultShield != null) {
            user.setCosmetic(defaultShield);
        } else if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
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
