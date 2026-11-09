package com.baz.divisas.api.core.sucursales.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;

import com.baz.divisas.api.core.sucursales.dao.CertificacionDao;
import com.baz.divisas.api.core.sucursales.model.BeanSucursales;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.LogApp;

import static com.baz.divisas.api.core.sucursales.utils.Constantes.*;

/**
 * Clase LectorArchivoExcel.
 *
 * @author 1096526
 */
public final class LectorArchivoExcel {

	/** The certificacion dao. */
	private CertificacionDao certificacionDao;
	

	/**
	 * Constructor para pruebas unitarias.
	 *
	 * @param certificacionDao - Mock
	 */
	@Autowired
	public LectorArchivoExcel(CertificacionDao certificacionDao) {
		super();
		this.certificacionDao = certificacionDao;
	}

	/**
	 * Metodo para leer datos de un archivo Excel.
	 *
	 * @param file - archivo a leer
	 * @return valor int con el total de registros actualizados
	 */
	public ResponseApi readDataFromColumns(InputStreamSource file) {
		ResponseApi resp = null;
		try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
			// Se lee la primera hoja de trabajo del archivo Excel
			Sheet sheet = workbook.getSheetAt(0);

			// Se lee la primera fila con los encabezados
			Row headerRow = sheet.getRow(0);
			List<String> missingFields = new ArrayList<>();

			// Se crea el Map con los encabezados
			HashMap<String, Integer> columnIndexMap = new HashMap<>();

			for (Cell cell : headerRow) {
				String headerName = cell.getStringCellValue().toLowerCase(Locale.getDefault());
				int columnIndex = cell.getColumnIndex();
				columnIndexMap.put(headerName, columnIndex);
			}

			// Nombres de los encabezados del Layout
			String[] requiredHeaders = {SUCURSAL, CONTACTOSUCURSAL, GERENTE, CONTACTOGERENTE,
					IMPLJR, IMPLSR, TERRITORIAL, REGIONAL, APPGEO, REGLIM, PRESUPUESTO, IMPLSIS,
					TIEMPOTRANS
			};

			// Se verifica si existen los encabezados requeridos
			for (String header : requiredHeaders) {
				int columnIndex = columnIndexMap.getOrDefault(header, -1);
				if (columnIndex == -1) {
					missingFields.add(header);
				}
			}

			// Si no existe alguno de los encabezados se agrega al mensaje de error
			if (!missingFields.isEmpty()) {
				return new ResponseApi(EstatusResponse.PETICION_INCORRECTA, Arrays.asList(
						"Error: no se encontraron los campos: " + String.join(",", missingFields)));
			}

			List<String> sucursalesActualizadas = new ArrayList<>();
			List<String> sucursalesNoActualizadas = new ArrayList<>();

			// Se obtienen los datos de una fila y se actualiza la sucursal
			obtenerDatos(sheet, columnIndexMap, sucursalesActualizadas, sucursalesNoActualizadas);

			StringBuilder stringBuilder = new StringBuilder();

			if (!sucursalesActualizadas.isEmpty()) {

				if (sucursalesActualizadas.size() > 1) {
					stringBuilder.append("Se actualizaron correctamente "
							+ sucursalesActualizadas.size() + " sucursales");
				}
				else {
					stringBuilder.append("Se actualizó correctamente 1 sucursal");
				}
			}

			if (!sucursalesNoActualizadas.isEmpty()) {
				if (!sucursalesActualizadas.isEmpty()) {
					stringBuilder.append(" | No se actualizaron las sucursales: ")
							.append(String.join(", ", sucursalesNoActualizadas));
				}
				else {
					stringBuilder.append("No se actualizó ninguna sucursal");
				}

			}

			return new ResponseApi(EstatusResponse.OK);

		}
		catch (Exception e) {
			LogApp.excepcion(e, "Ocurrio un error al intentar leer el archivo");
			resp = new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("Error: formato de archivo incorrecto, favor de verificar"));
		}

		return resp;
	}

	/**
	 * Gets the cell value as string.
	 *
	 * @param cell the cell
	 * @return the cell value as string
	 */
	private static String getCellValueAsString(Cell cell) {
		DataFormatter dataFormatter = new DataFormatter();
		String cellValue = null;

		if (cell.getCellType() == CellType.STRING) {
			// Si es de tipo String, obtenemos el valor directamente
			cellValue = cell.getStringCellValue();
		}
		else if (cell.getCellType() == CellType.NUMERIC) {
			// Si es de tipo numérico, formateamos el valor como texto
			cellValue = dataFormatter.formatCellValue(cell);
		}
		else {
			cellValue = "";
		}

		return cellValue;
	}

	/**
	 * Checks if is row empty.
	 *
	 * @param row the row
	 * @param indSucursal the ind sucursal
	 * @return true, if is row empty
	 */
	private static boolean isRowEmpty(Row row, int indSucursal) {

		return (row == null) || (null != getCellValueAsString(row.getCell(indSucursal))
				&& row.getCell(indSucursal).getCellType() == CellType.BLANK);

	}

	/**
	 * Obtener datos.
	 *
	 * @param sheet the sheet
	 * @param columnIndexMap the column index map
	 * @param sucursalesActualizadas the sucursales actualizadas
	 * @param sucursalesNoActualizadas the sucursales no actualizadas
	 */
	private void obtenerDatos(Sheet sheet, HashMap<String, Integer> columnIndexMap,
			List<String> sucursalesActualizadas, List<String> sucursalesNoActualizadas) {
		// Ciclo para obtener los registros del archivo de Excel
		for (Row row : sheet) {
			if ((!isRowEmpty(row, columnIndexMap.get(SUCURSAL))) && (row.getRowNum() != 0)) {
				int sucursal = Integer
						.parseInt(getCellValueAsString(row.getCell(columnIndexMap.get(SUCURSAL))));

				BeanSucursales respObtenerDireccionSucursal = certificacionDao
						.obtenerDireccionSucursal(sucursal);

				String direccion = "";
				if (respObtenerDireccionSucursal != null) {
					direccion = respObtenerDireccionSucursal.getNomCalle() + "| "
							+ respObtenerDireccionSucursal.getNumeroExterior();
				}
				Document filtro = new Document("sucursal", sucursal);
				Document datos = new Document();
				datos.append("contactoSucursal",
						getCellValueAsString(row.getCell(columnIndexMap.get(CONTACTOSUCURSAL)))
								.trim());
				datos.append("gerente",
						getCellValueAsString(row.getCell(columnIndexMap.get(GERENTE))).trim());
				datos.append("contactoGerente",
						getCellValueAsString(row.getCell(columnIndexMap.get(CONTACTOGERENTE)))
								.trim());
				datos.append("implementadorJr",
						getCellValueAsString(row.getCell(columnIndexMap.get(IMPLJR))).trim());
				datos.append("implementadorSr",
						getCellValueAsString(row.getCell(columnIndexMap.get(IMPLSR))).trim());
				datos.append("territorial",
						getCellValueAsString(row.getCell(columnIndexMap.get(TERRITORIAL))).trim());
				datos.append("regional",
						getCellValueAsString(row.getCell(columnIndexMap.get(REGIONAL))).trim());
				datos.append("regionalizacionLimites",
						getCellValueAsString(row.getCell(columnIndexMap.get(REGLIM))).trim());
				datos.append("presupuesto",
						getCellValueAsString(row.getCell(columnIndexMap.get(PRESUPUESTO))).trim());
				datos.append("implementacionDeSistemas",
						getCellValueAsString(row.getCell(columnIndexMap.get(IMPLSIS))).trim());
				datos.append("tiempoPorTransaccion",
						getCellValueAsString(row.getCell(columnIndexMap.get(TIEMPOTRANS))).trim());
				datos.append("appYGeolocalizacion",
						getCellValueAsString(row.getCell(columnIndexMap.get(APPGEO))).trim());
				datos.append("certificar", false);
				datos.append("direccion", direccion);

				if (actualizarSucursal(filtro, datos)) {
					sucursalesActualizadas.add("" + sucursal);
				}
				else {
					sucursalesNoActualizadas.add("" + sucursal);
				}

			}
		}
	}

	/**
	 * Actualizar sucursal.
	 *
	 * @param filtro the filtro
	 * @param datos the datos
	 * @return true, if successful
	 */
	private boolean actualizarSucursal(Document filtro, Document datos) {
		// Actualiza la sucursal con los datos obtenidos del documento de excel
		filtro.append("nomEstatus", "ACTIVO");
		return certificacionDao.procesaCargaMasiva(filtro, new Document("$set", datos))
				.getModifiedCount() > 0;
	}
}
