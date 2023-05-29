package com.restassured.exercise;

import org.testng.annotations.BeforeClass;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

import java.util.Base64;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class BaseClassAuth {
	 
	
	@BeforeClass
	public void setup() {
		
		RestAssured.authentication = RestAssured.preemptive().basic("postman", "password");
		
		RestAssured.baseURI = "https://parseapi.back4app.com";
	}

    @Test
    public String generatedToken() {
        
        JSONObject request = new JSONObject();

        request.put("username", System.getenv("username"));
        request.put("password", System.getenv("password"));

        RestAssured.baseURI = "https://parseapi.back4app.com";
        Response response =
        given()
            .headers("X-Parse-Application-Id",System.getenv("X-Parse-Application-Id"),"X-Parse-REST-API-Key",System.getenv("X-Parse-REST-API-Key"),"X-Parse-Revocable-Session","1")
            .contentType(ContentType.JSON)
            //.log().all()
            .body(request.toJSONString())
        .when()
           .post("/login");

		String jsonString = response.getBody().asString();
		String tokenGenerated = JsonPath.from(jsonString).getString("sessionToken");
		//System.out.println("El Token generado es: "+tokenGenerated);
            return tokenGenerated;
        
    }
    
   
}