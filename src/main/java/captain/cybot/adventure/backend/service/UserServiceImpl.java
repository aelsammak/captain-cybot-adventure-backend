package captain.cybot.adventure.backend.service;

import captain.cybot.adventure.backend.constants.COSMETICS;
import captain.cybot.adventure.backend.constants.ROLES;
import captain.cybot.adventure.backend.exception.InvalidRoleException;
import captain.cybot.adventure.backend.exception.PasswordInvalidException;
import captain.cybot.adventure.backend.exception.UserAlreadyExistsException;
import captain.cybot.adventure.backend.model.user.*;
import captain.cybot.adventure.backend.repository.user.*;
import captain.cybot.adventure.backend.utility.StringUtility;
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
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private CosmeticRepository cosmeticRepository;

    private WorldRepository worldRepository;
    private LevelRepository levelRepository;

    @Autowired
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public PasswordConstraintValidator passwordConstraintValidator() {
        return new PasswordConstraintValidator();
    }

    private void addWorldsToUser(String username) {
        User user = userRepository.findByUsername(username);


        String[] WORLD_NAMES = {"EARTH", "MARS", "NEPTUNE", "JUPITER"};
        int numQuestions = 4;
        World tempWorld;
        Level tempLevel;

        for (String worldName : WORLD_NAMES) {
            tempWorld = new World(worldName);
            for (int i = 0; i < numQuestions; i++) {
                tempLevel = new Level(i+1);
                tempLevel.setWorld(tempWorld);
                tempLevel = levelRepository.save(tempLevel);
                tempWorld.addLevel(tempLevel);
            }
            tempWorld.setUser(user);
            tempWorld = worldRepository.save(tempWorld);
            user.addWorld(tempWorld);
        }
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
        addWorldsToUser(newUser.getUsername());
        return newUser;
    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user " + username);
        return userRepository.findByUsername(username);
    }

    @Override
    public void deleteUser(User user) {
        log.info("Deleting user " + user.getUsername());
        userRepository.delete(user);
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

    @Override
    public List<AllowedQuestions> getAllowedQuestions(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        List<AllowedQuestions> res = new ArrayList<>();
        boolean endFound = false;
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        String[] WORLD_NAMES = {"EARTH", "MARS", "NEPTUNE", "JUPITER"};

        for (String worldName : WORLD_NAMES) {
            for (int i = 0; i < user.getWorlds().size(); i++) {
                if (!user.getWorlds().get(i).getPlanet().equals(worldName)) {
                    continue;
                }
                for (int j = 0; j < user.getWorlds().get(i).getLevelsCompleted() + 1; j++) {
                    AllowedQuestions allowedQuestion = new AllowedQuestions();
                    allowedQuestion.setPlanet(user.getWorlds().get(i).getPlanet());
                    allowedQuestion.setQuestionNumber(j + 1);
                    res.add(allowedQuestion);
                }
                if (user.getWorlds().get(i).getLevelsCompleted() + 1 != user.getWorlds().get(i).getLevels().size()) {
                    /* World not completed so do not allow other worlds to be accessed */
                    endFound = true;
                    break;
                }
            }
            if (endFound) {
                break;
            }
        }
        return res;
    }

    @Override
    public void incrementLevelsCompleted(String username, String planet) {
        User user = userRepository.findByUsername(username);
        World world = worldRepository.findByPlanetAndUser(planet, user);
        if (world != null && (world.getLevelsCompleted() < world.getLevels().size())) {
            world.incrementLevelsCompleted();
            worldRepository.save(world);
        }
    }

    @Override
    public void incrementIncorrectAttempts(String username, String planet, int levelNumber) {
        User user = userRepository.findByUsername(username);
        World world = worldRepository.findByPlanetAndUser(planet, user);
        Level level = levelRepository.findByLevelNumberAndWorld(levelNumber, world);
        if (level != null && (level.getIncorrectAttempts() < 3)) {
            level.incrementIncorrectAttempts();
            levelRepository.save(level);
        }
    }

    @Override
    public void updateStars(String username, String planet, int levelNumber, int stars) {
        User user = userRepository.findByUsername(username);
        World world = worldRepository.findByPlanetAndUser(planet, user);
        Level level = levelRepository.findByLevelNumberAndWorld(levelNumber, world);
        if (level != null) {
            if (stars > level.getStars()) {
                user.addStars(stars - level.getStars());
                userRepository.save(user);

                level.setIncorrectAttempts(0);
                level.setStars(stars);
                levelRepository.save(level);
            }
        }
    }

    @Override
    public int getIncorrectAttempts(String username, String planet, int levelNumber) {
        User user = userRepository.findByUsername(username);
        World world = worldRepository.findByPlanetAndUser(planet, user);
        Level level = levelRepository.findByLevelNumberAndWorld(levelNumber, world);
        if (level != null) {
            return level.getIncorrectAttempts();
        }
        return 3;
    }

    @Override
    public UserStars getUserStars(String username) {
        User user = userRepository.findByUsername(username);
        UserStars res = new UserStars();
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }


        for (int i = 0; i < user.getWorlds().size(); i++) {
            for (int j = 0; j < user.getWorlds().get(i).getLevels().size(); j++) {
                for (int k = 0; k < user.getWorlds().get(i).getLevels().size(); k++) {
                    if (user.getWorlds().get(i).getLevels().get(k).getLevelNumber() != (j+1)) {
                        continue;
                    }
                    switch (user.getWorlds().get(i).getPlanet()) {
                        case "EARTH":
                            res.getEARTH().add(user.getWorlds().get(i).getLevels().get(k).getStars());
                            break;
                        case "MARS":
                            res.getMARS().add(user.getWorlds().get(i).getLevels().get(k).getStars());
                            break;
                        case "NEPTUNE":
                            res.getNEPTUNE().add(user.getWorlds().get(i).getLevels().get(k).getStars());
                            break;
                        case "JUPITER":
                            res.getJUPITER().add(user.getWorlds().get(i).getLevels().get(k).getStars());
                            break;
                    }
                }
            }
        }

        res.setTotalStars(user.getTotalStars());

        return res;
    }

    public void ChangePassword(String username, String password) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        user.setPassword(password);
        userRepository.save(user);
    }

    public String SetRandomPassword(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        int length = 20;
        String password = StringUtility.GenerateRandomString(length);
        user.setPassword(password);
        userRepository.save(user);
        return password;
    }

    @Override
    public void setQuizScore(String username, String planet, int score) {
        User user = userRepository.findByUsername(username);
        World world = worldRepository.findByPlanetAndUser(planet, user);
        if (world != null) {
            world.setQuizScore(score);
            worldRepository.save(world);
        }
    }
}
