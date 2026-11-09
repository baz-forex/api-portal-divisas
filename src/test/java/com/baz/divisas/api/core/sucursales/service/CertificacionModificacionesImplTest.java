package com.baz.divisas.api.core.sucursales.service;

import static com.baz.divisas.api.core.sucursales.utils.Constantes.AVANCE;
import static com.baz.divisas.api.core.sucursales.utils.Constantes.AVANCETOTAL;
import static com.baz.divisas.api.core.sucursales.utils.Constantes.CUESTIONARIOS;
import static com.baz.divisas.api.core.sucursales.utils.Constantes.FECHAREGISTRO;
import static com.baz.divisas.api.core.sucursales.utils.Constantes.IMAGEN;
import static com.baz.divisas.api.core.sucursales.utils.Constantes.PREGUNTAS;
import static com.baz.divisas.api.core.sucursales.utils.Constantes.PREGUNTASRESPONDIDAS;
import static com.baz.divisas.api.core.sucursales.utils.Constantes.TEXTO;
import static com.baz.divisas.api.core.sucursales.utils.Constantes.TITULO;
import static com.baz.divisas.api.core.sucursales.utils.Constantes.TOTALREGISTROS;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.baz.divisas.api.core.sucursales.model.*;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.MailServiceCertificacion;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import com.baz.divisas.api.core.sucursales.dao.CertificacionDao;
import com.baz.divisas.api.core.sucursales.dto.ReqAsignarSucursales;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.bson.types.ObjectId;

@ExtendWith(MockitoExtension.class)
public class CertificacionModificacionesImplTest {

	private CertificacionModificacionesImpl certificacionModificacionesImpl;

	@Mock
	private CertificacionDao dao;

	@Mock
	private UpdateResult updateResult;

	@Mock
	private MongoCursor<BeanCuestionarios> mockCursor;

	@Mock
	private AggregateIterable<Document> aggIterable;

	@Mock
	private MongoCursor<Document> mockCursorDoc;

	@Mock
	private MailServiceCertificacion mail;

	private BeanCuestionarios reqActualizaCuestionario;
	private ReqAsignarSucursales reqAsignarSucursales;
	private BeanCuestionarios cuestioanrio;
	private String id;

	@BeforeEach
	public void setUp() {

		certificacionModificacionesImpl = new CertificacionModificacionesImpl(dao);
		reqActualizaCuestionario = new BeanCuestionarios();
		reqAsignarSucursales = new ReqAsignarSucursales();
		cuestioanrio = new BeanCuestionarios();
		Titulo titulo = new Titulo();
		titulo.setImagen("imegensota");
		titulo.setTexto("Prueba");
		cuestioanrio.setEstatus(false);
		cuestioanrio.setFechaRegistro("");
		cuestioanrio.setId("");
		List<Pregunta> preguntas = new ArrayList<>();
		preguntas.add(new Pregunta());
		cuestioanrio.setPreguntas(preguntas);
		cuestioanrio.setSucursales(new ArrayList<>());
		cuestioanrio.setTitulo(titulo);
		cuestioanrio.setTotal(1);
		id = "6476817e27ec6e6eb2dc4b46";
		CertificacionModificacionesImpl.setMail(mail);
	}

	@Test
	void testCreaCuestionario() {
		reqActualizaCuestionario.setEstatus(false);
		reqActualizaCuestionario.setFechaRegistro("Prueba");
		reqActualizaCuestionario.setId(new ObjectId().toHexString());
		Titulo titulo = new Titulo();
		titulo.setTexto("Prueba");
		reqActualizaCuestionario.setTitulo(titulo);

		when(updateResult.getModifiedCount()).thenReturn(1L);
		when(dao.actualizaCuestionario(reqActualizaCuestionario)).thenReturn(updateResult);
		certificacionModificacionesImpl.actualizaCuestionario(reqActualizaCuestionario);

		when(updateResult.getModifiedCount()).thenReturn(0L);
		when(dao.actualizaCuestionario(reqActualizaCuestionario)).thenReturn(updateResult);
		certificacionModificacionesImpl.actualizaCuestionario(reqActualizaCuestionario);

		reqActualizaCuestionario = new BeanCuestionarios();
		ResponseApi result = certificacionModificacionesImpl
				.actualizaCuestionario(reqActualizaCuestionario);

		assertNotNull(result);

	}

