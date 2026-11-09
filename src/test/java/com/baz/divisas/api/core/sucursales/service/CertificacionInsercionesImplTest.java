package com.baz.divisas.api.core.sucursales.service;

import static com.baz.divisas.api.core.sucursales.utils.Constantes.APPGEO;
import static com.baz.divisas.api.core.sucursales.utils.Constantes.CONTACTOGERENTE;
import static com.baz.divisas.api.core.sucursales.utils.Constantes.CONTACTOSUCURSAL;
import static com.baz.divisas.api.core.sucursales.utils.Constantes.GERENTE;
import static com.baz.divisas.api.core.sucursales.utils.Constantes.IMPLJR;
import static com.baz.divisas.api.core.sucursales.utils.Constantes.IMPLSIS;
import static com.baz.divisas.api.core.sucursales.utils.Constantes.IMPLSR;
import static com.baz.divisas.api.core.sucursales.utils.Constantes.PRESUPUESTO;
import static com.baz.divisas.api.core.sucursales.utils.Constantes.REGIONAL;
import static com.baz.divisas.api.core.sucursales.utils.Constantes.REGLIM;
import static com.baz.divisas.api.core.sucursales.utils.Constantes.SUCURSAL;
import static com.baz.divisas.api.core.sucursales.utils.Constantes.TERRITORIAL;
import static com.baz.divisas.api.core.sucursales.utils.Constantes.TIEMPOTRANS;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.baz.divisas.api.core.sucursales.model.*;
import com.baz.divisas.api.core.sucursales.utils.LectorArchivoExcel;
import com.baz.divisas.general.dto.ResponseApi;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import com.baz.divisas.api.core.sucursales.dao.CertificacionDao;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.bson.types.ObjectId;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class CertificacionInsercionesImplTest {

	private CertificacionInsercionesImpl certificacionInsercionesImpl;

	@Mock
	private CertificacionDao dao;

	@Mock
	private MongoCursor<BeanTopesCaja> cursorTopesCaja;

	@Mock
	private InsertOneResult insertOneResult;

	@Mock
	private UpdateResult updateResult;

	private BeanCuestionarios reqNuevoCuestionario;
	private MultipartFile fileMock;
	LectorArchivoExcel lectorExcel;

	@BeforeEach
	public void setUp() {
		certificacionInsercionesImpl = new CertificacionInsercionesImpl(dao);
		reqNuevoCuestionario = new BeanCuestionarios();
		lectorExcel = new LectorArchivoExcel(dao);
	}

	@Test
	void testCreaCuestionario() {
		reqNuevoCuestionario.setEstatus(false);
		reqNuevoCuestionario.setFechaRegistro("Prueba");
		reqNuevoCuestionario.setId(new ObjectId().toHexString());
		Pregunta preg = new Pregunta();
		List<Pregunta> preguntas = new ArrayList<>();
		TipoRespuesta tipoRes = new TipoRespuesta();
		tipoRes.setDescripcion("Prueba");
		tipoRes.setId("1");
		preg.setId(5);
		preg.setImagen("imagensota");
		preg.setObligatoria(false);
		preg.setOpciones(new ArrayList<>());
		preg.setPermiteImagen(false);
		preg.setRespuestaMultiple(false);
		preg.setTexto("Prueba");
		preg.setTipoRespuesta(tipoRes);
		preguntas.add(preg);
		reqNuevoCuestionario.setPreguntas(preguntas);
		reqNuevoCuestionario.setSucursales(Arrays.asList("107"));
		Titulo titulo = new Titulo();
		titulo.setImagen("Imagensota");
		titulo.setTexto("Prueba");
		reqNuevoCuestionario.setTitulo(titulo);
		reqNuevoCuestionario.setTotal(1L);

		when(insertOneResult.wasAcknowledged()).thenReturn(true);
		when(dao.nuevoCuestionario(reqNuevoCuestionario)).thenReturn(insertOneResult);
		certificacionInsercionesImpl.creaCuestionario(reqNuevoCuestionario);

		when(insertOneResult.wasAcknowledged()).thenReturn(false);
		when(dao.nuevoCuestionario(reqNuevoCuestionario)).thenReturn(insertOneResult);
		certificacionInsercionesImpl.creaCuestionario(reqNuevoCuestionario);

		preguntas = new ArrayList<>();
		preg = new Pregunta();
		preguntas.add(preg);
		reqNuevoCuestionario.setPreguntas(preguntas);
		certificacionInsercionesImpl.creaCuestionario(reqNuevoCuestionario);

		reqNuevoCuestionario.setPreguntas(new ArrayList<>());
		ResponseApi result = certificacionInsercionesImpl.creaCuestionario(reqNuevoCuestionario);

		assertNotNull(result);

	}

	@Test
	void testServicioCargaMasiva() {
		BeanSucursales datosSuc = new BeanSucursales();
		datosSuc.setNomCalle("Prueba");
		datosSuc.setNumeroExterior("10");
		byte[] excelFileContent = null;

		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("SampleSheet");

			// Create headers
			Row headerRow = sheet.createRow(0);
			String[] headers = {SUCURSAL, CONTACTOSUCURSAL, GERENTE, CONTACTOGERENTE, IMPLJR,
					IMPLSR, TERRITORIAL, REGIONAL, APPGEO, REGLIM, PRESUPUESTO, IMPLSIS, TIEMPOTRANS
			};
			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
			}

			// Add sample data
			String[][] sampleData = {{"0107", "John", "Manager", "Jane", "Impl Jr", "Impl Sr",
					"Territory", "Region", "App Geo", "Region Limit", "100000", "Impl Sis", "10"
					}, {"2", "Alice", "Supervisor", "Bob", "Impl Jr", "Impl Sr", "Territory",
							"Region", "App Geo", "Region Limit", "90000", "Impl Sis", "15"
					}
			};

			for (int rowIdx = 0; rowIdx < sampleData.length; rowIdx++) {
				Row dataRow = sheet.createRow(rowIdx + 1);
				for (int colIdx = 0; colIdx < sampleData[rowIdx].length; colIdx++) {
					Cell cell = dataRow.createCell(colIdx);
					cell.setCellValue(sampleData[rowIdx][colIdx]);
				}
			}

			// Write the workbook content to a ByteArrayOutputStream
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			excelFileContent = outputStream.toByteArray();
		}
		catch (IOException e) {

		}

		when(updateResult.getModifiedCount()).thenReturn(1L);
		when(dao.procesaCargaMasiva(any(Document.class), any(Document.class)))
				.thenReturn(updateResult);
		when(dao.obtenerDireccionSucursal(107)).thenReturn(datosSuc);

		fileMock = new MockMultipartFile("prueba", "prueba.xlsx",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
				excelFileContent);
		certificacionInsercionesImpl.servicioCargaMasiva(fileMock);

		fileMock = new MockMultipartFile("prueba", "prueba.xlsx", "text/plain", excelFileContent);
		ResponseApi result = certificacionInsercionesImpl.servicioCargaMasiva(fileMock);

		assertNotNull(result);
	}

}
