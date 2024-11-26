import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Tag("Users")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestUsers {
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
    }


    @Test
    @DisplayName("Get users list")
    @Epic(value = "Virtualization")
    @Story("Users")
    @Link(name = "doc link", url = "TO DO")
    @Feature("Get users list")
    @Description("Get users list")
    @Severity(SeverityLevel.MINOR)
    @Step ("Получить список пользователей сервера.")
    @Order(1)
    public void getUsersListTest() {
            Response response = null;
        try {
            response = RestAssured
                    .given()
                    .header("Authorization", TestVz.jwtToken)
                    .contentType("application/json")
                    .when()
                    .get("/users");
            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("GET\n" +
                    RestAssured.baseURI + "/users");
            TestVz.getResponseBody(response);
        }
    }

    @Test
    @DisplayName("Create new user")
    @Epic(value = "Virtualization")
    @Story("Users")
    @Link(name = "doc link", url = "")
    @Feature("Create new user")
    @Description("Create new user")
    @Severity(SeverityLevel.CRITICAL)
    @Step ("Создание нового пользователя сервера.")
    @Order(2)
    public void createNewUserTest() {
            String name = getRandomString(10);
            String password = getRandomString(15);
            String requestBody = "{\n" +
                "    \"login\": \"user_"+ name + "\",\n" +
                "    \"password\":\"" + password + "\",\n" +
                "    \"email\":\"" + name + "@mail.ru\",\n" +
                "    \"name\":\"" + name + "\",\n" +
                "    \"description\": \" test user " + name + " creating from autotest\"\n" +
                "}";
            Response response = null;
        try {
            response = RestAssured
                    .given()
                    .header("Authorization", TestVz.jwtToken)
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post("/users");
            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("POST\n" +
                    RestAssured.baseURI + "/users\n" +
                    requestBody);
            TestVz.getResponseBody(response);
        }
    }

    @Test
    @DisplayName("Get user info")
    @Epic(value = "Virtualization")
    @Story("Users")
    @Link(name = "doc link", url = "TO DO")
    @Feature("Get user info")
    @Description("Get user info")
    @Severity(SeverityLevel.MINOR)
    @Step ("Получить подробную информацию о пользователе сервера.")
    @Order(3)
    public void getUserInfoTest() {
            List<String> users_login = new ArrayList<>();
            Response response = null;
        try {
            users_login = TestVz.getUsersList();
            response = RestAssured
                    .given()
                    .header("Authorization", TestVz.jwtToken)
                    .contentType("application/json")
                    .when()
                    .get("/users/" + users_login.get(0));

            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("GET\n" +
                    RestAssured.baseURI + "/users/" + users_login.get(0));
            TestVz.getResponseBody(response);
        }
    }


    @Test
    @DisplayName("Delete user")
    @Epic(value = "Virtualization")
    @Story("Users")
    @Link(name = "doc link", url = "TO DO")
    @Feature("Delete user from server")
    @Description("Delete user from server")
    @Severity(SeverityLevel.MINOR)
    @Step ("Удалить пользователя сервера.")
    @Order(4)
    public void deleteUserTest() {
            List<String> users_login = new ArrayList<>();
            Response response = null;
        try {
            users_login = TestVz.getUsersList();
            response = RestAssured
                    .given()
                    .header("Authorization", TestVz.jwtToken)
                    .contentType("application/json")
                    .when()
                    .delete("/users/" + users_login.get(1));

            response.then()
                    .assertThat()
                    .statusCode(200)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 200 OK");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            TestVz.getRequestBody("DELETE\n" +
                    RestAssured.baseURI + "/users/" + users_login.get(1));
            TestVz.getResponseBody(response);
        }
    }
}
