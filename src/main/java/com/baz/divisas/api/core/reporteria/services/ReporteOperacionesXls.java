package com.baz.divisas.api.core.reporteria.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import org.bson.Document;

import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.LogApp;

/**
 * Clase ReporteOperacionesAppXls
 */
public class ReporteOperacionesXls {

	private Map<String, CellStyle> styles;
	private List<String> titles;
		
	private static final String TITLE1 = "title1";

	/**
	 * Instantiates a new reporte operaciones app xls.
	 *
	 */
	public ReporteOperacionesXls() {
		super();
		
		this.titles= Stream.of(			
				 "SUCURSAL",
                 "NUMERO OPERACIÓN",
                 "TIPO OPERACIÓN",
                 "FECHA DE OPERACIÓN",
                 "TIPO IDENTIFICACIÓN",
                 "FOLIO IDENTIFICACIÓN",
                 "NOMBRE",                
                 "APELLIDO PATERNO",
                 "APELLIDO MATERNO",
                 "FECHA NACIMIENTO",
                 "DIVISA",
                 "MONTO DIVISA",                 
                 "BONO",
                 "BENEFICIO EN TC LEALTAD",
                 "BENEFICIO EN TC CLIENTE",
                 "TIPO DE CAMBIO SUCURSAL",                   
                 "TIPO DE CAMBIO OPERADO",
                 "DIRECCIÓN",
                 "COLONIA",
                 "CIUDAD",
                 "ESTADO",
                 "CÓDIGO POSTAL",
                 "GIRO PROFESIONAL",
                 "ACTIVIDAD ECONÓMICA",                
                 "PAIS NACIMIENTO",
                 "NACIONALIDAD",
                 "GÉNERO",
                 "ORIGEN",
                 "CLIENTE DIVISAS",
                 "NÚMERO DE CUENTA",
                 "TIPO CLIENTE",
                 "TIPO FLUJO",
                 "CLIENTE ÚNICO",
                 "APLICACIÓN ORIGEN",
                 "FONDEO CAJA",
                 "CURP",
                 "ENTIDAD NACIMIENTO",
                 "NUMERO AUTORIZACIÓN",
                 "OPERACIÓN SOSPECHOSA",
                 "EXTRACCIÓN OCR",
                 "NUMERO DE OPERACIÓN DE CAJA REMESAS",
                 "MTCN",
                 "IMPORTE DE LA REMESA",
                 "NOMBRE DEL TRANSMISOR",
                 "EDICIÓN DE DICCIONARIO",
                 "TIPO DE BÚSQUEDA",
                 "CLIENTE ORO",
                 "CAMPAÑA",
                 "BENEFICIO RASCADITO DIGITAL",
                 "ORIGEN BENEFICIO",
                 "DURACIÓN")
                .collect(Collectors.toList());

	}

	/**
	 * Genera Excel Reporte Operaciones.
	 *
	 * @param listaOperaciones - Lista de Operaciones 
	 * @return ByteArrayInputStream
	 */
	public ByteArrayInputStream generaExel(Iterable<Document> listaOperaciones) {
		
		try (Workbook wb = new HSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

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
			LogApp.excepcion(e, "Fallo al exportar operaciones a Excel");
			return null;
		}

	}

