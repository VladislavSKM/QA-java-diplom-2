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

public class UpdateUserInfoTest {
    private User user;
    private UserSteps userSteps;
    private Credetntials credetntials;
    private String accessToken;
    private String name;

    @Before
    public void createTestData() {
        userSteps = new UserSteps(REQUEST_SPECIFICATION, RESPONSE_SPECIFICATION);
    }

    @Test
    @Description("Edit user (successful) - status 200, Body:\"success\" = true")
    public void editUserSuccessTest() {
        user = User.createRandom();
        userSteps.create(user);
        ValidatableResponse loginResponse = userSteps.login(Credetntials.fromUser(user));
        accessToken = loginResponse.extract().path("accessToken");
        ValidatableResponse editResponse = userSteps.edit(new User(user.getName() + "ABC.2727", user.getPassword() + "!27/ABCDE", user.getEmail() + "~SD1,?"), accessToken);
        editResponse.assertThat().statusCode(SC_OK).and().body("success", equalTo(true));
    }

    @Test
    @Description("Edit user without authorization (faild) - status 401, Body:\"success\" = false")
    public void editUserWithoutAuthTest() {
        user = User.createRandom();
        userSteps.create(user);
        userSteps.login(Credetntials.fromUser(user));
        ValidatableResponse editResponse = userSteps.editWithoutAuth(new User(user.getName() + "ABC.2727", user.getPassword() + "!27/ABCDE", user.getEmail() + "~SD1,?"));
        editResponse.assertThat().statusCode(SC_UNAUTHORIZED).and().body("success", equalTo(false));
        editResponse.assertThat().body("message", equalTo(YOU_SHOULD_BE_AUTHORIZED_MESSAGE));
    }
}
