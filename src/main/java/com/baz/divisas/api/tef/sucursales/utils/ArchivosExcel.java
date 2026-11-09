package com.baz.divisas.api.tef.sucursales.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.web.multipart.MultipartFile;

import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.tef.general.model.MontosLimite;
import com.baz.divisas.api.tef.general.model.TC;
import com.baz.divisas.api.tef.limites.dao.LimitesPorSucursalDao;
import com.baz.divisas.api.tef.limites.dto.LimitesPorSucursalDto;
import com.baz.divisas.api.tef.promoespacio.services.PromoEspacioServiceImpl;
import com.baz.divisas.api.tef.sucursales.dao.SucursalesDao;
import com.baz.divisas.api.tef.sucursales.dto.ReqActSucursal;
import com.baz.divisas.api.tef.sucursales.model.Divisa;
import com.baz.divisas.api.tef.sucursales.model.ResponseSucursalesMasivas;
import com.baz.divisas.api.tef.sucursales.model.Sucursal;
import com.mongodb.client.model.WriteModel;
import com.baz.divisas.general.config.PropApp;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.LogApp;

/**
 * The Class ArchivosExcel.
 */
public class ArchivosExcel {

	/** The sucursales dao. */
	private SucursalesDao sucursalesDao;
	/** The promo espacio impl. */
	private PromoEspacioServiceImpl promoEspacioImpl;
	/** The limites por sucursal dao. */
	private LimitesPorSucursalDao limitesPorSucursalDao;

	/** The bitacora. */
	private BitacoraServiceImpl bitacora;
	
	/**
	 * Constructor para pruebas unitarias.
	 *
	 * @param sucursalesDao the sucursales dao
	 * @param promoEspacioImpl the promo espacio impl
	 * @param limitesPorSucursalDao the limites por sucursal dao
	 * @param bitacora the bitacora
	 */
	@Autowired
	public ArchivosExcel(SucursalesDao sucursalesDao,PromoEspacioServiceImpl promoEspacioImpl,LimitesPorSucursalDao limitesPorSucursalDao,BitacoraServiceImpl bitacora) {
		super();
		this.sucursalesDao = sucursalesDao;
		this.promoEspacioImpl = promoEspacioImpl; 
		this.limitesPorSucursalDao = limitesPorSucursalDao;	
		this.bitacora = bitacora;
	}

