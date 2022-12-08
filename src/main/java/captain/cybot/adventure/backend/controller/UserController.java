package captain.cybot.adventure.backend.controller;

import captain.cybot.adventure.backend.exception.InvalidRoleException;
import captain.cybot.adventure.backend.exception.PasswordInvalidException;
import captain.cybot.adventure.backend.exception.UserAlreadyExistsException;
import captain.cybot.adventure.backend.model.user.User;
import captain.cybot.adventure.backend.model.user.UserStars;
import captain.cybot.adventure.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v0/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody User user) {
        try {
            User newUser = userService.saveUser(user);
            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/api/v0/users")
                            .toUriString());
            return ResponseEntity.created(uri).body(newUser);
        } catch (UserAlreadyExistsException | InvalidRoleException | PasswordInvalidException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> get(@PathVariable("username") String username) {
        User user = userService.getUser(username);
        if (user != null) {
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no user found with username: " + username);
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> delete(@PathVariable("username") String username) {
        User user = userService.getUser(username);
        if (user != null) {
            userService.deleteUser(user);
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no user found with username: " + username);
        }
    }

    @GetMapping("/{username}/stars")
    public ResponseEntity<?> getStars(@PathVariable("username") String username) {
        UserStars userStars = userService.getUserStars(username);
        if (userStars != null) {
            return ResponseEntity.ok().body(userStars);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no user found with username: " + username);
        }
    }
}
