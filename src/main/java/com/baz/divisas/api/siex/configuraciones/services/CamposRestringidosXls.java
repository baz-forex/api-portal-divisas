package com.baz.divisas.api.siex.configuraciones.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.baz.divisas.api.siex.configuraciones.model.CampoRestringido;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.LogApp;

/**
 * Clase CamposRestringidosXls
 */
public class CamposRestringidosXls {

	private Map<String, CellStyle> styles;
	private List<String> titles;


	/**
	 * Constructor de la clase.
	 *
	 */
	public CamposRestringidosXls() {
		super();
		
		this.titles= Stream.of(			
				"Empieza con",
				"Termina con",
				"Contiene",
				"Es igual")
                .collect(Collectors.toList());
	}

	/**
	 * Genera Excel Campos Restringidos.
	 *
	 * @param camposRestringidos - Lista de Campos Restringidos
	 * @return ByteArrayInputStream
	 */
	public ByteArrayInputStream generaExel(Iterable<CampoRestringido> camposRestringidos) {
		
		try (Workbook wb = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

			styles = createStyles(wb);
			
			for (CampoRestringido campo : camposRestringidos) {
				
				Sheet sheet = createSheet(wb, campo.getDescripcion());
				
				for (int i = 0; i < campo.getEmpiezaCon().getPalabras().size(); i++) {
					String restriccion = campo.getEmpiezaCon().getPalabras().get(i);
					createRow(sheet, restriccion, i + Constant.INT_2, Constant.INT_0);
				}
				
				for (int i = 0; i < campo.getTerminaCon().getPalabras().size(); i++) {
					String restriccion = campo.getTerminaCon().getPalabras().get(i);
					createRow(sheet, restriccion, i + Constant.INT_2, Constant.INT_1);
				}
				
				for (int i = 0; i < campo.getContiene().getPalabras().size(); i++) {
					String restriccion = campo.getContiene().getPalabras().get(i);
					createRow(sheet, restriccion, i + Constant.INT_2, Constant.INT_2);
				}
				
				for (int i = 0; i < campo.getIgualA().getPalabras().size(); i++) {
					String restriccion = campo.getIgualA().getPalabras().get(i);
					createRow(sheet, restriccion, i + Constant.INT_2, Constant.INT_3);
				}

			}
			
			wb.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} 
		catch (IOException | IndexOutOfBoundsException e) {
			LogApp.excepcion(e, "Fallo al importar datos al Excel");
			return null;
		}

	}

	private Sheet createSheet(Workbook wb, String nameSheet) {
					
		Sheet sheet = wb.createSheet(nameSheet);
		sheet.getPrintSetup().setLandscape(true);
		sheet.setFitToPage(true);
		sheet.setHorizontallyCenter(true);

		// title row
		Row titleRow = sheet.createRow(0);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellValue("RESTRICCIONES " + nameSheet);
		titleCell.setCellStyle(styles.get("title1"));
		
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$D$1"));

		// header row
		Row headerRow = sheet.createRow(1);
		Cell headerCell;
		for (int i = 0; i < titles.size(); i++) {
			headerCell = headerRow.createCell(i);
			headerCell.setCellValue(titles.get(i));
			headerCell.setCellStyle(styles.get("header1"));
			sheet.setColumnWidth(i, Constant.INT_20 * Constant.INT_256);
		}
		
		return sheet;
	}
	
	private void createRow(Sheet sheet, String restriccion, int rowNum, int colNum) {
				
		Row row = sheet.getRow(rowNum);
		
		if(row == null) {
			
			row = sheet.createRow(rowNum);
			
			for (int i = 0; i < titles.size(); i++) {
				Cell cell = row.createCell(i);
				cell.setCellStyle(styles.get("cell"));				
			}
			
		}		

		row.getCell(colNum).setCellValue(restriccion);
		
	}
	
	private static Map<String, CellStyle> createStyles(Workbook wb){

		Map<String, CellStyle> styles = new HashMap<>();
		CellStyle style;
		Font titleFont1 = wb.createFont();
		titleFont1.setFontHeightInPoints((short) Constant.INT_11);
		titleFont1.setColor(IndexedColors.WHITE.getIndex());
		titleFont1.setBold(true);
		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFillForegroundColor(IndexedColors.TEAL.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFont(titleFont1);
		styles.put("title1", style);

		Font titleFont3 = wb.createFont();
		titleFont3.setFontHeightInPoints((short) Constant.INT_11);
		titleFont3.setColor(IndexedColors.WHITE.getIndex());
		titleFont3.setBold(true);
		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFont(titleFont3);
		styles.put("title3", style);

		Font header1 = wb.createFont();
		header1.setFontHeightInPoints((short) Constant.INT_11);
		header1.setColor(IndexedColors.WHITE.getIndex());
		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFont(header1);
		style.setWrapText(true);
		styles.put("header1", style);

		Font header3 = wb.createFont();
		header3.setFontHeightInPoints((short) Constant.INT_11);
		header3.setColor(IndexedColors.WHITE.getIndex());
		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFillForegroundColor(IndexedColors.TEAL.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFont(header3);
		style.setWrapText(true);
		styles.put("header3", style);

		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setWrapText(true);
		styles.put("cell", style);

		return styles;
	}
	
}
