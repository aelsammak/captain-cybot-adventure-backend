package captain.cybot.adventure.backend.IntegrationTests;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(2)
class QuestionIntegrationTest {
    private final static String USER_NAME = "IntegrationTestUsername";
    private final static String USER_EMAIL = "integrationtests@gmail.com";
    private final static String USER_PASS = "MySecretPass123!";
    private final static String USER_URL = "/api/v0/users";
    private final static String LOGIN_URL = "/api/v0/login";
    private final static String QUESTION_URL = "/api/v0/questions";
    private final static String QUIZ_URL = "/api/v0/quizzes";


    @Autowired
    private MockMvc mvc;

    private String test_user, access_token;

    private String userBodyGenerator(String username, String email, String pass)
    {
        return "{" +
                "\"username\":\"" + username + "\"," +
                "\"email\":\"" + email + "\"," +
                "\"password\":\"" + pass + "\"" +
                "}";
    }

    private String loginBodyGenerator(String username, String pass)
    {
        return "{" +
                "\"username\":\"" + username + "\"," +
                "\"password\":\"" + pass + "\"" +
                "}";
    }

    private int getTotalStars(String username, String token) throws Exception {
        MvcResult res = mvc.perform(get(USER_URL +"/"+username + "/stars")
                .header("Authorization", token)).andReturn();
        String starsStr = (new JSONObject(res.getResponse().getContentAsString()))
                .getString("totalStars");

        int totalStars = Integer.parseInt(starsStr);
        return totalStars;
    }

    private int getStars(String username, String planet, int levelNumber, String token) throws Exception {
        MvcResult res = mvc.perform(get(USER_URL +"/"+username+ "/stars")
                .header("Authorization", token)).andReturn();
        String starsStr = (new JSONObject(res.getResponse().getContentAsString()))
                .getString(planet.toLowerCase()).replace("[", "").replace("]","");

        List<String> starsList = new ArrayList<>(Arrays.asList(starsStr.split(",")));

        int levelStars = Integer.parseInt(starsList.get(levelNumber-1));
        return levelStars;
    }

    private String resetUser(String userSignUp, String userSignIn) {
        String tmpToken = "";
        try {
            mvc.perform(post(USER_URL).contentType(MediaType.APPLICATION_JSON)
                    .content(userSignUp));
        } catch (Exception e) {}

        try {
            MvcResult res = mvc.perform(post(LOGIN_URL).contentType(MediaType.APPLICATION_JSON)
                    .content(userSignIn)).andReturn();
            tmpToken = "Bearer "+(new JSONObject(res.getResponse().getContentAsString())).getString("access_token");
        } catch (Exception e) {}

        try {
            mvc.perform(delete(USER_URL +"/"+ USER_NAME).header("Authorization", tmpToken));
        } catch (Exception e) { System.out.println(e.getMessage());}

        try {
            mvc.perform(post(USER_URL).contentType(MediaType.APPLICATION_JSON)
                    .content(userSignUp));
        } catch (Exception e) {}

        try {
            MvcResult res = mvc.perform(post(LOGIN_URL).contentType(MediaType.APPLICATION_JSON)
                    .content(userSignIn)).andReturn();
            return "Bearer "+(new JSONObject(res.getResponse().getContentAsString())).getString("access_token");
        } catch (Exception e) {}
        return null;
    }

