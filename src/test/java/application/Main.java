package application;

import java.util.Map;
import java.util.Objects;

import hooks.Hooks;
import tests.PointTest;
import tests.SuiteTest;
import tests.ResultAttachment;

public class Main {

	public static void main(String[] args) {
		
		try {

		String token = args[0];
		String testCaseId = args[1];
		String statusTestCase = args[2];
		String filePath = args[3];
		String isLastTest = args[4];
		Hooks init = new Hooks();
		SuiteTest suite = new SuiteTest(token);
		PointTest point = new PointTest(token);
		ResultAttachment resultAttach = new ResultAttachment(token);
		init.baseTest();
		Map<String, String> suiteAndPlan = suite.getSuiteIdAndPlanId(testCaseId);
		String pointId = point.getTestPointByTestCase(testCaseId);
		point.updateTestCaseResult(statusTestCase, suiteAndPlan.get("planId"), suiteAndPlan.get("suiteId"), pointId);

		if(Objects.equals(isLastTest,"True")){
			String fileName = ResultAttachment.getFileNameWithExtension(filePath);
			ResultAttachment.getFileExtension(filePath);
			String base64 = ResultAttachment.EncodeBase64(filePath);
			String runId = resultAttach.getTestRunByTestCase(testCaseId);
			String resultId = resultAttach.getTestResultByTestCase(testCaseId);
			resultAttach.sendAttachment(base64, fileName, runId, resultId);
		}



		} catch(Exception e) {

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
