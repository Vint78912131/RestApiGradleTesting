import io.qameta.allure.Attachment;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestVz {

    public static String endpoint = "http://192.168.12.40:80/api";

    public static final String password = "1q2w3e";
    public static final String login = "root";
    public static Map<String,String> servers = new HashMap<>();

    public static String cookies = null;
    public static String jwtToken = null;


    public static JSONArray hosts;
    public static JSONArray vms;
    public static JSONArray snapshots;
    public static JSONArray backups;

    public static List<String> srv_uuid = new ArrayList<>();
    public static List<String> srv_ip = new ArrayList<>();
    public static List<String> vms_uuid = new ArrayList<>();
    public static List<String> vms_names = new ArrayList<>();
    public static List<String> snapshots_uuid = new ArrayList<>();

    public static List<String> backups_uuid = new ArrayList<>();
    public static void setCookies () {
        RestAssured.baseURI = TestVz.endpoint;
        JSONObject requestBody = new JSONObject()
                .put("LoginPass", password)
                .put("LoginUser", login);
        Response response = RestAssured
                .given()
                .contentType("application/json")
                .body(requestBody.toString())
                .when()
                .post("/login");

        try {
            response.then()
                    .assertThat()
                    .statusCode(201)
                    .contentType("application/json")
                    .statusLine("HTTP/1.1 201 Created");
            cookies = response.getDetailedCookies().toString();
            jwtToken = JsonPath.from(response.getBody().asString()).getString("payload.jwtToken");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    static void setVMInfo () {
        Response response = RestAssured
                .given()
                .header("Authorization", jwtToken)
                .contentType("application/json")
                .when()
                .get("/vms");
        try {
            JSONObject body = new JSONObject(response.getBody().asString());
            JSONObject payload = (JSONObject) body.get("payload");
            vms = payload.getJSONArray("vms");

            vms_uuid.clear();
            vms_names.clear();
            for (int i = 0; i < vms.length(); i++) {
                vms_uuid.add(vms.getJSONObject(i).get("uuid").toString());
                vms_names.add(vms.getJSONObject(i).get("title").toString());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
//            System.out.println("vms_uuids: " + vms_uuid);
//            System.out.println("vms_names: " + vms_names);
        }
    }

    static void setServersInfo () {
        Response response = RestAssured
                .given()
                .header("Authorization", jwtToken)
                .contentType("application/json")
                .when()
                .get("/nodes");
        try {
            JSONObject body = new JSONObject(response.getBody().asString());
            JSONObject payload = (JSONObject) body.get("payload");
            hosts = payload.getJSONArray("hosts");

            srv_uuid.clear();
            srv_ip.clear();
            for (int i = 0; i < hosts.length(); i++) {
                servers.put(hosts.getJSONObject(i).get("hostname").toString(),hosts.getJSONObject(i).get("ip_address").toString());
                srv_uuid.add(hosts.getJSONObject(i).get("uuid").toString());
                srv_ip.add(hosts.getJSONObject(i).get("ip_address").toString());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
//            response.getBody().prettyPrint();
//            TestVz.getResponseBody(response);
        }
    }

    public static List<String> getSnapshotVMList (String vm_uuid) {
        Response response = RestAssured
                .given()
                .header("Authorization", TestVz.jwtToken)
                .contentType("application/json")
                .when()
                .get("/vm/" + vm_uuid + "/snapshot");
        try {
            JSONObject body = new JSONObject(response.getBody().asString());
            JSONObject payload = (JSONObject) body.get("payload");
            snapshots = payload.getJSONArray("snapshots");

            snapshots_uuid.clear();
            for (int i = 0; i < snapshots.length(); i++) {
                snapshots_uuid.add(snapshots.getJSONObject(i).get("uuid").toString());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("getSnapshotVMList is done");
            return snapshots_uuid;
        }
    }

    public static List<String> getBackupVMList (String vm_uuid) {
        Response response = RestAssured
                .given()
                .header("Authorization", TestVz.jwtToken)
                .contentType("application/json")
                .param("vm_uuid", vm_uuid)
                .when()
                .get("/vm/backup/list");
        try {
            JSONObject body = new JSONObject(response.getBody().asString());
            System.out.println(body);
            JSONObject payload = (JSONObject) body.get("payload");
            System.out.println(payload);
            backups = payload.getJSONArray("backups");

            backups_uuid.clear();
            for (int i = 0; i < backups.length(); i++) {
                backups_uuid.add(backups.getJSONObject(i).get("Backup_ID").toString());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("getBackupsVMList is done");
            return backups_uuid;
        }
    }


    @Attachment(value = "Request", type = "application/json", fileExtension = ".txt")
    public static String getRequestBody(String request) {
        return request;
    }

    @Attachment(value = "Response", type = "application/json", fileExtension = ".txt")
    public static String getResponseBody(Response response) {
        return response.getBody().prettyPrint();
    }
}