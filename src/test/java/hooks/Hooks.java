package hooks;

import org.junit.Before;

import io.restassured.RestAssured;

public class Hooks {
	

	
	@Before
	public void baseTest() {
		System.out.println("***********************************************************");
		System.out.println("***********************************************************");
		System.out.println("******************* STARTING AUTOMAZURE *******************");
		System.out.println("***********************************************************");
		System.out.println("***********************************************************");
		
		RestAssured.baseURI = "https://dev.azure.com";
	}
}
