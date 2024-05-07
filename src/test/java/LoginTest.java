import client.Credetntials;
import client.User;
import client.UserSteps;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static client.Constants.*;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;

public class LoginTest {

    private User user;
    private UserSteps userSteps;
    private Credetntials credetntials;

    @Before
    public void createTestData() {
        userSteps = new UserSteps(REQUEST_SPECIFICATION, RESPONSE_SPECIFICATION);
    }

    @Test
    @Description("Login user (successful) - status 201, Body:\"success\" = true")
    public void LoginSuccessTest() {
        user = User.createRandom();
        userSteps.create(user);
        ValidatableResponse loginResponse = userSteps.login(Credetntials.fromUser(user));
        loginResponse.assertThat().statusCode(SC_OK).and().body("success", equalTo(true));
    }

    @Test
    @Description("Login user incorrect (faild) - status 401, Body:\"success\" = false")
    public void loginAndPassIncorrectTest() {
        credetntials = new Credetntials("skmvlady@yandex.ru", "1234567890");
        ValidatableResponse loginResponse = userSteps.login(credetntials);
        loginResponse.assertThat().statusCode(SC_UNAUTHORIZED).and().body("success", equalTo(false));
        loginResponse.assertThat().body("message", equalTo(LOGIN_INCORRECT_MESSAGE));
    }

    @Test
    @Description("Login user without loggin  and password (faild) - status 401, Body:\"success\" = false")
    public void LoginWithoutPasswordTest() {
        user = User.createRandom();
        userSteps.create(user);
        ValidatableResponse loginResponse = userSteps.login(Credetntials.fromUserWithoutPass(user));
        loginResponse.assertThat().statusCode(SC_UNAUTHORIZED).and().body("success", equalTo(false));
        loginResponse.assertThat().body("message", equalTo(LOGIN_INCORRECT_MESSAGE));
    }
}