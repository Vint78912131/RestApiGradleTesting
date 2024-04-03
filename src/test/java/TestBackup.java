import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

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
    @DisplayName("Create backup VM")
    @Epic(value = "Virtualization")
    @Story("Backup")
    @Link(name = "doc link", url = "TO DO")
    @Feature("Create backup VM")
    @Description("Create backup VM")
    @Severity(SeverityLevel.MINOR)
    @Step ("Создание резервной копии виртуальной машины на сервере.")
    @Order(1)
    public void createBackupVMTest() {
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
                    .statusLine("HTTP/1.1 200 OK")
            ;

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
    @Order(2)
    public void getBackupVMListTest() {
        Response response = null;
        String vm_uuid = null;
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
                    .statusLine("HTTP/1.1 200 OK")
            ;

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
    @Order(3)
    public void restoreVMTest() {
        List <String> backups_uuid = null;
        String uuid = null;
        Response response = null;
        String vm_uuid = null;
        String requestBody = "{}";
        try {
            vm_uuid = TestVz.vms_uuid.get(0);
            backups_uuid = TestVz.getBackupVMList(vm_uuid);
            uuid = "%7Bfcaac7d3-70ae-4c8e-987c-893c9d243d16%7D.1"; //backups_uuid.get(0);
            response = RestAssured
                    .given()
                    .header("Authorization", TestVz.jwtToken)
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post("/vm/" + vm_uuid + "/backup/restore/" + uuid);

            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK")
            ;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("POST\n" +
                    RestAssured.baseURI + "/vm/" + vm_uuid + "/backup/restore/" + uuid + "\n" +
                    requestBody);
            TestVz.getResponseBody(response);
        }
    }


}
