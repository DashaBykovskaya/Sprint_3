package api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient {

    @Step("Courier login")
    public ValidatableResponse postCourierLogin(Object body){
        return given()
                .header("Content-type", "application/json")
                .and().body(body)
                .when().post("/api/v1/courier/login/")
                .then();
}
    @Step("Courier create")
    public ValidatableResponse postCreateCourier(Object body){
        return given()
                .header("Content-type", "application/json")
                .and().body(body)
                .when().post("/api/v1/courier/")
                .then();
    }

    @Step("Courier delete")
    public ValidatableResponse deleteCourier(int courierId){
        return given()
                .header("Content-type", "application/json")
                .when().post("/api/v1/courier/" + courierId)
                .then();
    }

}
