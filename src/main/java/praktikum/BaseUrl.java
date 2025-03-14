package praktikum;


import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.put;

public class BaseUrl {
    protected static final String BASEURL = "https://stellarburgers.nomoreparties.site/api/";
    protected static final String ORDERS = "/orders";
    protected static final String USER = "/auth/user";
    protected static final String REGISTER = "/auth/register";
    protected static final String LOGIN = "/auth/login";
    protected static final String INGREDIENTS = "/ingredients";

    @Step("Регистрация пользователя")
    public  ValidatableResponse register(CreateUser createUser) {
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .baseUri(BASEURL)
                .body(createUser)
                .when()
                .post(REGISTER)
                .then();
    }
    @Step("Удаление пользователя")
    public void delete (String token){
        given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .baseUri(BASEURL)
                .header("Authorization", token)
                .when()
                .delete()
                .then();
    }
    @Step("Логин пользователя")
    public  ValidatableResponse login (LoginUser loginUser){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .baseUri(BASEURL)
                .body(loginUser)
                .when()
                .post(LOGIN)
                .then();
    }
    @Step("Изменение данных пользователя")
    public ValidatableResponse update(String token, CreateUser createUser){
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .baseUri(BASEURL)
                .header("Authorization", token)
                .body(createUser)
                .when()
                .patch(USER)
                .then()
                .log()
                .all();
    }
    @Step("Создание заказа")
    public ValidatableResponse createOrders(String token, CreateOrder createOrder) {
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .baseUri(BASEURL)
                .header("Authorization", token)
                .body(createOrder)
                .when()
                .post(ORDERS)
                .then()
                .log()
                .all();
    }

    @Step("Получение заказов конкретного пользователя")
    public ValidatableResponse getOrdersListUser(String token) {
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .baseUri(BASEURL)
                .header("Authorization", token)
                .when()
                .get(ORDERS)
                .then()
                .log()
                .all();
    }
    @Step("Получение данных об ингредиентах")
    public List<String> getIngredients() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASEURL)
                .when()
                .get(INGREDIENTS)
                .then().log().all()
                .extract().jsonPath().getList("data._id");
    }
}
