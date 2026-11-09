package com.baz.divisas.api.tef.sucursales.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.tef.limites.dao.LimitesPorSucursalDao;
import com.baz.divisas.api.tef.limites.dto.LimitesPorSucursalDto;
import com.baz.divisas.api.tef.promoespacio.services.PromoEspacioServiceImpl;
import com.baz.divisas.api.tef.sucursales.dao.SucursalesDao;
import com.baz.divisas.api.tef.sucursales.model.Sucursal;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ArchivosExcelTest {

	@InjectMocks
	private ArchivosExcel archivosExcel;

	@Mock
	private SucursalesDao sucursalesDao;
	@Mock
	private PromoEspacioServiceImpl promoEspacioImpl;
	@Mock
	private LimitesPorSucursalDao limitesPorSucursalDao;
	@Mock
	private BitacoraServiceImpl bitacora;

	// Headers del layout de TC sucursales
	private static final String[] HEADERS_TC = { "x", "sucursal", "regíon", "divisa usd", "tc sucursal compra usd",
			"estrategia compra usd", "tc sucursal venta usd", "estrategia venta usd", "divisa eur",
			"tc sucursal compra eur", "estrategia compra eur", "tc sucursal venta eur", "estrategia venta eur",
			"divisa cad", "tc sucursal compra cad", "estrategia compra cad", "tc sucursal venta cad",
			"estrategia venta cad", "divisa gbp", "tc sucursal compra gbp", "estrategia compra gbp",
			"tc sucursal venta gbp", "estrategia venta gbp", "nombre", "tipo", "municipio", "estado", "calle",
			"número", "cp", "número de región" };

	// Headers del layout OLP
	private static final String[] HEADERS_OLP = { "sucursal", "divisa", "tc sucursal compra",
			"estrategia compra", "tc sucursal venta", "estrategia venta" };

	// Headers del layout de limites
	private static final String[] HEADERS_LIMITES = { "sucursal", "divisa", "tipo de operación",
			"límite diario cliente", "límite diario usuario", "límite mensual cliente", "límite mensual usuario",
			"límite diario cliente extranjero", "límite diario usuario extranjero", "límite mensual cliente extranjero",
			"límite mensual usuario extranjero" };

	private ArchivosExcel nuevo() {
		return new ArchivosExcel(sucursalesDao, promoEspacioImpl, limitesPorSucursalDao, bitacora);
	}

	// ======================= Helpers de construccion de Excel =======================

	private byte[] construirExcel(String[] headers, String[][] filas) {
		byte[] contenido = null;
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			Sheet sheet = workbook.createSheet("Hoja1");

			Row headerRow = sheet.createRow(0);
			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
			}

			for (int r = 0; r < filas.length; r++) {
				Row dataRow = sheet.createRow(r + 1);
				for (int c = 0; c < filas[r].length; c++) {
					Cell cell = dataRow.createCell(c);
					cell.setCellValue(filas[r][c]);
				}
			}

			workbook.write(out);
			contenido = out.toByteArray();
		}
		catch (IOException e) {
			// ignorado en test
		}
		return contenido;
	}

	private MultipartFile archivo(byte[] contenido) {
		return new MockMultipartFile("archivo", "prueba.xlsx", "text/plain", contenido);
	}

	/** Fila valida de TC (compra <= venta en todas las divisas). */
	private String[] filaTcValida(String sucursal) {
		return new String[] { "1", sucursal, "Norte", "USD", "18.50", "-0.50", "19.50", "1.20", "EUR",
				"15.20", "-0.20", "16.50", "1.0", "CAD", "12.20", "-0.02", "13.10", "0.20", "GBP",
				"12.20", "-0.02", "13.10", "0.20", "nombre", "tipo", "municipio", "estado", "calle",
				"123", "14000", "5" };
	}

	/** Fila de TC con error (compra USD > venta USD). */
	private String[] filaTcConError(String sucursal) {
		return new String[] { "1", sucursal, "Norte", "USD", "99.50", "-0.50", "10.50", "1.20", "EUR",
				"15.20", "-0.20", "16.50", "1.0", "CAD", "12.20", "-0.02", "13.10", "0.20", "GBP",
				"12.20", "-0.02", "13.10", "0.20", "nombre", "tipo", "municipio", "estado", "calle",
				"123", "14000", "5" };
	}

	// ======================= readDataSucursales =======================

	@Test
	void testReadDataSucursalesHeadersFaltantes() {
		archivosExcel = nuevo();
		// Solo un header -> faltan casi todos
		byte[] contenido = construirExcel(new String[] { "sucursal" }, new String[0][]);
		ResponseApi response = archivosExcel.readDataSucursales(archivo(contenido));
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void testReadDataSucursalesValidoSinErrores() {
		archivosExcel = nuevo();

		when(sucursalesDao.consultaSucursales(anyList())).thenReturn(new ArrayList<>());
		when(sucursalesDao.construirCrearDivisas(any())).thenReturn(null);

		String[][] filas = { filaTcValida("0172") };
		ResponseApi response = archivosExcel.readDataSucursales(archivo(construirExcel(HEADERS_TC, filas)));

		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testReadDataSucursalesConErrores() {
		archivosExcel = nuevo();

		when(sucursalesDao.consultaSucursales(anyList())).thenReturn(new ArrayList<>());

		String[][] filas = { filaTcConError("0172") };
		ResponseApi response = archivosExcel.readDataSucursales(archivo(construirExcel(HEADERS_TC, filas)));

		// Aun con errores responde OK con el detalle de errores
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testReadDataSucursalesConSucursalMM() {
		archivosExcel = nuevo();

		when(sucursalesDao.consultaSucursales(anyList())).thenReturn(new ArrayList<>());
		when(sucursalesDao.construirCrearDivisas(any())).thenReturn(null);

		// 1827 = SUCURSALMM -> dispara promoEspacioImpl.registraTCMM()
		String[][] filas = { filaTcValida("1827") };
		ResponseApi response = archivosExcel.readDataSucursales(archivo(construirExcel(HEADERS_TC, filas)));

		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testReadDataSucursalesArchivoInvalido() {
		archivosExcel = nuevo();
		// Contenido que no es un Excel valido -> excepcion -> ERROR_INTERNO
		MultipartFile invalido = new MockMultipartFile("archivo", "malo.xlsx", "text/plain",
				"contenido no valido".getBytes());
		ResponseApi response = archivosExcel.readDataSucursales(invalido);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	// ======================= readDataSucursalesOlp =======================

	private String[] filaOlpValida(String sucursal) {
		return new String[] { sucursal, "OLP", "1200.00", "1.00", "1300.00", "2.00" };
	}

	@Test
	void testReadDataSucursalesOlpHeadersFaltantes() {
		archivosExcel = nuevo();
		byte[] contenido = construirExcel(new String[] { "sucursal" }, new String[0][]);
		ResponseApi response = archivosExcel.readDataSucursalesOlp(archivo(contenido));
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void testReadDataSucursalesOlpValido() {
		archivosExcel = nuevo();

		when(sucursalesDao.consultaSucursalesConOlp(anyList())).thenReturn(new HashSet<>());
		when(sucursalesDao.construirUpdateOlp(any(), isA(boolean.class))).thenReturn(null);

		String[][] filas = { filaOlpValida("0172") };
		ResponseApi response = archivosExcel.readDataSucursalesOlp(archivo(construirExcel(HEADERS_OLP, filas)));

		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testReadDataSucursalesOlpConErrorTC() {
		archivosExcel = nuevo();

		// compra > venta -> error
		String[][] filas = { { "0172", "OLP", "1500.00", "1.00", "1200.00", "2.00" } };
		ResponseApi response = archivosExcel.readDataSucursalesOlp(archivo(construirExcel(HEADERS_OLP, filas)));

		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void testReadDataSucursalesOlpConErrorSpread() {
		archivosExcel = nuevo();

		// Spread compra > 300 -> error
		String[][] filas = { { "0172", "OLP", "1200.00", "500.00", "1300.00", "2.00" } };
		ResponseApi response = archivosExcel.readDataSucursalesOlp(archivo(construirExcel(HEADERS_OLP, filas)));

		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void testReadDataSucursalesOlpArchivoInvalido() {
		archivosExcel = nuevo();
		MultipartFile invalido = new MockMultipartFile("archivo", "malo.xlsx", "text/plain",
				"contenido no valido".getBytes());
		ResponseApi response = archivosExcel.readDataSucursalesOlp(invalido);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	// ======================= lecturaLimites =======================

	private String[] filaLimiteValida(String sucursal) {
		return new String[] { sucursal, "USD", "410", "1000", "500", "30000", "15000",
				"800", "400", "24000", "12000" };
	}

	@Test
	void testLecturaLimitesHeadersFaltantes() {
		archivosExcel = nuevo();
		byte[] contenido = construirExcel(new String[] { "sucursal" }, new String[0][]);
		ResponseApi response = archivosExcel.lecturaLimites(archivo(contenido));
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void testLecturaLimitesInsert() {
		archivosExcel = nuevo();

		// limite vacio -> rama insert
		when(limitesPorSucursalDao.findLimitesSucursalId(any())).thenReturn(new ArrayList<>());
		when(bitacora.registrar(isA(AccionBitacora.class), any(), any(), any())).thenReturn("bitacoraId");

		String[][] filas = { filaLimiteValida("0172") };
		ResponseApi response = archivosExcel.lecturaLimites(archivo(construirExcel(HEADERS_LIMITES, filas)));

		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testLecturaLimitesUpdate() {
		archivosExcel = nuevo();

		// limite existente -> rama update
		List<LimitesPorSucursalDto> existentes = new ArrayList<>();
		existentes.add(new LimitesPorSucursalDto());
		when(limitesPorSucursalDao.findLimitesSucursalId(any())).thenReturn(existentes);
		when(bitacora.registrar(isA(AccionBitacora.class), any(), any(), any())).thenReturn("bitacoraId");

		String[][] filas = { filaLimiteValida("0172") };
		ResponseApi response = archivosExcel.lecturaLimites(archivo(construirExcel(HEADERS_LIMITES, filas)));

		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testLecturaLimitesArchivoInvalido() {
		archivosExcel = nuevo();
		MultipartFile invalido = new MockMultipartFile("archivo", "malo.xlsx", "text/plain",
				"contenido no valido".getBytes());
		ResponseApi response = archivosExcel.lecturaLimites(invalido);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	// ======================= Ramas adicionales para 100% =======================

	@Test
	void testReadDataSucursalesSucursalExistenteActualiza() {
		archivosExcel = nuevo();

		// consultaSucursales devuelve una sucursal existente -> se ejercita el toMap
		// (lambda getId + merge) y la rama sucuDoc != null con construirUpdateTC devolviendo op
		Sucursal existente = new Sucursal();
		existente.setId("0172");
		when(sucursalesDao.consultaSucursales(anyList())).thenReturn(Arrays.asList(existente));
		when(sucursalesDao.construirUpdateTC(any(), any()))
				.thenReturn(new com.mongodb.client.model.UpdateOneModel<>(
						com.mongodb.client.model.Filters.eq("_id", "0172"),
						com.mongodb.client.model.Updates.set("x", 1)));

		String[][] filas = { filaTcValida("0172") };
		ResponseApi response = archivosExcel.readDataSucursales(archivo(construirExcel(HEADERS_TC, filas)));

		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testReadDataSucursalesSucursalExistenteConErrorUpdate() {
		archivosExcel = nuevo();

		// consultaSucursales devuelve existente pero construirUpdateTC devuelve null (diferencia > limite)
		// -> se ejercita la rama que agrega el id a errores
		Sucursal existente = new Sucursal();
		existente.setId("0172");
		when(sucursalesDao.consultaSucursales(anyList())).thenReturn(Arrays.asList(existente));
		when(sucursalesDao.construirUpdateTC(any(), any())).thenReturn(null);

		String[][] filas = { filaTcValida("0172") };
		ResponseApi response = archivosExcel.readDataSucursales(archivo(construirExcel(HEADERS_TC, filas)));

		// Responde OK con detalle de errores
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testReadDataSucursalesErroresEnCadEurGbp() {
		archivosExcel = nuevo();
		when(sucursalesDao.consultaSucursales(anyList())).thenReturn(new ArrayList<>());

		// compra > venta en CAD, EUR y GBP (USD valido) -> cubre las ramas de error de cada divisa
		String[][] filas = { { "1", "0172", "Norte", "USD", "18.50", "-0.50", "19.50", "1.20", "EUR",
				"99.20", "-0.20", "10.50", "1.0", "CAD", "99.20", "-0.02", "10.10", "0.20", "GBP",
				"99.20", "-0.02", "10.10", "0.20", "nombre", "tipo", "municipio", "estado", "calle",
				"123", "14000", "5" } };
		ResponseApi response = archivosExcel.readDataSucursales(archivo(construirExcel(HEADERS_TC, filas)));

		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testReadDataSucursalesConCeldasNumericas() {
		archivosExcel = nuevo();
		when(sucursalesDao.consultaSucursales(anyList())).thenReturn(new ArrayList<>());
		when(sucursalesDao.construirCrearDivisas(any())).thenReturn(null);

		// Excel con celdas NUMERIC para cubrir la rama numerica de getCellValueAsString
		// y celdas no-STRING para valueAsString (rama "OTRO")
		byte[] contenido = construirExcelNumerico();
		ResponseApi response = archivosExcel.readDataSucursales(archivo(contenido));

		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	/**
	 * Construye un Excel de TC donde los valores de TC son celdas NUMERIC
	 * y los campos de texto (nombre, tipo, etc.) tambien NUMERIC para cubrir
	 * las ramas numericas de getCellValueAsString y valueAsString.
	 */
	private byte[] construirExcelNumerico() {
		byte[] contenido = null;
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			Sheet sheet = workbook.createSheet("Hoja1");

			Row headerRow = sheet.createRow(0);
			for (int i = 0; i < HEADERS_TC.length; i++) {
				headerRow.createCell(i).setCellValue(HEADERS_TC[i]);
			}

			Row data = sheet.createRow(1);
			// x, sucursal
			data.createCell(0).setCellValue("1");
			data.createCell(1).setCellValue("0172");
			data.createCell(2).setCellValue("Norte");
			data.createCell(3).setCellValue("USD");
			// TC USD como NUMERIC
			data.createCell(4).setCellValue(18.50);
			data.createCell(5).setCellValue(-0.50);
			data.createCell(6).setCellValue(19.50);
			data.createCell(7).setCellValue(1.20);
			data.createCell(8).setCellValue("EUR");
			data.createCell(9).setCellValue(15.20);
			data.createCell(10).setCellValue(-0.20);
			data.createCell(11).setCellValue(16.50);
			data.createCell(12).setCellValue(1.0);
			data.createCell(13).setCellValue("CAD");
			data.createCell(14).setCellValue(12.20);
			data.createCell(15).setCellValue(-0.02);
			data.createCell(16).setCellValue(13.10);
			data.createCell(17).setCellValue(0.20);
			data.createCell(18).setCellValue("GBP");
			data.createCell(19).setCellValue(12.20);
			data.createCell(20).setCellValue(-0.02);
			data.createCell(21).setCellValue(13.10);
			data.createCell(22).setCellValue(0.20);
			// Campos de texto como NUMERIC -> valueAsString devuelve "OTRO"
			data.createCell(23).setCellValue(100);
			data.createCell(24).setCellValue(200);
			data.createCell(25).setCellValue(300);
			data.createCell(26).setCellValue(400);
			data.createCell(27).setCellValue(500);
			data.createCell(28).setCellValue(600);
			data.createCell(29).setCellValue(700);
			data.createCell(30).setCellValue(800);

			workbook.write(out);
			contenido = out.toByteArray();
		}
		catch (IOException e) {
			// ignorado en test
		}
		return contenido;
	}

	@Test
	void testReadDataSucursalesIdsDuplicados() {
		archivosExcel = nuevo();

		// consultaSucursales devuelve DOS sucursales con el mismo id -> ejercita la
		// funcion de merge del toMap (existente, nuevo) -> existente
		Sucursal s1 = new Sucursal();
		s1.setId("0172");
		Sucursal s2 = new Sucursal();
		s2.setId("0172");
		when(sucursalesDao.consultaSucursales(anyList())).thenReturn(Arrays.asList(s1, s2));
		when(sucursalesDao.construirUpdateTC(any(), any())).thenReturn(null);

		String[][] filas = { filaTcValida("0172") };
		ResponseApi response = archivosExcel.readDataSucursales(archivo(construirExcel(HEADERS_TC, filas)));

		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testReadDataSucursalesConCeldaVacia() {
		archivosExcel = nuevo();
		when(sucursalesDao.consultaSucursales(anyList())).thenReturn(new ArrayList<>());
		when(sucursalesDao.construirCrearDivisas(any())).thenReturn(null);

		// Excel con una fila de datos cuya primera celda es BLANK
		// -> cubre la rama getCell(0) == null (la fila se ignora) y la rama else de getCellValueAsString
		byte[] contenido = construirExcelConCeldaVacia();
		ResponseApi response = archivosExcel.readDataSucursales(archivo(contenido));

		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	/**
	 * Construye un Excel de TC valido pero con una fila extra cuya primera celda
	 * esta vacia (BLANK) para cubrir la rama getCell(0) == null y el else de getCellValueAsString.
	 */
	private byte[] construirExcelConCeldaVacia() {
		byte[] contenido = null;
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			Sheet sheet = workbook.createSheet("Hoja1");

			Row headerRow = sheet.createRow(0);
			for (int i = 0; i < HEADERS_TC.length; i++) {
				headerRow.createCell(i).setCellValue(HEADERS_TC[i]);
			}

			// Fila valida
			String[] valida = filaTcValida("0172");
			Row data = sheet.createRow(1);
			for (int c = 0; c < valida.length; c++) {
				data.createCell(c).setCellValue(valida[c]);
			}

			// Fila con celda 0 en blanco (no se crea la celda 0) -> getCell(0) == null
			Row vacia = sheet.createRow(2);
			vacia.createCell(1).setCellValue("dummy");

			workbook.write(out);
			contenido = out.toByteArray();
		}
		catch (IOException e) {
			// ignorado en test
		}
		return contenido;
	}

	@Test
	void testReadDataSucursalesOlpExistente() {
		archivosExcel = nuevo();

		// idsConOlp contiene la sucursal -> rama existeOlp = true
		java.util.Set<String> conOlp = new HashSet<>();
		conOlp.add("0172");
		when(sucursalesDao.consultaSucursalesConOlp(anyList())).thenReturn(conOlp);
		when(sucursalesDao.construirUpdateOlp(any(), isA(boolean.class))).thenReturn(
				new com.mongodb.client.model.UpdateOneModel<>(
						com.mongodb.client.model.Filters.eq("_id", "0172"),
						com.mongodb.client.model.Updates.set("x", 1)));

		String[][] filas = { filaOlpValida("0172") };
		ResponseApi response = archivosExcel.readDataSucursalesOlp(archivo(construirExcel(HEADERS_OLP, filas)));

		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	// ======================= Constructor =======================

	@Test
	void testConstructor() {
		ArchivosExcel instancia = new ArchivosExcel(sucursalesDao, promoEspacioImpl, limitesPorSucursalDao, bitacora);
		assertThat(instancia).isNotNull();
	}
}
