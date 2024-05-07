import client.*;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static client.Constants.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.*;

public class OrderTest {

    private OrderSteps orderSteps;
    private User user;
    private UserSteps userSteps;
    private String accessToken;
    private String[] ingredients = {BIOCOTLET_INGREDIENT, FILENIAN_WOOD_INGREDIENT};
    private String[] incorrectIngredients = {INCORRECT_INGREDIENT};


    @Before
    public void createTestData() {
        orderSteps = new OrderSteps(REQUEST_SPECIFICATION, RESPONSE_SPECIFICATION);
        userSteps = new UserSteps(REQUEST_SPECIFICATION, RESPONSE_SPECIFICATION);
    }

    @Test
    @Description("Create order wit authorization (successful) - status 200, Body:\"success\" = true")
    public void createOrderSuccessTest() {
        user = User.createRandom();
        ValidatableResponse response = userSteps.create(user);
        accessToken = response.extract().path("accessToken");
        ValidatableResponse orderResponse = orderSteps.createOrder(new Order(ingredients), accessToken);
        orderResponse.assertThat().statusCode(SC_OK).and().body("success", equalTo(true));
        orderResponse.assertThat().body("order.ingredients", notNullValue()).and().body("order.status", equalTo("done"));
        userSteps.removed(accessToken);
    }

    @Test
    @Description("Create order without authorization (successful) - status 200, Body:\"success\" = true")
    public void createOrderWithoutAuthSuccessTest() {
        ValidatableResponse orderResponse = orderSteps.createOrderWithoutAuth(new Order(ingredients));
        orderResponse.assertThat().statusCode(SC_OK).and().body("success", equalTo(true));
    }

    @Test
    @Description("Create order without ingredients (filed) - status 400, Body:\"success\" = false")
    public void createOrderWithoutIngredientsTest() {
        ValidatableResponse orderResponse = orderSteps.createOrderWithoutAuth(new Order(null));
        orderResponse.assertThat().statusCode(SC_BAD_REQUEST).and().body("success", equalTo(false));
        orderResponse.assertThat().body("message", equalTo(ORDER_WITHOUT_INGREDIENTS_MESSAGE));
    }

    @Test
    @Description("Create order with incorrect ingredients (filed) - status 400, Body:\"success\" = false")
    public void createOrderIncorrectIngredientsTest() {
        ValidatableResponse orderResponse = orderSteps.createOrderWithoutAuth(new Order(incorrectIngredients));
        orderResponse.assertThat().statusCode(SC_BAD_REQUEST).and().body("success", equalTo(false));
        orderResponse.assertThat().body("message", equalTo(ORDER_WITH_INCORRECT_INGREDIENTS_MESSAGE));
    }

    @Test
    @Description("get user orders with authorization (successful) - status 200, Body:\"success\" = true")
    public void getOrderTest() {
        user = User.createRandom();
        ValidatableResponse response = userSteps.create(user);
        accessToken = response.extract().path("accessToken");
        orderSteps.createOrder(new Order(ingredients), accessToken);
        ValidatableResponse orderResponse = orderSteps.getOrder(accessToken);
        orderResponse.assertThat().statusCode(SC_OK).and().body("success", equalTo(true));
        orderResponse.assertThat().body("orders", notNullValue());
        userSteps.removed(accessToken);
    }

    @Test
    @Description("get orders without authorization (successful) - status 401, Body:\"success\" = false")
    public void getOrderWithoutAuthTest() {
        orderSteps.createOrderWithoutAuth(new Order(ingredients));
        ValidatableResponse orderResponse = orderSteps.getOrderWithoutAuth();
        orderResponse.assertThat().statusCode(SC_UNAUTHORIZED).and().body("success", equalTo(false));
        orderResponse.assertThat().body("message", equalTo(YOU_SHOULD_BE_AUTHORIZED_MESSAGE));
    }
}