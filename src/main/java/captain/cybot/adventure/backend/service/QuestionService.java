package captain.cybot.adventure.backend.service;

import captain.cybot.adventure.backend.exception.PrerequsiteNotMetException;
import captain.cybot.adventure.backend.model.question.*;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public interface QuestionService {

    Question getQuestion(String username, String planet, int questionNumber) throws PrerequsiteNotMetException;

    boolean checkQuestion(String username, String planet, int questionNumber, String[] answers) throws PrerequsiteNotMetException;

    JSONObject crosswordToData(String[][] searchBlock, String[] hints, String[] answers) throws Exception;
}
