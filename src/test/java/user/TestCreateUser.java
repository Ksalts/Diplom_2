package user;

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

public class TestCreateUser {
    private final BaseUrl baseUrl = new BaseUrl();
    private CreateUser ramdomUserNew;
    private final UserAssertions userAssertions = new UserAssertions();
    private String accessToken = null;
    protected final RandomUser randomUser = new RandomUser();

    @Before
    @Step("Предусловия для регистрации")
    public void setUp(){
        ramdomUserNew = randomUser.random();
    }

    @Test
    @DisplayName("Регистрация нового пользователя")
    @Description("Успешная регистрация нового пользователя")
    public void successCreateUser(){
        ValidatableResponse create = baseUrl.register(ramdomUserNew);
        accessToken = userAssertions.assertCreateUser(create);
    }

    @Test
    @DisplayName("Регистрация пользователя без заполнения email")
    @Description("Пользователя нельзя создать без email")
    public void createUserWhithoutEmail(){
        ramdomUserNew.setEmail(null);
        ValidatableResponse create = baseUrl.register(ramdomUserNew);
        userAssertions.assertCreateUserNoRequiredField(create);
    }

    @Test
    @DisplayName("Регистрация пользователя без заполнения поля пароль")
    @Description("Нельзя зарегистрировать пользователя без заполнения поля пароль")
    public void createUserWithoutPassword(){
        ramdomUserNew.setPassword(null);
        ValidatableResponse create = baseUrl.register(ramdomUserNew);
        userAssertions.assertCreateUserNoRequiredField(create);
    }

    @Test
    @DisplayName("Регистрация пользователя без заполнения поля Имя")
    @Description("Нельзя зарегистрировать пользователя без заполнения поля Имя")
    public void createUserWithoutName(){
        ramdomUserNew.setName(null);
        ValidatableResponse create = baseUrl.register(ramdomUserNew);
        userAssertions.assertCreateUserNoRequiredField(create);
    }

    @Test
    @DisplayName("Регистрация уже существующего пользователя")
    @Description("Нельзя зарегистрировать пользователя, который уже существует")
    public void createExistUser(){
        ValidatableResponse create = baseUrl.register(ramdomUserNew);
        accessToken = userAssertions.assertCreateUser(create);

        ValidatableResponse create2 = baseUrl.register(ramdomUserNew);
        userAssertions.assertFailedCreateUser(create2);
    }

    @After
    @Step("Удалить пользователя")
    public void deleteUser(){
        if (accessToken != null){
            baseUrl.delete(accessToken);
        }
    }


}
