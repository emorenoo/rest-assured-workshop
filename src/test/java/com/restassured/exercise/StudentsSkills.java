package com.restassured.exercise;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
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


public class StudentsSkills extends BaseClassAuth{
	
	
	@Test
	
	public void AddAbility() {
		
		JSONObject request = new JSONObject();
		
		request.put("objectId", "KeDSWbW3KC");
		request.put("skill", "Functional testing");
		/*ArrayList<String> interests = new ArrayList<String>();
		interests.add("Boxing");
		interests.add("Baseball");
		interests.add("Tennis");
		request.put("interests", interests);*/
		
		RestAssured.baseURI = "https://parseapi.back4app.com";
		given()
			.headers("X-Parse-Application-Id","33VkH3xL6TfxON93Sduy9mPmK1grwO2f98dT3Ami","X-Parse-REST-API-Key","BzTMNFv954AlAjdiOQzyJnP5aexiorut8cVqf5Ha","X-Parse-Session-Token","r:4cc460c42a8672b98b1a346c2ca2ad1c")//.accept(ContentType.JSON);
			.contentType(ContentType.JSON)
			.body(request.toJSONString())
		.when()
			.post("/classes/StudentSkills")
		.then()
			.statusCode(201)
			//.body("results.name", hasItems("Joe"))
		.log().all();
	System.out.println(request.toJSONString());
	
		/*Response response = 
			given()
				.headers("X-Parse-Application-Id","33VkH3xL6TfxON93Sduy9mPmK1grwO2f98dT3Ami","X-Parse-REST-API-Key","BzTMNFv954AlAjdiOQzyJnP5aexiorut8cVqf5Ha","X-Parse-Session-Token","r:4cc460c42a8672b98b1a346c2ca2ad1c")//.accept(ContentType.JSON);
			.when()
				.get("/classes/Students");
				String jsonString = response.getBody().asString();
				String json = JsonPath.from(jsonString).prettyPrint();*/
		
	}
	
	@Test
	
	public void ValidatedAbilities() {
		
		RestAssured.baseURI = "https://parseapi.back4app.com";
		given()
			.headers("X-Parse-Application-Id","33VkH3xL6TfxON93Sduy9mPmK1grwO2f98dT3Ami","X-Parse-REST-API-Key","BzTMNFv954AlAjdiOQzyJnP5aexiorut8cVqf5Ha","X-Parse-Session-Token","r:eb538f98dfb4c803b7c705e58c54a0d0")//.accept(ContentType.JSON);
		.when()
			.get("/classes/StudentSkills/D2q8Hs4m8d") //Cambiar a POST
		.then()
			//.statusCode(200)
			//.body("results.name", hasItems("Joe"))
		.log().all();
	}
	
	public void AddAbilityRepeated() {
		
	}
	
	public void ValidatedWithoutToken() {
		
	    
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
			.headers("X-Parse-Application-Id","33VkH3xL6TfxON93Sduy9mPmK1grwO2f98dT3Ami","X-Parse-REST-API-Key","BzTMNFv954AlAjdiOQzyJnP5aexiorut8cVqf5Ha")//.accept(ContentType.JSON);
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