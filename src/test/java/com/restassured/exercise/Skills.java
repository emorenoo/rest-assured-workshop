package com.restassured.exercise;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

import java.util.List;

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


public class Skills {

	//private JsonPath jsonPathEvaluator;

	@Test

	public void listSkills() {

		RestAssured.baseURI = "https://parseapi.back4app.com";
		given()
			.headers("X-Parse-Application-Id",System.getenv("X-Parse-Application-Id"),"X-Parse-REST-API-Key",System.getenv("X-Parse-REST-API-Key"))
			.get("/classes/Skills")
		.then()
			.statusCode(200)
			.body("size()", greaterThan(0))
		.log().all();

		/*JsonPath jsonPathEvaluator = response.jsonPath();
		String jsonString = response.getBody().asString();
		String result = JsonPath.from(jsonString).getString("size");
		System.out.println(jsonString);*/

	}		
	
	@Test
	public void skillCode() {

		
		RestAssured.baseURI = "https://parseapi.back4app.com";
		given()
			.headers("X-Parse-Application-Id",System.getenv("X-Parse-Application-Id"),"X-Parse-REST-API-Key",System.getenv("X-Parse-REST-API-Key"))
		.when()
			.get("/classes/Skills/SfBT3mThiu")
		.then()
			.statusCode(200)
			.body("name", equalTo("RestAssured"))
			.body("description", equalTo("REST Assured is an open-source, Java-based library to test REST web services."))
			.body("objectId", equalTo("SfBT3mThiu"))
		.log().body();


	}	

	@Test
	public void deleteSkill() {

		
		RestAssured.baseURI = "https://parseapi.back4app.com";
		given()
			.headers("X-Parse-Application-Id",System.getenv("X-Parse-Application-Id"),"X-Parse-REST-API-Key",System.getenv("X-Parse-REST-API-Key"))
			//.contentType(ContentType.JSON)
		.when()
			.delete("/classes/Skills/SfBT3mThiu")
		.then()
			.statusCode(400)
			.body("error", equalTo("Permission denied for action delete on class Skills."))
		.log().body();


	}	
	
	@Test
	public void listSkillsNo() {

		
		RestAssured.baseURI = "https://parseapi.back4app.com";
		given()
			//.headers("X-Parse-Application-Id",System.getenv("X-Parse-Application-Id"),"X-Parse-REST-API-Key",System.getenv("X-Parse-REST-API-Key"))
			//.contentType(ContentType.JSON)
		.when()
			.get("/classes/Skills")
		.then()
			.statusCode(401)
			.body("error", equalTo("unauthorized"))
		.log().body();


	}	
}
 