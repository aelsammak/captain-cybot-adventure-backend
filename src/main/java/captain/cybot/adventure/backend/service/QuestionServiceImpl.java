package captain.cybot.adventure.backend.service;

import captain.cybot.adventure.backend.constants.ROLES;
import captain.cybot.adventure.backend.constants.TimeToStars;
import captain.cybot.adventure.backend.exception.PrerequisiteNotMetException;
import captain.cybot.adventure.backend.model.question.*;
import captain.cybot.adventure.backend.model.user.AllowedQuestions;
import captain.cybot.adventure.backend.model.user.Role;
import captain.cybot.adventure.backend.repository.question.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    private UserService userService;

    private QuestionOrderRepository questionOrderRepository;

    @Override
    public Question getQuestion(String username, String planet, int questionNumber) throws PrerequisiteNotMetException {
        QuestionOrder questionOrder = questionOrderRepository.findByPlanetAndQuestionNumber(planet, questionNumber);

        if (questionOrder == null)
        {
            return null;
        }

        List<AllowedQuestions> allowedQuestions = userService.getAllowedQuestions(username);
        userService.setNewUserFlag(username, false);

        return questionOrder.getQuestion();
    }

    @Override
    public QuestionAnswer checkQuestion(String username, String planet, int questionNumber, String[] answers, QuestionAnswer ansObj) throws PrerequisiteNotMetException {
        String[] questionAnswers = getQuestion(username, planet, questionNumber).getQuestionAnswers();
        String[] correctAnswers = new String[questionAnswers.length];
        for (int i = 0; i < questionAnswers.length; i++) {
            correctAnswers[i] = questionAnswers[i];
        }
        boolean isCorrect = true;

        Arrays.sort(correctAnswers);
        Arrays.sort(answers);

        if (correctAnswers.length != answers.length) {
            isCorrect = false;
        } else {
            for (int i = 0; i < answers.length; i++) {
                if (!correctAnswers[i].equalsIgnoreCase(answers[i])) {
                    isCorrect = false;
                    break;
                }
            }
        }

        if (isCorrect) {
            if (userService.getLevelsCompleted(username, planet) < questionNumber) {
                userService.incrementLevelsCompleted(username, planet);
            }
            int stars;
            if (getQuestion(username, planet, questionNumber).getType().equals("WORD_SEARCH") ||
                getQuestion(username, planet, questionNumber).getType().equals("CROSSWORD")) {
                if (ansObj.getTimeTaken() < TimeToStars.MAX_TIME_3_STARS.getTime()) {
                    stars = 3;
                } else if (ansObj.getTimeTaken() < TimeToStars.MAX_TIME_2_STARS.getTime()) {
                    stars = 2;
                } else {
                    stars = 1;
                }
            } else {
                int incorrectResponses = userService.getIncorrectAttempts(username, planet, questionNumber);
                stars = 3 - incorrectResponses;
                if (stars <= 0) {
                    stars = 1;
                }
                if (stars > 3) {
                    stars = 3;
                }
            }
            userService.updateStars(username, planet, questionNumber, stars);
            ansObj.setCorrect(true);

        } else {
            userService.incrementIncorrectAttempts(username, planet, questionNumber);
            ansObj.setCorrect(false);
        }


        switch (planet) {
            case "EARTH":
                ansObj.setStars(userService.getUserStars(username).getEARTH().get(questionNumber-1));
                break;
            case "MARS":
                ansObj.setStars(userService.getUserStars(username).getMARS().get(questionNumber-1));
                break;
            case "NEPTUNE":
                ansObj.setStars(userService.getUserStars(username).getNEPTUNE().get(questionNumber-1));
                break;
            case "JUPITER":
                ansObj.setStars(userService.getUserStars(username).getJUPITER().get(questionNumber-1));
                break;
        }

        return ansObj;


    }
}
