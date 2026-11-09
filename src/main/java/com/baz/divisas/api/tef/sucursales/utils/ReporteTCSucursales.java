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
import com.baz.divisas.api.tef.sucursales.model.SucursalReporte;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.LogApp;

public class ReporteTCSucursales {

	private List<String> titles;
	private List<String> titlesOLP;

	public ReporteTCSucursales() {
		super();

		this.titles = Stream.of("x", "Sucursal", "Regíon", "Divisa USD", "TC Sucursal compra USD",
				"Estrategia compra USD", "TC sucursal venta USD", "Estrategia venta USD", "Divisa CAD",
				"TC Sucursal compra CAD", "Estrategia compra CAD", "TC sucursal venta CAD", "Estrategia venta CAD",
				"Divisa EUR", "TC Sucursal compra EUR", "Estrategia compra EUR", "TC sucursal venta EUR",
				"Estrategia venta EUR",
				"Divisa GBP", "TC Sucursal compra GBP", "Estrategia compra GBP", "TC sucursal venta GBP",
				"Estrategia venta GBP",
				"NOMBRE", "TIPO", "MUNICIPIO", "ESTADO", "Calle", "Número", "CP",
				"Número de región").collect(Collectors.toList());
		
		this.titlesOLP = Stream.of("Sucursal", "Divisa", "TC Sucursal compra",
				"Estrategia compra", "TC sucursal venta", "Estrategia venta").collect(Collectors.toList());

	}


	public ByteArrayInputStream generaExcel(List<SucursalReporte> sucursales) {

		try (Workbook wb = new HSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

			int numPag = 0;
			int rowNum = 0;

			Sheet sheet = createSheet(wb, numPag);

			for (SucursalReporte sucursal : sucursales) {

				rowNum += 1;
				createRow(sheet, sucursal, rowNum);
			}

			wb.write(out);

			return new ByteArrayInputStream(out.toByteArray());
		} 
		catch (IOException | IndexOutOfBoundsException e) {
			LogApp.excepcion(e, "Fallo al exportar tipos de cambio a Excel");
			return null;
		}

	}

	private Sheet createSheet(Workbook wb, int numPag) {

		String nameSheet = "TC";

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

	private void createRow(Sheet sheet, SucursalReporte sucursal, int rowNum) {

		Row row = sheet.createRow(rowNum);

		for (int k = 0; k < titles.size(); k++) {
			row.createCell(k);
		}

		fillCell(row, Constant.INT_0, "404");
		fillCell(row, Constant.INT_1, sucursal.getId());
		fillCell(row, Constant.INT_2, sucursal.getRegionSucursalArchivo());


		fillCell(row, Constant.INT_3, sucursal.getDivisaUsd());
		fillCell(row, Constant.INT_4, sucursal.getTcSucursalCompraUsd());
		fillCell(row, Constant.INT_5, sucursal.getEstrategiaCompraUsd());
		fillCell(row, Constant.INT_6, sucursal.getTcSucursalVentaUsd());
		fillCell(row, Constant.INT_7, sucursal.getEstrategiaVentaUsd());
		
		fillCell(row, Constant.INT_8, sucursal.getDivisaCad());
		fillCell(row, Constant.INT_9, sucursal.getTcSucursalCompraCad());
		fillCell(row, Constant.INT_10, sucursal.getEstrategiaCompraCad());
		fillCell(row, Constant.INT_11, sucursal.getTcSucursalVentaCad());
		fillCell(row, Constant.INT_12, sucursal.getEstrategiaVentaCad());

		fillCell(row, Constant.INT_13, sucursal.getDivisaEur());
		fillCell(row, Constant.INT_14, sucursal.getTcSucursalCompraEur());
		fillCell(row, Constant.INT_15, sucursal.getEstrategiaCompraEur());
		fillCell(row, Constant.INT_16, sucursal.getTcSucursalVentaEur());
		fillCell(row, Constant.INT_17, sucursal.getEstrategiaVentaEur());		
		
		fillCell(row, Constant.INT_18, null != sucursal.getDivisaGbp() ? sucursal.getDivisaGbp() : "");
		fillCell(row, Constant.INT_19, sucursal.getTcSucursalCompraGbp());
		fillCell(row, Constant.INT_20, sucursal.getEstrategiaCompraGbp());
		fillCell(row, Constant.INT_21, sucursal.getTcSucursalVentaGbp());
		fillCell(row, Constant.INT_22, sucursal.getEstrategiaVentaGbp());		
		
		fillCell(row, Constant.INT_23, sucursal.getNombreSucursalArchivo());
		fillCell(row, Constant.INT_24, sucursal.getTipoSucursalArchivo());
		fillCell(row, Constant.INT_25, sucursal.getMunicipioSucursalArchivo());
		fillCell(row, Constant.INT_26, sucursal.getEstadoSucursalArchivo());
		fillCell(row, Constant.INT_27, sucursal.getCalleSucursalArchivo());
		fillCell(row, Constant.INT_28, sucursal.getNumeroSucursalArchivo());
		fillCell(row, Constant.INT_29, sucursal.getCpSucursalArchivo());
		fillCell(row, Constant.INT_30, sucursal.getNumeroRegionArchivo());

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


	public ByteArrayInputStream generaExcelOLP(List<SucursalReporte> sucursales) {
		try (Workbook wb = new HSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

			int numPag = 0;
			int rowNum = 0;

			Sheet sheet = createSheetOLP(wb, numPag);

			for (SucursalReporte sucursal : sucursales) {

				rowNum += 1;
				createRowOLP(sheet, sucursal, rowNum);
			}

			wb.write(out);

			return new ByteArrayInputStream(out.toByteArray());
		} 
		catch (IOException | IndexOutOfBoundsException e) {
			LogApp.excepcion(e, "Fallo al exportar tipos de cambio a Excel");
			return null;
		}
	}


	private Sheet createSheetOLP(Workbook wb, int numPag) {
		String nameSheet = "TC";

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
		for (int ii = 0; ii < titlesOLP.size(); ii++) {
			headerCell = headerRow.createCell(ii);
			headerCell.setCellValue(titlesOLP.get(ii));
		}

		return sheet;
	}


	private void createRowOLP(Sheet sheet, SucursalReporte sucursal, int rowNum) {
		Row row = sheet.createRow(rowNum);

		for (int k = 0; k < titlesOLP.size(); k++) {
			row.createCell(k);
		}

		fillCell(row, Constant.INT_0, sucursal.getId());
		fillCell(row, Constant.INT_1, sucursal.getDivisaOlp());
		fillCell(row, Constant.INT_2, sucursal.getTcSucursalCompraOlp());
		fillCell(row, Constant.INT_3, sucursal.getEstrategiaCompraOlp());
		fillCell(row, Constant.INT_4, sucursal.getTcSucursalVentaOlp());
		fillCell(row, Constant.INT_5, sucursal.getEstrategiaVentaOlp());
		
		sheet.setColumnWidth(0, Constant.INT_40 * Constant.INT_256);
		for (int n = 1; n < titlesOLP.size(); n++) {
			sheet.setColumnWidth(n, Constant.INT_20 * Constant.INT_256);
		}

		
	}
}
