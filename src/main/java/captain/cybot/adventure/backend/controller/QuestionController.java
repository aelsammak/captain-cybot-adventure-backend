package captain.cybot.adventure.backend.controller;

import captain.cybot.adventure.backend.exception.PrerequsiteNotMetException;
import captain.cybot.adventure.backend.model.question.*;
import captain.cybot.adventure.backend.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;

import static org.hibernate.internal.util.collections.ArrayHelper.toList;

@RestController
@RequestMapping("/api/v0/questions")
@RequiredArgsConstructor
@Slf4j
public class QuestionController {

    private final QuestionService questionService;

    public static Map<String, Object> toMap(JSONObject jsonobj)  throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Iterator<String> keys = jsonobj.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            Object value = jsonobj.get(key);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }   return map;
    }

    @GetMapping("")
    public ResponseEntity<?> get(@RequestParam(name = "planet") String planet,
                                 @RequestParam(name="questionNumber") int questionNumber) {
        try {
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Question question = questionService.getQuestion(username, planet, questionNumber);
            if (question.getClass() == Crossword.class) {
                JSONObject jsonObject = questionService.crosswordToData(((Crossword) question).getCrosswordBlock(),
                        ((Crossword) question).getHints(), ((Crossword) question).getAnswers());
                return ResponseEntity.ok().body(toMap(jsonObject));
            }
            return ResponseEntity.ok().body(question);
        } catch (PrerequsiteNotMetException e) {
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
        } catch (PrerequsiteNotMetException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
