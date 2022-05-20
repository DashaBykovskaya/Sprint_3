import api.OrdersClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import model.CreateOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;


@RunWith(Parameterized.class)
public class TestOrderCreate {

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;

    public TestOrderCreate(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Before
    public void setUp(){
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0},{1},{3},{4},{5},{6},{7},{8},{9}")
    public static Object[][] newOrder() {
        return new Object[][]{
                {"Имя", "Фамилия", "Набережная, д.10", "ВДНХ", "80299735245", 4, "2022-05-01", "Дай самокат", new String[]{" "}},
                {"Петя", "Петров", "Набережная, д.12", "ВДНХ", "80299735213", 1, "2022-04-01", "Плиииз", new String[]{"BLACK"}},
                {"Лера", "Смирнова", "Наумова, д.110", "Черниговская", "80299455555", 10, "2022-04-22", "Дай самокат", new String[]{"GRAY"}},
                {"Саша", "Чикова", "Свердлова, д.55", "Черниговская", "80299445555", 2, "2022-04-12", "коммент", new String[]{"BLACK", "GRAY"}}
        };
    }

    @Test
    @DisplayName("Создание заказа")
    public void createOrderColorTest(){
        CreateOrder createOrder = new CreateOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        OrdersClient ordersClient = new OrdersClient();
        ValidatableResponse orderResponse = ordersClient.postCreateOrder(createOrder);
        orderResponse.assertThat().statusCode(201)
                .and().assertThat().extract().path("track");
    }
}
