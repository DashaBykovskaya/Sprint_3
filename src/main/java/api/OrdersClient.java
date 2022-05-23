package api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrdersClient {
    @Step("Create orders")
    public ValidatableResponse postCreateOrder(Object body) {
        return given()
                .header("Content-type", "application/json")
                .and().body(body)
                .when().post("/api/v1/orders")
                .then();
    }

}

