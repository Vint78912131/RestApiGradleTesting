import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

@Tag("Log")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestLog {

    @BeforeAll
    public static void preparation() {
        TestVz.setCookies();
        TestVz.setServersInfo();
    }


    @Test
    @DisplayName("Get all warnings")
    @Epic(value = "Virtualization")
    @Story("Logging")
    @Link(name = "doc link", url = "TO DO")
    @Feature("Get all warnings")
    @Description("Get all warnings")
    @Severity(SeverityLevel.MINOR)
    @Step ("Получить список предупреждений кластера.")
    public void getWarningsListTest () {
        Response response = RestAssured
                .given()
                .header("Authorization",TestVz.jwtToken)
                .contentType("application/json")
                .when()
                .get("/warnings");
        try {
            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("GET\n" +
                    RestAssured.baseURI +
                    "/warnings\n");
            TestVz.getResponseBody(response);
        }
    }


}
