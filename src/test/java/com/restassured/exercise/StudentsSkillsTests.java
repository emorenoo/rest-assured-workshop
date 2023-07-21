package com.restassured.exercise;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import java.util.ArrayList;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class StudentsSkillsTests extends BaseClassAuth{

	public String newStudent(){

		JSONObject request = new JSONObject();

		request.put("name", "Miguel");
		request.put("lastname", "Parra");
		ArrayList<String> interests = new ArrayList<>();
		interests.add("Boxing");
		interests.add("Baseball");
		interests.add("Tennis");
		request.put("interests", interests);

		urlBase();
		Response response =
		given()
			.headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
			.contentType(ContentType.JSON)
			.body(request.toJSONString())
		.when()
			.post("/classes/Students");
		String jsonString = response.getBody().asString();
		String createdStudent = JsonPath.from(jsonString).getString("objectId");
		return createdStudent;
	}

	@Test
	public void testAddAbility() {

		JSONObject request = new JSONObject();
		String studentId = newStudent();

		request.put("studentId", studentId);
		request.put("skillId", "MGH19YBd3i");

		urlBase();
		given().filter(new AllureRestAssured())
			.headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
			.contentType(ContentType.JSON)
			.body(request.toJSONString())
		.when()
			.post("/classes/StudentSkills")
		.then()
			.statusCode(201)
			.body("$", hasKey("objectId"))
			.body("$", hasKey("createdAt"))
		.log().all();
		System.out.println(request.toJSONString());

		given().filter(new AllureRestAssured())
			.headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
		.when()
			.get("/classes/Students/"+ studentId)
		.then()
			.body("$", hasKey("skillNames"))
			.log().all();

		given().filter(new AllureRestAssured())
			.headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
		.when()
			.delete("/classes/Students/"+ studentId);
	}

	@Test
	public void testValidatedAbilities() {

		urlBase();
		given().filter(new AllureRestAssured())
			.headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
		.when()
			.get("/classes/Students/KeDSWbW3KC")
		.then()
			.statusCode(200)
		.log().all();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testAddAbilityRepeated() {

		JSONObject request = new JSONObject();

		request.put("studentId", "KeDSWbW3KC");
		request.put("skillId", "PeydSdIjxx");

		urlBase();
		given().filter(new AllureRestAssured())
			.headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
			.contentType(ContentType.JSON)
			.body(request.toJSONString())
		.when()
			.post("/classes/StudentSkills")
		.then()
			.statusCode(400)
			.body("error", equalTo("A duplicate value for a field with unique values was provided"))
		.log().all();
		System.out.println(request.toJSONString());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testValidatedWithoutToken() {

		JSONObject request = new JSONObject();

		request.put("studentId", "KeDSWbW3KC");
		request.put("skillId", "uz891Ex1g6");

		urlBase();
		given().filter(new AllureRestAssured())
			.headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"))
			.contentType(ContentType.JSON)
			.body(request.toJSONString())
		.when()
			.post("/classes/StudentSkills")
		.then()
			.statusCode(404)
			.body("error", equalTo("Permission denied, user needs to be authenticated."))
		.log().all();
		System.out.println(request.toJSONString());
	}
}