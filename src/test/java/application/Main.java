package application;

import java.util.Map;

import hooks.Hooks;
import tests.PointTest;
import tests.SuiteTest;

public class Main {

	public static void main(String[] args) {
		
		try {
		String token = args[0];
		String testCaseId = args[1];
		String statusTestCase = args[2];
		Hooks init = new Hooks();
		SuiteTest suite = new SuiteTest(token);
		PointTest point = new PointTest(token);
		init.baseTest();
		Map<String, String> suiteAndPlan = suite.getSuiteIdAndPlanId(testCaseId);
		String pointId = point.getTestPointByTestCase(testCaseId);
		point.updateTestCaseResult(statusTestCase, suiteAndPlan.get("planId"), suiteAndPlan.get("suiteId"), pointId);
		
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("***********************************************************");
			System.out.println("***********************************************************");
			System.out.println("***** Para Executar informe os seguintes parametros: ******");
			System.out.println("***** 1º Token                                       ******");
			System.out.println("***** 2º Test Case ID                                ******");
			System.out.println("***** 3º Status Test Case                            ******");
			System.out.println("***********************************************************");
			System.out.println("***********************************************************");
		
			e.printStackTrace();
		}
		

	}

}
