import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestBackup {
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
    @DisplayName("Create full backup VM")
    @Epic(value = "Virtualization")
    @Story("Backup")
    @Link(name = "doc link", url = "TO DO")
    @Feature("Create full backup VM")
    @Description("Create full backup VM")
    @Severity(SeverityLevel.MINOR)
    @Step ("Создание полной резервной копии виртуальной машины на сервере.")
    @Order(1)
    public void createFullBackupVMTest() {
            String name = getRandomString(10);
            Response response = null;
            String requestBody = "{\"full\":true,\"description\":\"test backup " + name + "\"}";
            String vm_uuid = null;
        try {
            vm_uuid = TestVz.vms_uuid.get(0);
            response = RestAssured
                    .given()
                    .header("Authorization", TestVz.jwtToken)
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post("/vm/" + vm_uuid + "/backup");
            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("POST\n" +
                    RestAssured.baseURI + "/vm/" + vm_uuid + "/backup\n" +
                    requestBody);
            TestVz.getResponseBody(response);
        }
    }

    @Test
    @DisplayName("Create incremental backup VM")
    @Epic(value = "Virtualization")
    @Story("Backup")
    @Link(name = "doc link", url = "TO DO")
    @Feature("Create incremental backup VM")
    @Description("Create incremental backup VM")
    @Severity(SeverityLevel.MINOR)
    @Step ("Создание инкрементальной резервной копии виртуальной машины на сервере.")
    @Order(1)
    public void createBackupVMTest() {
            String name = getRandomString(10);
            String op_uuid = "";
            Response response = null;
            String requestBody = "{\"full\":false,\"description\":\"test backup " + name + "\"}";
            String vm_uuid = null;
        try {
            vm_uuid = TestVz.vms_uuid.get(0);
            response = RestAssured
                    .given()
                    .header("Authorization", TestVz.jwtToken)
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post("/vm/" + vm_uuid + "/backup");
            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK");
            JSONObject body = new JSONObject(response.getBody().asString());
            JSONObject payload = body.getJSONObject("meta");
            op_uuid = payload.get("request_uuid").toString();
            TestVz.getOperationResult(op_uuid);
            System.out.println(op_uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("POST\n" +
                    RestAssured.baseURI + "/vm/" + vm_uuid + "/backup\n" +
                    requestBody);
            TestVz.getResponseBody(response);
        }
    }

    @Test
    @DisplayName("Get backup VM list")
    @Epic(value = "Virtualization")
    @Story("Backup")
    @Link(name = "doc link", url = "TO DO")
    @Feature("Get backup VM list")
    @Description("Get backup VM list")
    @Severity(SeverityLevel.MINOR)
    @Step ("Получение списка резервных копий виртуальной машины на сервере.")
    @Order(3)
    public void getBackupVMListTest() {
            Response response = null;
            String vm_uuid;
            String op_uuid = "";
        try {
            vm_uuid = TestVz.vms_uuid.get(0);
            response = RestAssured
                    .given()
                    .header("Authorization", TestVz.jwtToken)
                    .contentType("application/json")
                    .param("vm_uuid", vm_uuid)
                    .when()
                    .get("/vm/backup/list");
            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK");
            JSONObject body = new JSONObject(response.getBody().asString());
            JSONObject payload = body.getJSONObject("meta");
            op_uuid = payload.get("request_uuid").toString();
            TestVz.getOperationResult(op_uuid);
            System.out.println(op_uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("GET\n" +
                    RestAssured.baseURI + "/vm/snapshot");
            TestVz.getResponseBody(response);
        }
    }

    @Test
    @DisplayName("Restore from backup VM")
    @Epic(value = "Virtualization")
    @Story("Backup")
    @Link(name = "doc link", url = "TO DO")
    @Feature("Restore from backup VM")
    @Description("Restore from backup VM")
    @Severity(SeverityLevel.MINOR)
    @Step ("Восстановление виртуальной машины из бэкапа на сервере.")
    @Order(4)
    public void restoreVMTest() {
            List <String> backups_uuid = null;
            String uuid = null;
            Response response = null;
            String vm_uuid = null;
            String requestBody = "{}";
        try {
            vm_uuid = TestVz.vms_uuid.get(0);
            backups_uuid = TestVz.getBackupVMList(vm_uuid);
            uuid = backups_uuid.get(0);
            uuid = URLEncoder.encode(uuid, StandardCharsets.UTF_8);
//            System.out.println("!!!!!!!!!!!!!   " + uuid + "     !!!!!!!!!!!!!!!!");
            response = RestAssured
                    .given()
                    .urlEncodingEnabled(false)
                    .header("Authorization", TestVz.jwtToken)
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post("/vm/" + vm_uuid + "/backup/restore/" + uuid);

            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("POST\n" +
                    RestAssured.baseURI + "/vm/" + vm_uuid + "/backup/restore/" + uuid + "\n" +
                    requestBody);
            TestVz.getResponseBody(response);
        }
    }

    @Test
    @DisplayName("Delete backup VM")
    @Epic(value = "Virtualization")
    @Story("Backup")
    @Link(name = "doc link", url = "https://documenter.getpostman.com/view/607407/2s93CHtEzX#2d2d9ac2-a85a-4baf-aae0-9c238ad9d70e")
    @Feature("Delete backup VM")
    @Description("Delete backup VM")
    @Severity(SeverityLevel.MINOR)
    @Step ("Удаление бэкапа виртуальной машины на сервере.")
    @Order(5)
    public void deleteBackupVMTest() {
            List <String> backups_uuid = null;
            String uuid = null;
            Response response = null;
            String vm_uuid = null;
            String requestBody = "{}";
        try {
            vm_uuid = TestVz.vms_uuid.get(0);
            backups_uuid = TestVz.getBackupVMList(vm_uuid);
            uuid = backups_uuid.get(0);
//            uuid = URLEncoder.encode(uuid, StandardCharsets.UTF_8.toString());
            uuid = uuid.replace("{","");
            uuid = uuid.replace("}","");
            response = RestAssured
                    .given()
                    .header("Authorization", TestVz.jwtToken)
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .delete("/vm/" + vm_uuid + "/backup/" + uuid);
            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("DELETE\n" +
                    RestAssured.baseURI + "/vm/" + vm_uuid + "/backup" + uuid + "\n"+
                    requestBody);
            TestVz.getResponseBody(response);
        }
    }


}
