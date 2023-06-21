package com.restassured.exercise;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class StudentsCommentsTests extends BaseClassAuth{

    @Test
    public void testAddComments() {

        JSONObject request = new JSONObject();

        request.put("studentId", "9mAYjrEAyo");
		request.put("comment", "TestOneOneXER");

        urlBase();
        given()
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

        given()
            .headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
        .when()
            .get("/classes/Comments")
        .then()
            .body("results.comment", hasItem("TestOneOneXER"))
        .log().all();
    }

    @Test
    public void testValidatedComments() {

        urlBase();
        given()
            .headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
        .when()
            .get("/classes/Comments")
        .then()
            .statusCode(200)
        .log().all();
    }

    @Test
    public void testDeleteComments(){

        urlBase();
        given()
            .headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
        .when()
            .delete("/classes/Comments/7dMklmsKuP")
        .then()
            .statusCode(200)
        .log().all();

        given()
            .headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
        .when()
            .get("/classes/Comments")
        .then()
            .body("results.comment", not(hasItem("TestTwo")))
        .log().all();
    }

    @Test
    public void testAssociateComments(){
        JSONObject request = new JSONObject();

        request.put("studentId", "9mAYjrEAyo");
        request.put("comment", "TestOneYYyER");

        RestAssured.baseURI = "https://parseapi.back4app.com";
        given()
            .headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
            .contentType(ContentType.JSON)
            .body(request.toJSONString())
        .when()
            .post("/classes/Comments")
        .then()
            .statusCode(201)
        .log().all();
        System.out.println(request.toJSONString());

        given()
            .headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
        .when()
            .get("/classes/Students/9mAYjrEAyo")
        .then()
            .body("$", hasKey("comments"))
            .body("comments", hasItem("TestOneYYyER"))
        .log().all();
    }
}
