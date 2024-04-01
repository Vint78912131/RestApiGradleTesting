import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestHealthCheck {

    @BeforeAll
    public static void preparation() {
        TestVz.setCookies();
        TestVz.setServersInfo();
        TestVz.setVMInfo();
    }

    @Test
    @DisplayName("Get nodes list")
    @Epic(value = "Virtualization")
    @Story("Servers")
    @Link(name = "doc link", url = "https://documenter.getpostman.com/view/607407/2s93CHtEzX#8c40979e-8025-41a6-b611-f41fe4ddc68b")
    @Feature("Get nodes list")
    @Description("Get nodes list")
    @Severity(SeverityLevel.MINOR)
    @Step ("Получить список нод кластера.")
    public void getServerListTest () {
        Response response = RestAssured
                .given()
                .header("Authorization",TestVz.jwtToken)
                .contentType("application/json")
                .when()
                .get("/nodes");
        try {
            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK")
            ;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getBody(response);
        }
    }

    @Test
    @DisplayName("Get node system information")
    @Epic(value = "Virtualization")
    @Story("Servers")
    @Link(name = "doc link", url = "TO DO")
    @Feature("Get node system information")
    @Description("Get node system information")
    @Severity(SeverityLevel.MINOR)
    @Step ("Получить полную системную информацию о сервере.")
    public void getServerSysinfoTest () {
        Response response = RestAssured
                .given()
                .header("Authorization",TestVz.jwtToken)
                .contentType("application/json")
                .when()
                .get("/nodes/" + TestVz.srv_uuid.get(0) + "/sysinfo");
        try {
            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK")
            ;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getBody(response);
        }
    }

    @Test
    @DisplayName("Get node information")
    @Epic(value = "Virtualization")
    @Story("Servers")
    @Link(name = "doc link", url = "TO DO")
    @Feature("Get node information")
    @Description("Get node information")
    @Severity(SeverityLevel.MINOR)
    @Step ("Получить информацию о сервере.")
    public void getNodeInfoTest () {
        Response response = RestAssured
                .given()
                .header("Authorization",TestVz.jwtToken)
                .contentType("application/json")
                .when()
                .get("/nodes/" + TestVz.srv_uuid.get(0));
        try {
            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK")
            ;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getBody(response);
        }
    }

    @Test
    @DisplayName("Get all environments")
    @Epic(value = "Virtualization")
    @Story("Servers")
    @Link(name = "doc link", url = "TO DO")
    @Feature("Get all environments")
    @Description("Get all environments")
    @Severity(SeverityLevel.MINOR)
    @Step ("Получить список всех виртуальных сред кластера.")
    public void getAllEnvironmentsTest () {
        Response response = RestAssured
                .given()
                .header("Authorization",TestVz.jwtToken)
                .contentType("application/json")
                .when()
                .get("/environments");
        try {
            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK")
            ;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getBody(response);
        }
    }

    @Test
    @DisplayName("Get all active sessions")
    @Epic(value = "Virtualization")
    @Story("Servers")
    @Link(name = "doc link", url = "TO DO")
    @Feature("Get all all active sessions")
    @Description("Get all all active sessions")
    @Severity(SeverityLevel.MINOR)
    @Step ("Получить список активных сессий.")
    public void getSessionsTest () {
        Response response = RestAssured
                .given()
                .header("Authorization",TestVz.jwtToken)
                .contentType("application/json")
                .when()
                .get("/sessions");
        try {
            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK")
            ;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getBody(response);
        }
    }




}
