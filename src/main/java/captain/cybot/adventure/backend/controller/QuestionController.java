package captain.cybot.adventure.backend.controller;

import captain.cybot.adventure.backend.exception.PrerequisiteNotMetException;
import captain.cybot.adventure.backend.model.question.*;
import captain.cybot.adventure.backend.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v0/questions")
@RequiredArgsConstructor
@Slf4j
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("")
    public ResponseEntity<?> get(@RequestParam(name = "planet") String planet,
                                 @RequestParam(name="questionNumber") int questionNumber) {
        try {
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Question question = questionService.getQuestion(username, planet, questionNumber);

            return ResponseEntity.ok().body(question);
        } catch (PrerequisiteNotMetException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> post(@RequestParam(name = "planet") String planet,
                                 @RequestParam(name="questionNumber") int questionNumber,
                                 @Valid @RequestBody QuestionAnswer answer) {
        try {
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            boolean isCorrect = questionService.checkQuestion(username, planet, questionNumber, answer.getAnswers());

            answer.setCorrect(isCorrect);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/api/v0/questions")
                            .toUriString());
            return ResponseEntity.created(uri).body(answer);
        } catch (PrerequisiteNotMetException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
