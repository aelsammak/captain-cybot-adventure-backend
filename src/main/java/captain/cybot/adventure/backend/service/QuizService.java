package captain.cybot.adventure.backend.service;

import captain.cybot.adventure.backend.exception.PrerequisiteNotMetException;
import captain.cybot.adventure.backend.model.quiz.QuizQuestionAnswer;
import captain.cybot.adventure.backend.model.quiz.Quiz;

public interface QuizService {

    Quiz getQuiz(String username, String planet) throws PrerequisiteNotMetException;

    int checkQuiz(String username, String planet, QuizQuestionAnswer[] answers) throws PrerequisiteNotMetException;
}
