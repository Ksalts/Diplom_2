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
    private CreateUser randomUserNew; //
    private final UserAssertions userAssertions = new UserAssertions();
    private String accessToken = null;
    protected final RandomUser randomUser = new RandomUser();

    @Before
    @Step("Предусловия для регистрации")
    public void setUp(){
        randomUserNew = randomUser.random();
    }

    @Test
    @DisplayName("Регистрация нового пользователя")
    @Description("Успешная регистрация нового пользователя")
    public void successCreateUser(){
        ValidatableResponse create = baseUrl.register(randomUserNew);
        accessToken = userAssertions.assertCreateUser(create);
    }

    @Test
    @DisplayName("Регистрация пользователя без заполнения email")
    @Description("Пользователя нельзя создать без email")
    public void createUserWhithoutEmail(){
        randomUserNew.setEmail(null);
        ValidatableResponse create = baseUrl.register(randomUserNew);
        userAssertions.assertCreateUserNoRequiredField(create);
    }

    @Test
    @DisplayName("Регистрация пользователя без заполнения поля пароль")
    @Description("Нельзя зарегистрировать пользователя без заполнения поля пароль")
    public void createUserWithoutPassword(){
        randomUserNew.setPassword(null);
        ValidatableResponse create = baseUrl.register(randomUserNew);
        userAssertions.assertCreateUserNoRequiredField(create);
    }

    @Test
    @DisplayName("Регистрация пользователя без заполнения поля Имя")
    @Description("Нельзя зарегистрировать пользователя без заполнения поля Имя")
    public void createUserWithoutName(){
        randomUserNew.setName(null);
        ValidatableResponse create = baseUrl.register(randomUserNew);
        userAssertions.assertCreateUserNoRequiredField(create);
    }

    @Test
    @DisplayName("Регистрация уже существующего пользователя")
    @Description("Нельзя зарегистрировать пользователя, который уже существует")
    public void createExistUser(){
        ValidatableResponse create = baseUrl.register(randomUserNew);
        accessToken = userAssertions.assertCreateUser(create);

        ValidatableResponse create2 = baseUrl.register(randomUserNew);
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
