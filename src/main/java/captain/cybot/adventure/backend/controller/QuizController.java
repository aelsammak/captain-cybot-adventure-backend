package captain.cybot.adventure.backend.controller;

import captain.cybot.adventure.backend.exception.PrerequisiteNotMetException;
import captain.cybot.adventure.backend.model.quiz.QuizAnswers;
import captain.cybot.adventure.backend.model.quiz.QuizQuestionAnswer;
import captain.cybot.adventure.backend.model.quiz.Quiz;
import captain.cybot.adventure.backend.service.QuizService;
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
@RequestMapping("/api/v0/quizzes")
@RequiredArgsConstructor
@Slf4j
public class QuizController {

    private final QuizService quizService;

    @GetMapping("")
    public ResponseEntity<?> get(@RequestParam(name = "planet") String planet) {
        try {
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Quiz quiz = quizService.getQuiz(username, planet);

            return ResponseEntity.ok().body(quiz);
        } catch (PrerequisiteNotMetException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> post(@RequestParam(name = "planet") String planet,
                                  @Valid @RequestBody QuizAnswers answers) {
        try {
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            int score = quizService.checkQuiz(username, planet, answers.getAnswers());

            answers.setScore(score);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/api/v0/quizzes")
                            .toUriString());
            return ResponseEntity.created(uri).body(answers);
        } catch (PrerequisiteNotMetException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}