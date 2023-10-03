package application;

import java.util.Map;
import hooks.Hooks;
import tests.PointTest;
import tests.SuiteTest;
import tests.ResultAttachment;

public class Main {

	public static void main(String[] args) {

//		if (args.length < 4) {
//			System.err.println("Insufficient number of arguments. Provide at least 4 arguments.");
//			System.exit(1);
//		}

		try {

			String objective = "attachEvidence";
			String token = "vshp6h7yfo5etvhdz57vfv236t6hye5s52rkeqrnqhonxbqpikha";
			String testCaseId = "2331742";
			String statusTestCase = null;
			String filePath = "src/test/java/application/automa.png";

//			switch (objective){
//				case "setStatus":
//					token = args[1];
//					testCaseId = args[2];
//					statusTestCase = args[3];
//					break;
//				case "attachEvidence":
//					token = args[1];
//					testCaseId = args[2];
//					filePath = args[3];
//					break;
//				default:
//					System.err.println("Invalid Objective. Try again with option 'setStatus' ou 'attachEvidence'.");
//					break;
//			}

			Hooks init = new Hooks();
			SuiteTest suite = new SuiteTest(token);
			PointTest point = new PointTest(token);
			init.baseTest();
			Map<String, String> suiteAndPlan = suite.getSuiteIdAndPlanId(testCaseId);
			String pointId = point.getTestPointByTestCase(testCaseId);

			if (objective.equals("setStatus")){
				point.updateTestCaseResult(statusTestCase, suiteAndPlan.get("planId"), suiteAndPlan.get("suiteId"), pointId);
			}
			else{
				ResultAttachment resultAttach = new ResultAttachment(token);
				assert filePath != null;
				String fileName = ResultAttachment.getFileNameWithExtension(filePath);
				ResultAttachment.getFileExtension(filePath);
				String base64 = ResultAttachment.EncodeBase64(filePath);
				String runId = resultAttach.getTestRunByTestCase(testCaseId).replace("[", "").replace("]", "");
				String resultId = resultAttach.getTestResultByTestCase(testCaseId).replace("[", "").replace("]", "");
				resultAttach.sendAttachment(base64, fileName, runId, resultId);
				System.out.println(runId + " - " + resultId);
			}

		} catch(Exception e) {

			System.out.println("***********************************************************");
			System.out.println("***********************************************************");
			System.out.println("***** Para Executar informe os seguintes parametros: ******");
			System.out.println("***** 1º Token                                       ******");
			System.out.println("***** 2º Test Case ID                                ******");
			System.out.println("***** 3º Status Test Case ou filePath                ******");
			System.out.println("***********************************************************");
			System.out.println("***********************************************************");
		
			e.printStackTrace();
			
		}
	}
}
