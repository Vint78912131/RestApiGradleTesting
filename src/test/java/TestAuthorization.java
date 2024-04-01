import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

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
    public void logInPositiveTest() {
        RestAssured.baseURI = TestVz.endpoint;
        JSONObject requestBody = new JSONObject()
                .put("LoginPass", TestVz.password)
                .put("LoginUser", TestVz.login);
        Response response = RestAssured
                .given()
                .contentType("application/json")
                .body(requestBody.toString())
                .when()
                .post("/login");

        try {
            response.then()
                    .assertThat()
                    .statusCode(201)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 201 Created");
            TestVz.cookies = response.getDetailedCookies().toString();
            TestVz.jwtToken = JsonPath.from(response.getBody().asString()).getString("payload.jwtToken");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getBody(response);
        }
    }

}