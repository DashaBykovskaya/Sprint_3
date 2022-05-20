import api.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import model.CreateCourier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class TestCourierCreate {

    int courierId;

    @Before
    public void setUp() {
        CreateCourier courier = new CreateCourier("Testik", "Qwerty", "Adam");
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        Response courierId =
                given().header("Content-type", "application/json").and().body(courier).when().post("api/v1/courier");
    }

    @After
    public void cleanUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        CourierClient courierClient = new CourierClient();
        courierClient.deleteCourier(courierId);
    }

    @Test
        @DisplayName("Create Courier")
        public void createCourierTest(){
            CreateCourier courier = new CreateCourier("Test71", "Qwerty", "Adam");
            CourierClient courierClient = new CourierClient();
            ValidatableResponse courierCreateResponse = courierClient.postCreateCourier(courier);
            courierCreateResponse.assertThat().statusCode(201).and().body("ok", equalTo(true));
        }

        @Test
        @DisplayName("Create Courier Simple Name")
        public void createCourierSimpleNameTest(){
            CreateCourier courier = new CreateCourier("Testik", "Qwerty", "Adam");
            CourierClient courierClient = new CourierClient();
            ValidatableResponse courierCreateResponse = courierClient.postCreateCourier(courier);
            String expected = "Этот логин уже используется. Попробуйте другой.";
            courierCreateResponse.assertThat().statusCode(409).and().body("message", equalTo(expected));
        }

        @Test
        @DisplayName("Create Courier With Out Password")
        public void createCourierWithOutPasswordTest() {
            CreateCourier courier = new CreateCourier("Test2", "", "Mimi");
            String expected = "Недостаточно данных для входа";
            CourierClient courierClient = new CourierClient();
            ValidatableResponse courierCreateResponse = courierClient.postCourierLogin(courier);
            courierCreateResponse.assertThat().statusCode(400).and().body("message", equalTo(expected));
        }

        @Test
        @DisplayName("Create Courier With Out Login")
        public void createCourierWithOutLoginTest() {
            CreateCourier courier = new CreateCourier("", "Qwerty", "Mi");
            String expected = "Недостаточно данных для входа";
            CourierClient courierClient = new CourierClient();
            ValidatableResponse courierCreateResponse = courierClient.postCourierLogin(courier);
            courierCreateResponse.assertThat().statusCode(400).and().body("message", equalTo(expected));
        }
    }

