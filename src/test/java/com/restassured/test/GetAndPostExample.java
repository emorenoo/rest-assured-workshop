package com.restassured.test;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import io.restassured.*;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class GetAndPostExample {
	
	@Test
	public void testGet() {
		RestAssured.baseURI = "https://reqres.in/api";
		
		when()
			.get("/users?page=2")
		.then()
			.statusCode(200)
			.body("data.size()", is(6))
			.body("data.first_name", hasItems("George", "Rachel"))
		.log().all();
		
	}
	
	
	@Test
	public void PokeGet() {
		RestAssured.baseURI = "https://pokeapi.co/api/v2";
		
		when()
			.get("/pokemon/ditto")
		.then()
			.statusCode(200)
			//.body("data.size()", is(6))
			.body("abilities.ability.name", hasItem("limber"))
		.log().all();
		
	}

	@Test
	public void testPost() {
		
		JSONObject request = new JSONObject();
		
		request.put("name", "Ernesto Perez");
		request.put("job", "QA Automation");

		RestAssured.baseURI =  "https://reqres.in/api";
		
		given()
			.header("Content-Type", "application/json")
			.contentType(ContentType.JSON)
			.body(request.toJSONString())
		.when()
			.post("/users")
		.then()
			.statusCode(201)
			.log().all();
		System.out.println(request.toJSONString());
	}

}

