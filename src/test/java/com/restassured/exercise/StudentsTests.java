package com.restassured.exercise;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;
import java.util.ArrayList;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class StudentsTests extends BaseClassAuth{

	@Test
	public void testStudentCreated() {
	    
		JSONObject request = new JSONObject();

		request.put("name", "Carlos");
		request.put("lastname", "Parra");
		ArrayList<String> interests = new ArrayList<>();
		interests.add("Boxing");
		interests.add("Baseball");
		interests.add("Tennis");
		request.put("interests", interests);
		
		urlBase();
		Response response =
		given().filter(new AllureRestAssured())
			.headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
			.contentType(ContentType.JSON)
			.body(request.toJSONString())
		.when()
			.post("/classes/Students");
		String jsonString = response.getBody().asString();
		assertTrue(jsonString.contains("objectId"));
		assertTrue(jsonString.contains("createdAt"));
		String createdStudent = JsonPath.from(jsonString).getString("objectId");
		System.out.println("New student created: "+request.toJSONString());

		given().filter(new AllureRestAssured())
			.headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
		.when()
			.get("/classes/Students")
		.then()
			.statusCode(200)
			.body("results.name",hasItem("Carlos"))
			.body("results.lastname",hasItem("Parra"))
		.log().all();

		given().filter(new AllureRestAssured())
			.headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
		.when()
			.delete("/classes/Students/"+ createdStudent);
	}	
	
	@Test
	public void testStudentValidate() {
		urlBase();
		given().filter(new AllureRestAssured())
			.headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
		.when()
			.get("/classes/Students")
		.then()
			.statusCode(200)
			.log().all();
		}
	
	@Test
	public void testValidateCreatedStudent() {

		JSONObject request = new JSONObject();

		request.put("name", "Joe");
		request.put("lastname", "Perez");
		ArrayList<String> interests = new ArrayList<>();
		interests.add("Boxing");
		interests.add("Baseball");
		interests.add("Tennis");
		request.put("interests", interests);
		
		urlBase();
		given().filter(new AllureRestAssured())
			.headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
			.contentType(ContentType.JSON)
			.body(request.toJSONString())
		.when()
			.post("/classes/Students")
		.then()
			.statusCode(400)
			.body("error", equalTo("A duplicate value for a field with unique values was provided"))
		.log().body();
		System.out.println(request.toJSONString());
	}
	
	@Test
	public void testValidateSpecialCharacters() {
	
		JSONObject request = new JSONObject();

		request.put("name", "Ana&*");
		request.put("lastname", "Perez");
		ArrayList<String> interests = new ArrayList<>();
		interests.add("Boxing");
		interests.add("Baseball");
		interests.add("Tennis");
		request.put("interests", interests);
		
		urlBase();
		given().filter(new AllureRestAssured())
			.headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
			.contentType(ContentType.JSON)
			.body(request.toJSONString())
		.when()
			.post("/classes/Students")
		.then()
			.statusCode(400)
			.body("code", equalTo("Invalid characters on field \"name\""))
		.log().body();
		System.out.println(request.toJSONString());
	}

	@Test
	public void testUpdatedStudent() {
	
		JSONObject request = new JSONObject();

		request.put("name", "Andres");
		request.put("lastname", "Peña");
		ArrayList<String> interests = new ArrayList<>();
		interests.add("Boxing");
		interests.add("Baseball");
		interests.add("Tennis");
		request.put("interests", interests);

		RestAssured.baseURI = "https://parseapi.back4app.com";
		given().filter(new AllureRestAssured())
			.headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
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
	public void testValidateWithoutToken() {

		JSONObject request = new JSONObject();

		request.put("name", "Joe");
		request.put("lastname", "Perez");
		ArrayList<String> interests = new ArrayList<>();
		interests.add("Boxing");
		interests.add("Baseball");
		interests.add("Tennis");
		request.put("interests", interests);

		urlBase();
		given().filter(new AllureRestAssured())
			.headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"))
			.contentType(ContentType.JSON)
			.body(request.toJSONString())
		.when()
			.post("/classes/Students")
		.then()
			.statusCode(404)
			.body("error", equalTo("Permission denied, user needs to be authenticated."))
		.log().all();
		System.out.println(request.toJSONString());
	}
}