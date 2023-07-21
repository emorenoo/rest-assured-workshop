package com.restassured.exercise;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class StudentsCommentsTests extends BaseClassAuth{

    @Test
    public void testAddComments() {

        JSONObject request = new JSONObject();
        String comment = "lo que sea";

        request.put("studentId", "KeDSWbW3KC");
		request.put("comment", comment);

        urlBase();
        given().filter(new AllureRestAssured())
            .headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
            .contentType(ContentType.JSON)
            .body(request.toJSONString())
        .when()
            .post("/classes/Comments")
        .then()
            .statusCode(201)
            .body("$", hasKey("objectId"))
            .body("$", hasKey("createdAt"))
        .log().all();
        System.out.println(request.toJSONString());

        given().filter(new AllureRestAssured())
            .headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
        .when()
            .get("/classes/Comments")
        .then()
            .body("results.comment", hasItem(comment))
        .log().all();
    }

    @Test
    public void testValidatedComments() {

        urlBase();
        given().filter(new AllureRestAssured())
            .headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
        .when()
            .get("/classes/Comments")
        .then()
            .statusCode(200)
        .log().all();
    }

    @Test
    public void testDeleteComments(){
        JSONObject request = new JSONObject();
        String comment = "tests delete";

        request.put("studentId", "KeDSWbW3KC");
        request.put("comment", comment);

        urlBase();
        Response response =
        given().filter(new AllureRestAssured())
            .headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
            .contentType(ContentType.JSON)
            .body(request.toJSONString())
        .when()
            .post("/classes/Comments");
        String jsonString = response.getBody().asString();
        String idcomment = JsonPath.from(jsonString).getString("objectId");

        given().filter(new AllureRestAssured())
            .headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
        .when()
            .delete("/classes/Comments/"+idcomment)
        .then()
            .statusCode(200)
        .log().all();

        given().filter(new AllureRestAssured())
            .headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
        .when()
            .get("/classes/Comments")
        .then()
            .body("results.comment", not(hasItem(comment)))
        .log().all();
    }

    @Test
    public void testAssociateComments(){
        JSONObject request = new JSONObject();
        String comment = "lo que sea";

        request.put("studentId", "KeDSWbW3KC");
        request.put("comment", comment);

        urlBase();
        given().filter(new AllureRestAssured())
            .headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
            .contentType(ContentType.JSON)
            .body(request.toJSONString())
        .when()
            .post("/classes/Comments")
        .then()
            .statusCode(201)
        .log().all();
        System.out.println(request.toJSONString());

        given().filter(new AllureRestAssured())
            .headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
        .when()
            .get("/classes/Students/KeDSWbW3KC")
        .then()
            .body("$", hasKey("comments"))
            .body("comments", hasItem(comment))
        .log().all();
    }
}
