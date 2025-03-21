package order;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.*;


import java.util.List;

public class TestOrder {
    private String accessToken;
    private BaseUrl baseUrl;
    private final OrderAssertions orderAssertions = new OrderAssertions();
    protected final RandomUser randomUser = new RandomUser();

    @Before
    @Step("Предусловия для создания заказа")
    public void setUp(){
        baseUrl = new BaseUrl();
        final CreateUser randomUserNew = randomUser.random();
        final UserAssertions userAssertions = new UserAssertions();
        ValidatableResponse create = baseUrl.register(randomUserNew);
        accessToken = userAssertions.assertCreateUser(create);
    }
    @Test
    @DisplayName("Создание заказа c ингридиентами авторизованным пользователем")
    @Description("Можно создать заказ авторизованным пользователем")
    public void createOrderByAuthorizedUser(){
        List<String> ingredients = baseUrl.getIngredients();
        CreateOrder createOrder = new CreateOrder(ingredients);
        ValidatableResponse response = baseUrl.createOrders(accessToken, createOrder);
        orderAssertions.assertCreateOrder(response);
    }

    @Test
    @DisplayName("Создание заказа неавторизованным пользователем")
    @Description("нельзя создать заказ без авторизации")
    public void createOrderWithoutLogin(){
        List<String> ingredients = List.of();
        CreateOrder createOrder = new CreateOrder(ingredients);
        ValidatableResponse response = baseUrl.createOrders("123", createOrder);
        orderAssertions.assertCreateOrderWithoutLogin(response);
    }
    @Test
    @DisplayName("Создание заказа с неверными хешами ингридиентов")
    @Description("нельзя создать заказ с неверными хешами ингридиентов")
    public void createOrderWithWrongHash(){
        List<String> ingredients = List.of(
                "60d3463f7034a000269f45e7",
                "60d3463f7034a000269f45e9",
                "60d3463f7034a000269f45e8",
                "60d3463f7034a000269f45ea");
        CreateOrder createOrder = new CreateOrder(ingredients);
        ValidatableResponse response = baseUrl.createOrders(accessToken, createOrder);
        orderAssertions.assertCreateOrderWithHash(response);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Нельзя создать заказ без ингрелиентов")
    public void createOrderWithoutIngredients() {
        CreateOrder createOrder = new CreateOrder(null);
        ValidatableResponse response = baseUrl.createOrders(accessToken, createOrder);
        orderAssertions.assertCreateOrderWithoutIngredients(response);
    }
    @After
    @Step("Удалить пользователей")
    public void deleteUser(){
        baseUrl.delete(accessToken);
    }



}
