import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestHealthCheck {
    public static JSONArray sessions = null;
    public static ArrayList<String> session_ids = new ArrayList<>();

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
                    .statusLine("HTTP/1.1 200 OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("GET\n" +
                    RestAssured.baseURI +
                    "/nodes\n");
            TestVz.getResponseBody(response);
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
                    .statusLine("HTTP/1.1 200 OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("GET\n" +
                    RestAssured.baseURI +
                    "/nodes/" +
                    TestVz.srv_uuid.get(0) +
                    "/sysinfo");
            TestVz.getResponseBody(response);
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
                    .statusLine("HTTP/1.1 200 OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("GET\n" +
                    RestAssured.baseURI +
                    "/nodes/" +
                    TestVz.srv_uuid.get(0));
            TestVz.getResponseBody(response);
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
                    .statusLine("HTTP/1.1 200 OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("GET\n" +
                    RestAssured.baseURI +
                    "/environments");
            TestVz.getResponseBody(response);
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
                    .statusLine("HTTP/1.1 200 OK");

            sessions = null;
            JSONObject body = new JSONObject(response.getBody().asString());
            JSONObject payload = (JSONObject) body.get("payload");
            sessions = payload.getJSONArray("sessions");
            session_ids.clear();
            for (int i = 0; i < sessions.length(); i++) {
                session_ids.add(sessions.getJSONObject(i).get("session_id").toString());
            }
            System.out.println(session_ids);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("GET\n" +
                    RestAssured.baseURI +
                    "/sessions");
            TestVz.getResponseBody(response);
        }
    }

    @Test
    @DisplayName("Get all resources")
    @Epic(value = "Virtualization")
    @Story("Servers")
    @Link(name = "doc link", url = "TO DO")
    @Feature("Get all resources")
    @Description("Get all resources")
    @Severity(SeverityLevel.MINOR)
    @Step ("Получить информацию о всех ресурсах кластера.")
    public void getAllResourcesTest () {
        Map<String,String> params = new HashMap<>();
        params.put("time_interval","week");
        params.put("sort_type","environment");
        params.put("sort_dir","desc");
        params.put("limit","10");
        params.put("offset","0");

        Response response = RestAssured
                .given()
                .header("Authorization",TestVz.jwtToken)
                .contentType("application/json")
                .params(params)
                .when()
                .get("/resources");
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
                    "/resources\n" +
                    params);
            TestVz.getResponseBody(response);
        }
    }


//    @Test
//    @DisplayName("Delete first session from sessions list")
//    @Epic(value = "Virtualization")
//    @Story("Servers")
//    @Link(name = "doc link", url = "TO DO")
//    @Feature("Delete first session")
//    @Description("Delete first session")
//    @Severity(SeverityLevel.MINOR)
//    @Step ("Удаление первой активной сессии.")
//    public void deleteSessionTest () {
//        getSessionsTest();
//
//        Response response = RestAssured
//                .given()
//                .header("Authorization",TestVz.jwtToken)
//                .contentType("application/json")
//                .when()
//                .get("/sessions/" + session_ids.get(0));
//        try {
//            response.then()
//                    .assertThat()
//                    .statusCode(200)
//                    .contentType("application/json")
//                    .statusLine("HTTP/1.1 200 OK");
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            TestVz.getRequestBody("DELETE\n" +
//                    RestAssured.baseURI +
//                    "/sessions/" +
//                    session_ids.get(0));
//            TestVz.getResponseBody(response);
//        }
//    }


}
