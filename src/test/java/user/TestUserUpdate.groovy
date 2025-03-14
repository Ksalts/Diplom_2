package user

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.BaseUrl;
import praktikum.UserAssertions;
import praktikum.CreateUser;
import praktikum.RandomUser;

class TestUserUpdate {
    private String accessToken;
    private BaseUrl baseUrl;
    private CreateUser randomUserNew;
    private UserAssertions userAssertions;
    protected final RandomUser randomUser = new RandomUser();

    @Before
    @Step("Предусловия для изменения данных пользователя")
    public void setUp(){
        baseUrl = new BaseUrl();
        randomUserNew = randomUser.random();
        userAssertions = new UserAssertions();
        ValidatableResponse create = baseUrl.register(randomUserNew);
        accessToken = userAssertions.assertCreateUser(create);
    }
    @Test
    @DisplayName("Изменение данных авторизованного пользователя")
    @Description("Можно изменить пароль у авторизованного пользователя")
    public void updatePasswordSuccess(){
        randomUserNew.setPassword(randomUserNew.getPassword()+"123");
        ValidatableResponse response = baseUrl.update(accessToken,randomUserNew);
        userAssertions.assertUpdateUser(response);
    }
    @Test
    @DisplayName("Изменение данных авторизованного пользователя")
    @Description("Можно изменить имя авторизованного пользователя")
    public void updateNameSuccess() {
        randomUserNew.setName(randomUserNew.getName() + "asd");
        ValidatableResponse response = baseUrl.update(accessToken, randomUserNew);
        userAssertions.assertUpdateUser(response);
    }

    @Test
    @DisplayName("Изменение данных авторизованного пользователя")
    @Description("Изменение email авторизованого пользователя")
    public void updateEmailSuccess(){
        randomUserNew.setEmail(randomUserNew.getEmail()+"123");
        ValidatableResponse response = baseUrl.update(accessToken,randomUserNew);
        userAssertions.assertUpdateUser(response);
    }
    @Test
    @DisplayName("Изменение данных неавторизвоанного пользователя")
    @Description("Нельзя изменить пароль неавторизованного пользователя")
    public void updatePasswordFailed(){
        randomUserNew.setPassword(randomUserNew.getPassword()+"123");
        ValidatableResponse response = baseUrl.update("null", randomUserNew);
        userAssertions.assertUpdateUserFailed(response);
    }

    @Test
    @DisplayName("Изменение данных неавторизвоанного пользователя")
    @Description("Нельзя изменить email неавторизованного пользователя")
    public void updateEmailFailed(){
        randomUserNew.setEmail(randomUserNew.getEmail()+"123");
        ValidatableResponse response = baseUrl.update("null", randomUserNew);
        userAssertions.assertUpdateUserFailed(response);
    }

    @Test
    @DisplayName("Изменение данных неавторизвоанного пользователя")
    @Description("Нельзя изменить Имя неавторизованного пользователя")
    public void updateNameFailed(){
        randomUserNew.setName(randomUserNew.getName()+"qwe");
        ValidatableResponse response = baseUrl.update("null", randomUserNew);
        userAssertions.assertUpdateUserFailed(response);
    }

    @After
    @Step("Удалить пользователя")
    public void deleteUser(){
        baseUrl.delete(accessToken);
    }

}
