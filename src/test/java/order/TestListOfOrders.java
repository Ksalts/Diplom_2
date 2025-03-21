package order;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.*;

public class TestListOfOrders {
    private String accessToken;
    private BaseUrl baseUrl;
    private final OrderAssertions orderAssertions = new OrderAssertions();
    protected final RandomUser randomUser = new RandomUser();

    @Before
    @Step("Предусловия")
    public void setUp(){
        baseUrl = new BaseUrl();
        final CreateUser randomUserNew = randomUser.random();
        final UserAssertions userAssertions = new UserAssertions();
        ValidatableResponse create = baseUrl.register(randomUserNew);
        accessToken = userAssertions.assertCreateUser(create);
    }
    @Test
    @DisplayName("Получение списка заказов авторизованным пользователем")
    @Description("Можно получить список заказов авторизованным пользователем")
    public void getOrdersByAuthorizedUser(){
        ValidatableResponse response = baseUrl.getOrdersListUser(accessToken);
        orderAssertions.assertListOfOrders(response);
    }

    @Test
    @DisplayName("Получение списка заказов неавторизованным пользователем")
    @Description("Нельзя получить список заказов без авторизации")
    public void listOfOrdersWithoutLogin(){
        ValidatableResponse response = baseUrl.getOrdersListUser("123");
        orderAssertions.assertListOfOrdersWithoutLogin(response);
    }

    @After
    @Step("Удалить пользователя")
    public void deleteUser(){
        baseUrl.delete(accessToken);
    }



}
