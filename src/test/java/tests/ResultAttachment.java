package tests;

import hooks.Hooks;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class ResultAttachment extends Hooks {
	String token = "";

	public static String EncodeBase64(String filePath) {
		String base64String = null;

		try {
			File inputFile = new File(filePath);

			// Verifica a extensão do arquivo
			String fileExtension = getFileExtension(inputFile.getName());
			if (fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("docx")) {
				FileInputStream fis = new FileInputStream(inputFile);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len;

				// Lê o arquivo e escreve em um ByteArrayOutputStream
				while ((len = fis.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}

				fis.close();

				// Converte o conteúdo do ByteArrayOutputStream para Base64
				byte[] fileBytes = baos.toByteArray();
				base64String = Base64.getEncoder().encodeToString(fileBytes);

				// Imprime a string Base64
				System.out.println("In-Method: " + base64String);
			} else {
				System.err.println("File is not PNG or DOCX.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return base64String;
	}

	//Retorna o nome do arquivo com extensão
	public static String getFileNameWithExtension(String filePath) {
		File file = new File(filePath);
		return file.getName();
	}

	//Retorna apenas a extensão do arquivo
	public static String getFileExtension(String fileName) {
		int lastDotIndex = fileName.lastIndexOf(".");
		if (lastDotIndex > 0) {
			return fileName.substring(lastDotIndex + 1);
		}
		return "";
	}

	public ResultAttachment(String token) {
		this.token = Base64.getEncoder().encodeToString((":" + token).getBytes());
	}
	public String getTestRunByTestCase(String testCaseId) {
		String body = "{\"PointsFilter\": {\"TestcaseIds\": [" + testCaseId + "]}}";
		RequestSpecification spec = new RequestSpecBuilder().setContentType("application/json").build();
		Response resp = given()
				.spec(spec)
				.header("Authorization", "Basic " + token)
				.body(body)
				.post("/ab-inbev/Aurora_Program/_apis/test/points?api-version=7.0");

		assertEquals(resp.getStatusCode(), 200);
		JsonPath responseBody = resp.getBody().jsonPath();
		return responseBody.getString("points.lastTestRun.id");
	}

	public String getTestResultByTestCase(String testCaseId) {
		String body = "{\"PointsFilter\": {\"TestcaseIds\": [" + testCaseId + "]}}";
		RequestSpecification spec = new RequestSpecBuilder().setContentType("application/json").build();
		Response resp = given()
				.spec(spec)
				.header("Authorization", "Basic " + token)
				.body(body)
				.post("/ab-inbev/Aurora_Program/_apis/test/points?api-version=7.0");

		assertEquals(resp.getStatusCode(), 200);
		JsonPath responseBody = resp.getBody().jsonPath();
		return responseBody.getString("points.lastResult.id");
	}

	public void sendAttachment(String base64, String fileName, String runId, String ResultId) {
		String body = "{ \"stream\": \""+ base64 +"\",\"fileName\": \""+ fileName +"\",\"comment\": \"File Uploaded with Automazure\",\"attachmentType\": \"GeneralAttachment\"}";
		RequestSpecification spec = new RequestSpecBuilder().setContentType("application/json").build();
		Response resp = given()
				.spec(spec)
				.header("Authorization", "Basic " + token)
				.body(body)
				.post("/ab-inbev/Aurora_Program/_apis/testRuns/" + runId + "/Results/" + ResultId + "/attachments?api-version=7.0");

		assertEquals(resp.getStatusCode(), 200);
	}

}
