package com.baz.divisas.api.digital.reporteria.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
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
import org.bson.Document;

import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.LogApp;

/**
 * Clase ReporteOperacionesAppXls
 */
public class ReporteOperacionesAppXls {

	private Map<String, CellStyle> styles;
	private boolean entregaDomicilio;
	private List<String> titles;
		
	private static final String TITLE1 = "title1";
	private static final String TIPO_OPERACION = "tipo_operacion";


	/**
	 * Instantiates a new reporte operaciones app xls.
	 *
	 * @param entregaDomicilio the entrega domicilio
	 */
	public ReporteOperacionesAppXls(boolean entregaDomicilio) {
		super();
		this.entregaDomicilio = entregaDomicilio;
		
		this.titles= Stream.of(			
				"BDMID",
				"CTE ALNV",
				"CTE UNICO",
				"NOMBRE",
				"APELLIDO PAT",
				"APELLIDO MAT",
				"SUC",
				"NUM CTA",
				"TIPO CTA",
				"NUM RETEN",
				"ESTATUS",
				"MONTO USD",
				"MONTO MXN",
				"TIP CAMB VTA",
				"TIP CAMB COM",
				"FECH OPER",
				"LATITUD",
				"LONGITUD",
				"FECH REDEN",
				"FECH VENCI",
				"HR OPE",
				"VIGENCIA (DIAS)",
				"PROGRAMA",
				"ULTIMA MODIF",
				"GENERO",
				"BENEFICIO",
				"MONTO BENEFICIO")
                .collect(Collectors.toList());
		
		if(this.entregaDomicilio) {
			
			List<String> titlesEntregaDomicilio = Arrays.asList(
					"NOMBRE DRIVER",
					"APELLIDO PATERNO DRIVER",
					"APELLIDO MATERNO DRIVER",
					"FECHA NACIMIENTO DRIVER",
					"NUM EMPLEADO DRIVER",
					"CORREO DRIVER",
					"TELEFONO DRIVER",
					"CALLE",
					"NUM EXTERIOR",
					"NUM INTERIOR",
					"COLONIA",
					"CP",
					"LATITUD ENTREGA",
					"LONGITUD ENTREGA",
					"ESTATUS",
					"RECOMPRA",
					"MONTO DIVISA",
					"MONTO MXN",
					"TIPO CAMBIO RECOMPRA",
					"ABONO TOTAL",
					"FECHA DE ABONO"					
			);
			
			titles.addAll(titlesEntregaDomicilio);
		}
	}

	/**
	 * Genera Excel Reporte Operaciones App.
	 *
	 * @param listaOperaciones - Lista de Operaciones 
	 * @return ByteArrayInputStream
	 */
	public ByteArrayInputStream generaExel(Iterable<Document> listaOperaciones) {
		
		try (Workbook wb = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

			styles = createStyles(wb);
			
			int numPag = 0;
			int rowNum = 1; 
			
			Sheet sheet = createSheet(wb, numPag);

			for (Document operacion : listaOperaciones) {
				
				if (rowNum >= Constant.INT_50000) {
					rowNum = 1;
					numPag += 1;
					sheet = createSheet(wb, numPag);

				}
				
				rowNum += 1;
				createRow(sheet, operacion, rowNum);
			}

			wb.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} 
		catch (IOException | IndexOutOfBoundsException e) {
			LogApp.excepcion(e, "Fallo al importar datos al Excel");
			return null;
		}

	}

