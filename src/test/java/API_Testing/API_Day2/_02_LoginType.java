package API_Testing.API_Day2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class _02_LoginType {
    /*
    Log in with Full URL with query params
    and verify status code and Content-type is equal to JSON
    */

    @Test
    public void testUsingQueryParams(){
        RestAssured.given()
                .when()
                .post("https://api.octoperf.com/public/users/login?password=Ptt12345&username=spicielover@gmail.com")
                .then()
                .assertThat().statusCode(200)
                .and()
                .assertThat().contentType(ContentType.JSON);
    }
    /*
    Log in using Map to verify Content Type
    Mao=> Stores Key and Value -> Hashmap implement Map, We can store different data Type of Object
     */
    @Test
    public void LogInWithMap(){
        RestAssured.baseURI="https://api.octoperf.com";
        String path = "/public/users/login";
        // WRITE A MAP
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", "spicielover@gmail.com");
        map.put("password", "Ptt12345");

        RestAssured.given()
                .queryParams(map)
                .when()
                .post(path)
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .and()
                .assertThat()
                .statusCode(200);
    }
    //Using query Param
    @Test
    public void LogInWithQueryParam(){
        RestAssured.baseURI="https://api.octoperf.com";
        String path = "/public/users/login";

        RestAssured.given()
                .queryParam("username", "spicielover@gmail.com")
                .queryParam("password", "Ptt12345")
                .when()
                .post(path)
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .and()
                .assertThat()
                .statusCode(200);
    }
}
