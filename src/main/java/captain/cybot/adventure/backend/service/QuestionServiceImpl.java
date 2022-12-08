package captain.cybot.adventure.backend.service;

import captain.cybot.adventure.backend.exception.PrerequsiteNotMetException;
import captain.cybot.adventure.backend.model.question.*;
import captain.cybot.adventure.backend.model.user.AllowedQuestions;
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
    public Question getQuestion(String username, String planet, int questionNumber) throws PrerequsiteNotMetException {
        QuestionOrder questionOrder = questionOrderRepository.findByPlanetAndQuestionNumber(planet, questionNumber);

        if (questionOrder == null)
        {
            return null;
        }

        List<AllowedQuestions> allowedQuestions = userService.getAllowedQuestions(username);

        boolean isAllowed = false;

        for (AllowedQuestions q : allowedQuestions) {
            if (q.getPlanet().equals(planet) && q.getQuestionNumber() == questionNumber) {
                isAllowed = true;
                break;
            }
        }

        if (!isAllowed) {
            throw new PrerequsiteNotMetException("User: " + username + " does not have access to question world: " +
                    planet + " number: " + questionNumber);
        }


        return questionOrder.getQuestion();
    }

    @Override
    public boolean checkQuestion(String username, String planet, int questionNumber, String[] answers) throws PrerequsiteNotMetException {
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
            userService.incrementLevelsCompleted(username, planet);
            int incorrectResponses = userService.getIncorrectAttempts(username, planet, questionNumber);
            int stars = 3 - incorrectResponses;
            if (stars <= 0) {
                stars = 1;
            }
            if (stars > 3) {
                stars = 3;
            }
            userService.updateStars(username, planet, questionNumber, stars);

        } else {
            userService.incrementIncorrectAttempts(username, planet, questionNumber);
            return false;
        }
        return true;
    }
}