	/**
	 * Metodo para leer datos de un archivo Excel.
	 *
	 * @param file - archivo a leer
	 * @return valor int con el total de registros actualizados
	 */
	public ResponseApi readDataSucursales(InputStreamSource file) {
		ResponseApi response = null;
		try (Workbook archivo = WorkbookFactory.create(file.getInputStream())) {
			// Se lee la primera hoja de trabajo del archivo Excel
			Sheet hoja = archivo.getSheetAt(0);

			// Se lee la primera fila con los encabezados
			Row cabecero = hoja.getRow(0);
			List<String> missingFields = new ArrayList<>();

			// Se crea el Map con los encabezados
			HashMap<String, Integer> mapaDeColumnas = new HashMap<>();

			for (Cell cell : cabecero) {
				String nombreCabecero = cell.getStringCellValue().toLowerCase(Locale.getDefault());
				int indexColumna = cell.getColumnIndex();
				mapaDeColumnas.put(nombreCabecero, indexColumna);
			}

			// Nombres de los encabezados del Layout
			String[] headersRequeridos = { "x", "sucursal", "regíon", "divisa usd", "tc sucursal compra usd",
					"estrategia compra usd", "tc sucursal venta usd", "estrategia venta usd", "divisa eur",
					"tc sucursal compra eur", "estrategia compra eur", "tc sucursal venta eur", "estrategia venta eur",
					"divisa cad", "tc sucursal compra cad", "estrategia compra cad", "tc sucursal venta cad",
					"estrategia venta cad", "divisa gbp", "tc sucursal compra gbp", "estrategia compra gbp",
					"tc sucursal venta gbp", "estrategia venta gbp", "nombre", "tipo", "municipio", "estado", "calle",
					"número", "cp", "número de región" };

			// Se verifica si existen los encabezados requeridos
			for (String header : headersRequeridos) {
				int columnIndex = mapaDeColumnas.getOrDefault(header, -1);
				if (columnIndex == -1) {
					missingFields.add(header);
				}
			}

			// Si no existe alguno de los encabezados se agrega al mensaje de error
			if (!missingFields.isEmpty()) {
				return new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
						Arrays.asList("Error: no se encontraron los campos: " + String.join(",", missingFields)));
			}
			
			List<ReqActSucursal> sucursales = Collections.synchronizedList(new ArrayList<>());
			List<String> errores = new ArrayList<>();
			List<String> sucursalesPromo = Collections.synchronizedList(new ArrayList<>());
			// Se obtienen los datos de una fila y se actualiza la sucursal
			obtenerTCSucursales(hoja, mapaDeColumnas, sucursales, errores, sucursalesPromo);
			
			actualizaTCMasivo(sucursales, errores);
				
			if (!errores.isEmpty()) {
				sucursalesPromo.removeAll(errores);
			}
			
			String sucursalMM = sucursalesPromo.stream()
					.filter(element -> PropApp.getSucursalMM().equals(element)).findFirst().orElse(null);
			
			if(null != sucursalMM) {
				promoEspacioImpl.registraTCMM();
			}

			if (sucursalesPromo.size() > 0) {
				new Thread(() -> {
					promoEspacioImpl.armaTCSucursal(sucursalesPromo);
				}).start();
			}
			ResponseSucursalesMasivas res;
			if (!errores.isEmpty()) {
				// Entidad nueva con sucursalesExito y sucursales error
				res = new ResponseSucursalesMasivas(String.valueOf(sucursalesPromo.size()), errores);
				return new ResponseApi(EstatusResponse.OK, res);
			}
			res = new ResponseSucursalesMasivas(String.valueOf(sucursalesPromo.size()), null);
			return new ResponseApi(EstatusResponse.OK, res);

		} 
		catch (InterruptedException e) {
			LogApp.excepcion(e, "Ocurrio un error al intentar leer el archivo");
			response = new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("Error: formato de archivo incorrecto, favor de verificar"));
			Thread.currentThread().interrupt();
		}
	 	catch (Exception  e) {
		LogApp.excepcion(e, "Ocurrio un error al intentar leer el archivo");
		response = new ResponseApi(EstatusResponse.ERROR_INTERNO,
				Arrays.asList("Error: formato de archivo incorrecto, favor de verificar"));
		}

		return response;
	}

	/**
	 * Actualiza TC masivo.
	 *
	 * @param sucursales the sucursales
	 */
	/** Tamano de lote para las escrituras masivas (bulkWrite). */
	private static final int TAMANO_LOTE_BULK = 1000;

	private void actualizaTCMasivo(List<ReqActSucursal> sucursales, List<String> sucursalesError) {

		// Set con busqueda O(1) en lugar de List.contains() O(n) para evitar comportamiento O(n^2)
		Set<String> idsConError = new HashSet<>(sucursalesError);

		// Ids a procesar (excluyendo los que ya tienen error)
		List<String> idsAProcesar = sucursales.stream()
				.map(ReqActSucursal::getIdsucursal)
				.filter(id -> !idsConError.contains(id))
				.distinct()
				.collect(Collectors.toList());

		// Consulta batch: una sola llamada a BD en lugar de N consultas (evita N+1)
		Map<String, Sucursal> sucursalesBD = sucursalesDao.consultaSucursales(idsAProcesar).stream()
				.collect(Collectors.toMap(Sucursal::getId, s -> s, (existente, nuevo) -> existente));

		// Se construyen todas las operaciones en memoria para ejecutarlas por lotes (bulkWrite)
		List<WriteModel<Sucursal>> operaciones = new ArrayList<>();

		for (ReqActSucursal sucursal : sucursales) {

			String idSucursal = sucursal.getIdsucursal();

			// Se omiten las sucursales que ya tienen error
			if (idsConError.contains(idSucursal)) {
				continue;
			}

			Sucursal sucuDoc = sucursalesBD.get(idSucursal);

			WriteModel<Sucursal> operacion;
			if (null == sucuDoc) {
				// Alta de divisas
				operacion = sucursalesDao.construirCrearDivisas(sucursal);
			}
			else {
				// Actualizacion de TC + campos de archivo en una sola operacion.
				// Devuelve null si la diferencia de compra/venta supera el limite (error).
				operacion = sucursalesDao.construirUpdateTC(sucursal, sucuDoc);
				if (null == operacion) {
					// Se registra el error una sola vez (el Set evita duplicados)
					idsConError.add(idSucursal);
				}
			}

			if (null != operacion) {
				operaciones.add(operacion);

				// Flush por lotes para acotar memoria y tamano de request
				if (operaciones.size() >= TAMANO_LOTE_BULK) {
					sucursalesDao.ejecutarBulkSucursales(operaciones);
					operaciones.clear();
				}
			}
		}

		// Se ejecuta el ultimo lote pendiente
		sucursalesDao.ejecutarBulkSucursales(operaciones);

		// Se sincronizan los errores acumulados con la lista original sin duplicados
		sucursalesError.clear();
		sucursalesError.addAll(idsConError);
	}

	/**
	 * Obtener TC sucursales.
	 *
	 * @param sheet          the sheet
	 * @param columnIndexMap the column index map
	 * @param sucursales     the sucursales
	 * @param errores the errores
	 * @param sucursalesPromoEspacio the sucursales promo
	 * @throws InterruptedException the interrupted exception
	 */
	private void obtenerTCSucursales(Sheet sheet, HashMap<String, Integer> columnIndexMap,
			List<ReqActSucursal> sucursales, List<String> errores, List<String> sucursalesPromoEspacio)
			throws InterruptedException {
		// Ciclo para obtener los registros del archivo de Excel
		ExecutorService ejecutor = Executors.newFixedThreadPool(4);

		Iterator<Row> iteradorRenglon = sheet.iterator();
		List<Row> listaActualizada = new ArrayList<>();
		iteradorRenglon.forEachRemaining(listaActualizada::add);
		listaActualizada.stream().forEach((Row row) -> {
			// for (Row row : sheet) {
			ejecutor.execute(() -> {

				if (null != row && row.getRowNum() != 0 && null != row.getCell(0)) {
					// Validar Reglas de carga del archivo
					ReqActSucursal ReqSucu = new ReqActSucursal();
					ReqSucu.setIdsucursal(getCellValueAsString(row.getCell(columnIndexMap.get("sucursal"))).trim());
					sucursalesPromoEspacio.add(ReqSucu.getIdsucursal());
					List<Divisa> listaDivisas = new ArrayList<>();
					Divisa divisaUSD = new Divisa();
					divisaUSD.setIdDivisa(getCellValueAsString(row.getCell(columnIndexMap.get("divisa usd"))).trim());

					// ----- USD
					TC spreadUSD = new TC();
					spreadUSD.setCompra(Double
							.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("estrategia compra usd")))));
					spreadUSD.setVenta(Double
							.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("estrategia venta usd")))));
					divisaUSD.setSpread(spreadUSD);

					TC spotUSD = new TC();
					spotUSD.setCompra(Double
							.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("tc sucursal compra usd")))));
					spotUSD.setVenta(Double
							.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("tc sucursal venta usd")))));
					divisaUSD.setTc(spotUSD);
					if (spotUSD.getCompra() > spotUSD.getVenta()) {
						errores.add(ReqSucu.getIdsucursal());
					}

					listaDivisas.add(divisaUSD);

					// ----- CAD
					Divisa divisaCAD = new Divisa();
					divisaCAD.setIdDivisa(getCellValueAsString(row.getCell(columnIndexMap.get("divisa cad"))).trim());

					TC spreadCAD = new TC();
					spreadCAD.setCompra(Double
							.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("estrategia compra cad")))));
					spreadCAD.setVenta(Double
							.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("estrategia venta cad")))));
					divisaCAD.setSpread(spreadCAD);

					TC spotCAD = new TC();
					spotCAD.setCompra(Double
							.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("tc sucursal compra cad")))));
					spotCAD.setVenta(Double
							.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("tc sucursal venta cad")))));
					divisaCAD.setTc(spotCAD);

					if (spotCAD.getCompra() > spotCAD.getVenta()) {
						errores.add(ReqSucu.getIdsucursal());
					}

					listaDivisas.add(divisaCAD);

					// ----- EURO
					Divisa divisaEUR = new Divisa();
					divisaEUR.setIdDivisa(getCellValueAsString(row.getCell(columnIndexMap.get("divisa eur"))).trim());

					TC spreadEUR = new TC();
					spreadEUR.setCompra(Double
							.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("estrategia compra eur")))));
					spreadEUR.setVenta(Double
							.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("estrategia venta eur")))));
					divisaEUR.setSpread(spreadEUR);

					TC spotEUR = new TC();
					spotEUR.setCompra(Double
							.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("tc sucursal compra eur")))));
					spotEUR.setVenta(Double
							.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("tc sucursal venta eur")))));
					divisaEUR.setTc(spotEUR);

					if (spotEUR.getCompra() > spotEUR.getVenta()) {
						errores.add(ReqSucu.getIdsucursal());
					}

					listaDivisas.add(divisaEUR);

					// ----- LIBRA ESTERLINA
					Divisa divisaGBP = new Divisa();
					divisaGBP.setIdDivisa(getCellValueAsString(row.getCell(columnIndexMap.get("divisa gbp"))).trim());

					TC spreadGBP = new TC();
					spreadGBP.setCompra(Double
							.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("estrategia compra gbp")))));
					spreadGBP.setVenta(Double
							.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("estrategia venta gbp")))));
					divisaGBP.setSpread(spreadGBP);

					TC spotGBP = new TC();
					spotGBP.setCompra(Double
							.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("tc sucursal compra gbp")))));
					spotGBP.setVenta(Double
							.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("tc sucursal venta gbp")))));
					divisaGBP.setTc(spotGBP);

					if (spotGBP.getCompra() > spotGBP.getVenta()) {
						errores.add(ReqSucu.getIdsucursal());
					}

					listaDivisas.add(divisaGBP);

					ReqSucu.setDivisas(listaDivisas);

					ReqSucu.setRegionSucursalArchivo(valueAsString(row.getCell(columnIndexMap.get("regíon"))));

					ReqSucu.setNombreSucursalArchivo(valueAsString(row.getCell(columnIndexMap.get("nombre"))));

					ReqSucu.setTipoSucursalArchivo(valueAsString(row.getCell(columnIndexMap.get("tipo"))));

					ReqSucu.setMunicipioSucursalArchivo(valueAsString(row.getCell(columnIndexMap.get("municipio"))));

					ReqSucu.setEstadoSucursalArchivo(valueAsString(row.getCell(columnIndexMap.get("estado"))));

					ReqSucu.setCalleSucursalArchivo(valueAsString(row.getCell(columnIndexMap.get("calle"))));

					ReqSucu.setNumeroSucursalArchivo(valueAsString(row.getCell(columnIndexMap.get("número"))));

					ReqSucu.setCpSucursalArchivo(valueAsString(row.getCell(columnIndexMap.get("cp"))));

					ReqSucu.setNumeroRegionArchivo(valueAsString(row.getCell(columnIndexMap.get("número de región"))));

					sucursales.add(ReqSucu);
				}
			});

		});
		ejecutor.shutdown();
		ejecutor.awaitTermination(1, TimeUnit.HOURS);

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
	 * Gets the cell value as string.
	 *
	 * @param cell the cell
	 * @return the cell value as string
	 */
	private static String valueAsString(Cell cell) {
	
		String cellValue = null;

		if (cell.getCellType() == CellType.STRING) {
			// Si es de tipo String, obtenemos el valor directamente
			cellValue = cell.getStringCellValue();
		} 
		else {
			cellValue = "OTRO";
		}

		return cellValue;
	}
	
	/**
	 * Read data sucursales OLP.
	 *
	 * @param file the file
	 * @return the response api
	 */
	public ResponseApi readDataSucursalesOlp(MultipartFile file) {
		ResponseApi respOLP = null;
		try (Workbook workbookOLP = WorkbookFactory.create(file.getInputStream())) {
			// Se lee la primera hoja de trabajo del archivo Excel
			Sheet sheetOLP = workbookOLP.getSheetAt(0);

			// Se lee la primera fila con los encabezados
			Row headerRowOLP = sheetOLP.getRow(0);
			List<String> missingFieldsOLP = new ArrayList<>();

			// Se crea el Map con los encabezados
			HashMap<String, Integer> columnIndexMapOLP = new HashMap<>();

			for (Cell cellOLP : headerRowOLP) {
				String headerNameOLP = cellOLP.getStringCellValue().toLowerCase(Locale.getDefault());
				int columnIndexOLP = cellOLP.getColumnIndex();
				columnIndexMapOLP.put(headerNameOLP, columnIndexOLP);
			}

			// Nombres de los encabezados del Layout
			String[] requiredHeadersOLP = { "sucursal", "divisa", "tc sucursal compra",
					"estrategia compra", "tc sucursal venta", "estrategia venta"};

			// Se verifica si existen los encabezados requeridos
			for (String header : requiredHeadersOLP) {
				int columnIndex = columnIndexMapOLP.getOrDefault(header, -1);
				if (columnIndex == -1) {
					missingFieldsOLP.add(header);
				}
			}

			// Si no existe alguno de los encabezados se agrega al mensaje de error
			if (!missingFieldsOLP.isEmpty()) {
				return new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
						Arrays.asList("Error: no se encontraron los campos: " + String.join(",", missingFieldsOLP)));
			}
			
			List<ReqActSucursal> sucursales = Collections.synchronizedList(new ArrayList<>());
			List<String> errores = new ArrayList<>();
			List<String> sucursalesPromo = new ArrayList<>();
			// Se obtienen los datos de una fila y se actualiza la sucursal
			obtenerTCSucursalesOlp(sheetOLP, columnIndexMapOLP, sucursales, errores,sucursalesPromo);
			if (!errores.isEmpty()) {
				return new ResponseApi(EstatusResponse.PETICION_INCORRECTA, errores);
			}
			actualizaTCMasivoOlp(sucursales);
			//promoEspacioImpl.informarSucursalesPromoOLP(sucursalesPromo);
			promoEspacioImpl.armaTCSucursal(sucursalesPromo);
			return new ResponseApi(EstatusResponse.OK);

		} 
		catch (InterruptedException e) {
			LogApp.excepcion(e, "Ocurrio un error al intentar leer el archivo");
			respOLP = new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("Error: formato de archivo incorrecto, favor de verificar"));
			Thread.currentThread().interrupt();
		}
		catch (Exception e) {
			LogApp.excepcion(e, "Ocurrio un error al intentar leer el archivo");
			respOLP = new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("Error: formato de archivo incorrecto, favor de verificar"));
		}

		return respOLP;
	}

	/**
	 * Obtener TC sucursales olp.
	 *
	 * @param sheet the sheet
	 * @param columnIndexMap the column index map
	 * @param sucursales the sucursales
	 * @param errores the errores
	 * @param sucursalesPromo the sucursales promo
	 * @throws InterruptedException the interrupted exception
	 */
	private void obtenerTCSucursalesOlp(Sheet sheet, HashMap<String, Integer> columnIndexMap,
			List<ReqActSucursal> sucursales, List<String> errores, List<String> sucursalesPromo) throws InterruptedException {
		// Ciclo para obtener los registros del archivo de Excel		
		ExecutorService executor = Executors.newFixedThreadPool(4);

		Iterator<Row> rowIterator = sheet.iterator();
		List<Row> actualList = new ArrayList<>();
		rowIterator.forEachRemaining(actualList::add);
		actualList.stream().forEach((Row row) -> {
			executor.execute(() -> {

				if (null != row && row.getRowNum() != 0 && null != row.getCell(0)) {
					// Validar Reglas de carga del archivo
					ReqActSucursal sucu = new ReqActSucursal();
					sucu.setIdsucursal(getCellValueAsString(row.getCell(columnIndexMap.get("sucursal"))).trim());
					sucursalesPromo.add(sucu.getIdsucursal());

					List<Divisa> divisas = new ArrayList<>();
					Divisa divisaOLP = new Divisa();
					divisaOLP.setIdDivisa(getCellValueAsString(row.getCell(columnIndexMap.get("divisa"))).trim());

					// ----- OLP
					TC spreadOLP = new TC();
					spreadOLP.setCompra(
							Double.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("estrategia compra")))));
					spreadOLP.setVenta(
							Double.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("estrategia venta")))));
					divisaOLP.setSpread(spreadOLP);

					if (Math.abs(spreadOLP.getCompra()) > 300.0) {
						errores.add("El Spread de compra es mayor a 3 pesos de la divisa OLP para la sucursal : "
								+ sucu.getIdsucursal());
					}
					if (Math.abs(spreadOLP.getVenta()) > 300.0) {
						errores.add("El Spread de venta es mayor a 3 pesos de la divisa OLP para la sucursal : "
								+ sucu.getIdsucursal());
					}
					TC spotOLP = new TC();
					spotOLP.setCompra(Double
							.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("tc sucursal compra")))));
					spotOLP.setVenta(
							Double.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("tc sucursal venta")))));
					divisaOLP.setTc(spotOLP);
					if (spotOLP.getCompra() > spotOLP.getVenta()) {
						errores.add("El TC de compra es mayor al TC de la venta de la divisa OLP para la sucursal : "
								+ sucu.getIdsucursal());
					}

					divisas.add(divisaOLP);

					sucu.setTcOlp(divisaOLP);

					sucursales.add(sucu);
				}				
			});

		});
		executor.shutdown(); 
		executor.awaitTermination(1, TimeUnit.HOURS);

	}

	/**
	 * Actualiza TC masivo olp.
	 *
	 * @param sucursales the sucursales
	 */
	private void actualizaTCMasivoOlp(List<ReqActSucursal> sucursales) {

		// Ids unicos a procesar
		List<String> idsAProcesar = sucursales.stream()
				.map(ReqActSucursal::getIdsucursal)
				.distinct()
				.collect(Collectors.toList());

		// Consulta batch: una sola llamada a BD para saber que sucursales ya tienen tcOlp (evita N+1)
		Set<String> idsConOlp = sucursalesDao.consultaSucursalesConOlp(idsAProcesar);

		// Se construyen todas las operaciones en memoria para ejecutarlas por lotes (bulkWrite)
		List<WriteModel<Sucursal>> operaciones = new ArrayList<>();

		for (ReqActSucursal sucursal : sucursales) {

			boolean existeOlp = idsConOlp.contains(sucursal.getIdsucursal());

			// Una sola operacion por sucursal: crea (upsert) o actualiza compra+venta a la vez
			operaciones.add(sucursalesDao.construirUpdateOlp(sucursal, existeOlp));

			// Flush por lotes para acotar memoria y tamano de request
			if (operaciones.size() >= TAMANO_LOTE_BULK) {
				sucursalesDao.ejecutarBulkSucursales(operaciones);
				operaciones.clear();
			}
		}

		// Se ejecuta el ultimo lote pendiente
		sucursalesDao.ejecutarBulkSucursales(operaciones);
	}
	
	//Carga de Limites masivos
	

	/**
	 * Lectura limites.
	 *
	 * @param file the file
	 * @return the response api
	 */
	public ResponseApi lecturaLimites(InputStreamSource file) {
		ResponseApi respLimites = new ResponseApi(EstatusResponse.NO_CONTENT);
		try (Workbook workbookLimites = WorkbookFactory.create(file.getInputStream())) {
			// Se lee la primera hoja de trabajo del archivo Excel
			Sheet sheetLimites = workbookLimites.getSheetAt(0);

			// Se lee la primera fila con los encabezados
			Row headerRowLimites = sheetLimites.getRow(0);
			List<String> missingFieldsLimites = new ArrayList<>();

			// Se crea el Map con los encabezados
			HashMap<String, Integer> columnIndexMapLimites = new HashMap<>();

			for (Cell cellLimites : headerRowLimites) {
				String headerNameLimites = cellLimites.getStringCellValue().toLowerCase(Locale.getDefault());
				int columnIndexLimites = cellLimites.getColumnIndex();
				columnIndexMapLimites.put(headerNameLimites, columnIndexLimites);
			}

			// Nombres de los encabezados del Layout
			String[] requiredHeadersLimites = { "sucursal", "divisa", "tipo de operación", "límite diario cliente",
					"límite diario usuario", "límite mensual cliente", "límite mensual usuario","límite diario cliente extranjero",
					"límite diario usuario extranjero", "límite mensual cliente extranjero", "límite mensual usuario extranjero" };

			// Se verifica si existen los encabezados requeridos
			for (String header : requiredHeadersLimites) {
				int columnIndex = columnIndexMapLimites.getOrDefault(header, -1);
				if (columnIndex == -1) {
					missingFieldsLimites.add(header);
				}
			}

			// Si no existe alguno de los encabezados se agrega al mensaje de error
			if (!missingFieldsLimites.isEmpty()) {
				return new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
						Arrays.asList("Error: no se encontraron los campos: " + String.join(",", missingFieldsLimites)));
			}
			
			List<LimitesPorSucursalDto> sucursales = Collections.synchronizedList(new ArrayList<>());
			List<String> errores = new ArrayList<>();
			// Se obtienen los datos de una fila y se actualiza la sucursal
			obtenerLimitesSucursales(sheetLimites, columnIndexMapLimites, sucursales, errores);
			if (!errores.isEmpty()) {
				return new ResponseApi(EstatusResponse.PETICION_INCORRECTA, errores);
			}
			
			actualizaLimitesMasivos(sucursales);
			
			return new ResponseApi(EstatusResponse.OK);		
		} 
		catch (InterruptedException e) {
			LogApp.excepcion(e, "Ocurrio un error al intentar leer el archivo");
			respLimites = new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("Error: formato de archivo incorrecto, favor de verificar"));
			Thread.currentThread().interrupt();
		}
		catch (Exception e) {
			LogApp.excepcion(e, "Ocurrio un error al intentar leer el archivo");
			respLimites = new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("Error: formato de archivo incorrecto, favor de verificar"));
		}

		return respLimites;
	}

	/**
	 * Obtener limites sucursales.
	 *
	 * @param sheet the sheet
	 * @param columnIndexMap the column index map
	 * @param sucursales the sucursales
	 * @param errores the errores
	 * @throws InterruptedException the interrupted exception
	 */
	private void obtenerLimitesSucursales(Sheet sheet, HashMap<String, Integer> columnIndexMap,
			List<LimitesPorSucursalDto> sucursales, List<String> errores) throws InterruptedException {
		// Ciclo para obtener los registros del archivo de Excel		
		ExecutorService executor = Executors.newFixedThreadPool(4);

		Iterator<Row> rowIterator = sheet.iterator();
		List<Row> actualList = new ArrayList<>();
		rowIterator.forEachRemaining(actualList::add);
		actualList.stream().forEach((Row row) -> {
			executor.execute(() -> {

				if (null != row && row.getRowNum() != 0 && null != row.getCell(0)) {
					// Validar Reglas de carga del archivo
					LimitesPorSucursalDto sucu = new LimitesPorSucursalDto();
					sucu.setActivo(true);
					sucu.setIdSucursal(getCellValueAsString(row.getCell(columnIndexMap.get("sucursal"))));
					sucu.setCodigoDivisa(getCellValueAsString(row.getCell(columnIndexMap.get("divisa"))));
					sucu.setIdTipoOperacion(getCellValueAsString(row.getCell(columnIndexMap.get("tipo de operación"))));					

					MontosLimite diario = new MontosLimite();
					diario.setCliente(Double.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("límite diario cliente")))));
					diario.setUsuario(Double.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("límite diario usuario")))));
					diario.setClienteExtranjero(Double.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("límite diario cliente extranjero")))));
					diario.setUsuarioExtranjero(Double.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("límite diario usuario extranjero")))));
					sucu.setDiario(diario);

					MontosLimite mensual = new MontosLimite();
					mensual.setCliente(Double.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("límite mensual cliente")))));
					mensual.setUsuario(Double.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("límite mensual usuario")))));
					mensual.setClienteExtranjero(Double.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("límite mensual cliente extranjero")))));
					mensual.setUsuarioExtranjero(Double.valueOf(getCellValueAsString(row.getCell(columnIndexMap.get("límite mensual usuario extranjero")))));
					sucu.setMensual(mensual);

					sucursales.add(sucu);
				}				
			});

		});
		executor.shutdown(); 
		executor.awaitTermination(1, TimeUnit.HOURS);

	}
	
	/**
	 * Actualiza limites masivos.
	 *
	 * @param sucursales the sucursales
	 */
	private void actualizaLimitesMasivos(List<LimitesPorSucursalDto> sucursales) {

		sucursales.stream().forEach((LimitesPorSucursalDto sucursal) -> { 
			List<LimitesPorSucursalDto> limite = limitesPorSucursalDao.findLimitesSucursalId(sucursal);

			if (limite.isEmpty()) {
				
				sucursal.setIdBitacoraAlta(bitacora.registrar(AccionBitacora.REGISTRAR,
						"Registro del limites de sucursal " + sucursal.getIdSucursal(), null,
						sucursal));

				sucursal.setActivo(true);

				limitesPorSucursalDao.insertLimitesSucursal(sucursal);

			}
			else {

				sucursal.setIdBitacoraActualizacion(bitacora.registrar(AccionBitacora.REGISTRAR,
						"Registro del limites de sucursal " + sucursal.getIdSucursal(), null,
						sucursal));
				
				sucursal.setActivo(true);

				limitesPorSucursalDao.updateLimitesSucursal(sucursal);

			}			

		});


	}
}
