package API_Testing.API_Day1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

public class _01_introRestAssured {
    // Rest Assured is a java library that is used to perform API tests
// It uses BDD Style such as given, when, then, and, etc.
// Rest Assured has methods to fetch data from the response body. When making a request.
// Rest Assured works with HTTP requests: CRUD (Create, Read, Update, Delete) (POST, GET, PUT, DELETE)
// As testers we mostly use 2 requests GET, POST among DELETE and PUT.

// NOTE: As testers we mostly work with JSON Payloads (BODY), we also experience XML files, text, pdf etc.

// Some of the most used methods in RestAssured are:
//  When making a request:
//      given() ---> used to prepare the request
//      when() ---> used to send the request
//      then() ---> used to verify the request
//  When we verify the response:
//      prettyPeek() ---> used to print the response headers, urls etc. in pretty format in the Console
//      prettyPrint() ---> used to print the response in pretty format
//      log() ---> logs(prints) all the response
//      asString() ---> allows us to print in String format
//      contentType() ---> used to verify the content type from the response body. When use POST request
//      accept() ---> used to verify body response from the header when making a GET request

    //      baseURI ---> used to save the base URL for all the resources
    public static String baseURI = "https://api.octoperf.com";

    //  When we make a request we only provide the path(endpoint) to a specific baseURI
    private String path = "public/users/login";

// FULL URL = https://api.octoperf.com/public/users/login

// What is an endpoint?
// An endpoint is a unique URL that represents and object or collections of Objects

// Example: http://www.google.com/search?source=JavaBook
//                  baseURI       Endpoint  ? parameter

// Base url = https://api.octoperf.com
// Full url = https://api.octoperf.com/public/users/login
// Full url with Query params =
//          https://api.octoperf.com/public/users/login?password=test12&username=tla.jiraone@gmail.com


// Task: Make a HTTP: POST request with given
// full url with query params and print the response

    @Test
    public void printResponse(){
        RestAssured.given()
                .when()
                .post("https://api.octoperf.com/public/users/login?password=Ptt12345&username=spicielover@gmail.com")
                .prettyPeek(); // Prints the whole response body and headers
    }

    @Test
    public void printResponsePrettyPrint(){
        RestAssured.given()
                .when()
                .post("https://api.octoperf.com/public/users/login?password=Ptt12345&username=spicielover@gmail.com")
                .prettyPeek(); // Prints the whole response body and headers
    }

    @Test
    public void verifyStatusCode(){
        RestAssured.given()
                .when()
                .post("https://api.octoperf.com/public/users/login?password=Ptt12345&username=spicielover@gmail.com")
                .then()
                .assertThat()
                .statusCode(200); // if 201 will be different with print
        System.out.println("Test verified ,status code is 200 ok"); // Optional

    }

    //Perform a Post request to log in API endpoint
    //Verify Content Type = JSON

    @Test
    public void verifyContentType(){
        RestAssured.given()
                .when()
                .post("https://api.octoperf.com/public/users/login?password=Ptt12345&username=spicielover@gmail.com")
                .then()
                .assertThat()
                .contentType(ContentType.JSON); // Try to put XML fail will tell what is it
        System.out.println("Test verified that content type = JSON");
    }

}
