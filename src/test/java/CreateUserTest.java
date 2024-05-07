import client.User;
import client.UserSteps;
import static client.Constants.*;
import static org.apache.http.HttpStatus.*;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserTest {
    private User user;
    private UserSteps userSteps;
    private String accessToken;

    @Before
    public void createTestData() {
        userSteps = new UserSteps(REQUEST_SPECIFICATION, RESPONSE_SPECIFICATION);
    }

    @Test
    @Description("Create user (successful) - status 201, Body:\"success\" = true")
    public void createUserSuccessTest() {
        user = User.createRandom();
        ValidatableResponse response = userSteps.create(user);
        response.assertThat().statusCode(SC_OK).and().body("success", equalTo(true));
        accessToken = response.extract().path("accessToken");
        userSteps.removed(accessToken);
    }

    @Test
    @Description("Create user already exists (faild) - status 403, Body:\"success\" = false")
    public void createUserAlreadyExistsTest() {
        user = new User(USER_EMAIL_ALREADY_EXISTS, USER_PASSWORD_ALREADY_EXISTS, USER_NAME_ALREADY_EXISTS);
        ValidatableResponse response = userSteps.create(user);
        response.assertThat().statusCode(SC_FORBIDDEN).and().body("success", equalTo(false));
        response.assertThat().body("message", equalTo(USER_ALREADY_EXIST_MESSAGE));
    }

    @Test
    @Description("Create user without required fields (faild) - status 403, Body:\"success\" = false")
    public void createUserWithoutRequiredFieldTest() {
        user = new User("skmvlady@yandex.ru", "password", "");
        ValidatableResponse response = userSteps.create(user);
        response.assertThat().statusCode(SC_FORBIDDEN).and().body("success", equalTo(false));
        response.assertThat().body("message", equalTo(USER_WITHOUT_REQUIRED_FIELD_MESSAGE));
    }

    @Test
    @Description("Removed user (successful) - status 202, Body:\"success\" = true")
    public void removedUserTest() {
        user = User.createRandom();
        ValidatableResponse response = userSteps.create(user);
        accessToken = response.extract().path("accessToken");
        ValidatableResponse removeResponse = userSteps.removed(accessToken);
        removeResponse.assertThat().statusCode(SC_ACCEPTED).and().body("success", equalTo(true));
        removeResponse.assertThat().body("message", equalTo(USER_REMOVED_MESSAGE));
    }
}