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
        MvcResult res = mvc.perform(get(USER_URL + "/stars/"+username)
                .header("Authorization", token)).andReturn();
        String starsStr = (new JSONObject(res.getResponse().getContentAsString()))
                .getString("totalStars");

        int totalStars = Integer.parseInt(starsStr);
        return totalStars;
    }

    private int getStars(String username, String planet, int levelNumber, String token) throws Exception {
        MvcResult res = mvc.perform(get(USER_URL + "/stars/"+username)
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
        String w1Q3 = jsonObj.toString();
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
        String w1Q4 = jsonObj.toString();

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
}