	@Test
	void testActualizaEstatusCuestionario() {

		when(updateResult.getModifiedCount()).thenReturn(1L);
		when(updateResult.wasAcknowledged()).thenReturn(true);
		when(dao.actualizaEstatusCuestionario(id, true)).thenReturn(updateResult);
		when(dao.consultaCuestionario(id)).thenReturn(cuestioanrio);
		certificacionModificacionesImpl.actualizaEstatusCuestionario(id);
		
		
		when(updateResult.wasAcknowledged()).thenReturn(false);
		when(dao.actualizaEstatusCuestionario(id, true)).thenReturn(updateResult);
		certificacionModificacionesImpl.actualizaEstatusCuestionario(id);
		
		cuestioanrio.setPreguntas(new ArrayList<>());
		when(dao.consultaCuestionario(id)).thenReturn(cuestioanrio);
		certificacionModificacionesImpl.actualizaEstatusCuestionario(id);
		
		id = "123";
		ResponseApi result = certificacionModificacionesImpl.actualizaEstatusCuestionario(id);

		assertNotNull(result);
	}

	@Test
	void testAsignarSucursales() {
		reqAsignarSucursales.setId(id);
		reqAsignarSucursales.setSucursales(Arrays.asList("0107"));

		when(updateResult.getModifiedCount()).thenReturn(1L);
		when(updateResult.wasAcknowledged()).thenReturn(true);
		when(dao.asignarSucursales(reqAsignarSucursales)).thenReturn(updateResult);
		certificacionModificacionesImpl.asignarSucursales(reqAsignarSucursales);

		when(updateResult.getModifiedCount()).thenReturn(0L);
		when(updateResult.wasAcknowledged()).thenReturn(true);
		when(dao.asignarSucursales(reqAsignarSucursales)).thenReturn(updateResult);
		certificacionModificacionesImpl.asignarSucursales(reqAsignarSucursales);

		when(updateResult.wasAcknowledged()).thenReturn(false);
		when(dao.asignarSucursales(reqAsignarSucursales)).thenReturn(updateResult);
		certificacionModificacionesImpl.asignarSucursales(reqAsignarSucursales);

		reqAsignarSucursales = new ReqAsignarSucursales();
		ResponseApi result = certificacionModificacionesImpl
				.asignarSucursales(reqAsignarSucursales);

		assertNotNull(result);
	}

	@Test
	void testCertificaSucursal() {
		String sucursal = "0107";
		Document titulo = new Document(TEXTO, "Prueba").append(IMAGEN, "Imagensota");
		List<Document> cuestionarios = new ArrayList<>();
		Document cuestionario = new Document(AVANCE, 100).append(FECHAREGISTRO, "Prueba")
				.append("id", new ObjectId()).append(PREGUNTAS, 4).append(PREGUNTASRESPONDIDAS, 4)
				.append(TITULO, titulo);

		cuestionarios.add(cuestionario);
		Document listCuestionario = new Document(CUESTIONARIOS, cuestionarios)
				.append(AVANCETOTAL, 100).append(TOTALREGISTROS, 1L);

		BeanSucursales beanSucursales = new BeanSucursales();
		beanSucursales.setAppYGeolocalizacion("Prueba");
		beanSucursales.setCertificar(false);
		beanSucursales.setContactoGerente("Prueba");
		beanSucursales.setContactoSucursal("Prueba");
		beanSucursales.setDireccion("Prueba");
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
		Principal principal = new Principal();
		principal.setCanadiense(10);
		principal.setDolar(15);
		principal.setEuro(11);
		beanSucursales.setTopesCaja(new Document());
		List<BeanCuestionarios> listCuestionarios = new ArrayList<>();
		BeanCuestionarios beanCuestionarios = new BeanCuestionarios();
		beanCuestionarios.setEstatus(false);
		beanCuestionarios.setFechaRegistro("Prueba");
		beanCuestionarios.setId(id);
		beanCuestionarios.setPreguntas(new ArrayList<>());
		beanCuestionarios.setSucursales(Arrays.asList("0107"));
		beanCuestionarios.setTitulo(new Titulo());
		beanCuestionarios.setTotal(1L);
		listCuestionarios.add(beanCuestionarios);

		when(dao.actualizaCuestionarioHist(any(), any())).thenReturn(true);
		when(mockCursor.hasNext()).thenReturn(true, false);
		when(mockCursor.next()).thenReturn(listCuestionarios.get(0));
		when(dao.getCuestionarios("0107")).thenReturn(mockCursor);
		when(dao.consultaSucursal(sucursal)).thenReturn(beanSucursales);
		when(updateResult.getModifiedCount()).thenReturn(1L);
		when(dao.certificaSucursal(sucursal)).thenReturn(updateResult);
		when(mockCursorDoc.hasNext()).thenReturn(true, false);
		when(mockCursorDoc.next()).thenReturn(listCuestionario);
		when(aggIterable.iterator()).thenReturn(mockCursorDoc);
		when(dao.consultaRespuestasSucursal(any())).thenReturn(aggIterable);
		certificacionModificacionesImpl.certificaSucursal(sucursal);

		when(updateResult.getModifiedCount()).thenReturn(0L);
		when(dao.certificaSucursal(sucursal)).thenReturn(updateResult);
		when(mockCursorDoc.hasNext()).thenReturn(true, false);
		when(mockCursorDoc.next()).thenReturn(listCuestionario);
		when(aggIterable.iterator()).thenReturn(mockCursorDoc);
		when(dao.consultaRespuestasSucursal(any())).thenReturn(aggIterable);

		ResponseApi result = certificacionModificacionesImpl.certificaSucursal(sucursal);

		assertNotNull(result);
	}

