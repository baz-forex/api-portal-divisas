package com.baz.divisas.api.core.reporteria.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.baz.divisas.api.core.reporteria.model.Incidencia;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.LogApp;

/**
 * ReporteIncidenciasXls
 * @author B813196
 * itoxtle
 *
 */

@Component
public class ReporteIncidenciasXls {

	private int rowNum;
	private int numPag;
	private Sheet sheet;
	private PrintSetup printSetup;
	private Map<String, CellStyle> styles;
	private List<String> tituloBusquedaMW = Arrays.asList("FECHA REGISTRO", "NOMBRE", "APELLIDO PATERNO", 
			"APELLIDO MATERNO", "FECHA NACIMIENTO", "SUCURSAL");
	private List<String> tituloResultadosLN = Arrays.asList("LISTA NEGRA", "NOMBRE", "APELLIDOS", "FECHA NACIMIENTO",
			"COINCIDENCIA NOMBRE");

	/**
	 * Constructor de la clase
	 * 
	 */
	public ReporteIncidenciasXls() {
		super();
	}

	/**
	 * Obtiene el excel de la lista de incidencias
	 * 
	 * @param listaIncidencias - lista de incidencias por fecha
	 * @param tipoIncidencia - Tipo de Incidencia
	 * 
	 * @return Retorna arreglo de bytes para generar el excel
	 */
	public ByteArrayInputStream generaExcel(Iterable<Incidencia> listaIncidencias, String tipoIncidencia) {
		
		try (Workbook wb = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

			styles = createStyles(wb);

			rowNum = 0;
			numPag = 0;
			sheet = wb.createSheet(tipoIncidencia);
			printSetup = sheet.getPrintSetup();
			printSetup.setLandscape(true);
			sheet.setFitToPage(true);
			sheet.setHorizontallyCenter(true);

			for (Incidencia incidencia : listaIncidencias) {
	
				listIncidenciasCheck(incidencia, wb, tipoIncidencia);
			}

			wb.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} 
		catch (IOException | IndexOutOfBoundsException e) {
			LogApp.excepcion(e, "Fallo al importar datos al Excel");
			return null;
		}
	}
	
	/**
	 * Obtiene el excel de la lista de incidencias
	 * 
	 * @param incidenciasFor - Incidencias
	 * @param wb             - Workbook
	 * @param tipoIncidencia - Tipo Incidencia
	 */
	public void listIncidenciasCheck(Incidencia incidenciasFor, Workbook wb, String tipoIncidencia) {

		if (rowNum >= Constant.INT_50000) {
			numPag += 1;
			sheet = wb.createSheet(tipoIncidencia + numPag);
			printSetup = sheet.getPrintSetup();
			printSetup.setLandscape(true);
			sheet.setFitToPage(true);
			sheet.setHorizontallyCenter(true);
			rowNum = 0;
		}

		// title row
		Row titleRow = sheet.createRow(rowNum);
		titleRow.setHeightInPoints(Constant.INT_11);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellValue("DATOS DE BUSQUEDA");
		titleCell.setCellStyle(styles.get("title1"));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$" + (rowNum + 1) + ":$F$" + (rowNum + 1)));

		// header row
		rowNum += 1;
		Row headerRow = sheet.createRow(rowNum);
		Cell headerCell;
		for (int ii = 0; ii < tituloBusquedaMW.size(); ii++) {
			headerCell = headerRow.createCell(ii);
			headerCell.setCellValue(tituloBusquedaMW.get(ii));
			headerCell.setCellStyle(styles.get("header1"));
		}

		rowNum += 1;
		Row row = sheet.createRow(rowNum);
		for (int k = 0; k < tituloBusquedaMW.size(); k++) {
			Cell cell = row.createCell(k);
			cell.setCellStyle(styles.get("cell"));
		}

		row.getCell(0).setCellValue(incidenciasFor.getFechaIncidencia());
		row.getCell(1).setCellValue(incidenciasFor.getDatosBusqueda().getNombre());
		row.getCell(Constant.INT_2).setCellValue(incidenciasFor.getDatosBusqueda().getApellidoPaterno());
		row.getCell(Constant.INT_3).setCellValue(incidenciasFor.getDatosBusqueda().getApellidoMaterno());
		row.getCell(Constant.INT_4).setCellValue(incidenciasFor.getDatosBusqueda().getFechaNacimiento());
		row.getCell(Constant.INT_5).setCellValue(incidenciasFor.getSucursal());

		rowNum += Constant.INT_2;
		// title row
		Row titleRow2 = sheet.createRow(rowNum);
		titleRow2.setHeightInPoints(Constant.INT_11);
		Cell titleCell2 = titleRow2.createCell(0);
		// Titulos de tablas lista negra
		titleCell2.setCellValue("RESULTADOS " + incidenciasFor.getTipoIncidencia());
		titleCell2.setCellStyle(styles.get("title3"));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$" + (rowNum + 1) + ":$E$" + (rowNum + 1)));

		// header row
		rowNum += 1;
		Row headerRow2 = sheet.createRow(rowNum);
		Cell headerCell2;
		for (int ii = 0; ii < tituloResultadosLN.size(); ii++) {
			headerCell2 = headerRow2.createCell(ii);
			headerCell2.setCellValue(tituloResultadosLN.get(ii));
			headerCell2.setCellStyle(styles.get("header3"));
		}

		// Se acota el numero de iteraciones para evitar loops no acotados por el
		// tamano de la lista de resultados (Unchecked_Input_for_Loop_Condition, CWE-606).
		int totalResultados = Math.min(incidenciasFor.getResultados().size(), Constant.MAX_RESULTADOS_INCIDENCIA);
		for (int k = 0; k < totalResultados; k++) {
			rowNum += 1;
			Row row2 = sheet.createRow(rowNum);
			for (int k1 = 0; k1 < tituloBusquedaMW.size(); k1++) {
				Cell cell = row2.createCell(k1);
				cell.setCellStyle(styles.get("cell"));
			}
			row2.getCell(0).setCellValue(
					incidenciasFor.getResultados().get(k).getTipoLista().getDescripcion());
			row2.getCell(1).setCellValue(incidenciasFor.getResultados().get(k).getNombres());
			row2.getCell(Constant.INT_2).setCellValue(incidenciasFor.getResultados().get(k).getApellidos());
			row2.getCell(Constant.INT_3).setCellValue(incidenciasFor.getResultados().get(k).getFechaNacimiento());
			row2.getCell(Constant.INT_4)
					.setCellValue(incidenciasFor.getResultados().get(k).getPorcentajeCoincidencia());
		}

		rowNum += Constant.INT_3;

		// Establecer el ancho de las columnas, el ancho se mide en unidades de 1/256th
		// de un ancho de caracteres - 30 characters wide
		sheet.setColumnWidth(0, Constant.INT_100 * Constant.INT_256);
		for (int n = 0; n < (tituloBusquedaMW.size() + tituloResultadosLN.size()); n++) {
			// 20 characters wide
			sheet.setColumnWidth(n, Constant.INT_20 * Constant.INT_256);
		}

	}
	
	/**
	 * Create a library of cell styles
	 * 
	 * @param wb - libro de excel de lista de incidencias por fecha
	 * @return Retorna arreglo de estilos excel
	 */
	public Map<String, CellStyle> createStyles(Workbook wb) {

		Map<String, CellStyle> styles2 = new HashMap<>();
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
		styles2.put("title1", style);

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
		styles2.put("title3", style);

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
		styles2.put("header1", style);

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
		styles2.put("header3", style);

		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setWrapText(true);
		styles2.put("cell", style);

		return styles2;
	}

}
