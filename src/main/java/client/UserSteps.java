package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.Data;
import static client.Constants.*;
import static io.restassured.RestAssured.given;

@Data
public class UserSteps {

    private final RequestSpecification requestSpecification;
    private final ResponseSpecification responseSpecification;

    @Step("Create user")
    public ValidatableResponse create(User user) {
        return given()
                .spec(requestSpecification)
                .body(user)
                .post(CREATE_USER_ENDPOINT)
                .then()
                .spec(responseSpecification);
    }

    @Step("Login user")
    public ValidatableResponse login(Credetntials creditantials) {
        return given()
                .spec(requestSpecification)
                .body(creditantials)
                .post(LOGIN_USER_ENDPOINT)
                .then()
                .spec(responseSpecification);
    }

    @Step("Edit user")
    public ValidatableResponse edit(User user, String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .spec(requestSpecification)
                .body(user)
                .patch(AUTH_USER_ENDPOINT)
                .then()
                .spec(responseSpecification);
    }

    @Step("Edit user without authorization")
    public ValidatableResponse editWithoutAuth(User user) {
        return given()
                .spec(requestSpecification)
                .body(user)
                .patch(AUTH_USER_ENDPOINT)
                .then()
                .spec(responseSpecification);
    }

    @Step("Removed user")
    public ValidatableResponse removed(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .spec(requestSpecification)
                .delete(AUTH_USER_ENDPOINT)
                .then()
                .spec(responseSpecification);
    }

}
