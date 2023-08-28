package tests;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import hooks.Hooks;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SuiteTest extends Hooks {
	String token = "";
	
	public SuiteTest(String token) {
		this.token = token;
	}
	
	Map<String, String> suiteAndPlan = new HashMap<String, String>();


	public Map<String, String>  getSuiteIdAndPlanId(String testCaseId) {
		RequestSpecification  spec = new RequestSpecBuilder()
                .setContentType("application/json")
                .build();
				Response resp = given().spec(spec).header("Authorization", "Basic "+token)
						.param("testCaseId", testCaseId).when().get("/ab-inbev/_apis/test/suites");
				
				assertEquals(resp.getStatusCode(), 200);
				JsonPath body = resp.getBody().jsonPath();
				String suiteId = body.getString("value.id[0]");
				String planId = body.getString("value.plan.id[0]");
				suiteAndPlan.put("planId", planId);
				suiteAndPlan.put("suiteId", suiteId);
				return suiteAndPlan;
	}
}
