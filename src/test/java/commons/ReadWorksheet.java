package commons;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadWorksheet {
	List<String> testPlanId = new ArrayList<>();
	List<String> testSuiteId = new ArrayList<>();
	List<String> testCaseId = new ArrayList<>();
	List<String> status = new ArrayList<>();
	String token = "";

	public void readWroksheet(String worksheet) {

		try {

			FileInputStream worksheetAzure = new FileInputStream(worksheet);
			XSSFWorkbook workbook = new XSSFWorkbook(worksheetAzure);
			XSSFSheet sheet = workbook.getSheetAt(0);
			XSSFSheet sheetToken = workbook.getSheetAt(1);

			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				Row row = sheet.getRow(i);

				if (row.getCell(0).getCellType() == 0) {
					testCaseId.add(String.format("%.0f", row.getCell(0).getNumericCellValue()));

				} else if (row.getCell(0).getCellType() == 1) {
					testCaseId.add(String.valueOf(row.getCell(0).getStringCellValue()));
				}

				if (row.getCell(1).getCellType() == 0) {
					status.add(String.valueOf(row.getCell(1).getNumericCellValue()));

				} else if (row.getCell(1).getCellType() == 1) {
					status.add(String.valueOf(row.getCell(1).getStringCellValue()));
				}
			}
			
			Row row = sheetToken.getRow(1);
			token = String.valueOf(row.getCell(0).getStringCellValue());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<String> getTestPlanId() {
		return this.testPlanId;
	}

	public List<String> getTestSuiteId() {
		return this.testSuiteId;
	}

	public List<String> getTestCaseId() {
		return this.testCaseId;
	}

	public List<String> getStatus() {
		return this.status;
	}
	
	public String getToken() {
		return this.token;
	}
}