    private void inputQuestion(String token, String planet, int questionNumber, boolean correct, int timeTaken) throws Exception{
        JSONArray answerArr = new JSONArray();
        JSONObject jsonObj = new JSONObject();
        answerArr.put("creeper");
        jsonObj.put("answers", answerArr);
        String w1Q1 = jsonObj.toString();
        answerArr = new JSONArray();
        jsonObj = new JSONObject();
        answerArr.put("replicate");
        jsonObj.put("answers", answerArr);
        jsonObj.put("timeTaken", timeTaken);
        String w1Q2 = jsonObj.toString();
        answerArr = new JSONArray();
        jsonObj = new JSONObject();
        answerArr.put("software");
        answerArr.put("norton");
        answerArr.put("replicate");
        answerArr.put("creeper");
        answerArr.put("computer");
        answerArr.put("email");
        answerArr.put("malware");
        answerArr.put("viruses");
        jsonObj.put("answers", answerArr);
        jsonObj.put("timeTaken", timeTaken);
        String w1Q4 = jsonObj.toString();
        answerArr = new JSONArray();
        jsonObj = new JSONObject();
        answerArr.put("protection");
        answerArr.put("antivirus");
        answerArr.put("virus");
        answerArr.put("replicate");
        answerArr.put("slow");
        answerArr.put("malware");
        answerArr.put("creeper");
        answerArr.put("damage");
        jsonObj.put("answers", answerArr);
        jsonObj.put("timeTaken", timeTaken);
        String w1Q3 = jsonObj.toString();
        answerArr = new JSONArray();
        jsonObj = new JSONObject();
        answerArr.put("Incorrect");
        jsonObj.put("answers", answerArr);
        jsonObj.put("timeTaken", timeTaken);
        String ans = jsonObj.toString();

        if (correct) {
            switch (planet) {
                case "EARTH":
                    switch (questionNumber) {
                        case 1:
                            ans = w1Q1;
                            break;
                        case 2:
                            ans = w1Q2;
                            break;
                        case 3:
                            ans = w1Q3;
                            break;
                        case 4:
                            ans = w1Q4;
                            break;
                    }
                    break;
                case "MARS":
                    switch (questionNumber) {
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                    }
                    break;
                case "NEPTUNE":
                    switch (questionNumber) {
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                    }
                    break;
                case "JUPITER":
                    switch (questionNumber) {
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                    }
                    break;
            }
        }

        mvc.perform(post(QUESTION_URL +"?planet="+planet+"&questionNumber="+questionNumber)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ans));
    }

    @BeforeEach
    void setUp() {
        test_user = userBodyGenerator(USER_NAME, USER_EMAIL, USER_PASS);

        String test_user_login = loginBodyGenerator(USER_NAME, USER_PASS);

        access_token = resetUser(test_user, test_user_login);
    }

    @Test
    @Order(1)
    void getUnauthorizedQuestion() throws Exception {
        mvc.perform(get(QUESTION_URL +"?planet=EARTH&questionNumber=2").header("Authorization", access_token))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(2)
    void getAuthorizedQuestion() throws Exception {
        mvc.perform(get(QUESTION_URL +"?planet=EARTH&questionNumber=1")
                        .header("Authorization", access_token))
                        .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    void getLockedWorldQuestion() throws Exception {
        mvc.perform(get(QUESTION_URL +"?planet=MARS&questionNumber=1")
                        .header("Authorization", access_token))
                        .andExpect(status().isForbidden());
    }

    @Test
    @Order(4)
    void incrementLevelCompletedCounter() throws Exception {
        JSONArray answerArr = new JSONArray();
        JSONObject jsonObj = new JSONObject();
        answerArr.put("creeper");
        jsonObj.put("answers", answerArr);
        String postBody = jsonObj.toString();
        mvc.perform(post(QUESTION_URL +"?planet=EARTH&questionNumber=2")
                .header("Authorization", access_token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(postBody)).andExpect(status().isForbidden());
        mvc.perform(post(QUESTION_URL +"?planet=EARTH&questionNumber=1")
                        .header("Authorization", access_token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postBody)).andExpect(status().isCreated());
        mvc.perform(post(QUESTION_URL +"?planet=EARTH&questionNumber=2")
                .header("Authorization", access_token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(postBody)).andExpect(status().isCreated());
    }

    @Test
    @Order(5)
    void updatePartialStars() throws Exception {
        JSONArray answerArr = new JSONArray();
        JSONObject jsonObj = new JSONObject();
        answerArr.put("creeper");
        jsonObj.put("answers", answerArr);
        String postBodyCorrect = jsonObj.toString();
        answerArr = new JSONArray();
        jsonObj = new JSONObject();
        answerArr.put("creepea");
        jsonObj.put("answers", answerArr);
        String postBodyIncorrect = jsonObj.toString();
        mvc.perform(post(QUESTION_URL +"?planet=EARTH&questionNumber=1")
                .header("Authorization", access_token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(postBodyIncorrect));
        mvc.perform(post(QUESTION_URL +"?planet=EARTH&questionNumber=1")
                        .header("Authorization", access_token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postBodyCorrect));
        int stars = getStars(USER_NAME,"EARTH",1,access_token);
        int totalStars = getTotalStars(USER_NAME, access_token);
        assertEquals(2, stars, "Level stars expected 2 but got "+stars);
        assertEquals(2, totalStars, "Total stars expected 2 but got "+stars);
    }

    @Test
    @Order(6)
    void updateFullStars() throws Exception {
        JSONArray answerArr = new JSONArray();
        JSONObject jsonObj = new JSONObject();
        answerArr.put("creeper");
        jsonObj.put("answers", answerArr);
        String postBody = jsonObj.toString();
        mvc.perform(post(QUESTION_URL +"?planet=EARTH&questionNumber=1")
                .header("Authorization", access_token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(postBody));
         int stars = getStars(USER_NAME,"EARTH",1,access_token);
         int totalStars = getTotalStars(USER_NAME, access_token);

        assertEquals(3, stars, "Level stars expected 3 but got "+stars);
        assertEquals(3, totalStars, "Total stars expected 3 but got "+totalStars);
    }

    @Test
    @Order(6)
    void changePartialToFullStars() throws Exception {
        JSONArray answerArr = new JSONArray();
        JSONObject jsonObj = new JSONObject();
        answerArr.put("creeper");
        jsonObj.put("answers", answerArr);
        String postBodyCorrect = jsonObj.toString();
        answerArr = new JSONArray();
        jsonObj = new JSONObject();
        answerArr.put("creepea");
        jsonObj.put("answers", answerArr);
        String postBodyIncorrect = jsonObj.toString();
        mvc.perform(post(QUESTION_URL +"?planet=EARTH&questionNumber=1")
                .header("Authorization", access_token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(postBodyIncorrect));
        mvc.perform(post(QUESTION_URL +"?planet=EARTH&questionNumber=1")
                .header("Authorization", access_token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(postBodyCorrect));
        int stars = getStars(USER_NAME,"EARTH",1,access_token);
        int totalStars = getTotalStars(USER_NAME, access_token);
        assertEquals(2, stars, "Level stars expected 2 but got "+stars);
        assertEquals(2, totalStars, "Total stars expected 2 but got "+stars);

        mvc.perform(post(QUESTION_URL +"?planet=EARTH&questionNumber=1")
                .header("Authorization", access_token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(postBodyCorrect));
        stars = getStars(USER_NAME,"EARTH",1,access_token);
        totalStars = getTotalStars(USER_NAME, access_token);
        assertEquals(3, stars, "Level stars expected 3 but got "+stars);
        assertEquals(3, totalStars, "Total stars expected 3 but got "+stars);
    }

    @Test
    @Order(7)
    void getAuthorizedNewWorldQuestion() throws Exception {
        JSONArray answerArr = new JSONArray();
        JSONObject jsonObj = new JSONObject();
        answerArr.put("creeper");
        jsonObj.put("answers", answerArr);
        String w1Q1 = jsonObj.toString();
        answerArr = new JSONArray();
        jsonObj = new JSONObject();
        answerArr.put("replicate");
        jsonObj.put("answers", answerArr);
        String w1Q2 = jsonObj.toString();
        answerArr = new JSONArray();
        jsonObj = new JSONObject();
        answerArr.put("software");
        answerArr.put("norton");
        answerArr.put("replicate");
        answerArr.put("creeper");
        answerArr.put("computer");
        answerArr.put("email");
        answerArr.put("malware");
        answerArr.put("viruses");
        jsonObj.put("answers", answerArr);
        jsonObj.put("timeTaken", 100);
        String w1Q4 = jsonObj.toString();
        answerArr = new JSONArray();
        jsonObj = new JSONObject();
        answerArr.put("protection");
        answerArr.put("antivirus");
        answerArr.put("virus");
        answerArr.put("replicate");
        answerArr.put("slow");
        answerArr.put("malware");
        answerArr.put("creeper");
        answerArr.put("damage");
        jsonObj.put("answers", answerArr);
        jsonObj.put("timeTaken", 100);
        String w1Q3 = jsonObj.toString();
        jsonObj = new JSONObject();
        answerArr = new JSONArray();
        JSONObject tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "Software that may harm your computer");
        tmpJsonObj.put("questionNumber", 1);
        answerArr.put(tmpJsonObj);
        tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "Computer runs very slowly");
        tmpJsonObj.put("questionNumber", 2);
        answerArr.put(tmpJsonObj);
        jsonObj.put("answers", answerArr);
        String quizAnswers = jsonObj.toString();

        mvc.perform(get(QUESTION_URL +"?planet=MARS&questionNumber=1")
                        .header("Authorization", access_token))
                .andExpect(status().isForbidden());

        mvc.perform(post(QUESTION_URL +"?planet=EARTH&questionNumber=1")
                .header("Authorization", access_token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(w1Q1));
        mvc.perform(post(QUESTION_URL +"?planet=EARTH&questionNumber=2")
                .header("Authorization", access_token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(w1Q2));
        mvc.perform(post(QUESTION_URL +"?planet=EARTH&questionNumber=3")
                .header("Authorization", access_token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(w1Q3));
        mvc.perform(post(QUESTION_URL +"?planet=EARTH&questionNumber=4")
                .header("Authorization", access_token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(w1Q4));



        mvc.perform(post(QUIZ_URL +"?planet=EARTH").header("Authorization", access_token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(quizAnswers));

        mvc.perform(get(QUESTION_URL +"?planet=MARS&questionNumber=1")
                        .header("Authorization", access_token))
                        .andExpect(status().isOk());
    }

    @Test
    @Order(8)
    void incorrectAnswerCheck() throws Exception {
        JSONArray answerArr = new JSONArray();
        JSONObject jsonObj = new JSONObject();
        answerArr.put("creepea");
        jsonObj.put("answers", answerArr);
        String postBody = jsonObj.toString();
        MvcResult res = mvc.perform(post(QUESTION_URL +"?planet=EARTH&questionNumber=1")
                        .header("Authorization", access_token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postBody))
                .andExpect(status().isCreated())
                .andReturn();

        boolean isCorrect = Boolean.parseBoolean((new JSONObject(res.getResponse().getContentAsString()))
                .getString("correct"));

        assertFalse(isCorrect);
    }

    @Test
    @Order(9)
    void correctAnswerCheck() throws Exception {
        JSONArray answerArr = new JSONArray();
        JSONObject jsonObj = new JSONObject();
        answerArr.put("creeper");
        jsonObj.put("answers", answerArr);
        String postBody = jsonObj.toString();
        MvcResult res = mvc.perform(post(QUESTION_URL +"?planet=EARTH&questionNumber=1")
                        .header("Authorization", access_token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postBody))
                        .andExpect(status().isCreated())
                        .andReturn();

        boolean isCorrect = Boolean.parseBoolean((new JSONObject(res.getResponse().getContentAsString()))
                .getString("correct"));

        assertTrue(isCorrect);
    }

    @Test
    @Order(9)
    void sumWorldStars() throws Exception {
        JSONArray answerArr = new JSONArray();
        JSONObject jsonObj = new JSONObject();
        answerArr.put("creeper");
        jsonObj.put("answers", answerArr);
        String postBodyCorrect = jsonObj.toString();
        answerArr = new JSONArray();
        jsonObj = new JSONObject();
        answerArr.put("creepea");
        jsonObj.put("answers", answerArr);
        String postBodyIncorrect = jsonObj.toString();
        answerArr = new JSONArray();
        jsonObj = new JSONObject();
        answerArr.put("replicate");
        jsonObj.put("answers", answerArr);
        String postBodyCorrect2 = jsonObj.toString();
        mvc.perform(post(QUESTION_URL +"?planet=EARTH&questionNumber=1")
                .header("Authorization", access_token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(postBodyIncorrect));
        mvc.perform(post(QUESTION_URL +"?planet=EARTH&questionNumber=1")
                .header("Authorization", access_token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(postBodyCorrect));
        int stars = getStars(USER_NAME,"EARTH",1,access_token);
        int totalStars = getTotalStars(USER_NAME, access_token);
        assertEquals(2, stars, "Level stars expected 2 but got "+stars);
        assertEquals(2, totalStars, "Total stars expected 2 but got "+stars);

        mvc.perform(post(QUESTION_URL +"?planet=EARTH&questionNumber=1")
                .header("Authorization", access_token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(postBodyCorrect));
        stars = getStars(USER_NAME,"EARTH",1,access_token);
        totalStars = getTotalStars(USER_NAME, access_token);
        assertEquals(3, stars, "Level stars expected 3 but got "+stars);
        assertEquals(3, totalStars, "Total stars expected 3 but got "+stars);

        mvc.perform(post(QUESTION_URL +"?planet=EARTH&questionNumber=2")
                .header("Authorization", access_token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(postBodyIncorrect));
        stars = getStars(USER_NAME,"EARTH",2,access_token);
        totalStars = getTotalStars(USER_NAME, access_token);
        assertEquals(0, stars, "Level stars expected 0 but got "+stars);
        assertEquals(3, totalStars, "Total stars expected 3 but got "+stars);

        mvc.perform(post(QUESTION_URL +"?planet=EARTH&questionNumber=2")
                .header("Authorization", access_token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(postBodyCorrect2));
        stars = getStars(USER_NAME,"EARTH",2,access_token);
        totalStars = getTotalStars(USER_NAME, access_token);
        assertEquals(2, stars, "Level stars expected 0 but got "+stars);
        assertEquals(5, totalStars, "Total stars expected 3 but got "+stars);

        mvc.perform(post(QUESTION_URL +"?planet=EARTH&questionNumber=2")
                .header("Authorization", access_token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(postBodyCorrect2));
        stars = getStars(USER_NAME,"EARTH",2,access_token);
        totalStars = getTotalStars(USER_NAME, access_token);
        assertEquals(3, stars, "Level stars expected 0 but got "+stars);
        assertEquals(6, totalStars, "Total stars expected 3 but got "+stars);
    }

    @Test
    @Order(10)
    void noLevelIncrementOnDuplicateQuestion() throws Exception {
        JSONArray answerArr = new JSONArray();
        JSONObject jsonObj = new JSONObject();
        answerArr.put("creepea");
        jsonObj.put("answers", answerArr);
        String postBody = jsonObj.toString();
        mvc.perform(post(QUESTION_URL +"?planet=EARTH&questionNumber=1")
                .header("Authorization", access_token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(postBody));
        mvc.perform(post(QUESTION_URL +"?planet=EARTH&questionNumber=1")
                .header("Authorization", access_token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(postBody));
        mvc.perform(get(QUESTION_URL +"?planet=EARTH&questionNumber=3")
                .header("Authorization", access_token))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(11)
    void starCalculationWordScramble() throws Exception{
        inputQuestion(access_token,"EARTH",1, false, 100);
        inputQuestion(access_token,"EARTH",1, false, 100);
        inputQuestion(access_token,"EARTH",1, true, 100);
        int stars = getStars(USER_NAME,"EARTH",1,access_token);
        assertEquals(stars, 1);
        inputQuestion(access_token,"EARTH",1, false, 100);
        inputQuestion(access_token,"EARTH",1, true, 100);
        stars = getStars(USER_NAME,"EARTH",1,access_token);
        assertEquals(stars, 2);
        inputQuestion(access_token,"EARTH",1, true, 100);
        stars = getStars(USER_NAME,"EARTH",1,access_token);
        assertEquals(stars, 3);
    }

    @Test
    @Order(12)
    void starCalculationGuessTheImage() throws Exception {
        inputQuestion(access_token,"EARTH",1, true, 100);
        inputQuestion(access_token,"EARTH",2, false, 100);
        inputQuestion(access_token,"EARTH",2, false, 100);
        inputQuestion(access_token,"EARTH",2, true, 100);
        int stars = getStars(USER_NAME,"EARTH",2,access_token);
        assertEquals(stars, 1);
        inputQuestion(access_token,"EARTH",2, false, 100);
        inputQuestion(access_token,"EARTH",2, true, 100);
        stars = getStars(USER_NAME,"EARTH",2,access_token);
        assertEquals(stars, 2);
        inputQuestion(access_token,"EARTH",2, true, 100);
        stars = getStars(USER_NAME,"EARTH",2,access_token);
        assertEquals(stars, 3);
    }

    @Test
    @Order(12)
    void starCalculationCrossword() throws Exception{
        inputQuestion(access_token,"EARTH",1, true, 100);
        inputQuestion(access_token,"EARTH",2, true, 100);
        inputQuestion(access_token,"EARTH",3, true, 1000);
        int stars = getStars(USER_NAME,"EARTH",3,access_token);
        assertEquals(stars, 1);
        inputQuestion(access_token,"EARTH",3, true, 200);
        stars = getStars(USER_NAME,"EARTH",3,access_token);
        assertEquals(stars, 2);
        inputQuestion(access_token,"EARTH",3, true, 100);
        stars = getStars(USER_NAME,"EARTH",3,access_token);
        assertEquals(stars, 3);
    }

    @Test
    @Order(13)
    void starCalculationWordSearch() throws Exception{
        inputQuestion(access_token,"EARTH",1, true, 100);
        inputQuestion(access_token,"EARTH",2, true, 100);
        inputQuestion(access_token,"EARTH",3, true, 100);
        inputQuestion(access_token,"EARTH",4, true, 1000);
        int stars = getStars(USER_NAME,"EARTH",4,access_token);
        assertEquals(stars, 1);
        inputQuestion(access_token,"EARTH",4, true, 200);
        stars = getStars(USER_NAME,"EARTH",4,access_token);
        assertEquals(stars, 2);
        inputQuestion(access_token,"EARTH",4, true, 100);
        stars = getStars(USER_NAME,"EARTH",4,access_token);
        assertEquals(stars, 3);
    }
}