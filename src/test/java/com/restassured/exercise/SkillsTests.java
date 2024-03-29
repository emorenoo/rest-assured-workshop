package com.restassured.exercise;

import io.qameta.allure.restassured.AllureRestAssured;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class SkillsTests extends BaseClassAuth{

	@Test
	public void testListSkills() {

		urlBase();
		given().filter(new AllureRestAssured())
			.headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"))
			.get("/classes/Skills")
		.then()
			.statusCode(200)
			.body("size()", greaterThan(0))
		.log().all();

	}

	@Test
	public void testSkillCode() {

		urlBase();
		given().filter(new AllureRestAssured())
			.headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"))
		.when()
			.get("/classes/Skills/SfBT3mThiu")
		.then()
			.statusCode(200)
			.body("name", equalTo("RestAssured"))
			.body("description", equalTo("REST Assured is an open-source, Java-based library to test REST web services."))
			.body("objectId", equalTo("SfBT3mThiu"))
		.log().all();
	}	

	@Test
	public void testDeleteSkill() {

		urlBase();
		given().filter(new AllureRestAssured())
			.headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"))
		.when()
			.delete("/classes/Skills/SfBT3mThiu")
		.then()
			.statusCode(400)
			.body("error", equalTo("Permission denied for action delete on class Skills."))
		.log().body();
	}	
	
	@Test
	public void testListSkillsNo() {

		urlBase();
		given().filter(new AllureRestAssured())
			//.headers("X-Parse-Application-Id",System.getenv("Id"),"X-Parse-REST-API-Key",System.getenv("Key"))
		.when()
			.get("/classes/Skills")
		.then()
			.statusCode(401)
			.body("error", equalTo("unauthorized"))
		.log().body();
	}	
}
 
