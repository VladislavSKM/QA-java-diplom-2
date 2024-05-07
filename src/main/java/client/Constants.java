package client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Constants {

    public static final String USER_EMAIL_ALREADY_EXISTS = "skmvlady@yandex.ru";
    public static final String USER_NAME_ALREADY_EXISTS = "Onshapes";
    public static final String USER_PASSWORD_ALREADY_EXISTS = "password";
    public static final String ORDER_ENDPOINT = "/api/orders";
    public static final String CREATE_USER_ENDPOINT = "/api/auth/register";
    public static final String LOGIN_USER_ENDPOINT = "/api/auth/login";
    public static final String AUTH_USER_ENDPOINT = "/api/auth/user";
    public static final String USER_ALREADY_EXIST_MESSAGE = "User already exists";
    public static final String LOGIN_INCORRECT_MESSAGE = "email or password are incorrect";
    public static final String USER_WITHOUT_REQUIRED_FIELD_MESSAGE = "Email, password and name are required fields";
    public static final String YOU_SHOULD_BE_AUTHORIZED_MESSAGE = "You should be authorised";
    public static final String USER_REMOVED_MESSAGE = "User successfully removed";
    public static final String ORDER_WITHOUT_INGREDIENTS_MESSAGE = "Ingredient ids must be provided";
    public static final String ORDER_WITH_INCORRECT_INGREDIENTS_MESSAGE = "One or more ids provided are incorrect";
    public static final String BIOCOTLET_INGREDIENT = "61c0c5a71d1f82001bdaaa71";
    public static final String INCORRECT_INGREDIENT = "11c0c5a71d1f82001bdaaa71";
    public static final String FILENIAN_WOOD_INGREDIENT = "61c0c5a71d1f82001bdaaa77";
    public static final RequestSpecification REQUEST_SPECIFICATION =
            new RequestSpecBuilder()
                    .log(LogDetail.ALL)
                    .addHeader("Content-Type", "application/json")
                    .setBaseUri("https://stellarburgers.nomoreparties.site")
                    .build();
    public static final ResponseSpecification RESPONSE_SPECIFICATION =
            new ResponseSpecBuilder()
                    .log(LogDetail.ALL)
                    .build();
}
