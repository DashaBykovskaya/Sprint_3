import api.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import model.CreateCourier;
import model.LoginCourier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class TestCourierLogin {

    CreateCourier courier = new CreateCourier("Testik", "Qwerty", "Adam");
    int courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        given().header("Content-type", "application/json").and().body(courier).when().post("api/v1/courier");
    }

   @After
    public void cleanUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        CourierClient courierClient = new CourierClient();
        courierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Логин курьера в системе")
    public void loginCourierTest() {
        LoginCourier loginCourier = new LoginCourier("Testik", "Qwerty");
        CourierClient courierClient = new CourierClient();
        ValidatableResponse courierLoginResponse = courierClient.postCourierLogin(loginCourier);
        courierLoginResponse.assertThat().statusCode(200).and().assertThat().extract().path("id");
    }

    @Test
    @DisplayName("Логин курьера без указания login")
    public void loginCourierWithOutLoginTest() {
        LoginCourier loginCourier = new LoginCourier("", "Qwerty");
        String expected = "Недостаточно данных для входа";
        CourierClient courierClient = new CourierClient();
        ValidatableResponse courierLoginResponse = courierClient.postCourierLogin(loginCourier);
        courierLoginResponse.assertThat().statusCode(400).and().body("message", equalTo(expected));
    }

    @Test
    @DisplayName("Логин курьера без пароля")
    public void loginCourierWithOutPasswordTest() {
        LoginCourier loginCourier = new LoginCourier("Testik", "");
        String expected = "Недостаточно данных для входа";
        CourierClient courierClient = new CourierClient();
        ValidatableResponse courierLoginResponse = courierClient.postCourierLogin(loginCourier);
        courierLoginResponse.assertThat().statusCode(400).and().body("message", equalTo(expected));
    }

    @Test
    @DisplayName("Логин курьера с login, которого нет в базе")
    public void loginCourierWrongLoginTest() {
        LoginCourier loginCourier = new LoginCourier("Пример", "Qwerty");
        String expected = "Учетная запись не найдена";
        CourierClient courierClient = new CourierClient();
        ValidatableResponse courierLoginResponse = courierClient.postCourierLogin(loginCourier);
        courierLoginResponse.assertThat().statusCode(404).and().body("message", equalTo(expected));
    }

    @Test
    @DisplayName("Логин курьера с неверным паролем")
    public void loginCourierWrongPasswordTest() {
        LoginCourier loginCourier = new LoginCourier("Testik", "Любовь");
        String expected = "Учетная запись не найдена";
        CourierClient courierClient = new CourierClient();
        ValidatableResponse courierLoginResponse = courierClient.postCourierLogin(loginCourier);
        courierLoginResponse.assertThat().statusCode(404).and().body("message", equalTo(expected));
    }
}
