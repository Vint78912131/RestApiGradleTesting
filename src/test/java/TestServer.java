import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

@Tag("Server")
public class TestServer {

    @BeforeAll
    public static void preparation() {
        TestVz.setCookies();
    }

    @Test
    @DisplayName("Get server list")
    @Epic(value = "Virtualization")
    @Story("Servers")
    @Link(name = "doc link", url = "    https://documenter.getpostman.com/view/607407/2s93CHtEzX#8c40979e-8025-41a6-b611-f41fe4ddc68b")
    @Feature("Get server list")
    @Description("Get server list")
    @Severity(SeverityLevel.MINOR)
    @Step ("Получить список серверов кластера.")
    public void getServerList () {
            Response response = null;
        try {
            response = RestAssured
                    .given()
                    .header("Authorization",TestVz.jwtToken)
                    .contentType("application/json")
                    .when()
                    .get("/nodes");
            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK")
            ;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("GET\n" +
                    RestAssured.baseURI +
                    "/nodes\n");
            TestVz.getResponseBody(response);
        }
    }



}