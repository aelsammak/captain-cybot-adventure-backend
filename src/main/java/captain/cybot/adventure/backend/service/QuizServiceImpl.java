package captain.cybot.adventure.backend.service;

import captain.cybot.adventure.backend.constants.ROLES;
import captain.cybot.adventure.backend.exception.PrerequisiteNotMetException;
import captain.cybot.adventure.backend.model.quiz.QuizQuestion;
import captain.cybot.adventure.backend.model.quiz.QuizQuestionAnswer;
import captain.cybot.adventure.backend.model.quiz.Quiz;
import captain.cybot.adventure.backend.model.user.Role;
import captain.cybot.adventure.backend.model.user.User;
import captain.cybot.adventure.backend.model.user.World;
import captain.cybot.adventure.backend.repository.quiz.QuizRepository;
import captain.cybot.adventure.backend.repository.user.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class QuizServiceImpl implements QuizService{

    private final QuizRepository quizRepository;

    private final UserService userService;

    private final RoleRepository roleRepository;

    @Override
    public Quiz getQuiz(String username, String planet) throws PrerequisiteNotMetException {
        return quizRepository.findByPlanet(planet);
    }

    @Override
    public int checkQuiz(String username, String planet, QuizQuestionAnswer[] answers) throws PrerequisiteNotMetException {
        Quiz quiz = quizRepository.findByPlanet(planet);
        int correctCount = 0;

        if (quiz == null)
        {
            return -1;
        }

        User user = userService.getUser(username);

        if (!user.getRoles().contains(roleRepository.findByName(ROLES.ROLE_ADMIN.toString()))) {
            boolean isAllowed = false;
            for (World world : user.getWorlds()) {
                if (world.getPlanet().equals(planet)) {
                    if (world.getLevelsCompleted() == 4) {
                        isAllowed = true;
                    }
                }
            }

            if (!isAllowed) {
                throw new PrerequisiteNotMetException("User: " + username + " does not have access to quiz on planet: " +
                        planet);
            }
        }

        for (QuizQuestion question : quiz.getQuestions()) {
            for (QuizQuestionAnswer ans : answers) {
                if (ans.getQuestionNumber() == question.getQuestionNumber() &&
                        ans.getAnswer().equals(question.getAnswer())) {
                    correctCount++;
                }
            }
        }

        correctCount = correctCount*100;
        int score = correctCount / quiz.getQuestions().size();
        userService.setQuizScore(username, planet, score);

        return score;
    }
}
