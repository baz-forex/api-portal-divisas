package com.baz.divisas.api.core.sucursales.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static com.baz.divisas.api.core.sucursales.utils.Constantes.*;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.baz.divisas.api.core.sucursales.dto.*;
import com.baz.divisas.api.core.sucursales.model.*;
import com.baz.divisas.general.dto.ResponseApi;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.baz.divisas.api.core.sucursales.dao.CertificacionDao;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class CertificacionConsultasImplTest {

	private CertificacionConsultasImpl certificacionConsultasImpl;
	private ResponseApi result;
	private BeanSucursales beanSucursales;
	private ReqPaginado reqPaginado;
	private List<BeanSucursales> listSucursales;
	@Mock
	private FindIterable<BeanSucursales> mockIterable;
	@Mock
	private AggregateIterable<Document> mockAggIterable;
	@Mock
	private AggregateIterable<Document> mockAggIterable2;
	@Mock
	private AggregateIterable<Document> mockAggIterable3;
	@Mock
	private MongoCursor<BeanSucursales> mockCursor;
	@Mock
	private MongoCursor<BeanTopesCaja> mockCursorTopes;
	@Mock
	private MongoCursor<Document> mockCursorDoc;
	@Mock
	private MongoCursor<Document> mockCursorDoc2;
	@Mock
	private MongoCursor<Document> mockCursorDoc3;

	@Mock
	private CertificacionDao dao;

	@BeforeEach
	public void setUp() {
		certificacionConsultasImpl = new CertificacionConsultasImpl(dao);
		beanSucursales = new BeanSucursales();
		reqPaginado = new ReqPaginado();
		listSucursales = new ArrayList<>();
		beanSucursales.setAppYGeolocalizacion("Prueba");
		beanSucursales.setCertificar(false);
		beanSucursales.setContactoGerente("Prueba");
		beanSucursales.setContactoSucursal("Prueba");
		beanSucursales.setDireccion(null);
		beanSucursales.setFechaApertura("Prueba");
		beanSucursales.setGerente("Senen");
		beanSucursales.setId("107");
		beanSucursales.setImplementacionDeSistemas("Prueba");
		beanSucursales.setImplementadorJr("Prueba");
		beanSucursales.setImplementadorSr("Prueba");
		beanSucursales.setNomCalle("Prueba");
		beanSucursales.setNumeroExterior("10");
		beanSucursales.setPresupuesto("Prueba");
		beanSucursales.setNomRegion("Prueba");
		beanSucursales.setRegional("Prueba");
		beanSucursales.setSucursal(107);
		beanSucursales.setTerritorial("Prueba");
		beanSucursales.setNomTerritorio("Prueba");
		beanSucursales.setTiempoPorTransaccion("10");
		beanSucursales.setTopesCaja(new Document());
	}

	@Test
	void testConsultaSucursales() {
		reqPaginado.setElementosPagina(1);
		reqPaginado.setPagina(1);
		listSucursales.add(beanSucursales);
		List<Integer> divisa = new ArrayList<>();
		divisa.add(2);
		divisa.add(3);
		divisa.add(5);
		divisa.add(7);
		List<Integer> tipoCaja = new ArrayList<>();
		tipoCaja.add(102);
		tipoCaja.add(101);

		for (int numDivisa : divisa) {
			BeanTopesCaja beanTopesCaja = new BeanTopesCaja();
			beanTopesCaja.setIdDivisa(numDivisa);
			beanTopesCaja.setIdSucursal(107);
			if (numDivisa % 2 == 0) {
				beanTopesCaja.setIdCaja(101);
			}
			else {
				beanTopesCaja.setIdCaja(102);
			}
			when(mockCursorTopes.hasNext()).thenReturn(true, false);
			when(mockCursorTopes.next()).thenReturn(beanTopesCaja);
			when(dao.consultaTopeCaja(divisa, tipoCaja, 107)).thenReturn(mockCursorTopes);
			when(dao.conteoConsultaSucursales()).thenReturn(1L);
			when(mockCursor.hasNext()).thenReturn(true, false);
			when(mockCursor.next()).thenReturn(listSucursales.get(0));
			when(mockIterable.iterator()).thenReturn(mockCursor);
			when(dao.consultaSucursales(any(ReqPaginado.class))).thenReturn(mockIterable);
			certificacionConsultasImpl.consultaSucursales(reqPaginado);
		}

		reqPaginado.setElementosPagina(5);
		when(mockCursor.hasNext()).thenReturn(false);
		when(dao.consultaSucursales(any(ReqPaginado.class))).thenReturn(mockIterable);
		certificacionConsultasImpl.consultaSucursales(reqPaginado);

		reqPaginado.setElementosPagina(0);

		result = certificacionConsultasImpl.consultaSucursales(reqPaginado);

		assertNotNull(result);

	}

	@Test
	void testConsultaCuestionarios() {
		ReqPaginado reqPaginado = new ReqPaginado();
		reqPaginado.setElementosPagina(5);
		reqPaginado.setPagina(1);
		Document totalReg = new Document("total", 1);

		when(mockCursorDoc.hasNext()).thenReturn(true, false);
		when(mockCursorDoc.next()).thenReturn(totalReg);
		when(mockAggIterable.iterator()).thenReturn(mockCursorDoc);
		when(dao.conteoCuestionarios()).thenReturn(mockAggIterable);

		totalReg = new Document().append("id", new ObjectId());
		totalReg.append(ESTATUS, true);
		totalReg.append(FECHAREGISTRO, "2023-05-30T14:02:41.256");
		totalReg.append(TITULO, new Document());
		totalReg.append(SUCURSALES, Arrays.asList("107"));
		totalReg.append(PREGUNTAS, 10);

		when(mockCursorDoc2.hasNext()).thenReturn(true, false);
		when(mockCursorDoc2.next()).thenReturn(totalReg);
		when(mockAggIterable2.iterator()).thenReturn(mockCursorDoc2);
		when(dao.consultaCuestionarios(anyInt(), anyInt())).thenReturn(mockAggIterable2);
		certificacionConsultasImpl.consultaCuestionarios(reqPaginado);

		reqPaginado.setElementosPagina(1);
		reqPaginado.setPagina(1);
		when(mockCursorDoc.hasNext()).thenReturn(false);
		when(dao.conteoCuestionarios()).thenReturn(mockAggIterable);
		certificacionConsultasImpl.consultaCuestionarios(reqPaginado);

		reqPaginado.setElementosPagina(0);
		reqPaginado.setPagina(0);

		result = certificacionConsultasImpl.consultaCuestionarios(reqPaginado);

		assertNotNull(result);
	}

	@Test
	void testConsultaPreguntas() {
		String id = new ObjectId().toHexString();
		RespConsultaPreguntas resp = new RespConsultaPreguntas();
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
		resp.setPreguntas(preguntas);

		when(dao.consultaPreguntas(id)).thenReturn(resp);
		certificacionConsultasImpl.consultaPreguntas(id);

		resp.setPreguntas(new ArrayList<>());
		when(dao.consultaPreguntas(id)).thenReturn(resp);
		certificacionConsultasImpl.consultaPreguntas(id);

		id = "5055";
		result = certificacionConsultasImpl.consultaPreguntas(id);

		assertNotNull(result);
	}

	@Test
	void testConsultaRespuestas() {
		ReqPaginado reqPaginado = new ReqPaginado();
		reqPaginado.setElementosPagina(5);
		reqPaginado.setPagina(1);
		Contacto contacto = new Contacto();
		contacto.setCorreo("Prueba");
		contacto.setTelefono("10965265");
		List<Integer> totales = new ArrayList<>();
		totales.add(0);
		totales.add(50);
		totales.add(100);

		for (int i = 0; i < 3; i++) {
			Document respuesta = new Document("_id", "0107").append(AVANCE, totales.get(i))
					.append(TOTAL, 75);

			Document totalRegistros = new Document("total", 1L);

			when(dao.datosSucursal(107)).thenReturn(beanSucursales);
			when(mockCursorDoc.hasNext()).thenReturn(true, false);
			when(mockCursorDoc.next()).thenReturn(respuesta);
			when(mockAggIterable.iterator()).thenReturn(mockCursorDoc);
			when(dao.consultaRespuestas(reqPaginado)).thenReturn(mockAggIterable);
			when(mockCursorDoc2.hasNext()).thenReturn(true, false);
			when(mockCursorDoc2.next()).thenReturn(totalRegistros);
			when(mockAggIterable2.iterator()).thenReturn(mockCursorDoc2);
			when(dao.conteoConsultaRespuesta()).thenReturn(mockAggIterable2);
			certificacionConsultasImpl.consultaRespuestas(reqPaginado);
		}

		when(dao.consultaRespuestas(reqPaginado)).thenReturn(null);
		certificacionConsultasImpl.consultaRespuestas(reqPaginado);

		reqPaginado.setElementosPagina(0);
		reqPaginado.setPagina(0);
		result = certificacionConsultasImpl.consultaRespuestas(reqPaginado);

		assertNotNull(result);
	}

	@Test
	void testConsultaRespuestasSucursal() {
		ReqRespuestasSucursal req = new ReqRespuestasSucursal();
		req.setElementosPagina(5);
		req.setPagina(1);
		req.setSucursal("0107");

		Document titulo = new Document(TEXTO, "Prueba").append(IMAGEN, "Imagensota");
		List<Document> cuestionarios = new ArrayList<>();
		Document cuestionario = new Document(AVANCE, 90).append(FECHAREGISTRO, "Prueba")
				.append("id", new ObjectId()).append(PREGUNTAS, 4).append(PREGUNTASRESPONDIDAS, 3)
				.append(TITULO, titulo);

		cuestionarios.add(cuestionario);
		Document listCuestionario = new Document(CUESTIONARIOS, cuestionarios)
				.append(AVANCETOTAL, 90).append(TOTALREGISTROS, 1L);

		when(mockCursorDoc.hasNext()).thenReturn(true, false);
		when(mockCursorDoc.next()).thenReturn(listCuestionario);
		when(mockAggIterable.iterator()).thenReturn(mockCursorDoc);
		when(dao.consultaRespuestasSucursal(req)).thenReturn(mockAggIterable);
		certificacionConsultasImpl.consultaRespuestasSucursal(req);

		when(mockCursorDoc.hasNext()).thenReturn(false);
		when(dao.consultaRespuestasSucursal(req)).thenReturn(mockAggIterable);
		certificacionConsultasImpl.consultaRespuestasSucursal(req);

		req.setElementosPagina(0);
		req.setPagina(0);
		req.setSucursal("0107000");

		result = certificacionConsultasImpl.consultaRespuestasSucursal(req);

		assertNotNull(result);
	}

	@Test
	void testSucursalesAsignadasHist() {
		String id = new ObjectId().toHexString();
		BeanCuestionarios beanCuestionarios = new BeanCuestionarios();
		beanCuestionarios.setEstatus(false);
		beanCuestionarios.setFechaRegistro("Prueba");
		beanCuestionarios.setId(id);
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
		beanCuestionarios.setPreguntas(preguntas);
		beanCuestionarios.setSucursales(Arrays.asList("107"));
		Titulo titulo = new Titulo();
		titulo.setImagen("Imagensota");
		titulo.setTexto("Prueba");
		beanCuestionarios.setTitulo(titulo);
		beanCuestionarios.setTotal(1L);

		when(dao.consultaSucAsignadasHist(id)).thenReturn(beanCuestionarios);
		certificacionConsultasImpl.sucursalesAsignadasHist(id);

		when(dao.consultaSucAsignadasHist(id)).thenReturn(null);
		certificacionConsultasImpl.sucursalesAsignadasHist(id);

		id = "123";
		result = certificacionConsultasImpl.sucursalesAsignadasHist(id);

		assertNotNull(result);
	}

	@Test
	void testConsultaPreguntasRespuestas() {
		ReqPregRespSucursal req = new ReqPregRespSucursal();
		req.setId(new ObjectId().toHexString());
		req.setSucursal("0107");

		List<Document> listaOpciones = new ArrayList<>();
		Document opcion = new Document("_id", 1).append(TEXTO, "Prueba");
		listaOpciones.add(opcion);
		Document tipoRespuesta = new Document("id", "1").append("descripcion", "Prueba");
		Document pregunta = new Document("_id", 1).append(TEXTO, "Prueba")
				.append(IMAGEN, "Imagensota").append("tipoRespuesta", tipoRespuesta)
				.append("permiteImagen", false).append("respuestaMultiple", false)
				.append("obligatoria", false).append("opciones", listaOpciones);

		List<Document> listaPreguntas = new ArrayList<>();
		listaPreguntas.add(pregunta);
		Document preguntas = new Document("preguntas", listaPreguntas);
		Document respuestas = new Document("respuestas", new ArrayList<>());

		when(mockCursorDoc.hasNext()).thenReturn(true, false);
		when(mockCursorDoc.next()).thenReturn(preguntas);
		when(mockAggIterable.iterator()).thenReturn(mockCursorDoc);
		when(dao.consultaPreguntasSuc(req)).thenReturn(mockAggIterable);

		when(mockCursorDoc2.hasNext()).thenReturn(true, false);
		when(mockCursorDoc2.next()).thenReturn(respuestas);
		when(mockAggIterable2.iterator()).thenReturn(mockCursorDoc2);
		when(dao.consultaRespuestasSuc(req)).thenReturn(mockAggIterable2);
		certificacionConsultasImpl.consultaPreguntasRespuestas(req);

		when(dao.consultaPreguntasSuc(req)).thenReturn(mockAggIterable);
		certificacionConsultasImpl.consultaPreguntasRespuestas(req);

		List<Document> listaRespuestas = new ArrayList<>();
		Document respuesta = new Document("idPregunta", 1).append("idTipoRespuesta", 1)
				.append("respuesta", "Prueba").append(IMAGEN, "Imagensota")
				.append("opciones", null);
		listaRespuestas.add(respuesta);
		respuestas = new Document("respuestas", listaRespuestas);

		when(mockCursorDoc3.hasNext()).thenReturn(true, false);
		when(mockCursorDoc3.next()).thenReturn(respuestas);
		when(mockAggIterable3.iterator()).thenReturn(mockCursorDoc3);
		when(dao.consultaRespuestasSuc(req)).thenReturn(mockAggIterable3);
		certificacionConsultasImpl.consultaPreguntasRespuestas(req);

		req.setId("123");
		result = certificacionConsultasImpl.consultaPreguntasRespuestas(req);

		assertNotNull(result);
	}
}