	private Sheet createSheet(Workbook wb, int numPag) {
		
		String nameSheet = "OperacionesApp";
		
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
		titleCell.setCellValue("REPORTE DE OPERACIONES APP");
		titleCell.setCellStyle(styles.get(TITLE1));
		
		if(!entregaDomicilio) {
			sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$AA$1"));
		}
		else {
			sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$AV$1"));
		}

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
	
	private void createRow(Sheet sheet, Document operacion, int rowNum) {
		
		Row row = sheet.createRow(rowNum);
		
		for (int k = 0; k < titles.size(); k++) {
			Cell cell = row.createCell(k);
			cell.setCellStyle(styles.get("cell"));
		}

		fillCell(row, Constant.INT_0, operacion.getString("icu"));
		fillCell(row, Constant.INT_1, operacion.getString("id_cliente_alnova"));
		fillCell(row, Constant.INT_2, operacion.getString("id_cliente_unico"));
		fillCell(row, Constant.INT_3, operacion.getString("nombre"));
		fillCell(row, Constant.INT_4, operacion.getString("apellido_paterno"));
		fillCell(row, Constant.INT_5, operacion.getString("apellido_materno"));
		fillCell(row, Constant.INT_6, operacion.getString("sucursal"));
		fillCell(row, Constant.INT_7, operacion.getString("numero_cuenta"));
		fillCell(row, Constant.INT_8, operacion.getString("alias_cuenta"));
		fillCell(row, Constant.INT_9, operacion.getString("numero_retencion"));
		fillCell(row, Constant.INT_10, operacion.getString("estatus_banca"));
		fillCell(row, Constant.INT_11, operacion.get("monto_divisa").toString());
		fillCell(row, Constant.INT_12, operacion.get("monto_mxn").toString());
		
		if("413".equals(operacion.get(TIPO_OPERACION))) {
			fillCell(row, Constant.INT_13, operacion.get("tipo_cambio").toString());
		}
		else {
			fillCell(row, Constant.INT_13, "0.0");
		}
		
		if("412".equals(operacion.get(TIPO_OPERACION))) {
			fillCell(row, Constant.INT_14, operacion.get("tipo_cambio").toString());
		}
		else {
			fillCell(row, Constant.INT_14, "0.0");
		}

		
		fillCell(row, Constant.INT_15, operacion.getString("fecha_cotizacion"));
		fillCell(row, Constant.INT_16, operacion.getString("latitud"));
		fillCell(row, Constant.INT_17, operacion.getString("longitud"));
		
		fillCell(row, Constant.INT_18, operacion.getString("fecha_redencion"));
		
		if(row.getCell(Constant.INT_18).getStringCellValue().length() >= Constant.INT_10) {
			fillCell(row, Constant.INT_18, 
					 row.getCell(Constant.INT_18).getStringCellValue().substring(Constant.INT_0, Constant.INT_10));
		}
		
		fillCell(row, Constant.INT_19, operacion.getString("fecha_limite"));
		fillCell(row, Constant.INT_20, operacion.getString("hora_cotizacion"));
		fillCell(row, Constant.INT_21, operacion.getString("vigencia"));
		fillCell(row, Constant.INT_22, operacion.getString("programa"));

		if("GA10".equals(row.getCell(Constant.INT_22).getStringCellValue()) &&
		   "413".equals(operacion.get(TIPO_OPERACION))) {
			fillCell(row, Constant.INT_22, "GA11");
		}

		fillCell(row, Constant.INT_23, operacion.getString("fecha_actualizacion"));
		fillCell(row, Constant.INT_24, operacion.getString("genero"));
		fillCell(row, Constant.INT_25, operacion.getString("tipoBeneficioRemesas"));
		fillCell(row, Constant.INT_26, operacion.getString("montoBeneficioRemesas"));
		
		if(entregaDomicilio && operacion.getBoolean("isEntregaDomicilio").booleanValue()) {
			
			Document entrega = operacion.getEmbedded(Arrays.asList("entrega"), new Document());
			Document conductor = operacion.getEmbedded(Arrays.asList("conductor"), new Document());
			
			fillCell(row, Constant.INT_27, conductor.getString("nombre"));
			fillCell(row, Constant.INT_28, conductor.getString("apellidoPaterno"));
			fillCell(row, Constant.INT_29, conductor.getString("apellidoMaterno"));
			fillCell(row, Constant.INT_30, conductor.getString("fechaNacimiento"));
			fillCell(row, Constant.INT_31, conductor.getString("numeroEmpleado"));
			fillCell(row, Constant.INT_32, conductor.getString("correoElectronico"));
			fillCell(row, Constant.INT_33, conductor.getString("numeroTelefono"));
			fillCell(row, Constant.INT_34, entrega.getString("calle"));
			fillCell(row, Constant.INT_35, entrega.getString("numeroExterior"));
			fillCell(row, Constant.INT_36, entrega.getString("numeroInterior"));
			fillCell(row, Constant.INT_37, entrega.getString("colonia"));
			fillCell(row, Constant.INT_38, entrega.getString("codigoPostal"));
			fillCell(row, Constant.INT_39, entrega.getString("latitud"));
			fillCell(row, Constant.INT_40, entrega.getString("longitud"));
			fillCell(row, Constant.INT_41, entrega.getString("descripcionEstatus"));
		}
		
		sheet.setColumnWidth(0, Constant.INT_40 * Constant.INT_256);
		for (int n = 1; n < titles.size(); n++) {
			sheet.setColumnWidth(n, Constant.INT_20 * Constant.INT_256);
		}
		
	}
	
	private static void fillCell(Row row, int indexCell, String value) {
		
		if(value != null) {
			row.getCell(indexCell).setCellValue(value);
		}
		else {
			row.getCell(indexCell).setCellValue(" ");
		}
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
