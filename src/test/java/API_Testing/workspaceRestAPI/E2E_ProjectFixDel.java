package API_Testing.workspaceRestAPI;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class E2E_ProjectFixDel {
    public String path;
    String memberOf = "/workspaces/member-of";
    Map<String, String> variables;
    String Id;
    String user_Id;
    Response response;
    String projectID;

    // What is a TestNG annotation that allows us to run a Test Before each Test
    @BeforeTest
    public String setupLogInAndToken(){
        RestAssured.baseURI = "https://api.octoperf.com";
        path = "/public/users/login";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("password", "Ptt12345");
        map.put("username", "spicielover@gmail.com");

        return RestAssured.given()
                .queryParams(map)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .post(path)// send request to end point
                .then()
                .statusCode(SC_OK) // Verify status code = 200 or OK
                .extract() // Method that extracts response JSON data
                .body() // Body Extracted as JSON format
                .jsonPath() // Navigate using jsonPath
                .get("token"); // get value for key Token
    }

    // Write a test for API endpoint member-of
    @Test
    public void memberOf(){
        response = RestAssured.given()
                .header("Authorization", setupLogInAndToken())
                .when()
                .get(memberOf)
                .then()
                .log().all()
                .extract().response();

        // Verify status code
        Assert.assertEquals(SC_OK, response.statusCode());
        Assert.assertEquals("Default", response.jsonPath().getString("name[0]"));
        // TODO add tests for ID, userID, Description

        // Save the id so it can be used in other requests
        Id = response.jsonPath().get("id[0]");
        // Save the userId so it can be used in other requests
        user_Id = response.jsonPath().get("userId[0]");

        // What can we use to Store data as Key and Value
        variables = new HashMap<String, String>();
        variables.put("id",Id);
        variables.put("userID", user_Id);

//        ??????????????????????????? ?????????
//        for (Map.Entry<String,String> entry : variables.entrySet())
//            System.out.println("Key = " + entry.getKey() +
//                    ", Value = " + entry.getValue());

    }

    @Test(dependsOnMethods = {"memberOf"})
    public void createProject(){

        String requestBody = "{\"id\":\"\",\"created\":\"2021-03-11T06:15:20.845Z\",\"lastModified\":\"2021-03-11T06:15:20.845Z\",\"userId\":\"" + variables.get("userID") + "\",\"workspaceId\":\"" + variables.get("id") + "\",\"name\":\"testing22\",\"description\":\"testing\",\"type\":\"DESIGN\",\"tags\":[]}";

        response = RestAssured.given()
                .headers("Content-type", "application/json")
                .header("Authorization", setupLogInAndToken())
                .and()
                .body(requestBody)
                .when()
                .post("/design/projects/")
                .then()
                .extract()
                .response();

        System.out.println(response.prettyPrint());

        // TASK Create TestNG Assertions Name, id, userId, workspaceID
        Assert.assertEquals("testing22", response.jsonPath().getString("name"));
        // TASK Assert Status code and Content-type

        // Using hamcrest Matchers validation
        assertThat(response.jsonPath().getString("name"), is("testing22"));

        // Store id in a variable for future use
        projectID = response.jsonPath().get("id");
        System.out.println("New id created when creating a project "+ projectID);

    }
    @Test(dependsOnMethods = {"memberOf","createProject"})
    public void updateProject(){
        String requestBody1 = "{\"created\":1615443320845,\"description\":\"TLAUpate\",\"id\":\"" + projectID + "\",\"lastModified\":1629860121757,\"name\":\"tlaAccounting firm\",\"tags\":[],\"type\":\"DESIGN\",\"userId\":\"" + variables.get("userID") + "\",\"workspaceId\":\"" + variables.get("id") + "\"}";

        response = RestAssured.given()
                .headers("Content-type", "application/json")
                .header("Authorization", setupLogInAndToken())
                .and()
                .body(requestBody1)
                .when()
                .put("/design/projects/"+projectID)
                .then()
                .extract()
                .response();
        System.out.println(response.prettyPeek());

        // TODO Homework add Assertions for id, name, type, userId, workspaceId, Status code, Contenty Type
    }

    @Test(dependsOnMethods = {"memberOf", "createProject", "updateProject"})
    public void deleteProject(){
        response = RestAssured.given()
                .header("Authorization", setupLogInAndToken())
                .when()
                .delete("/design/projects/"+projectID)
                .then()
                .log().all()
                .extract()
                .response();

        // TODO Validate Status code
        assertThat(response.statusCode(),is(204));
    }
}
