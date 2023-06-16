package com.restassured.exercise;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;

public class StudentsSkillsTests extends BaseClassAuth{

	@SuppressWarnings("unchecked")
	@Test
	public void testAddAbility() {

		JSONObject request = new JSONObject();

		request.put("studentId", "P3bisg6e2n");
		request.put("skillId", "Z3NyhPqOQT");

		urlBase();
		given()
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

		given()
			.headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
		.when()
			.get("/classes/Students/P3bisg6e2n")
		.then()
			.body("$", hasKey("skillNames"))
			.log().all();
	}

	@Test
	public void testValidatedAbilities() {

		urlBase();
		given()
			.headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"),"X-Parse-Session-Token",generatedToken())
		.when()
			.get("/classes/Students/P3bisg6e2n")
		.then()
			.statusCode(200)
		.log().all();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testAddAbilityRepeated() {

		JSONObject request = new JSONObject();

		request.put("studentId", "P3bisg6e2n");
		request.put("skillId", "PeydSdIjxx");

		urlBase();
		given()
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

		request.put("studentId", "P3bisg6e2n");
		request.put("skillId", "uz891Ex1g6");

		urlBase();
		given()
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