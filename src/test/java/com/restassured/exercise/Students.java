package com.restassured.exercise;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import  io.restassured.*;

import org.json.simple.JSONObject;
import org.junit.runner.Request;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;


public class Students extends BaseClassAuth{


	@Test

	public void studentCreated() {
	    
		JSONObject request = new JSONObject();
		
		request.put("name", "Pepito");
		request.put("lastname", "Guzman");
		ArrayList<String> interests = new ArrayList<String>();
		interests.add("Boxing");
		interests.add("Baseball");
		interests.add("Tennis");
		request.put("interests", interests);
		
		RestAssured.baseURI = "https://parseapi.back4app.com";
		given()
			.headers("X-Parse-Application-Id",System.getenv("X-Parse-Application-Id"),"X-Parse-REST-API-Key",System.getenv("X-Parse-REST-API-Key"),"X-Parse-Session-Token",generatedToken())
			.contentType(ContentType.JSON)
			.body(request.toJSONString())
		.when()
			.post("/classes/Students")
		.then()
			.statusCode(201)
			.body("$", hasKey("objectId"))
			.body("$", hasKey("createdAt"))
			//.body("$", not(hasKey("age")))
			//.body("results.name", hasItems("Joe"))
		.log().all();
		System.out.println(request.toJSONString());
	
		Response response = 
		given()
			.headers("X-Parse-Application-Id",System.getenv("X-Parse-Application-Id"),"X-Parse-REST-API-Key",System.getenv("X-Parse-REST-API-Key"),"X-Parse-Session-Token",generatedToken())		.when()
			.get("/classes/Students");
		String jsonString = response.getBody().asString();
		String json = JsonPath.from(jsonString).prettyPrint();

	}	
	
	@Test

	public void studentValidated() {
		RestAssured.baseURI = "https://parseapi.back4app.com";
		given()
			.headers("X-Parse-Application-Id",System.getenv("X-Parse-Application-Id"),"X-Parse-REST-API-Key",System.getenv("X-Parse-REST-API-Key"),"X-Parse-Session-Token",generatedToken())
		.when()
			.get("/classes/Students")
		.then()
			//.statusCode(200)
			//.body("results.name", hasItems("Joe"))
		.log().all();


	}	
	
	@Test
	
	public void validatedCreatedStudent() {

		JSONObject request = new JSONObject();
		
		request.put("name", "Joe");
		request.put("lastname", "Perez");
		ArrayList<String> interests = new ArrayList<String>();
		interests.add("Boxing");
		interests.add("Baseball");
		interests.add("Tennis");
		request.put("interests", interests);
		
		RestAssured.baseURI = "https://parseapi.back4app.com";
		given()
			.headers("X-Parse-Application-Id",System.getenv("X-Parse-Application-Id"),"X-Parse-REST-API-Key",System.getenv("X-Parse-REST-API-Key"),"X-Parse-Session-Token",generatedToken())
			.contentType(ContentType.JSON)
			.body(request.toJSONString())
		.when()
			.post("/classes/Students") //Cambiar a POST
		.then()
			.statusCode(400)
			.body("error", equalTo("A duplicate value for a field with unique values was provided"))
		.log().body();
	System.out.println(request.toJSONString());
		
	}
	
	@Test
	
	public void validatedSpecialCharacters() {
	
		JSONObject request = new JSONObject();
		
		request.put("name", "Ana&");
		request.put("lastname", "Perez");
		ArrayList<String> interests = new ArrayList<String>();
		interests.add("Boxing");
		interests.add("Baseball");
		interests.add("Tennis");
		request.put("interests", interests);
		
		RestAssured.baseURI = "https://parseapi.back4app.com";
		given()
			.headers("X-Parse-Application-Id",System.getenv("X-Parse-Application-Id"),"X-Parse-REST-API-Key",System.getenv("X-Parse-REST-API-Key"),"X-Parse-Session-Token",generatedToken())
			.contentType(ContentType.JSON)
			.body(request.toJSONString())
		.when()
			.post("/classes/Students") //Cambiar a POST
		.then()
			.statusCode(400)
			.body("code", equalTo("Invalid characters on field \"name\""))
		.log().body();
	System.out.println(request.toJSONString());
		
	}
		
		

	@Test
	
	public void updatedStudent() {
	
			JSONObject request = new JSONObject();
			
			request.put("name", "Andres");
			request.put("lastname", "Peña");
			ArrayList<String> interests = new ArrayList<String>();
			interests.add("Boxing");
			interests.add("Baseball");
			interests.add("Tennis");
			request.put("interests", interests);
			
			RestAssured.baseURI = "https://parseapi.back4app.com";
			given()
				.headers("X-Parse-Application-Id",System.getenv("X-Parse-Application-Id"),"X-Parse-REST-API-Key",System.getenv("X-Parse-REST-API-Key"),"X-Parse-Session-Token",generatedToken())
				.contentType(ContentType.JSON)
				.body(request.toJSONString())
			.when()
				.put("/classes/Students/cjFz5DlW9G") 
			.then()
				.statusCode(200)
				.body("$", hasKey("updatedAt"))
			.log().all();
		System.out.println(request.toJSONString());

		
		
	}
	
	@Test
	
	public void validatedWithoutToken() {
		
	    
			JSONObject request = new JSONObject();
			
			request.put("name", "Joe");
			request.put("lastname", "Perez");
			ArrayList<String> interests = new ArrayList<String>();
			interests.add("Boxing");
			interests.add("Baseball");
			interests.add("Tennis");
			request.put("interests", interests);
			
			RestAssured.baseURI = "https://parseapi.back4app.com";
			given()
				.headers("X-Parse-Application-Id",System.getenv("X-Parse-Application-Id"),"X-Parse-REST-API-Key",System.getenv("X-Parse-REST-API-Key"))
				.contentType(ContentType.JSON)
				.body(request.toJSONString())
			.when()
				.post("/classes/Students") //Cambiar a POST
			.then()
				.statusCode(404)
				.body("error", equalTo("Permission denied, user needs to be authenticated."))
			.log().all();
		System.out.println(request.toJSONString());


		}	
}