	private Sheet createSheet(Workbook wb, int numPag) {
		
		String nameSheet = "Operaciones";
		
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
		titleCell.setCellValue("REPORTE DE OPERACIONES");
		titleCell.setCellStyle(styles.get(TITLE1));
		
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$AY$1"));

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

		fillCell(row, Constant.INT_0, operacion.getString("sucursal"));
		fillCell(row, Constant.INT_1, operacion.getString("numero_operacion"));
		fillCell(row, Constant.INT_2, operacion.getString("tipo_operacion"));
		
		validaTipoOperacion(row, Constant.INT_2);

		
		fillCell(row, Constant.INT_3, operacion.getString("fecha_operacion").trim().replace("T", " "));
		fillCell(row, Constant.INT_4, operacion.getString("tipo_identificacion"));
		
		validaTipoIdentificacion(row, Constant.INT_4);	
		
		fillCell(row, Constant.INT_5, operacion.getString("folio_identificacion"));
		fillCell(row, Constant.INT_6, operacion.getString("nombre"));
		fillCell(row, Constant.INT_7, operacion.getString("apellido_paterno"));
		fillCell(row, Constant.INT_8, operacion.getString("apellido_materno"));
		fillCell(row, Constant.INT_9, operacion.getString("fecha_nacimiento"));
		fillCell(row, Constant.INT_10, operacion.getString("divisa"));
		fillCell(row, Constant.INT_11, operacion.get("monto_divisa"));
		
		if (null != operacion.get("premio_beneficio")) {
			fillCell(row, Constant.INT_12, operacion.get("premio_beneficio"));
			fillCell(row, Constant.INT_13, "0.15");
			fillCell(row, Constant.INT_14, "0.05");
		}

		fillCell(row, Constant.INT_15, operacion.get("tipo_cambio_sucursal"));
		fillCell(row, Constant.INT_16, operacion.get("tipo_cambio"));
		fillCell(row, Constant.INT_17, operacion.getString("calle"));		
		fillCell(row, Constant.INT_18, operacion.getString("colonia"));
		fillCell(row, Constant.INT_19, operacion.getString("poblacion"));
		fillCell(row, Constant.INT_20, operacion.getString("estado"));
		fillCell(row, Constant.INT_21, operacion.getString("codigo_postal"));
		fillCell(row, Constant.INT_22, operacion.getString("giro"));
		fillCell(row, Constant.INT_23, operacion.getString("actividad"));
		fillCell(row, Constant.INT_24, operacion.getString("pais_nacimiento"));
		fillCell(row, Constant.INT_25, operacion.getString("nacionalidad"));
		fillCell(row, Constant.INT_26, operacion.getString("genero"));
		fillCell(row, Constant.INT_27, operacion.getString("origen"));
		fillCell(row, Constant.INT_28, operacion.getString("id_cliente"));
		fillCell(row, Constant.INT_29, operacion.getString("numero_cuenta"));
		fillCell(row, Constant.INT_30, operacion.getString("tipo_cliente"));
		fillCell(row, Constant.INT_31, operacion.getString("tipo_flujo"));
		fillCell(row, Constant.INT_32, operacion.getString("id_cliente_unico"));
		fillCell(row, Constant.INT_33, operacion.getString("aplicacion_origen"));
		fillCell(row, Constant.INT_34, operacion.getString("fondeo_caja"));
		fillCell(row, Constant.INT_35, operacion.getString("curp"));
		fillCell(row, Constant.INT_36, operacion.getString("id_entidad_nacimiento"));
		fillCell(row, Constant.INT_37, operacion.getString("numero_autorizacion"));
		fillCell(row, Constant.INT_38, operacion.getString("operacionSospechosa"));
		fillCell(row, Constant.INT_39, operacion.getString("extraccionOcr"));
		fillCell(row, Constant.INT_40, operacion.getString("numero_operacion_remesas"));
		fillCell(row, Constant.INT_41, operacion.getString("mtcn_remesas"));
		fillCell(row, Constant.INT_42, operacion.getString("importe_remesas"));
		fillCell(row, Constant.INT_43, operacion.getString("transmisor_remesas"));
		fillCell(row, Constant.INT_44, operacion.getString("edicionDic"));
		fillCell(row, Constant.INT_45, operacion.getString("tipoBusqueda"));
		fillCell(row, Constant.INT_46, operacion.getString("cliente_opb"));
		fillCell(row, Constant.INT_47, operacion.getString("campania"));
		fillCell(row, Constant.INT_48, operacion.get("beneficioCupon"));
		fillCell(row, Constant.INT_49, operacion.get("origenCupon"));
		fillCell(row, Constant.INT_50, operacion.getString("duracion"));
		
		sheet.setColumnWidth(0, Constant.INT_40 * Constant.INT_256);
		for (int n = 1; n < titles.size(); n++) {
			sheet.setColumnWidth(n, Constant.INT_20 * Constant.INT_256);
		}
		
	}
	
	private static void validaTipoOperacion(Row row, int indexCell) {
		
		if(row.getCell(indexCell).getStringCellValue().equals(Constant.COMPRA_EFECTIVO)) {
			fillCell(row, indexCell, Constant.DESC_COMPRA_EFECTIVO);
		}
		else if(row.getCell(indexCell).getStringCellValue().equals(Constant.COMPRA_ABONO)) {
			fillCell(row, indexCell, Constant.DESC_COMPRA_ABONO);
		}
		else if(row.getCell(indexCell).getStringCellValue().equals(Constant.VENTA_EFECTIVO)) {
			fillCell(row, indexCell, Constant.DESC_VENTA_EFECTIVO);
		}
		else if(row.getCell(indexCell).getStringCellValue().equals(Constant.VENTA_CARGO)) {
			fillCell(row, indexCell, Constant.DESC_VENTA_CARGO);
		}
		else {
			// Do nothing
		}
	}
	
	private static void validaTipoIdentificacion(Row row, int indexCell) {
		
		if(row.getCell(indexCell).getStringCellValue().equals(Constant.INE)) {
			fillCell(row, indexCell, Constant.DESC_INE);
		}
		else if(row.getCell(indexCell).getStringCellValue().equals(Constant.PASAPORTE)) {
			fillCell(row, indexCell, Constant.DESC_PASAPORTE);
		}
		else if(row.getCell(indexCell).getStringCellValue().equals(Constant.PASSPORT_CARD)) {
			fillCell(row, indexCell, Constant.DESC_PASSPORT_CARD);
		}
		else if(row.getCell(indexCell).getStringCellValue().equals(Constant.FORMA_MIGRATORIA)) {
			fillCell(row, indexCell, Constant.DESC_FORMA_MIGRATORIA);
		}
		else {
			// Do nothing
		}
	}
	
	private static void fillCell(Row row, int indexCell, Object value) {
		
		if(value != null) {
			row.getCell(indexCell).setCellValue(value.toString());
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
