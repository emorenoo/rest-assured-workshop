package com.restassured.exercise;

import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;


public class RestAssureAuthTest extends BaseClassAuth{

	@Test(priority = 0, description="Valid Autentication Scenario with valid username and password.")
	@Severity(SeverityLevel.BLOCKER)
	@Description("Test Description: Login test with valid username and password.")
	@Story("Get autentication token")
	@Step("Petition get to autentication")
	public void test1() {

			RestAssured.given().filter(new AllureRestAssured())
				.get()
			.then()
				.statusCode(200)
				.body("authenticated", equalTo(true));
	}
}