import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestAuthorization {
    @Test
    @Epic(value = "Authorization Endpoints")
    @Story("Authorization")
    @Feature("LogIn Positive")
    @Description("LogIn Virtualization Positive")
    @Link(name = "doc link", url = "https://documenter.getpostman.com/view/607407/2s93CHtEzX#049192d2-8de7-49b9-a0b8-a4c6a67bf8c3")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("LogIn Virtualization Positive")
    @Step ("Успешная авторизация пользователя.")
    @Order(2)
    public void logInPositiveTest() {
            RestAssured.baseURI = TestVz.endpoint;
            JSONObject requestBody = new JSONObject()
                .put("LoginPass", TestVz.password)
                .put("LoginUser", TestVz.login);
            Response response = null;
        try {
            response = RestAssured
                    .given()
                    .contentType("application/json")
                    .body(requestBody.toString())
                    .when()
                    .post("/login");
            response.then()
                    .assertThat()
                    .statusCode(201)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 201 Created");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("POST\n" +
                    RestAssured.baseURI +
                    "/login\n" +
                    requestBody);
            TestVz.getResponseBody(response);
        }
    }

    @Test
    @Epic(value = "Authorization Endpoints")
    @Story("Authorization")
    @Feature("LogIn Negative")
    @Description("LogIn Virtualization Negative")
    @Link(name = "doc link", url = "https://documenter.getpostman.com/view/607407/2s93CHtEzX#049192d2-8de7-49b9-a0b8-a4c6a67bf8c3")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("LogIn Virtualization Negative")
    @Step ("Не успешная авторизация пользователя.")
    @Order(1)
    public void logInNegativeTest() {
            RestAssured.baseURI = TestVz.endpoint;
            JSONObject requestBody = new JSONObject()
                .put("LoginPass", "password")
                .put("LoginUser", TestVz.login);
            Response response = null;
        try {
            response = RestAssured
                    .given()
                    .contentType("application/json")
                    .body(requestBody.toString())
                    .when()
                    .post("/login");
            response.then()
                    .assertThat()
                    .statusCode(401)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 401 Unauthorized");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("POST\n" +
                    RestAssured.baseURI +
                    "/login\n" +
                    requestBody);
            TestVz.getResponseBody(response);
        }
    }




}