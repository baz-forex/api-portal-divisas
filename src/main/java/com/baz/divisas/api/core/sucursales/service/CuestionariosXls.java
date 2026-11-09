package com.baz.divisas.api.core.sucursales.service;

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
import org.bson.Document;

import com.baz.divisas.api.core.sucursales.dao.CertificacionDao;
import com.baz.divisas.api.core.sucursales.dto.ReqFiltroCuestionarios;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.LogApp;
import com.mongodb.client.AggregateIterable;

/**
 * Clase ReporteOperacionesAppXls
 */
public class CuestionariosXls {

	private Map<String, CellStyle> styles;
	private List<String> titles;

	private static final String TITLE1 = "title1";
	private final CertificacionDao certificacionDao;
	private int rowNum = 1;

	/**
	 * Constructor para Pruebas Unitarias
	 * 
	 * @param certificacionDao - Mock
	 */
	public CuestionariosXls(CertificacionDao certificacionDao) {
		this.certificacionDao = certificacionDao;
		this.titles = Stream.of("FECHA", "TITULO", "PREGUNTAS", "RESPUESTAS")
				.collect(Collectors.toList());
	}

	/**
	 * Metodo para obtener los cuestionarios en un archivo xlsx
	 *
	 * @param reqFiltroCuestionarios - Request de la solicitud
	 * 
	 * @return objeto ByteArrayInputStream con los datos de los cuestionarios
	 */
	public ByteArrayInputStream generaExel(ReqFiltroCuestionarios reqFiltroCuestionarios) {

		AggregateIterable<Document> cuestionarios = certificacionDao
				.getCuestionariosFiltroHis(reqFiltroCuestionarios);

		try (Workbook wb = new XSSFWorkbook();
				ByteArrayOutputStream out = new ByteArrayOutputStream()) {

			styles = createStyles(wb);

			Sheet sheet = createSheet(wb, 0, reqFiltroCuestionarios.getSucursal());

			for (Document cuestionario : cuestionarios) {
				createRow(sheet, cuestionario);
			}

			wb.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
		catch (IOException | IndexOutOfBoundsException e) {
			LogApp.excepcion(e, "Fallo al exportar ganadores a Excel");
			return null;
		}

	}

	private Sheet createSheet(Workbook wb, int numPag, String sucursal) {

		String nameSheet = "Cuestionarios";

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
		titleCell.setCellValue("Cuestionarios de la Sucursal " + sucursal);
		titleCell.setCellStyle(styles.get(TITLE1));

		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$Q$1"));

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

	private void createRow(Sheet sheet, Document cuestionario) {

		Row row = sheet.createRow(rowNum);

		for (int k = 0; k < titles.size(); k++) {
			Cell cell = row.createCell(k);
			cell.setCellStyle(styles.get("cell"));
		}

		List<Document> respuesta = cuestionario.getList("respuestas", Document.class);

		int contador = 0;

		for (Document pregunta : cuestionario.getList("preguntas", Document.class)) {
			fillCell(row, Constant.INT_0, cuestionario.getString("fechaRegistro"));
			Document titulo = cuestionario.get("titulo", Document.class);
			fillCell(row, Constant.INT_1, titulo.getString("texto"));
			fillCell(row, Constant.INT_2,
					"Pregunta " + (contador + 1) + ": " + pregunta.getString("texto"));

			String idPregunta = pregunta.get("tipoRespuesta", Document.class).getString("_id");

			if ((Integer.parseInt(idPregunta) == 2) || (Integer.parseInt(idPregunta) == 4)) {
				String resp = "";

				for (Document opcion : respuesta.get(contador).getList("opciones",
						Document.class)) {
					resp += opcion.getString("texto") + "   ";
				}

				fillCell(row, Constant.INT_3, resp);

			}
			else {
				if ((Integer.parseInt(idPregunta) == 3
						&& "0".equals(respuesta.get(contador).getString("respuesta")))) {

					fillCell(row, Constant.INT_3, respuesta.get(contador).getString("respuesta")
							+ "   Motivo: " + respuesta.get(contador).getString("motivo"));
				}
				else {
					fillCell(row, Constant.INT_3, respuesta.get(contador).getString("respuesta"));
				}

			}

			rowNum++;
			row = sheet.createRow(rowNum);
			for (int k = 0; k < titles.size(); k++) {
				Cell cell = row.createCell(k);
				cell.setCellStyle(styles.get("cell"));
			}
			contador++;
		}

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
