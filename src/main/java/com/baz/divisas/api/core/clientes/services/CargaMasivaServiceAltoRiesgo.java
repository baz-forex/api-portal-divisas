package com.baz.divisas.api.core.clientes.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baz.divisas.api.core.clientes.dao.ClientesDao;
import com.baz.divisas.api.core.clientes.model.ClienteAltoRiesgo;
import com.baz.divisas.api.core.clientes.model.Registros;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.LogApp;

@Service
public class CargaMasivaServiceAltoRiesgo {

	/** The clientes dao. */
	private ClientesDao clientesDao;
	
	@Autowired
	public CargaMasivaServiceAltoRiesgo(ClientesDao clientesDao) {
		super();
		this.clientesDao = clientesDao;
	}
	
	public ResponseApi registraClientesAltoRiesgo(MultipartFile archivo, String nombre, String descripcion) {
		ResponseApi resp = null;
		try (Workbook workbook = WorkbookFactory.create(archivo.getInputStream())) {
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
			String[] requiredHeaders = { "cliente", "nombre", "apellido paterno", "apellido materno","fecha de nacimiento" };

			// Se verifica si existen los encabezados requeridos
			for (String header : requiredHeaders) {
				int columnIndex = columnIndexMap.getOrDefault(header, -1);
				if (columnIndex == -1) {
					missingFields.add(header);
				}
			}

			// Si no existe alguno de los encabezados se agrega al mensaje de error
			if (!missingFields.isEmpty()) {
				return new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
						Arrays.asList("Error: no se encontraron los campos: " + String.join(",", missingFields)));
			}
			
			List<ClienteAltoRiesgo> clientes = Collections.synchronizedList(new ArrayList<>());
			List<String> errores = new ArrayList<>();
			
			// Se obtienen los datos de una fila y se actualiza la sucursal
			obtenerClientesAltoRiesgo(sheet, columnIndexMap, clientes, errores);
			if (!errores.isEmpty()) {
				return new ResponseApi(EstatusResponse.PETICION_INCORRECTA, errores);
			}
			
			if (clientes.size() > 500) {
				return new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
						Arrays.asList("Error: el archivo supera el máximo de 500 clientes"));
			}
			
			int clientesCargados = cargaClientesAltoRiesgo(clientes);
			
			if(clientesCargados > 0) {
				
				clientesDao.registraBitacoraClienteAltoRiesgo(nombre, descripcion, clientesCargados);
			}
			Registros registro = new Registros();
			registro.setRegistrosExitosos(clientesCargados);
			return new ResponseApi(EstatusResponse.OK, Arrays.asList(registro));

		} 
		catch (Exception  e) {
			LogApp.excepcion(e, "Ocurrio un error al intentar leer el archivo");
			resp = new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("Error: formato de archivo incorrecto, favor de verificar"));
		}

		return resp;
	}

	/**
	 * Actualiza TC masivo.
	 *
	 * @param sucursales the sucursales
	 * @return 
	 */
	private int cargaClientesAltoRiesgo(List<ClienteAltoRiesgo> clientes) {

		int i = 0;
		for(ClienteAltoRiesgo cliente : clientes) {
			boolean registrado = clientesDao.registraClienteAltoRiesgo(cliente);
			if(registrado) {
				i = i +1;
			}
		}

		return i;
	}

	/**
	 * Obtener TC sucursales.
	 *
	 * @param sheet          the sheet
	 * @param columnIndexMap the column index map
	 * @param clientes     the sucursales
	 * @param errores the errores
	 * @param sucursalesPromo the sucursales promo
	 * @throws InterruptedException the interrupted exception
	 */
	private void obtenerClientesAltoRiesgo(Sheet sheet, HashMap<String, Integer> columnIndexMap,
			List<ClienteAltoRiesgo> clientes, List<String> errores) throws InterruptedException {
		// Ciclo para obtener los registros del archivo de Excel		
		ExecutorService executor = Executors.newFixedThreadPool(4);

		Iterator<Row> rowIterator = sheet.iterator();
		List<Row> actualList = new ArrayList<>();
		rowIterator.forEachRemaining(actualList::add);
		actualList.parallelStream().forEach((Row row) -> {
			executor.execute(() -> {

				if (null != row && row.getRowNum() != 0 && null != row.getCell(0)) {
					// Validar Reglas de carga del archivo
					ClienteAltoRiesgo cliente = new ClienteAltoRiesgo();
					cliente.setId(getCellValueAsString(row.getCell(columnIndexMap.get("cliente"))).trim());
					cliente.setNombre(getCellValueAsString(row.getCell(columnIndexMap.get("nombre"))).trim());
					cliente.setApellidoPaterno(getCellValueAsString(row.getCell(columnIndexMap.get("apellido paterno"))).trim());
					cliente.setApellidoMaterno(getCellValueAsString(row.getCell(columnIndexMap.get("apellido materno"))).trim());
					cliente.setFechaNacimiento(getCellValueAsString(row.getCell(columnIndexMap.get("fecha de nacimiento"))).trim());
					if(validaFormatoCliente(cliente)) {
						clientes.add(cliente);
					}
				}				
			});

		});
		executor.shutdown(); 
		executor.awaitTermination(1, TimeUnit.HOURS);

	}

	/**
	 * Gets the cell value as string.
	 *
	 * @param cell the cell
	 * @return the cell value as string
	 */
	private static String getCellValueAsString(Cell cell) {
		String cellValue = null;

		if (cell.getCellType() == CellType.STRING) {
			// Si es de tipo String, obtenemos el valor directamente
			cellValue = cell.getStringCellValue();
		} 
		else {
			cellValue = "";
		}

		return cellValue;
	}
	
	
	private static boolean validaFormatoCliente(ClienteAltoRiesgo cliente) {
		
		boolean valido = false;
		
		String regexNombre = "^[a-zA-ZñÑ\\s]{2,45}$";
		if (cliente.getId().matches("^[0-9]{1,20}$") 
				&& cliente.getNombre().matches(regexNombre)
				&& cliente.getApellidoPaterno().matches(regexNombre)
				&& cliente.getApellidoMaterno().matches(regexNombre)
				&& cliente.getFechaNacimiento().matches("^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$")) {
		
			valido = true;
		}
		return valido;
	}

}
