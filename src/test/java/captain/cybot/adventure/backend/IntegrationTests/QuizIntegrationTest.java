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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(1)
public class QuizIntegrationTest {
    private final static String USER_NAME = "IntegrationTestUsername";
    private final static String USER_EMAIL = "integrationtests@gmail.com";
    private final static String USER_PASS = "MySecretPass123!";
    private final static String USER_URL = "/api/v0/users";
    private final static String LOGIN_URL = "/api/v0/login";
    private final static String QUIZ_URL = "/api/v0/quizzes";
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
    void getUnauthorizedQuiz() throws Exception {
        mvc.perform(get(QUIZ_URL +"?planet=EARTH").header("Authorization", access_token))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(2)
    void getAuthorizedQuiz() throws Exception {
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
        String w1Q3 = jsonObj.toString();

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


        mvc.perform(get(QUIZ_URL +"?planet=EARTH").header("Authorization", access_token))
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    void quizScoreCalculation0() throws Exception {
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
        String w1Q3 = jsonObj.toString();

        jsonObj = new JSONObject();
        answerArr = new JSONArray();
        JSONObject tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "IncorrectAnswer");
        tmpJsonObj.put("questionNumber", 1);
        answerArr.put(tmpJsonObj);
        tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "IncorrectAnswer");
        tmpJsonObj.put("questionNumber", 2);
        answerArr.put(tmpJsonObj);
        tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "IncorrectAnswer");
        tmpJsonObj.put("questionNumber", 3);
        answerArr.put(tmpJsonObj);
        tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "IncorrectAnswer");
        tmpJsonObj.put("questionNumber", 4);
        answerArr.put(tmpJsonObj);
        tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "IncorrectAnswer");
        tmpJsonObj.put("questionNumber", 5);
        answerArr.put(tmpJsonObj);
        tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "IncorrectAnswer");
        tmpJsonObj.put("questionNumber", 6);
        answerArr.put(tmpJsonObj);
        tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "IncorrectAnswer");
        tmpJsonObj.put("questionNumber", 7);
        answerArr.put(tmpJsonObj);
        tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "IncorrectAnswer");
        tmpJsonObj.put("questionNumber", 8);
        answerArr.put(tmpJsonObj);
        tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "IncorrectAnswer");
        tmpJsonObj.put("questionNumber", 9);
        answerArr.put(tmpJsonObj);
        tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "IncorrectAnswer");
        tmpJsonObj.put("questionNumber", 10);
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
                        .content(quizAnswers))
                .andExpect(jsonPath("$.score").value(0));
    }

    @Test
    @Order(4)
    void quizScoreCalculation50() throws Exception {
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
        tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "All the options listed");
        tmpJsonObj.put("questionNumber", 3);
        answerArr.put(tmpJsonObj);
        tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "Malicious self-reproducing programs that change how a computer works");
        tmpJsonObj.put("questionNumber", 4);
        answerArr.put(tmpJsonObj);
        tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "Anti-Virus Software");
        tmpJsonObj.put("questionNumber", 5);
        answerArr.put(tmpJsonObj);
        tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "IncorrectAnswer");
        tmpJsonObj.put("questionNumber", 6);
        answerArr.put(tmpJsonObj);
        tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "IncorrectAnswer");
        tmpJsonObj.put("questionNumber", 7);
        answerArr.put(tmpJsonObj);
        tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "IncorrectAnswer");
        tmpJsonObj.put("questionNumber", 8);
        answerArr.put(tmpJsonObj);
        tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "IncorrectAnswer");
        tmpJsonObj.put("questionNumber", 9);
        answerArr.put(tmpJsonObj);
        tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "IncorrectAnswer");
        tmpJsonObj.put("questionNumber", 10);
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
                        .content(quizAnswers))
                .andExpect(jsonPath("$.score").value(50));
    }

    @Test
    @Order(5)
    void quizScoreCalculation100() throws Exception {
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
        tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "All the options listed");
        tmpJsonObj.put("questionNumber", 3);
        answerArr.put(tmpJsonObj);
        tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "Malicious self-reproducing programs that change how a computer works");
        tmpJsonObj.put("questionNumber", 4);
        answerArr.put(tmpJsonObj);
        tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "Anti-Virus Software");
        tmpJsonObj.put("questionNumber", 5);
        answerArr.put(tmpJsonObj);
        tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "Anti-Virus Software");
        tmpJsonObj.put("questionNumber", 6);
        answerArr.put(tmpJsonObj);
        tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "Inspects computer files and email attachments for viruses and removes any that it finds");
        tmpJsonObj.put("questionNumber", 7);
        answerArr.put(tmpJsonObj);
        tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "Antivirus");
        tmpJsonObj.put("questionNumber", 8);
        answerArr.put(tmpJsonObj);
        tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "Creeper");
        tmpJsonObj.put("questionNumber", 9);
        answerArr.put(tmpJsonObj);
        tmpJsonObj = new JSONObject();
        tmpJsonObj.put("answer", "Protection");
        tmpJsonObj.put("questionNumber", 10);
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
                .content(quizAnswers))
                .andExpect(jsonPath("$.score").value(100));

    }
}
