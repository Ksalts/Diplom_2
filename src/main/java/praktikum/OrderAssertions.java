package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static org.hamcrest.Matchers.*;

public class OrderAssertions {
    @Step("Создание заказа с авторизацией")
    public void assertCreateOrder(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    @Step("Создать заказ без авторизации")
    public void assertCreateOrderWithoutLogin(ValidatableResponse response) {
        response.assertThat()
                .statusCode(400)
                .body("success", is(false));
    }



    @Step("Создать заказ без индгредиентов")
    public void assertCreateOrderWithoutIngredients(ValidatableResponse response) {
        response.assertThat()
                .statusCode(400)
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Step("Создание заказа с неверным хешем")
    public void assertCreateOrderWithHash(ValidatableResponse response) {
        response.assertThat()
                .statusCode(400)
                .body("message", equalTo("One or more ids provided are incorrect"));
    }
    @Step("Список заказов без авторизации")
    public void assertListOfOrdersWithoutLogin(ValidatableResponse response){
        response.assertThat()
                .statusCode(401)
                .body("message",equalTo("You should be authorised"));
    }
    @Step("Заказы авторизованного пользователя")
    public void assertListOfOrders(ValidatableResponse response){
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }
}


