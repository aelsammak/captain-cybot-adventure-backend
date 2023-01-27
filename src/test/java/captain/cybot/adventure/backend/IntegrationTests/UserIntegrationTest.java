package captain.cybot.adventure.backend.IntegrationTests;

import captain.cybot.adventure.backend.model.user.LeaderboardEntry;
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
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(1)
class UserIntegrationTest {
    private final static String USER_NAME = "IntegrationTestUsername";
    private final static String USER_NAME_2 = "IntegrationTestUsername2";
    private final static String USER_EMAIL = "integrationtests@gmail.com";
    private final static String USER_EMAIL_2 = "integrationtests2@gmail.com";
    private final static String USER_PASS = "MySecretPass123!";
    private final static String INVALID_PASS = "pass";
    private final static String DUMMY_USER_NAME = "DummyIntegrationTestUsername";
    private final static String DUMMY_USER_EMAIL = "dummyintegrationtests@gmail.com";
    private final static String DUMMY_USER_PASS = "DummyMySecretPass123!";
    private final static String CONTROLLER_URL = "/api/v0/users";
    private final static String LOGIN_URL = "/api/v0/login";

    private final static String QUESTION_URL = "/api/v0/questions";


    @Autowired
    private MockMvc mvc;

    private String test_user, test_user_login, test_user_duplicate_email, test_user_duplicate_username,
            test_user_invalid_password, test_user_login_incorrect_pass, test_user_login_incorrect_username;

    private String userBodyGenerator(String username, String email, String pass) {
        return "{" +
                "\"username\":\"" + username + "\"," +
                "\"email\":\"" + email + "\"," +
                "\"password\":\"" + pass + "\"" +
                "}";
    }

    private String loginBodyGenerator(String username, String pass) {
        return "{" +
                "\"username\":\"" + username + "\"," +
                "\"password\":\"" + pass + "\"" +
                "}";
    }

    private String obtainDummyAccessToken() throws Exception {
        String dummy_user = userBodyGenerator(DUMMY_USER_NAME, DUMMY_USER_EMAIL, DUMMY_USER_PASS);
        String dummy_login = loginBodyGenerator(DUMMY_USER_NAME, DUMMY_USER_PASS);
        try {
            mvc.perform(post(CONTROLLER_URL).contentType(MediaType.APPLICATION_JSON)
                    .content(dummy_user));
        } catch (Exception e) {
        }

        MvcResult res = mvc.perform(post(LOGIN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dummy_login))
                .andReturn();

        return "Bearer " + (new JSONObject(res.getResponse().getContentAsString())).getString("access_token");
    }

