import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestGroups {
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

    public String getRandomInt(int length) {
        int leftLimit = 0 - length;
        int rightLimit = length;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append(randomLimitedInt);
        }
        return buffer.toString();
    }

    @BeforeAll
    public static void preparation() {
        TestVz.setCookies();
    }

    @Test
    @DisplayName("Get groups list")
    @Epic(value = "Virtualization")
    @Story("Groups")
    @Link(name = "doc link", url = "TO DO")
    @Feature("Get groups list")
    @Description("Get groups list")
    @Severity(SeverityLevel.MINOR)
    @Step ("Получить список групп пользователей сервера.")
    @Order(1)
    public void getGroupsListTest() {
            Response response = null;
        try {
            response = RestAssured
                    .given()
                    .header("Authorization", TestVz.jwtToken)
                    .contentType("application/json")
                    .when()
                    .get("/groups");
            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("GET\n" +
                    RestAssured.baseURI + "/groups");
            TestVz.getResponseBody(response);
        }
    }

    @Test
    @DisplayName("Create new user group")
    @Epic(value = "Virtualization")
    @Story("Groups")
    @Link(name = "doc link", url = "")
    @Feature("Create new user group")
    @Description("Create new user group")
    @Severity(SeverityLevel.CRITICAL)
    @Step ("Создание новой группы пользователей сервера.")
    @Order(2)
    public void createNewGroupTest() {
        String name = getRandomString(10);
        String number = getRandomInt(10);
        String requestBody = "{\n" +
                "    \"name\": \"group_"+ name + number +"\",\n" +
                "    \"description\": \" test user group " + name + number +" creating from autotest\"\n" +
                "}";
        Response response = null;
        try {
            response = RestAssured
                    .given()
                    .header("Authorization", TestVz.jwtToken)
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post("/groups");
            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("POST\n" +
                    RestAssured.baseURI + "/groups\n" +
                    requestBody);
            TestVz.getResponseBody(response);
        }
    }

    @Test
    @DisplayName("Get group information")
    @Epic(value = "Virtualization")
    @Story("Groups")
    @Link(name = "doc link", url = "TO DO")
    @Feature("Get group information")
    @Description("Get group information")
    @Severity(SeverityLevel.MINOR)
    @Step ("Получить информацию о группе пользователей сервера.")
    @Order(3)
    public void getGroupInfoTest() {
        List<String> group_names = new ArrayList<>();
        Response response = null;
        try {
            group_names = TestVz.geGroupsList();
            response = RestAssured
                    .given()
                    .header("Authorization", TestVz.jwtToken)
                    .contentType("application/json")
                    .when()
                    .get("/groups/" + group_names.get(0));
            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("GET\n" +
                    RestAssured.baseURI + "/groups/" + group_names.get(0));
            TestVz.getResponseBody(response);
        }
    }


    @Test
    @DisplayName("Add user to group")
    @Epic(value = "Virtualization")
    @Story("Groups")
    @Link(name = "doc link", url = "TO DO")
    @Feature("Add user to group")
    @Description("Add user to group")
    @Severity(SeverityLevel.MINOR)
    @Step ("Добавить пользователя сервера в группу.")
    @Order(3)
    public void addUserToGroupTest() {
            List<String> users_login;
            List<String> group_names = new ArrayList<>();
            Response response = null;
            String requestBody = "{}";
        try {
            users_login = TestVz.getUsersList();
            group_names = TestVz.geGroupsList();
            requestBody = "{\n" +
                    "    \"users\":\n" +
                    "    [\n" +
                    "        \"" + users_login.get(1) + "\"\n" +
                    "    ]\n" +
                    "}";
            response = RestAssured
                    .given()
                    .header("Authorization", TestVz.jwtToken)
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post("/groups/" + group_names.get(0) + "/add");

            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("POST\n" +
                    RestAssured.baseURI + "/groups/" + group_names.get(0) + "/add\n" +
                    requestBody);
            TestVz.getResponseBody(response);
        }
    }

    @Test
    @DisplayName("Delete user from group")
    @Epic(value = "Virtualization")
    @Story("Groups")
    @Link(name = "doc link", url = "TO DO")
    @Feature("Delete user from group")
    @Description("Delete user from group")
    @Severity(SeverityLevel.MINOR)
    @Step ("Удаление пользователя сервера из группы.")
    @Order(4)
    public void deleteUserToGroupTest() {
            List<String> users_login;
            List<String> group_names = new ArrayList<>();
            Response response = null;
            String requestBody = "{}";
        try {
            users_login = TestVz.getUsersList();
            group_names = TestVz.geGroupsList();
            requestBody = "{\n" +
                    "    \"users\":\n" +
                    "    [\n" +
                    "        \"" + users_login.get(1) + "\"\n" +
                    "    ]\n" +
                    "}";
            response = RestAssured
                    .given()
                    .header("Authorization", TestVz.jwtToken)
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post("/groups/" + group_names.get(0) + "/remove");

            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("POST\n" +
                    RestAssured.baseURI + "/groups/" + group_names.get(0) + "/remove\n" +
                    requestBody);
            TestVz.getResponseBody(response);
        }
    }


    @Test
    @DisplayName("Delete group")
    @Epic(value = "Virtualization")
    @Story("Groups")
    @Link(name = "doc link", url = "TO DO")
    @Feature("Delete group")
    @Description("Delete group")
    @Severity(SeverityLevel.MINOR)
    @Step ("Удаление группы пользователей сервера.")
    @Order(5)
    public void deleteGroupTest() {
        List<String> group_names = new ArrayList<>();
        Response response = null;
        try {
            group_names = TestVz.geGroupsList();
            response = RestAssured
                    .given()
                    .header("Authorization", TestVz.jwtToken)
                    .contentType("application/json")
                    .when()
                    .delete("/groups/" + group_names.get(0));

            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("DELETE\n" +
                    RestAssured.baseURI + "/groups/" + group_names.get(0));
            TestVz.getResponseBody(response);
        }
    }




}
