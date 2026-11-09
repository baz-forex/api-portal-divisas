package com.baz.divisas.api.tef.sucursales.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.baz.divisas.api.tef.sucursales.model.LimitesReporte;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.LogApp;

public class ReporteLimitesSucursales {

	private List<String> titles;
	
	public ReporteLimitesSucursales() {
		super();

		this.titles = Stream.of("Sucursal", "Divisa", "Tipo de Operación", "Límite Diario Cliente",
				"Límite Diario Usuario", "Límite Mensual Cliente", "Límite Mensual Usuario",
				"Límite Diario Cliente Extranjero",
				"Límite Diario Usuario Extranjero", "Límite Mensual Cliente Extranjero", "Límite Mensual Usuario Extranjero")
				.collect(Collectors.toList());
	}
	
	public ByteArrayInputStream generaExcel(List<LimitesReporte> limites) {

		try (Workbook wb = new HSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

			int numPag = 0;
			int rowNum = 0;

			Sheet sheet = createSheet(wb, numPag);

			for (LimitesReporte limite : limites) {

				rowNum += 1;
				createRow(sheet, limite, rowNum);
			}

			wb.write(out);

			return new ByteArrayInputStream(out.toByteArray());
		} 
		catch (IOException | IndexOutOfBoundsException e) {
			LogApp.excepcion(e, "Fallo al exportar los límites a Excel");
			return null;
		}

	}

	private Sheet createSheet(Workbook wb, int numPag) {

		String nameSheet = "Límites";

		if (numPag > 0) {
			nameSheet = nameSheet + numPag;
		}

		Sheet sheet = wb.createSheet(nameSheet);
		sheet.getPrintSetup().setLandscape(true);
		sheet.setFitToPage(true);
		sheet.setHorizontallyCenter(true);

		// header row
		Row headerRow = sheet.createRow(0);
		Cell headerCell;
		for (int ii = 0; ii < titles.size(); ii++) {
			headerCell = headerRow.createCell(ii);
			headerCell.setCellValue(titles.get(ii));
		}

		return sheet;
	}

	private void createRow(Sheet sheet, LimitesReporte limite, int rowNum) {

		Row row = sheet.createRow(rowNum);

		for (int k = 0; k < titles.size(); k++) {
			row.createCell(k);
		}

		fillCell(row, Constant.INT_0, limite.getId());
		fillCell(row, Constant.INT_1, limite.getDivisa());
		fillCell(row, Constant.INT_2, limite.getTipoOperacion());


		fillCell(row, Constant.INT_3, limite.getClienteDiario());
		fillCell(row, Constant.INT_4, limite.getUsuarioDiario());
		fillCell(row, Constant.INT_5, limite.getClienteMensual());
		fillCell(row, Constant.INT_6, limite.getUsuarioMensual());
		
		fillCell(row, Constant.INT_7, limite.getExtranjeroClienteDiario());		
		fillCell(row, Constant.INT_8, limite.getExtranjeroUsuarioDiario());
		fillCell(row, Constant.INT_9, limite.getExtranjeroClienteMensual());
		fillCell(row, Constant.INT_10, limite.getExtranjeroUsuarioMensual());

		sheet.setColumnWidth(0, Constant.INT_40 * Constant.INT_256);
		for (int n = 1; n < titles.size(); n++) {
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
}
