import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import model.CreateCourier;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class TestCourierCreate {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Create Courier")
    public void createCourierTest() {
        CreateCourier courier = new CreateCourier("Test9_", "Qwerty", "Adam");
        given().header("Content-type", "application/json").and().body(courier).when().post("api/v1/courier").then().assertThat().statusCode(201).and().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Create Courier Simple Name")
    public void createCourierSimpleNameTest() {
        CreateCourier courier = new CreateCourier("Test1", "Qwerty123", "Mila");
        String expected = "Этот логин уже используется. Попробуйте другой.";
        given().header("Content-type", "application/json").and().body(courier).when().post("api/v1/courier").then().assertThat().statusCode(409).and().body("message", equalTo(expected));
    }

    @Test
    @DisplayName("Create Courier With Out Password")
    public void createCourierWithOutPasswordTest() {
        CreateCourier courier = new CreateCourier("Test2", "", "Mimi");
        String expected = "Недостаточно данных для создания учетной записи";
        given().header("Content-type", "application/json").and().body(courier).when().post("api/v1/courier").then().assertThat().statusCode(400).and().body("message", equalTo(expected));
    }

    @Test
    @DisplayName("Create Courier With Out Login")
    public void createCourierWithOutLoginTest() {
        CreateCourier courier = new CreateCourier("", "Qwerty", "Mi");
        String expected = "Недостаточно данных для создания учетной записи";
        given().header("Content-type", "application/json").and().body(courier).when().post("api/v1/courier").then().assertThat().statusCode(400).and().body("message", equalTo(expected));
    }

}
