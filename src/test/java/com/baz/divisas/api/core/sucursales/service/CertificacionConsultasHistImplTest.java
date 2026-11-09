package com.baz.divisas.api.core.sucursales.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.baz.divisas.api.core.sucursales.dto.ReqPaginado;
import com.baz.divisas.api.core.sucursales.dto.ReqPaginadoBitacora;
import com.baz.divisas.api.core.sucursales.model.BeanCuestionarios;
import com.baz.divisas.api.core.sucursales.model.BeanCuestionariosBitacora;
import com.baz.divisas.api.core.sucursales.model.Titulo;
import com.baz.divisas.general.dto.ResponseApi;
import com.mongodb.client.MongoCursor;
import com.baz.divisas.api.core.sucursales.dao.CertificacionDao;

import java.util.List;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class CertificacionConsultasHistImplTest {

	private CertificacionConsultasHistImpl certificacionConsultasHistImpl;

	@Mock
	private CertificacionDao dao;

	@Mock
	private MongoCursor<BeanCuestionarios> cursorCuestionarios;

	private BeanCuestionarios cuestioanrio;
	private ResponseApi result;
	private String id;

	@BeforeEach
	public void setUp() {
		certificacionConsultasHistImpl = new CertificacionConsultasHistImpl(dao);
		cuestioanrio = new BeanCuestionarios();
		Titulo titulo = new Titulo();
		titulo.setImagen("imegensota");
		titulo.setTexto("Prueba");
		cuestioanrio.setEstatus(false);
		cuestioanrio.setFechaRegistro("");
		cuestioanrio.setId("");
		cuestioanrio.setPreguntas(new ArrayList<>());
		cuestioanrio.setSucursales(new ArrayList<>());
		cuestioanrio.setTitulo(titulo);
		cuestioanrio.setTotal(1);
		id = "6476817e27ec6e6eb2dc4b46";
	}

	@Test
	void testConsultaCuestionariosHistorico() {
		// Arrange
		ReqPaginado reqPaginado = new ReqPaginado();
		reqPaginado.setElementosPagina(1);
		reqPaginado.setPagina(1);

		List<BeanCuestionarios> registros = new ArrayList<>();

		registros.add(cuestioanrio);

		when(dao.consultaCuestionariosHistorico(reqPaginado)).thenReturn(registros);

		when(dao.conteoCuestionariosHist()).thenReturn(1L);

		certificacionConsultasHistImpl.consultaCuestionariosHistorico(reqPaginado);

		registros = new ArrayList<>();
		when(dao.consultaCuestionariosHistorico(reqPaginado)).thenReturn(registros);
		certificacionConsultasHistImpl.consultaCuestionariosHistorico(reqPaginado);

		when(dao.consultaCuestionariosHistorico(reqPaginado))
				.thenThrow(new IllegalArgumentException());
		certificacionConsultasHistImpl.consultaCuestionariosHistorico(reqPaginado);

		reqPaginado.setElementosPagina(0);
		reqPaginado.setPagina(0);

		result = certificacionConsultasHistImpl.consultaCuestionariosHistorico(reqPaginado);

		assertNotNull(result);

	}

	@Test
	public void testConsultaPregCuestionariosHist() {

		when(cursorCuestionarios.hasNext()).thenReturn(true, true, false);
		when(cursorCuestionarios.next()).thenReturn(cuestioanrio);
		when(dao.consultaPregCuestionariosHist(id)).thenReturn(cursorCuestionarios);
		certificacionConsultasHistImpl.consultaPregCuestionariosHist(id);

		when(cursorCuestionarios.hasNext()).thenReturn(false);
		certificacionConsultasHistImpl.consultaPregCuestionariosHist(id);
		
		when(dao.consultaPregCuestionariosHist(id)).thenThrow(new IllegalArgumentException());
		certificacionConsultasHistImpl.consultaPregCuestionariosHist(id);
		
		id = "45412";
		result = certificacionConsultasHistImpl.consultaPregCuestionariosHist(id);

		assertNotNull(result);
	}

	@Test
	public void testConsultaBitacoraCuestionario() {
		ReqPaginadoBitacora reqPaginadoBitacora = new ReqPaginadoBitacora();
		reqPaginadoBitacora.setElementosPagina(10);
		reqPaginadoBitacora.setPagina(1);
		reqPaginadoBitacora.setId(id);

		List<BeanCuestionariosBitacora> registros = new ArrayList<>();

		registros.add(new BeanCuestionariosBitacora());

		when(dao.consultaBitacoraCuestionario(reqPaginadoBitacora)).thenReturn(registros);
		certificacionConsultasHistImpl.consultaBitacoraCuestionario(reqPaginadoBitacora);

		registros = new ArrayList<>();
		when(dao.consultaBitacoraCuestionario(reqPaginadoBitacora)).thenReturn(registros);
		certificacionConsultasHistImpl.consultaBitacoraCuestionario(reqPaginadoBitacora);

		BeanCuestionariosBitacora beanCuestionariosBitacora = new BeanCuestionariosBitacora();
		beanCuestionariosBitacora.setEvento("Nuevo Cuestionario");
		beanCuestionariosBitacora.setTitulo(new Titulo());
		beanCuestionariosBitacora.setPreguntas(new ArrayList<>());
		beanCuestionariosBitacora.setFechaRegistro("prueba");
		registros = new ArrayList<>();
		registros.add(beanCuestionariosBitacora);
		when(dao.consultaBitacoraCuestionario(reqPaginadoBitacora)).thenReturn(registros);
		certificacionConsultasHistImpl.consultaBitacoraCuestionario(reqPaginadoBitacora);

		beanCuestionariosBitacora = new BeanCuestionariosBitacora();
		beanCuestionariosBitacora.setEvento("Asigna Cuestionario");
		beanCuestionariosBitacora.setSucursales(new ArrayList<>());
		registros = new ArrayList<>();
		registros.add(beanCuestionariosBitacora);
		when(dao.consultaBitacoraCuestionario(reqPaginadoBitacora)).thenReturn(registros);
		certificacionConsultasHistImpl.consultaBitacoraCuestionario(reqPaginadoBitacora);

		beanCuestionariosBitacora = new BeanCuestionariosBitacora();
		beanCuestionariosBitacora.setEvento("Actualiza Estatus Cuestionario");
		beanCuestionariosBitacora.setEstatus(false);
		registros = new ArrayList<>();
		registros.add(beanCuestionariosBitacora);
		when(dao.consultaBitacoraCuestionario(reqPaginadoBitacora)).thenReturn(registros);
		certificacionConsultasHistImpl.consultaBitacoraCuestionario(reqPaginadoBitacora);

		when(dao.consultaBitacoraCuestionario(reqPaginadoBitacora))
				.thenThrow(new IllegalArgumentException());
		certificacionConsultasHistImpl.consultaBitacoraCuestionario(reqPaginadoBitacora);

		reqPaginadoBitacora.setElementosPagina(0);
		reqPaginadoBitacora.setPagina(0);
		result = certificacionConsultasHistImpl.consultaBitacoraCuestionario(reqPaginadoBitacora);

		assertNotNull(result);
	}
}
