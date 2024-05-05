package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.Data;
import static client.Constants.*;
import static io.restassured.RestAssured.given;

@Data
public class OrderSteps {

    private final RequestSpecification requestSpecification;
    private final ResponseSpecification responseSpecification;

    @Step("Create order")
    public ValidatableResponse createOrder(Order order, String accessToken) {
        return given()
                .spec(requestSpecification)
                .header("Authorization", accessToken)
                .body(order)
                .post(ORDER_ENDPOINT)
                .then()
                .spec(responseSpecification);
    }

    @Step("Create order")
    public ValidatableResponse createOrderWithoutAuth(Order order) {
        return given()
                .spec(requestSpecification)
                .body(order)
                .post(ORDER_ENDPOINT)
                .then()
                .spec(responseSpecification);
    }

    @Step("Create order")
    public ValidatableResponse getOrder(String accessToken) {
        return given()
                .spec(requestSpecification)
                .header("Authorization", accessToken)
                .get(ORDER_ENDPOINT)
                .then()
                .spec(responseSpecification);
    }

    @Step("Create order")
    public ValidatableResponse getOrderWithoutAuth() {
        return given()
                .spec(requestSpecification)
                .get(ORDER_ENDPOINT)
                .then()
                .spec(responseSpecification);
    }
}
