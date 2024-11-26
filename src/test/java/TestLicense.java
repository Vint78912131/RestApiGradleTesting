import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.testng.reporters.Files;
import java.io.File;

@Tag("License")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestLicense {

    @BeforeAll
    public static void preparation() {
        TestVz.setCookies();
        TestVz.setServersInfo();
    }

    @Test
    @DisplayName("Delete server license")
    @Epic(value = "Virtualization")
    @Story("Servers")
    @Link(name = "doc link", url = "TO DO")
    @Feature("Delete server license")
    @Description("Delete server license")
    @Severity(SeverityLevel.MINOR)
    @Step ("Удалить лицензию сервероа кластера.")
    @Order(1)
    public void deleteServerLicense () {
        Response response = null;
        try {
            response = RestAssured
                    .given()
                    .header("Authorization",TestVz.jwtToken)
                    .contentType("application/json")
                    .when()
                    .delete("/lic/" + TestVz.srv_uuid.get(0));
            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK")
            ;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("DELETE\n" +
                    "/lic/" + TestVz.srv_uuid.get(0));
            TestVz.getResponseBody(response);
        }
    }


    @Test
    @DisplayName("Setting server license")
    @Epic(value = "Virtualization")
    @Story("Servers")
    @Link(name = "doc link", url = "TO DO")
    @Feature("Setting server license")
    @Description("Setting server license")
    @Severity(SeverityLevel.MINOR)
    @Step ("Установить лицензию сервероа кластера.")
    @Order(2)
    public void setServerLicense () {
        Response response = null;
        String content = "";
        try {
            File lic = new File("./src/test/resources/RVZ.000000981.0004.txt");
            try {
                System.out.println(lic.toString());
                //content = Files.readFile(lic);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            response = RestAssured
                    .given()
                    .header("Authorization",TestVz.jwtToken)
                    .multiPart("file", lic)
                    .when()
                    .post("/lic/" + TestVz.srv_uuid.get(TestVz.srv_uuid.size()-1));
            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("POST\n" +
                    "/lic/" + TestVz.srv_uuid.get(TestVz.srv_uuid.size()-1) + "\n" +
                    content);
            TestVz.getResponseBody(response);
        }
    }





}
