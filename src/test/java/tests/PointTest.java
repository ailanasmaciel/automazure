package tests;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import hooks.Hooks;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PointTest extends Hooks {
	String token = "";
	
	

	public PointTest(String token) {
		this.token = token;
	}

	public String getTestPointByTestCase(String testCaseId) {
		String body = "{\"PointsFilter\": {\"TestcaseIds\": [" + testCaseId + "]}}";
		RequestSpecification spec = new RequestSpecBuilder().setContentType("application/json").build();
		Response resp = given()
				.spec(spec)
				.header("Authorization", "Basic " + token)
				.body(body)
				.post("/ab-inbev/Aurora_Program/_apis/test/points?api-version=7.0");

		assertEquals(resp.getStatusCode(), 200);
		JsonPath responseBody = resp.getBody().jsonPath();
		return responseBody.getString("points.id[0]");
	}

	public void updateTestCaseResult(String testCaseStatus, String planId, String suiteId, String pointId) {
		String body = "{\"outcome\": \"" + testCaseStatus + "\"}";
		
		RequestSpecification spec = new RequestSpecBuilder().setContentType("application/json").build();

		Response resp = given()
				        .spec(spec)
				        .header("Authorization", "Basic " + token)
				        .body(body)
				        .patch("/ab-inbev/Aurora_Program/_apis/test/Plans/" + planId + "/Suites/" + suiteId + "/points/"
						+ pointId + "?api-version=7.0");
		System.out.println("***********************************************************");
		System.out.println("***********************************************************");
		System.out.println("****************** SUCCESSFULLY EXECUTED ******************");
		System.out.println("***********************************************************");
		System.out.println("***********************************************************");
		
		assertEquals(resp.getStatusCode(), 200);
	}

}