	@Test
	void testCertificaSucursal2() {
		String sucursal = "0107";
		Document titulo = new Document(TEXTO, "Prueba").append(IMAGEN, "Imagensota");
		List<Document> cuestionarios = new ArrayList<>();
		Document cuestionario = new Document(AVANCE, 100).append(FECHAREGISTRO, "Prueba")
				.append("id", new ObjectId()).append(PREGUNTAS, 4).append(PREGUNTASRESPONDIDAS, 4)
				.append(TITULO, titulo);

		cuestionarios.add(cuestionario);

		BeanSucursales beanSucursales = new BeanSucursales();
		beanSucursales.setAppYGeolocalizacion("Prueba");
		beanSucursales.setCertificar(false);
		beanSucursales.setContactoGerente("Prueba");
		beanSucursales.setContactoSucursal("Prueba");
		beanSucursales.setDireccion("Prueba");
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
		Principal principal = new Principal();
		principal.setCanadiense(10);
		principal.setDolar(15);
		principal.setEuro(11);
		beanSucursales.setTopesCaja(new Document());
		List<BeanCuestionarios> listCuestionarios = new ArrayList<>();
		BeanCuestionarios beanCuestionarios = new BeanCuestionarios();
		beanCuestionarios.setEstatus(false);
		beanCuestionarios.setFechaRegistro("Prueba");
		beanCuestionarios.setId(id);
		beanCuestionarios.setPreguntas(new ArrayList<>());
		beanCuestionarios.setSucursales(Arrays.asList("0107"));
		beanCuestionarios.setTitulo(new Titulo());
		beanCuestionarios.setTotal(1L);
		listCuestionarios.add(beanCuestionarios);

		when(dao.consultaRespuestasSucursal(any())).thenReturn(null);
		certificacionModificacionesImpl.certificaSucursal(sucursal);

		ResponseApi result = certificacionModificacionesImpl.certificaSucursal(sucursal);

		assertNotNull(result);
	}

	@Test
	void testCertificaSucursal3() {
		String sucursal = "0107";
		Document titulo = new Document(TEXTO, "Prueba").append(IMAGEN, "Imagensota");
		List<Document> cuestionarios = new ArrayList<>();
		Document cuestionario = new Document(AVANCE, 90).append(FECHAREGISTRO, "Prueba")
				.append("id", new ObjectId()).append(PREGUNTAS, 4).append(PREGUNTASRESPONDIDAS, 4)
				.append(TITULO, titulo);

		cuestionarios.add(cuestionario);
		Document listCuestionario = new Document(CUESTIONARIOS, cuestionarios)
				.append(AVANCETOTAL, 90).append(TOTALREGISTROS, 1L);

		BeanSucursales beanSucursales = new BeanSucursales();
		beanSucursales.setAppYGeolocalizacion("Prueba");
		beanSucursales.setCertificar(false);
		beanSucursales.setContactoGerente("Prueba");
		beanSucursales.setContactoSucursal("Prueba");
		beanSucursales.setDireccion("Prueba");
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
		Principal principal = new Principal();
		principal.setCanadiense(10);
		principal.setDolar(15);
		principal.setEuro(11);
		beanSucursales.setTopesCaja(new Document());
		List<BeanCuestionarios> listCuestionarios = new ArrayList<>();
		BeanCuestionarios beanCuestionarios = new BeanCuestionarios();
		beanCuestionarios.setEstatus(false);
		beanCuestionarios.setFechaRegistro("Prueba");
		beanCuestionarios.setId(id);
		beanCuestionarios.setPreguntas(new ArrayList<>());
		beanCuestionarios.setSucursales(Arrays.asList("0107"));
		beanCuestionarios.setTitulo(new Titulo());
		beanCuestionarios.setTotal(1L);
		listCuestionarios.add(beanCuestionarios);

		when(mockCursorDoc.hasNext()).thenReturn(true, false);
		when(mockCursorDoc.next()).thenReturn(listCuestionario);
		when(aggIterable.iterator()).thenReturn(mockCursorDoc);
		when(dao.consultaRespuestasSucursal(any())).thenReturn(aggIterable);
		certificacionModificacionesImpl.certificaSucursal(sucursal);
		
		sucursal = "0107000";
		ResponseApi result = certificacionModificacionesImpl.certificaSucursal(sucursal);

		assertNotNull(result);
	}

}
