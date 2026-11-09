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

import com.baz.divisas.api.core.beneficios.model.CuponMasSalud;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.LogApp;

/**
 * Clase ReporteCuponesMasSaludXls.
 */
public class ReporteCuponesMasSaludXls {

	private Map<String, CellStyle> styles;
	private List<String> titles;

	private static final String TITLE1 = "title1";

	/**
	 * Instantiates a new reporte cupones Mas Salud xls.
	 */
	public ReporteCuponesMasSaludXls() {
		super();

		this.titles = Stream.of(
				"SUCURSAL",
				"CODIGO DESCUENTO",
				"CODIGO MEMBRESIA",
				"NOMBRE CLIENTE",
				"FECHA ENTREGA",
				"HORA ENTREGA",
				"MONTO",
				"DIVISA")
				.collect(Collectors.toList());
	}

	/**
	 * Genera Excel Reporte Cupones Mas Salud.
	 *
	 * @param listaCupones - Lista de cupones entregados
	 * @return ByteArrayInputStream
	 */
	public ByteArrayInputStream generaExcel(List<CuponMasSalud> listaCupones) {

		try (Workbook wb = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

			styles = createStyles(wb);

			int numPag = 0;
			int rowNum = 1;

			Sheet sheet = createSheet(wb, numPag);

			for (CuponMasSalud cupon : listaCupones) {

				if (rowNum >= Constant.INT_50000) {
					rowNum = 1;
					numPag += 1;
					sheet = createSheet(wb, numPag);
				}

				rowNum += 1;
				createRow(sheet, cupon, rowNum);
			}

			wb.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} 
		catch (IOException | IndexOutOfBoundsException e) {
			LogApp.excepcion(e, "Fallo al exportar cupones Mas Salud a Excel");
			return null;
		}
	}

	private Sheet createSheet(Workbook wb, int numPag) {

		String nameSheet = "Cupones Mas Salud";

		if (numPag > 0) {
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
		titleCell.setCellValue("ENTREGAS CUPONES MAS SALUD");
		titleCell.setCellStyle(styles.get(TITLE1));

		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$H$1"));

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

	private void createRow(Sheet sheet, CuponMasSalud cupon, int rowNum) {

		Row row = sheet.createRow(rowNum);

		for (int k = 0; k < titles.size(); k++) {
			Cell cell = row.createCell(k);
			cell.setCellStyle(styles.get("cell"));
		}

		fillCell(row, Constant.INT_0, cupon.getSucursal());
		
		// Dependiendo del tipo se llena codigo descuento o codigo membresia
		if ("1".equals(cupon.getTipo())) {
			fillCell(row, Constant.INT_1, cupon.getId());
			fillCell(row, Constant.INT_2, "");
		} else {
			fillCell(row, Constant.INT_1, "");
			fillCell(row, Constant.INT_2, cupon.getId());
		}
		
		fillCell(row, Constant.INT_3, cupon.getNombreCliente());
		fillCell(row, Constant.INT_4, cupon.getFechaEntrega());
		fillCell(row, Constant.INT_5, cupon.getHoraEntrega());
		fillCell(row, Constant.INT_6, cupon.getMonto());
		fillCell(row, Constant.INT_7, cupon.getDivisa());

		for (int n = 0; n < titles.size(); n++) {
			sheet.setColumnWidth(n, Constant.INT_20 * Constant.INT_256);
		}
	}

	private static void fillCell(Row row, int indexCell, Object value) {

		if (value != null) {
			row.getCell(indexCell).setCellValue(value.toString());
		} 
		else {
			row.getCell(indexCell).setCellValue(" ");
		}
	}

	private static Map<String, CellStyle> createStyles(Workbook wb) {

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

		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setWrapText(true);
		styles.put("cell", style);

		return styles;
	}
}
