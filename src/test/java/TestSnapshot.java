import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Tag("Snapshot")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestSnapshot {
    public String getRandomString(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
    @BeforeAll
    public static void preparation() {
        TestVz.setCookies();
        TestVz.setServersInfo();
        TestVz.setVMInfo();
    }
    @Test
    @DisplayName("Create snapshot VM")
    @Epic(value = "Virtualization")
    @Story("Snapshot")
    @Link(name = "doc link", url = "https://documenter.getpostman.com/view/607407/2s93CHtEzX#9c203aba-50c5-4883-895f-1ca123caa12b")
    @Feature("Create snapshot VM")
    @Description("Create snapshot VM")
    @Severity(SeverityLevel.MINOR)
    @Step ("Создание снапшота виртуальной машины на сервере.")
    @Order(1)
    public void createSnapshotVMTest() {
            String name = getRandomString(10);
            Response response = null;
            String requestBody = "{\"name\":\"" + name + "\",\"description\":\"test snapshot " + name + "\"}";
            String vm_uuid = null;
        try {
            vm_uuid = TestVz.vms_uuid.get(0);
            response = RestAssured
                .given()
                .header("Authorization", TestVz.jwtToken)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/vm/" + vm_uuid + "/snapshot");
            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("POST\n" +
                    RestAssured.baseURI + "/vm/" + vm_uuid + "/snapshot\n" +
                    requestBody);
            TestVz.getResponseBody(response);
        }
    }

    @Test
    @DisplayName("Get snapshot VM list")
    @Epic(value = "Virtualization")
    @Story("Snapshot")
    @Link(name = "doc link", url = "https://documenter.getpostman.com/view/607407/2s93CHtEzX#2a9cd371-81b0-4bfe-a02f-50324b9b00b2")
    @Feature("Get snapshot VM list")
    @Description("Get snapshot VM list")
    @Severity(SeverityLevel.MINOR)
    @Step ("Получение списка снапшотов виртуальной машины на сервере.")
    @Order(2)
    public void getSnapshotVMListTest() {
            Response response = null;
            String vm_uuid = null;
        try {
            vm_uuid = TestVz.vms_uuid.get(0);
            response = RestAssured
                .given()
                .header("Authorization", TestVz.jwtToken)
                .contentType("application/json")
                .when()
                .get("/vm/"+ TestVz.vms_uuid.get(0) + "/snapshot");

            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("GET\n" +
                    RestAssured.baseURI + "/vm/" + vm_uuid + "/snapshot\n");
            TestVz.getResponseBody(response);
        }
    }


    @Test
    @DisplayName("Switch snapshot VM")
    @Epic(value = "Virtualization")
    @Story("Snapshot")
    @Link(name = "doc link", url = "https://documenter.getpostman.com/view/607407/2s93CHtEzX#f900a807-f067-4704-8769-c9fc7a496249")
    @Feature("Switch snapshot VM")
    @Description("Switch snapshot VM")
    @Severity(SeverityLevel.MINOR)
    @Step ("Переключение снапшота виртуальной машины на сервере.")
    @Order(3)
    public void switchSnapshotVMTest() {
            List <String> snapshots_uuid;
            String uuid = null;
            Response response = null;
            String vm_uuid = null;
        try {
            vm_uuid = TestVz.vms_uuid.get(0);
            snapshots_uuid = TestVz.getSnapshotVMList(vm_uuid);
            uuid = snapshots_uuid.get(0);
            System.out.println(uuid);
            response = RestAssured
                .given()
                .header("Authorization", TestVz.jwtToken)
                .contentType("application/json")
                .when()
                .post("/vm/" + vm_uuid + "/snapshot/" + uuid);
            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("POST\n" +
                    RestAssured.baseURI + "/vm/" + vm_uuid + "/snapshot" + uuid);
            TestVz.getResponseBody(response);
        }
    }

    @Test
    @DisplayName("Get snapshot VM parametrs")
    @Epic(value = "Virtualization")
    @Story("Snapshot")
    @Link(name = "doc link", url = "https://documenter.getpostman.com/view/607407/2s93CHtEzX#83124d19-b484-4365-8db7-fdf245859daf")
    @Feature("Get snapshot VM parametrs")
    @Description("Get snapshot VM parametrs")
    @Severity(SeverityLevel.MINOR)
    @Step ("Получение параметров снапшотов виртуальной машины на сервере.")
    @Order(4)
    public void getSnapshotVMParametersTest() {
            List<String> snapshots_uuid;
            String uuid = null;
            Response response = null;
            String vm_uuid = null;
        try {
            vm_uuid = TestVz.vms_uuid.get(0);
            snapshots_uuid = TestVz.getSnapshotVMList(vm_uuid);
            uuid = snapshots_uuid.get(0);
            response = RestAssured
                .given()
                .header("Authorization", TestVz.jwtToken)
                .contentType("application/json")
                .when()
                .get("/vm/" + vm_uuid + "/snapshot/" + uuid);
            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("GET\n" +
                    RestAssured.baseURI + "/vm/" + vm_uuid + "/snapshot" + uuid);
            TestVz.getResponseBody(response);
        }
    }

    @Test
    @DisplayName("Delete snapshot VM")
    @Epic(value = "Virtualization")
    @Story("Snapshot")
    @Link(name = "doc link", url = "https://documenter.getpostman.com/view/607407/2s93CHtEzX#2d2d9ac2-a85a-4baf-aae0-9c238ad9d70e")
    @Feature("Delete snapshot VM")
    @Description("Delete snapshot VM")
    @Severity(SeverityLevel.MINOR)
    @Step ("Удаление снапшота виртуальной машины на сервере.")
    @Order(5)
    public void deleteSnapshotVMTest() {
            List<String> snapshots_uuid;
            String uuid = null;
            Response response = null;
            String vm_uuid = null;
            String requestBody = "{\"children\":false}";
        try {
            vm_uuid = TestVz.vms_uuid.get(0);
            snapshots_uuid = TestVz.getSnapshotVMList(vm_uuid);
            uuid = snapshots_uuid.get(0);
            response = RestAssured
                .given()
                .header("Authorization", TestVz.jwtToken)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .delete("/vm/" + vm_uuid + "/snapshot/" + uuid);
            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("DELETE\n" +
                    RestAssured.baseURI + "/vm/" + vm_uuid + "/snapshot" + uuid + "\n"+
                    requestBody);
            TestVz.getResponseBody(response);
        }
    }
}