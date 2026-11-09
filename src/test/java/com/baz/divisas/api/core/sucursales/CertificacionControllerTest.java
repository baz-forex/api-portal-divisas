package com.baz.divisas.api.core.sucursales;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.baz.divisas.api.core.sucursales.dao.CertificacionDao;
import com.baz.divisas.api.core.sucursales.dto.ReqAsignarSucursales;
import com.baz.divisas.api.core.sucursales.dto.ReqFiltroCuestionarios;
import com.baz.divisas.api.core.sucursales.dto.ReqPaginado;
import com.baz.divisas.api.core.sucursales.dto.ReqPaginadoBitacora;
import com.baz.divisas.api.core.sucursales.dto.ReqPregRespSucursal;
import com.baz.divisas.api.core.sucursales.dto.ReqRespuestasSucursal;
import com.baz.divisas.api.core.sucursales.model.BeanCuestionarios;
import com.baz.divisas.api.core.sucursales.service.CertificacionConsultasHistImpl;
import com.baz.divisas.api.core.sucursales.service.CertificacionConsultasImpl;
import com.baz.divisas.api.core.sucursales.service.CertificacionInsercionesImpl;
import com.baz.divisas.api.core.sucursales.service.CertificacionModificacionesImpl;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;
import org.springframework.core.io.Resource;

@ExtendWith(MockitoExtension.class)
public class CertificacionControllerTest {
	@InjectMocks
	private CertificacionController controller;
	@Mock
	private CertificacionInsercionesImpl certificacionInsercionesImpl;
	@Mock
	private CertificacionConsultasImpl certificacionConsultasImpl;
	@Mock
	private CertificacionConsultasHistImpl certificacionConsultasHistImpl;
	@Mock
	private CertificacionModificacionesImpl certificacionModificacionesImpl;
	@Mock
	private CertificacionDao certificacionDao;
	@Mock
	private AggregateIterable<Document> aiterable;
	@Mock
	private MongoCursor<Document> mockCursorDoc;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testConsultaSucursales() throws IOException {
		ReqPaginado reqPaginado = new ReqPaginado();
		ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);

		when(certificacionConsultasImpl.consultaSucursales(reqPaginado)).thenReturn(responseApi);

		ResponseApi result = controller.consultaSucursales(reqPaginado);

