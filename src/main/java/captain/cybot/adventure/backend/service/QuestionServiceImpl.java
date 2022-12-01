package captain.cybot.adventure.backend.service;

import captain.cybot.adventure.backend.exception.PrerequsiteNotMetException;
import captain.cybot.adventure.backend.model.question.*;
import captain.cybot.adventure.backend.model.user.AllowedQuestions;
import captain.cybot.adventure.backend.repository.question.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONObject;
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

    private String getDirection(int cellCol, int cellRow, String[][] searchBlock) {
        boolean isMaxCol = (cellCol == searchBlock[0].length-1);
        boolean isMaxRow= (cellRow == searchBlock.length-1);

        String dir;

        if (!isMaxCol && !isMaxRow) {
            if (searchBlock[cellRow][cellCol+1].equals("|")) {
                dir = "down";
            } else {
                dir = "across";
            }
        } else if (isMaxCol && !isMaxRow) {
            dir = "down";
        } else if (!isMaxCol) {
            dir = "across";
        } else {
            if (searchBlock[0][1].equals("|")) {
                dir = "down";
            } else {
                dir = "across";
            }
        }

        return dir;
    }

    public JSONObject crosswordToData(String[][] searchBlock, String[] hints, String[] answers) throws Exception {
        JSONObject jsonObject = new JSONObject();
        JSONObject acrossJson = new JSONObject();
        JSONObject downJson = new JSONObject();

        for (int i = 0; i < searchBlock.length; i++) {
            for (int j = 0; j < searchBlock[i].length; j++) {
                try {
                    String cell = searchBlock[i][j];
                    int cellValue = Integer.parseInt(cell);
                    String dir = getDirection(j, i, searchBlock);
                    JSONObject tmpObj = new JSONObject();
                    tmpObj.put("clue", hints[cellValue-1]);
                    String answerStr = "";
                    for (int k = 0; k < answers[cellValue-1].length(); k++) {
                        answerStr = answerStr + "X";
                    }
                    tmpObj.put("answer", answerStr);
                    tmpObj.put("row", i);
                    tmpObj.put("col", j);
                    if (dir.equals("across")) {
                        acrossJson.put(cell, tmpObj);
                    } else {
                        downJson.put(cell, tmpObj);
                    }
                } catch (Exception e) {}
            }
        }
        jsonObject.put("across", acrossJson);
        jsonObject.put("down", downJson);
        return jsonObject;
    }

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
        boolean isCorrect = true;

        Arrays.sort(questionAnswers);
        Arrays.sort(answers);

        if (questionAnswers.length != answers.length) {
            isCorrect = false;
        } else {
            for (int i = 0; i < answers.length; i++) {
                if (!questionAnswers[i].equalsIgnoreCase(answers[i])) {
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
