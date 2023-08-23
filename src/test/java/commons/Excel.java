package commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import utils.Utils;

public class Excel extends BaseTest {

	String fileName = System.getProperty("user.home") + "\\Documents\\EXTRACTOR_TAX_WEB_"+Utils.getDate()+".xlsx";


	public void write_excel(List<String> codigo,List<String> estado,List<String> nome,List<String> material,List<String> servcode,List<String> subitem,
			List<String> taxrate,List<String> date,List<String> description ) {
		System.out.println("Starting ... writing in datasheet!!");
		try {

			FileInputStream arquivo = new FileInputStream(fileName);
			XSSFWorkbook workbook = new XSSFWorkbook(arquivo);
			XSSFSheet sheet = workbook.getSheetAt(0);

			for (int i = 0; i < codigo.size(); i++) {
				Row row = sheet.createRow(i+1);
				row.createCell(0);
				row.getCell(0).setCellValue(codigo.get(i));
				row.createCell(1);
				row.getCell(1).setCellValue(estado.get(i));
				row.createCell(2);
				row.getCell(2).setCellValue(nome.get(i));
				row.createCell(3);
				row.getCell(3).setCellValue(material.get(i));
				row.createCell(4);
				row.getCell(4).setCellValue(servcode.get(i));
				row.createCell(5);
				row.getCell(5).setCellValue(subitem.get(i));
				row.createCell(6);
				row.getCell(6).setCellValue(taxrate.get(i));
				row.createCell(7);
				row.getCell(7).setCellValue(date.get(i));
				row.createCell(8);
				row.getCell(8).setCellValue(description.get(i));
			}

			arquivo.close();

			FileOutputStream outFile = new FileOutputStream(new File(fileName));
			workbook.write(outFile);
			outFile.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void createSheet() {
		try {

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet();

			List<String> lista = new ArrayList<>();
			lista.add("Codigo");
			lista.add("Estado");
			lista.add("Nome");
			lista.add("Material");
			lista.add("ServCode");
			lista.add("SubItem");
			lista.add("Tax Rate");
			lista.add("Date");
			lista.add("Description");

			int r = 0;
			Row row = sheet.createRow(0);

			for (String item : lista) {
				Cell cel = row.createCell(r);
				cel.setCellValue(item);
				r++;
			}

			FileOutputStream outFile = new FileOutputStream(new File(fileName));
			workbook.write(outFile);
			outFile.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Starting Data Extractor!");
	}

	public int getNextRow(XSSFSheet sheet) {
		int nextRow = 0;
		int rowValue = 0;
		Row row = null;
		try {
			row = sheet.getRow(nextRow);
			while (row.getCell(0).getStringCellValue().isBlank() == false) {
				nextRow++;
				row = sheet.getRow(nextRow);
			}
			rowValue = row.getRowNum();

		} catch (Exception e) {
			rowValue = nextRow;
		}

		return rowValue;

	}
}