		assertNotNull(result);
	}

	@Test
	void testCreaCuestionario() throws IOException {
		BeanCuestionarios reqNuevoCuestionario = new BeanCuestionarios();
		ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);

		when(certificacionInsercionesImpl.creaCuestionario(reqNuevoCuestionario))
				.thenReturn(responseApi);

		ResponseApi result = controller.creaCuestionario(reqNuevoCuestionario);

		assertNotNull(result);
	}

	@Test
	void testServicioCargaMasiva() throws IOException {
		String datos = "SUCURSAL(CECO),CONTACTO SUCURSAL,CORREO SUCURSAL,GERENTE,CONTACTO GERENTE,CORREO GERENTE,FECHA DE APERTURA,REGION,TERRITORIO,IMPLEMENTADOR JR,IMPLEMENTADOR SR,TERRITORIAL,REGIONAL,REGIONALIZACION/LIMITES,PRESUPUESTO,IMPLEMENTACION DE SISTEMAS,TIEMPO POR TRANSACCION,APP Y GEOLOCALIZACION\r\n"
				+ "107,2225232671,sucursal@mail.com,Jose Juan Perez,4562189756,gerente@mail.com,12/07/2022,Puebla de Zaragoza,Amozoc,Jaunita Perez Perez,Juana Perez Perez,Prueba Territorial,Prueba Regional,Prueba regionalizacion y limites,Prueba presupuesto,Prueba implementacion de sistemas,5 segundos,Prueba App y Geolocalizacion";
		MultipartFile fileMock = new MockMultipartFile("prueba", "prueba.txt", "text/plain",
				datos.getBytes());

		ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);

		when(certificacionInsercionesImpl.servicioCargaMasiva(fileMock)).thenReturn(responseApi);

		ResponseEntity<ResponseApi> result = controller.servicioCargaMasiva(fileMock);

		assertNotNull(result);
	}

	@Test
	void testConsultaCuestionarios() throws IOException {
		ReqPaginado reqPaginado = new ReqPaginado();
		ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);

		when(certificacionConsultasImpl.consultaCuestionarios(reqPaginado)).thenReturn(responseApi);

		ResponseApi result = controller.consultaCuestionarios(reqPaginado);

		assertNotNull(result);
	}

	@Test
	void testConsultaPreguntas() throws IOException {
		String id = "107";
		ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);

		when(certificacionConsultasImpl.consultaPreguntas(id)).thenReturn(responseApi);

		ResponseApi result = controller.consultaPreguntas(id);

		assertNotNull(result);
	}

	@Test
	void testActualizaCuestionario() throws IOException {
		BeanCuestionarios reqActualizaCuestionario = new BeanCuestionarios();
		ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);

		when(certificacionModificacionesImpl.actualizaCuestionario(reqActualizaCuestionario))
				.thenReturn(responseApi);

		ResponseApi result = controller.actualizaCuestionario(reqActualizaCuestionario);

		assertNotNull(result);
	}

	@Test
	void testActualizaEstatusCuestionario() throws IOException {
		String id = "107";
		ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);

		when(certificacionModificacionesImpl.actualizaEstatusCuestionario(id))
				.thenReturn(responseApi);

		ResponseApi result = controller.actualizaEstatusCuestionario(id);

		assertNotNull(result);
	}

	@Test
	void testAsignarSucursaleso() throws IOException {
		ReqAsignarSucursales reqAsignarSucursales = new ReqAsignarSucursales();
		ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);

		when(certificacionModificacionesImpl.asignarSucursales(reqAsignarSucursales))
				.thenReturn(responseApi);

		ResponseApi result = controller.asignarSucursales(reqAsignarSucursales);

		assertNotNull(result);
	}

	@Test
	void testConsultaRespuestas() throws IOException {
		ReqPaginado reqPaginado = new ReqPaginado();
		ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);

		when(certificacionConsultasImpl.consultaRespuestas(reqPaginado)).thenReturn(responseApi);

		ResponseApi result = controller.consultaRespuestas(reqPaginado);

		assertNotNull(result);
	}

	@Test
	void testCertificacion() throws IOException {
		String sucursal = "0107";
		ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);

		when(certificacionModificacionesImpl.certificaSucursal(sucursal)).thenReturn(responseApi);

		ResponseApi result = controller.certificacion(sucursal);

		assertNotNull(result);
	}

	@Test
	void testConsultaRespuestasSucursal() throws IOException {
		ReqRespuestasSucursal reqRespuestasSucursal = new ReqRespuestasSucursal();
		ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);

		when(certificacionConsultasImpl.consultaRespuestasSucursal(reqRespuestasSucursal))
				.thenReturn(responseApi);

		ResponseApi result = controller.consultaRespuestasSucursal(reqRespuestasSucursal);

		assertNotNull(result);
	}

	@Test
	void testConsultaCuestionariosHistorico() throws IOException {
		ReqPaginado reqPaginado = new ReqPaginado();
		ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);

		when(certificacionConsultasHistImpl.consultaCuestionariosHistorico(reqPaginado))
				.thenReturn(responseApi);

		ResponseApi result = controller.consultaCuestionariosHistorico(reqPaginado);

		assertNotNull(result);
	}

	@Test
	void testConsultaCuestionariosHist() throws IOException {
		String id = "107";
		ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);

		when(certificacionConsultasHistImpl.consultaPregCuestionariosHist(id))
				.thenReturn(responseApi);

		ResponseApi result = controller.consultaCuestionariosHist(id);

		assertNotNull(result);
	}

	@Test
	void testSucursalesAsignadasHist() throws IOException {
		String id = "107";
		ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);

		when(certificacionConsultasImpl.sucursalesAsignadasHist(id)).thenReturn(responseApi);

		ResponseApi result = controller.sucursalesAsignadasHist(id);

		assertNotNull(result);
	}

	@Test
	void testConsultaPreguntasRespuestas() throws IOException {
		ReqPregRespSucursal reqPregRespSucursal = new ReqPregRespSucursal();
		ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);

		when(certificacionConsultasImpl.consultaPreguntasRespuestas(reqPregRespSucursal))
				.thenReturn(responseApi);

		ResponseApi result = controller.consultaPreguntasRespuestas(reqPregRespSucursal);

		assertNotNull(result);
	}

	@Test
	void testConsultaBitacoraCuestionario() throws IOException {
		ReqPaginadoBitacora reqPaginadoBitacora = new ReqPaginadoBitacora();
		ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);

		when(certificacionConsultasHistImpl.consultaBitacoraCuestionario(reqPaginadoBitacora))
				.thenReturn(responseApi);

		ResponseApi result = controller.consultaBitacoraCuestionario(reqPaginadoBitacora);

		assertNotNull(result);
	}

	@Test
	void testDescargarCuestionariosDocx() throws IOException {
		ReqFiltroCuestionarios reqFiltroCuestionarios = new ReqFiltroCuestionarios();
		reqFiltroCuestionarios.setAnio(2023);
		reqFiltroCuestionarios.setMes(8);
		reqFiltroCuestionarios.setSucursal("4624");
		List<Document> opciones = new ArrayList<>();
		Document opcion = new Document("texto", "prueba");
		opciones.add(opcion);
		List<Document> respuestas = new ArrayList<>();
		Document respuesta = new Document("respuesta", "prueba").append("opciones", opciones);
		respuestas.add(respuesta);
		Document cuestionario = new Document("titulo", new Document("texto", "prueba"))
				.append("respuestas", respuestas);
		List<Document> preguntas = new ArrayList<>();
		Document pregunta = new Document("tipoRespuesta", new Document("_id", "4")).append("texto",
				"prueba");
		preguntas.add(pregunta);
		cuestionario.append("preguntas", preguntas);

		when(mockCursorDoc.hasNext()).thenReturn(true, false);
		when(mockCursorDoc.next()).thenReturn(cuestionario);
		when(aiterable.iterator()).thenReturn(mockCursorDoc);
		when(certificacionDao.getCuestionariosFiltroHis(reqFiltroCuestionarios))
				.thenReturn(aiterable);

		controller.descargarCuestionariosDocx(reqFiltroCuestionarios);

		respuestas = new ArrayList<>();
		respuesta = new Document("respuesta", "prueba").append("opciones",
				new Document("texto", "prueba"));
		respuestas.add(respuesta);
		cuestionario = new Document("titulo", new Document("texto", "prueba")).append("respuestas",
				respuestas);
		preguntas = new ArrayList<>();
		pregunta = new Document("tipoRespuesta", new Document("_id", "1")).append("texto",
				"prueba");
		preguntas.add(pregunta);
		cuestionario.append("preguntas", preguntas);

		when(mockCursorDoc.hasNext()).thenReturn(true, false);
		when(mockCursorDoc.next()).thenReturn(cuestionario);
		when(aiterable.iterator()).thenReturn(mockCursorDoc);
		when(certificacionDao.getCuestionariosFiltroHis(reqFiltroCuestionarios))
				.thenReturn(aiterable);

		ResponseEntity<byte[]> result = controller
				.descargarCuestionariosDocx(reqFiltroCuestionarios);
		assertNotNull(result);
		Files.delete(Paths.get("ReporteCuestionario.docx"));
	}
	
	@Test
	void testDescargarCuestionariosXlsx() throws IOException {
		ReqFiltroCuestionarios reqFiltroCuestionarios = new ReqFiltroCuestionarios();
		reqFiltroCuestionarios.setAnio(2023);
		reqFiltroCuestionarios.setMes(8);
		reqFiltroCuestionarios.setSucursal("4624");
		List<Document> opciones = new ArrayList<>();
		Document opcion = new Document("texto", "prueba");
		opciones.add(opcion);
		List<Document> respuestas = new ArrayList<>();
		Document respuesta = new Document("respuesta", "prueba").append("opciones", opciones);
		respuestas.add(respuesta);
		Document cuestionario = new Document("titulo", new Document("texto", "prueba"))
				.append("respuestas", respuestas);
		List<Document> preguntas = new ArrayList<>();
		Document pregunta = new Document("tipoRespuesta", new Document("_id", "4")).append("texto",
				"prueba");
		preguntas.add(pregunta);
		cuestionario.append("preguntas", preguntas);

		when(mockCursorDoc.hasNext()).thenReturn(true, false);
		when(mockCursorDoc.next()).thenReturn(cuestionario);
		when(aiterable.iterator()).thenReturn(mockCursorDoc);
		when(certificacionDao.getCuestionariosFiltroHis(reqFiltroCuestionarios))
				.thenReturn(aiterable);

		controller.descargarCuestionariosXlsx(reqFiltroCuestionarios);

		respuestas = new ArrayList<>();
		respuesta = new Document("respuesta", "prueba").append("opciones",
				new Document("texto", "prueba"));
		respuestas.add(respuesta);
		cuestionario = new Document("titulo", new Document("texto", "prueba")).append("respuestas",
				respuestas);
		preguntas = new ArrayList<>();
		pregunta = new Document("tipoRespuesta", new Document("_id", "1")).append("texto",
				"prueba");
		preguntas.add(pregunta);
		cuestionario.append("preguntas", preguntas);

		when(mockCursorDoc.hasNext()).thenReturn(true, false);
		when(mockCursorDoc.next()).thenReturn(cuestionario);
		when(aiterable.iterator()).thenReturn(mockCursorDoc);
		when(certificacionDao.getCuestionariosFiltroHis(reqFiltroCuestionarios))
				.thenReturn(aiterable);

		ResponseEntity<Resource> result = controller
				.descargarCuestionariosXlsx(reqFiltroCuestionarios);

		assertNotNull(result);
	}

}
