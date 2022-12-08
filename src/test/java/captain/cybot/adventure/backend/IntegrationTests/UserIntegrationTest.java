package captain.cybot.adventure.backend.IntegrationTests;

import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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


    @Autowired
    private MockMvc mvc;

    private String test_user, test_user_login, test_user_duplicate_email, test_user_duplicate_username,
                    test_user_invalid_password, test_user_login_incorrect_pass, test_user_login_incorrect_username;

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

    private String obtainDummyAccessToken() throws Exception {
        String dummy_user = userBodyGenerator(DUMMY_USER_NAME, DUMMY_USER_EMAIL, DUMMY_USER_PASS);
        String dummy_login = loginBodyGenerator(DUMMY_USER_NAME, DUMMY_USER_PASS);
        try {
            mvc.perform(post(CONTROLLER_URL).contentType(MediaType.APPLICATION_JSON)
                    .content(dummy_user));
        } catch (Exception e) {}

        MvcResult res = mvc.perform(post(LOGIN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dummy_login))
                        .andReturn();

        return "Bearer "+(new JSONObject(res.getResponse().getContentAsString())).getString("access_token");
    }

    private String obtainAccessToken(String username, String password) throws Exception {
        String login_body = loginBodyGenerator(username, password);
        MvcResult res = mvc.perform(post(LOGIN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(login_body))
                        .andReturn();
        return "Bearer "+(new JSONObject(res.getResponse().getContentAsString())).getString("access_token");
    }

    private void deleteDummyAccount() {
        try {
            String access_token = obtainAccessToken(DUMMY_USER_NAME, DUMMY_USER_PASS);
            mvc.perform(delete(CONTROLLER_URL + "/" + DUMMY_USER_NAME).header("Authorization", access_token));
        } catch (Exception e) {}
    }

    @BeforeEach
    void setUp() {
        test_user = userBodyGenerator(USER_NAME, USER_EMAIL, USER_PASS);
        test_user_duplicate_email = userBodyGenerator(USER_NAME_2, USER_EMAIL, USER_PASS);
        test_user_duplicate_username = userBodyGenerator(USER_NAME, USER_EMAIL_2, USER_PASS);
        test_user_invalid_password =userBodyGenerator(USER_NAME_2, USER_EMAIL_2, INVALID_PASS);

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
            mvc.perform(delete(CONTROLLER_URL +"/"+ USER_NAME).header("Authorization", access_token));
        } catch (Exception e) {}
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
        mvc.perform(get(CONTROLLER_URL +"/"+ USER_NAME).header("Authorization", access_token))
                    .andExpect(status().isOk());
    }

    @Test
    @Order(9)
    void getUserInvalidToken() throws Exception {
        String access_token = obtainDummyAccessToken();
        mvc.perform(get(CONTROLLER_URL +"/"+ USER_NAME).header("Authorization", access_token))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(10)
    void getUserNotFound() throws Exception {
        String access_token = obtainDummyAccessToken();
        deleteDummyAccount();
        mvc.perform(get(CONTROLLER_URL +"/"+ DUMMY_USER_NAME).header("Authorization", access_token))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(11)
    void deleteUserValidToken() throws Exception {
        String access_token = obtainAccessToken(USER_NAME, USER_PASS);
        mvc.perform(delete(CONTROLLER_URL +"/"+ USER_NAME).header("Authorization", access_token))
                .andExpect(status().isOk());
    }

    @Test
    @Order(12)
    void deleteUserNotFound() throws Exception {
        String access_token = obtainDummyAccessToken();
        deleteDummyAccount();
        mvc.perform(delete(CONTROLLER_URL +"/"+ DUMMY_USER_NAME).header("Authorization", access_token))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(13)
    void deleteUserInvalidToken() throws Exception {
        String access_token = obtainDummyAccessToken();
        mvc.perform(delete(CONTROLLER_URL +"/"+ USER_NAME).header("Authorization", access_token))
                                .andExpect(status().isForbidden());
    }
}