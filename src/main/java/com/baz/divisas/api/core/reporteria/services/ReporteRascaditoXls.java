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

import com.baz.divisas.api.core.reporteria.model.ClienteGanador;
import com.baz.divisas.api.core.reporteria.model.GanadorPromoDigital;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.LogApp;

/**
 * Clase ReporteOperacionesAppXls
 */
public class ReporteRascaditoXls {

	private Map<String, CellStyle> styles;
	private List<String> titles;
		
	private static final String TITLE1 = "title1";

	/**
	 * Instantiates a new reporte operaciones app xls.
	 *
	 */
	public ReporteRascaditoXls() {
		super();
		
		this.titles= Stream.of(			
				 "ID",
                 "CAMPAÑA",
                 "ORIGEN",
                 "CODIGO",
                 "NOMBRE",                
                 "APELLIDO PATERNO",
                 "APELLIDO MATERNO",
                 "GENERO",
                 "TELEFONO",
                 "CORREO ELECTRONICO",                 
                 "BENEFICIO",
                 "MONTO MINIMO",
                 "DIVISA",                   
                 "ESTATUS",
                 "FECHA REGISTRO",
                 "FECHA VIGENCIA",
                 "FECHA ACTUALIZACION")
                .collect(Collectors.toList());
	}

	/**
	 * Genera Excel Reporte Ganadores Rascadito Digital.
	 *
	 * @param listaGanadores - Lista de Ganadores 
	 * @return ByteArrayInputStream
	 */
	public ByteArrayInputStream generaExel(Iterable<GanadorPromoDigital> listaGanadores) {
		
		try (Workbook wb = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

			styles = createStyles(wb);
			
			int numPag = 0;
			int rowNum = 1; 
			
			Sheet sheet = createSheet(wb, numPag);

			for (GanadorPromoDigital ganador : listaGanadores) {
				
				if(ganador.getCliente() == null) {
					ganador.setCliente(new ClienteGanador());
				}
				
				if (rowNum >= Constant.INT_50000) {
					rowNum = 1;
					numPag += 1;
					sheet = createSheet(wb, numPag);

				}
				
				rowNum += 1;
				createRow(sheet, ganador, rowNum);
			}

			wb.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} 
		catch (IOException | IndexOutOfBoundsException e) {
			LogApp.excepcion(e, "Fallo al exportar ganadores a Excel");
			return null;
		}

	}

	private Sheet createSheet(Workbook wb, int numPag) {
		
		String nameSheet = "Ganadores";
		
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
		titleCell.setCellValue("BENEFICIOS RASCADITO DIGITAL");
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
	
	private void createRow(Sheet sheet, GanadorPromoDigital ganador, int rowNum) {
		
		Row row = sheet.createRow(rowNum);
		
		for (int k = 0; k < titles.size(); k++) {
			Cell cell = row.createCell(k);
			cell.setCellStyle(styles.get("cell"));
		}

		fillCell(row, Constant.INT_0, ganador.getId());
		fillCell(row, Constant.INT_1, ganador.getNombreCampania());
		fillCell(row, Constant.INT_2, ganador.getOrigen());
		fillCell(row, Constant.INT_3, ganador.getCodigo());
		fillCell(row, Constant.INT_4, ganador.getCliente().getNombre());		
		fillCell(row, Constant.INT_5, ganador.getCliente().getApellidoPaterno());
		fillCell(row, Constant.INT_6, ganador.getCliente().getApellidoMaterno());
		fillCell(row, Constant.INT_7, ganador.getCliente().getGenero());
		fillCell(row, Constant.INT_8, ganador.getCliente().getNumeroTelefono());
		fillCell(row, Constant.INT_9, ganador.getCliente().getCorreoElectronico());
		fillCell(row, Constant.INT_10, ganador.getBeneficio());
		fillCell(row, Constant.INT_11, ganador.getMontoMinimoOperacion());
		fillCell(row, Constant.INT_12, ganador.getCodigoDivisa());
		fillCell(row, Constant.INT_13, ganador.getEstatus());
		fillCell(row, Constant.INT_14, ganador.getFechaRegistro());
		fillCell(row, Constant.INT_14, row.getCell(Constant.INT_14).getStringCellValue().replace("T", " "));
		fillCell(row, Constant.INT_15, ganador.getVigencia());
		fillCell(row, Constant.INT_15, row.getCell(Constant.INT_15).getStringCellValue().replace("T23:59:59.000", ""));
		fillCell(row, Constant.INT_16, ganador.getFechaActualizacion());
		fillCell(row, Constant.INT_16, row.getCell(Constant.INT_16).getStringCellValue().replace("T", " "));

		sheet.setColumnWidth(0, Constant.INT_40 * Constant.INT_256);
		for (int n = 1; n < titles.size(); n++) {
			sheet.setColumnWidth(n, Constant.INT_20 * Constant.INT_256);
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
