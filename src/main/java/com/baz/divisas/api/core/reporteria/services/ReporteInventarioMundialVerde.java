package com.baz.divisas.api.core.reporteria.services;

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

import com.baz.divisas.api.core.reporteria.model.InventarioMundialVerde;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.LogApp;

/**
 * The Class ReporteInventarioMundialVerde.
 */
public class ReporteInventarioMundialVerde {
	
	/** The styles. */
	private Map<String, CellStyle> styles;
	
	/** The titles. */
	private List<String> titles;
		
	/** The Constant TITLE1. */
	private static final String TITLE1 = "title1";

	/**
	 * Instantiates a new reporte operaciones app xls.
	 *
	 */
	public ReporteInventarioMundialVerde() {
		super();
		
		this.titles= Stream.of(			
				 "SUCURSAL",
                 "STOCK ASIGNADO",                
                 "STOCK ENTREGADO",
                 "STOCK RESTANTE")
                .collect(Collectors.toList());
	}


	/**
	 * Genera exel.
	 *
	 * @param listaInventario the lista inventario
	 * @return the byte array input stream
	 */
	public ByteArrayInputStream generaExcel(Iterable<InventarioMundialVerde> listaInventario) {
		
		try (Workbook wb = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

			styles = createStyles(wb);
			
			int numPag = 0;
			int rowNum = 1; 
			
			Sheet sheet = createSheet(wb, numPag);

			for (InventarioMundialVerde inventario : listaInventario) {
				
				if (rowNum >= Constant.INT_50000) {
					rowNum = 1;
					numPag += 1;
					sheet = createSheet(wb, numPag);

				}
				
				rowNum += 1;
				createRow(sheet, inventario, rowNum);
			}

			wb.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} 
		catch (IOException | IndexOutOfBoundsException e) {
			LogApp.excepcion(e, "Fallo al exportar inventarios a Excel");
			return null;
		}

	}

	/**
	 * Creates the sheet.
	 *
	 * @param wb the wb
	 * @param numPag the num pag
	 * @return the sheet
	 */
	private Sheet createSheet(Workbook wb, int numPag) {
		
		String nameSheet = "Inventario";
		
		if(numPag > 0) {
			nameSheet = nameSheet + numPag;
		}
			
		Sheet sheet = wb.createSheet(nameSheet);
		sheet.getPrintSetup().setLandscape(true);
		sheet.setFitToPage(true);
		sheet.setHorizontallyCenter(true);

		// title row
		Row titleRow = sheet.createRow(0);
		titleRow.setHeightInPoints(Constant.INT_20);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellValue("STOCK MUNDIAL VERDE");
		titleCell.setCellStyle(styles.get(TITLE1));
		
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$D$1"));

		// header row
		Row headerRow = sheet.createRow(1);
		Cell headerCell;
		for (int ii = 0; ii < titles.size(); ii++) {
			headerCell = headerRow.createCell(ii);
			headerCell.setCellValue(titles.get(ii));
			headerCell.setCellStyle(styles.get("header1"));
		}
		
		return sheet;
	}
	
	/**
	 * Creates the row.
	 *
	 * @param sheet the sheet
	 * @param inventario the inventario
	 * @param rowNum the row num
	 */
	private void createRow(Sheet sheet, InventarioMundialVerde inventario, int rowNum) {
		
		Row row = sheet.createRow(rowNum);
		
		for (int k = 0; k < titles.size(); k++) {
			Cell cell = row.createCell(k);
			cell.setCellStyle(styles.get("cell"));
		}

		fillCell(row, Constant.INT_0, inventario.getId());
		fillCell(row, Constant.INT_1, inventario.getStock());
		fillCell(row, Constant.INT_2, inventario.getStockEntregado());
		fillCell(row, Constant.INT_3, inventario.getStockRestante());

		
		sheet.setColumnWidth(0, Constant.INT_40 * Constant.INT_256);
		for (int n = 1; n < titles.size(); n++) {
			sheet.setColumnWidth(n, Constant.INT_20 * Constant.INT_256);
		}
		
	}
	
	/**
	 * Fill cell.
	 *
	 * @param row the row
	 * @param indexCell the index cell
	 * @param value the value
	 */
	private static void fillCell(Row row, int indexCell, Object value) {
		
		if(value != null) {
			row.getCell(indexCell).setCellValue(value.toString());
		}
		else {
			row.getCell(indexCell).setCellValue(" ");
		}
	}
	
	/**
	 * Creates the styles.
	 *
	 * @param wb the wb
	 * @return the map
	 */
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
		styles.put(TITLE1, style);


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
