package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;


import static org.hamcrest.Matchers.*;

public class UserAssertions {

    @Step("Успешная регистрация пользователя")
    public String assertCreateUser(ValidatableResponse response){
        return response.assertThat()
                .statusCode(200)
                .body("success",is(true))
                .extract().path("accessToken");
    }

    @Step("Регистрация с пустым обязательным полем")
    public void assertCreateUserNoRequiredField(ValidatableResponse response){
        response.assertThat()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Step("Регистрация существующего пользователя")
    public void assertFailedCreateUser(ValidatableResponse response){
        response.assertThat()
                .statusCode(403)
                .body("message", equalTo("User already exists"));
    }

    @Step("Успешная авторизация пользователя")
    public void assertSuccessLogin(ValidatableResponse response){
        response.assertThat()
                .statusCode(200)
                .body("accessToken", notNullValue());
    }
    @Step("Авторизация с неверными логином и паролем")
    public void assertFailedLogin(ValidatableResponse response){
        response.assertThat()
                .statusCode(401)
                .body("message", equalTo("email or password are incorrect"));
    }

    @Step("Успешное изменение данных пользователя")
    public void assertUpdateUser(ValidatableResponse response){
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }
    @Step("Попытка изменения данных пользователя без авторизации")
    public void assertUpdateUserFailed(ValidatableResponse response){
        response.assertThat()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"));
    }


}
