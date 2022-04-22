import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class TestOrdersGet {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Просмотр ответа заказа")
    public void getOrdersTest() {
        given()
                .header("Content-type", "application/json")
                .get("/api/v1/orders")
                .then().statusCode(200);
    }
}
