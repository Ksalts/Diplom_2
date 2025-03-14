package user;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import praktikum.*;

public class TestLoginUser {
    private BaseUrl baseUrl;
    private CreateUser randomUserNew;
    private UserAssertions userAssertions;
    private String accessToken;
    protected final RandomUser randomUser = new RandomUser();

    @Before
    @Step("Предусловия для авторизации пользователя")
    public void setUp(){
        baseUrl = new BaseUrl();
        randomUserNew = randomUser.random();
        userAssertions = new UserAssertions();
        ValidatableResponse create = baseUrl.register(randomUserNew);
        accessToken = userAssertions.assertCreateUser(create);

    }

    @Test
    @DisplayName("Успешный логин пользователя")
    @Description("Пользователь может авторизоваться")
    public void userLoginSuccess(){
        LoginUser loginUser = LoginUser.from(randomUserNew);
        ValidatableResponse login = baseUrl.login(loginUser);
        userAssertions.assertSuccessLogin(login);
    }

    @Test
    @DisplayName("Авторизация с неверным паролем")
    @Description("Нельзя авторизоваться с неверным паролем")
    public void userLoginWithWrongPassword(){
        LoginUser loginUser = LoginUser.from(randomUserNew);
        loginUser.setPassword(loginUser.getPassword() + "123");
        ValidatableResponse login = baseUrl.login(loginUser);
        userAssertions.assertFailedLogin(login);
    }

    @Test
    @DisplayName("Авторизация с неверным логином")
    @Description("ельзя авторизоваться с неверным логином")
    public void userLoginWithWrongEmail(){
        LoginUser loginUser = LoginUser.from(randomUserNew);
        loginUser.setEmail(loginUser.getEmail()+"123");
        ValidatableResponse login = baseUrl.login(loginUser);
        userAssertions.assertFailedLogin(login);
    }

    @After
    @Step("Удаление пользователя")
    public void deleteUser(){
        baseUrl.delete(accessToken);
    }


}
