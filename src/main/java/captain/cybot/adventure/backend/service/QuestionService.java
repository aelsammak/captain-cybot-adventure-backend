package captain.cybot.adventure.backend.service;

import captain.cybot.adventure.backend.exception.PrerequisiteNotMetException;
import captain.cybot.adventure.backend.model.question.*;

public interface QuestionService {

    Question getQuestion(String username, String planet, int questionNumber) throws PrerequisiteNotMetException;

    QuestionAnswer checkQuestion(String username, String planet, int questionNumber, String[] answers, QuestionAnswer ansObj) throws PrerequisiteNotMetException;
}