    private String obtainAccessToken(String username, String password) throws Exception {
        String login_body = loginBodyGenerator(username, password);
        MvcResult res = mvc.perform(post(LOGIN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(login_body))
                .andReturn();
        return "Bearer " + (new JSONObject(res.getResponse().getContentAsString())).getString("access_token");
    }

    private void deleteDummyAccount() {
        try {
            String access_token = obtainAccessToken(DUMMY_USER_NAME, DUMMY_USER_PASS);
            mvc.perform(delete(CONTROLLER_URL + "/" + DUMMY_USER_NAME).header("Authorization", access_token));
        } catch (Exception e) {
        }
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

    private void setUserStars(String username, String password, int stars) throws Exception{
        String token = obtainAccessToken(username, password);
        if (stars <= 3) {
            for (int i = 0; i < (stars - 3)*-1; i++) {
                inputQuestion(token,"EARTH",1,false, 10);
            }
            inputQuestion(token,"EARTH",1,true, 10);
        } else if (stars <= 6) {
            inputQuestion(token,"EARTH",1,true, 10);
            stars -= 3;
            for (int i = 0; i < (stars - 3)*-1; i++) {
                inputQuestion(token,"EARTH",2,false, 10);
            }
            inputQuestion(token,"EARTH",2,true, 10);
        } else if (stars <= 9) {
            inputQuestion(token,"EARTH",1,true, 10);
            inputQuestion(token,"EARTH",2,true, 10);
            stars -= 6;
            if (stars == 3) {
                inputQuestion(token, "EARTH", 3, true, 10);
            } else if (stars == 2) {
                inputQuestion(token, "EARTH", 3, true, 200);
            } else {
                inputQuestion(token, "EARTH", 3, true, 1000);
            }

        } else if (stars <= 12) {
            inputQuestion(token,"EARTH",1,true, 10);
            inputQuestion(token,"EARTH",2,true, 10);
            inputQuestion(token,"EARTH",3,true, 10);
            stars -= 9;

            if (stars == 3) {
                inputQuestion(token, "EARTH", 4, true, 10);
            } else if (stars == 2) {
                inputQuestion(token, "EARTH", 4, true, 200);
            } else {
                inputQuestion(token, "EARTH", 4, true, 1000);
            }
        } else {
            inputQuestion(token,"EARTH",1,true, 10);
            inputQuestion(token,"EARTH",2,true, 10);
            inputQuestion(token,"EARTH",3,true, 10);
            inputQuestion(token,"EARTH",4,true, 10);
        }
    }

    @BeforeEach
    void setUp() {
        test_user = userBodyGenerator(USER_NAME, USER_EMAIL, USER_PASS);
        test_user_duplicate_email = userBodyGenerator(USER_NAME_2, USER_EMAIL, USER_PASS);
        test_user_duplicate_username = userBodyGenerator(USER_NAME, USER_EMAIL_2, USER_PASS);
        test_user_invalid_password = userBodyGenerator(USER_NAME_2, USER_EMAIL_2, INVALID_PASS);

        test_user_login = loginBodyGenerator(USER_NAME, USER_PASS);
        test_user_login_incorrect_pass = loginBodyGenerator(USER_NAME, INVALID_PASS);
        test_user_login_incorrect_username = loginBodyGenerator(USER_NAME_2, USER_PASS);
    }

    @Test
    @Order(1)
    void createUser() throws Exception {
        try {
            /* Delete user if already exists */
            String access_token = obtainAccessToken(USER_NAME, USER_PASS);
            mvc.perform(delete(CONTROLLER_URL + "/" + USER_NAME).header("Authorization", access_token));
        } catch (Exception e) {
        }
        mvc.perform(post(CONTROLLER_URL).contentType(MediaType.APPLICATION_JSON)
                .content(test_user)).andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    void createUserDuplicateUsername() throws Exception {
        mvc.perform(post(CONTROLLER_URL).contentType(MediaType.APPLICATION_JSON)
                .content(test_user_duplicate_username)).andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    void createUserDuplicateEmail() throws Exception {
        mvc.perform(post(CONTROLLER_URL).contentType(MediaType.APPLICATION_JSON)
                .content(test_user_duplicate_email)).andExpect(status().isBadRequest());
    }

    @Test
    @Order(4)
    void createUserInvalidPassword() throws Exception {
        mvc.perform(post(CONTROLLER_URL).contentType(MediaType.APPLICATION_JSON)
                .content(test_user_invalid_password)).andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    void loginUser() throws Exception {
        mvc.perform(post(LOGIN_URL).contentType(MediaType.APPLICATION_JSON)
                        .content(test_user_login))
                .andExpect(status().isOk());
    }

    @Test
    @Order(6)
    void loginUserIncorrectPassword() throws Exception {
        mvc.perform(post(LOGIN_URL).contentType(MediaType.APPLICATION_JSON)
                        .content(test_user_login_incorrect_pass))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(7)
    void loginUserUnknownUsername() throws Exception {
        mvc.perform(post(LOGIN_URL).contentType(MediaType.APPLICATION_JSON)
                        .content(test_user_login_incorrect_username))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(8)
    void getUserValidToken() throws Exception {
        String access_token = obtainAccessToken(USER_NAME, USER_PASS);
        mvc.perform(get(CONTROLLER_URL + "/" + USER_NAME).header("Authorization", access_token))
                .andExpect(status().isOk());
    }

    @Test
    @Order(9)
    void getUserInvalidToken() throws Exception {
        String access_token = obtainDummyAccessToken();
        mvc.perform(get(CONTROLLER_URL + "/" + USER_NAME).header("Authorization", access_token))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(10)
    void getUserNotFound() throws Exception {
        String access_token = obtainDummyAccessToken();
        deleteDummyAccount();
        mvc.perform(get(CONTROLLER_URL + "/" + DUMMY_USER_NAME).header("Authorization", access_token))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(11)
    void deleteUserValidToken() throws Exception {
        String access_token = obtainAccessToken(USER_NAME, USER_PASS);
        mvc.perform(delete(CONTROLLER_URL + "/" + USER_NAME).header("Authorization", access_token))
                .andExpect(status().isOk());
    }

    @Test
    @Order(12)
    void deleteUserNotFound() throws Exception {
        String access_token = obtainDummyAccessToken();
        deleteDummyAccount();
        mvc.perform(delete(CONTROLLER_URL + "/" + DUMMY_USER_NAME).header("Authorization", access_token))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(13)
    void deleteUserInvalidToken() throws Exception {
        String access_token = obtainDummyAccessToken();
        mvc.perform(delete(CONTROLLER_URL + "/" + USER_NAME).header("Authorization", access_token))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(14)
    void getLeaderboardPage1() throws Exception {
        String temp_user;
        String token = "";
        List<LeaderboardEntry> exp_leaderboard = new ArrayList<>();
        for (int i = 0; i < 23; i++) {
            try {
                /* Delete user if already exists */
                String access_token = obtainAccessToken(USER_NAME+"POS" +i, USER_PASS);
                mvc.perform(delete(CONTROLLER_URL + "/" + USER_NAME+"POS" +i).header("Authorization", access_token));
            } catch (Exception e) {
            }
            temp_user = userBodyGenerator(USER_NAME+"POS" +i, USER_EMAIL+i, USER_PASS);

            mvc.perform(post(CONTROLLER_URL).contentType(MediaType.APPLICATION_JSON)
                    .content(temp_user)).andExpect(status().isCreated());

            setUserStars(USER_NAME+"POS" +i,USER_PASS, 12-Math.floorDiv(i,2));

            if (i == 0) {
                token = obtainAccessToken(USER_NAME+"POS" +i, USER_PASS);
            }

            if (i < 10) {
                LeaderboardEntry leaderboardEntry = new LeaderboardEntry();
                leaderboardEntry.setUsername(USER_NAME+"POS" +i);
                leaderboardEntry.setPosition(i+1);
                leaderboardEntry.setStars(12-Math.floorDiv(i,2));
                exp_leaderboard.add(leaderboardEntry);
            }
        }

        for (int i = 0; i < 10; i++) {
            mvc.perform(get(CONTROLLER_URL + "/leaderboard?pageNumber=1&usersPerPage=10")
                            .header("Authorization", token))
                            .andExpect(jsonPath("$.entries[" + Integer.toString(i) + "].username").value(exp_leaderboard.get(i).getUsername()))
                            .andExpect(jsonPath("$.entries[" + Integer.toString(i)  + "].stars").value(exp_leaderboard.get(i).getStars()))
                            .andExpect(jsonPath("$.entries[" + Integer.toString(i)  + "].position").value(exp_leaderboard.get(i).getPosition()));
        }

    }

    @Test
    @Order(15)
    void getLeaderboardPage2() throws Exception {
        String temp_user;
        String token = "";
        List<LeaderboardEntry> exp_leaderboard = new ArrayList<>();
        for (int i = 0; i < 23; i++) {
            try {
                /* Delete user if already exists */
                String access_token = obtainAccessToken(USER_NAME+"POS" +i, USER_PASS);
                mvc.perform(delete(CONTROLLER_URL + "/" + USER_NAME+"POS" +i).header("Authorization", access_token));
            } catch (Exception e) {
            }
            temp_user = userBodyGenerator(USER_NAME+"POS" +i, USER_EMAIL+i, USER_PASS);

            mvc.perform(post(CONTROLLER_URL).contentType(MediaType.APPLICATION_JSON)
                    .content(temp_user)).andExpect(status().isCreated());

            setUserStars(USER_NAME+"POS" +i,USER_PASS, 12-Math.floorDiv(i,2));

            if (i == 15) {
                token = obtainAccessToken(USER_NAME+"POS" +i, USER_PASS);
            }

            if (i>=10 && i < 20) {
                LeaderboardEntry leaderboardEntry = new LeaderboardEntry();
                leaderboardEntry.setUsername(USER_NAME+"POS" +i);
                leaderboardEntry.setPosition(i+1);
                leaderboardEntry.setStars(12-Math.floorDiv(i,2));
                exp_leaderboard.add(leaderboardEntry);
            }
        }

        for (int i = 0; i < 10; i++) {
            mvc.perform(get(CONTROLLER_URL + "/leaderboard?pageNumber=2&usersPerPage=10")
                            .header("Authorization", token))
                    .andExpect(jsonPath("$.entries[" + Integer.toString(i) + "].username").value(exp_leaderboard.get(i).getUsername()))
                    .andExpect(jsonPath("$.entries[" + Integer.toString(i)  + "].stars").value(exp_leaderboard.get(i).getStars()))
                    .andExpect(jsonPath("$.entries[" + Integer.toString(i)  + "].position").value(exp_leaderboard.get(i).getPosition()));
        }
    }

    @Test
    @Order(16)
    void getLeaderboardUpdateUsersPerPage() throws Exception {
        String temp_user;
        String token = "";
        List<LeaderboardEntry> exp_leaderboard = new ArrayList<>();
        for (int i = 0; i < 23; i++) {
            try {
                /* Delete user if already exists */
                String access_token = obtainAccessToken(USER_NAME+"POS" +i, USER_PASS);
                mvc.perform(delete(CONTROLLER_URL + "/" + USER_NAME+"POS" +i).header("Authorization", access_token));
            } catch (Exception e) {
            }
            temp_user = userBodyGenerator(USER_NAME+"POS" +i, USER_EMAIL+i, USER_PASS);

            mvc.perform(post(CONTROLLER_URL).contentType(MediaType.APPLICATION_JSON)
                    .content(temp_user)).andExpect(status().isCreated());

            setUserStars(USER_NAME+"POS" +i,USER_PASS, 12-Math.floorDiv(i,2));

            if (i == 0) {
                token = obtainAccessToken(USER_NAME+"POS" +i, USER_PASS);
            }

            if (i < 15) {
                LeaderboardEntry leaderboardEntry = new LeaderboardEntry();
                leaderboardEntry.setUsername(USER_NAME+"POS" +i);
                leaderboardEntry.setPosition(i+1);
                leaderboardEntry.setStars(12-Math.floorDiv(i,2));
                exp_leaderboard.add(leaderboardEntry);
            }
        }

        for (int i = 0; i < 15; i++) {
            mvc.perform(get(CONTROLLER_URL + "/leaderboard?pageNumber=1&usersPerPage=15")
                            .header("Authorization", token))
                    .andExpect(jsonPath("$.entries[" + Integer.toString(i) + "].username").value(exp_leaderboard.get(i).getUsername()))
                    .andExpect(jsonPath("$.entries[" + Integer.toString(i)  + "].stars").value(exp_leaderboard.get(i).getStars()))
                    .andExpect(jsonPath("$.entries[" + Integer.toString(i)  + "].position").value(exp_leaderboard.get(i).getPosition()));
        }
    }

    @Test
    @Order(17)
    void userAppendedToLeaderboard() throws Exception {
        String temp_user;
        String token = "";
        List<LeaderboardEntry> exp_leaderboard = new ArrayList<>();
        for (int i = 0; i < 23; i++) {
            try {
                /* Delete user if already exists */
                String access_token = obtainAccessToken(USER_NAME+"POS" +i, USER_PASS);
                mvc.perform(delete(CONTROLLER_URL + "/" + USER_NAME+"POS" +i).header("Authorization", access_token));
            } catch (Exception e) {
            }
            temp_user = userBodyGenerator(USER_NAME+"POS" +i, USER_EMAIL+i, USER_PASS);

            mvc.perform(post(CONTROLLER_URL).contentType(MediaType.APPLICATION_JSON)
                    .content(temp_user)).andExpect(status().isCreated());

            setUserStars(USER_NAME+"POS" +i,USER_PASS, 12-Math.floorDiv(i,2));

            if (i == 15) {
                token = obtainAccessToken(USER_NAME+"POS" +i, USER_PASS);
            }
        }

        mvc.perform(get(CONTROLLER_URL + "/leaderboard?pageNumber=1&usersPerPage=10")
                        .header("Authorization", token))
                .andExpect(jsonPath("$.entries.length()").value(11));
    }

    @Test
    @Order(18)
    void userIncludedInPageNotAppended() throws Exception {
        String temp_user;
        String token = "";
        List<LeaderboardEntry> exp_leaderboard = new ArrayList<>();
        for (int i = 0; i < 23; i++) {
            try {
                /* Delete user if already exists */
                String access_token = obtainAccessToken(USER_NAME+"POS" +i, USER_PASS);
                mvc.perform(delete(CONTROLLER_URL + "/" + USER_NAME+"POS" +i).header("Authorization", access_token));
            } catch (Exception e) {
            }
            temp_user = userBodyGenerator(USER_NAME+"POS" +i, USER_EMAIL+i, USER_PASS);

            mvc.perform(post(CONTROLLER_URL).contentType(MediaType.APPLICATION_JSON)
                    .content(temp_user)).andExpect(status().isCreated());

            setUserStars(USER_NAME+"POS" +i,USER_PASS, 12-Math.floorDiv(i,2));

            if (i == 0) {
                token = obtainAccessToken(USER_NAME+"POS" +i, USER_PASS);
            }
        }

        mvc.perform(get(CONTROLLER_URL + "/leaderboard?pageNumber=1&usersPerPage=10")
                        .header("Authorization", token))
                .andExpect(jsonPath("$.entries.length()").value(10));

    }

    @Test
    @Order(19)
    void newUserFlagSetting() throws Exception {
        try {
            /* Delete user if already exists */
            String access_token = obtainAccessToken(USER_NAME+"_tmp", USER_PASS);
            mvc.perform(delete(CONTROLLER_URL + "/" + USER_NAME+"_tmp").header("Authorization", access_token));
        } catch (Exception e) {
        }
        String temp_user = userBodyGenerator(USER_NAME+"_tmp", USER_EMAIL+"_tmp", USER_PASS);

        mvc.perform(post(CONTROLLER_URL).contentType(MediaType.APPLICATION_JSON)
                .content(temp_user)).andExpect(status().isCreated());
        String access_token = obtainAccessToken(USER_NAME+"_tmp", USER_PASS);
        mvc.perform(get(CONTROLLER_URL + "/" + USER_NAME+"_tmp").header("Authorization", access_token))
                .andExpect(jsonPath("$.newUser").value(true));
        mvc.perform(get(QUESTION_URL +"?planet=EARTH&questionNumber=1")
                .header("Authorization", access_token));
        mvc.perform(get(CONTROLLER_URL + "/" + USER_NAME+"_tmp").header("Authorization", access_token))
                .andExpect(jsonPath("$.newUser").value(false));
    }

    @Test
    @Order(19)
    void defaultCosmeticSet() throws Exception {
        try {
            /* Delete user if already exists */
            String access_token = obtainAccessToken(USER_NAME, USER_PASS);
            mvc.perform(delete(CONTROLLER_URL + "/" + USER_NAME).header("Authorization", access_token));
        } catch (Exception e) {
        }
        String temp_user = userBodyGenerator(USER_NAME, USER_EMAIL, USER_PASS);

        mvc.perform(post(CONTROLLER_URL).contentType(MediaType.APPLICATION_JSON)
                .content(temp_user)).andExpect(status().isCreated());
        String access_token = obtainAccessToken(USER_NAME, USER_PASS);
        mvc.perform(get(CONTROLLER_URL + "/" + USER_NAME +"/cosmetic").header("Authorization", access_token))
                .andExpect(jsonPath("$.unlockWorld").value(0));
    }

    @Test
    @Order(19)
    void updateCosmetic() throws Exception {
        try {
            /* Delete user if already exists */
            String access_token = obtainAccessToken(USER_NAME, USER_PASS);
            mvc.perform(delete(CONTROLLER_URL + "/" + USER_NAME).header("Authorization", access_token));
        } catch (Exception e) {
        }
        String temp_user = userBodyGenerator(USER_NAME, USER_EMAIL, USER_PASS);

        mvc.perform(post(CONTROLLER_URL).contentType(MediaType.APPLICATION_JSON)
                .content(temp_user)).andExpect(status().isCreated());
        String access_token = obtainAccessToken(USER_NAME, USER_PASS);
        mvc.perform(get(CONTROLLER_URL + "/" + USER_NAME +"/cosmetic").header("Authorization", access_token))
                .andExpect(jsonPath("$.unlockWorld").value(0));
        mvc.perform(patch(CONTROLLER_URL + "/" + USER_NAME +"/cosmetic").header("Authorization", access_token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"unlockWorld\": 1}"));
        mvc.perform(get(CONTROLLER_URL + "/" + USER_NAME +"/cosmetic").header("Authorization", access_token))
                .andExpect(jsonPath("$.unlockWorld").value(1));
    }
}