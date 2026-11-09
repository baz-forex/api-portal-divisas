package com.baz.divisas.api.digital.reporteria.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;

import com.baz.divisas.api.digital.reporteria.dao.CotizacionesDao;
import com.baz.divisas.api.digital.reporteria.dto.ReqActualizaEstatus;
import com.baz.divisas.api.digital.reporteria.dto.ReqBusqOperacionesApp;
import com.baz.divisas.api.digital.reporteria.services.ReporteriaServiceImpl;
import com.baz.divisas.general.dto.ResponseApi;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ReporteriaServiceImplTest {
	
	@InjectMocks
	private ReporteriaServiceImpl reporteriaService;
	@Mock
	private CotizacionesDao cotizacionesDao;
	
	@Test
	void testGetOperacionesApp() {
	
		ReqBusqOperacionesApp request = new ReqBusqOperacionesApp();

		when(cotizacionesDao.countOperacionesApp(isA(Document.class))).thenReturn(0L);
		
		ResponseApi response = reporteriaService.getOperacionesAppImpl(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());	
		
		/////////////
		request.setDivisa("");
		request.getDivisa();
		request.setEstatus(Arrays.asList());
		request.getEstatus();
		request.setFechaFinal("");
		request.getFechaFinal();
		request.setFechaInicial("");
		request.getFechaInicial();
		request.setIdCliente("");
		request.getIdCliente();
		request.setSucursal("");
		request.getSucursal();
		request.setTipoOperacion(Arrays.asList());
		request.getTipoOperacion();
		request.setEntregaDomicilio(false);
		request.isEntregaDomicilio();
		
		
		when(cotizacionesDao.countOperacionesApp(isA(Document.class))).thenReturn(1L);
		List<Document> listOperaciones = new ArrayList<>();
		when(cotizacionesDao.consultaOperacionesApp(isA(Document.class), isA(boolean.class))).thenReturn(listOperaciones);

		response = reporteriaService.getOperacionesAppImpl(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		/////////////
		request.setFechaInicial("1");
		request.setEntregaDomicilio(true);

		response = reporteriaService.getOperacionesAppImpl(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		/////////////
		request.setFechaFinal("1");
		request.setDivisa("1");
		request.setIdCliente("1");
		request.setSucursal("1");
		request.setEstatus(Arrays.asList(""));
		request.setAplicacionOrigen("1");
		request.getAplicacionOrigen();
		
		response = reporteriaService.getOperacionesAppImpl(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
	
		/////////////
		listOperaciones.add(new Document()); 

		response = reporteriaService.getOperacionesAppImpl(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testDescargaOperacionesAppImpl() {
		
		ReqBusqOperacionesApp request = new ReqBusqOperacionesApp();

		List<Document> listOperaciones = new ArrayList<>();
		when(cotizacionesDao.consultaOperacionesApp(isA(Document.class), isA(boolean.class))).thenReturn(listOperaciones);
		
		ByteArrayInputStream response = reporteriaService.descargaOperacionesAppImpl(request);
		assertThat(response).isNotNull();
		
		/////////////
		Document docOperacion = new Document("tipo_operacion", "413");
		docOperacion.put("monto_divisa", "0.0");
		docOperacion.put("monto_mxn", "0.0");
		docOperacion.put("tipo_cambio", "0.0");
		listOperaciones.add(docOperacion);
		
		response = reporteriaService.descargaOperacionesAppImpl(request);
		assertThat(response).isNotNull();
			
		/////////////		
		docOperacion.put("programa", "GA10");
		docOperacion.put("isEntregaDomicilio", false);
		
		request.setEntregaDomicilio(true);
		
		response = reporteriaService.descargaOperacionesAppImpl(request);
		assertThat(response).isNotNull();
		
		/////////////		
		docOperacion.put("tipo_operacion", "412");
		docOperacion.put("isEntregaDomicilio", true);
		docOperacion.put("fecha_redencion", "2023-04-01T23:29:59");
		
		request.setEntregaDomicilio(true);
		
		response = reporteriaService.descargaOperacionesAppImpl(request);
		assertThat(response).isNotNull();

	}
	
	@Test
	void testGetIncidenciasAppImpl() {
	
		List<Document> listaIncidencias = new ArrayList<>();
		when(cotizacionesDao.consultaIncidenciasApp()).thenReturn(listaIncidencias);
	
		ResponseApi response = reporteriaService.getIncidenciasAppImpl();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());	
		
		/////////////
		listaIncidencias.add(new Document());
		
		response = reporteriaService.getIncidenciasAppImpl();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
	}
	
	@Test
	void testActualizaCotizacionImpl() {
	
		when(cotizacionesDao.actualizaEstatusCotizacion(isA(String.class), isA(String.class), isA(String.class))).thenReturn(false);
	
		ReqActualizaEstatus request = new ReqActualizaEstatus();
		request.setIdCotizacion("");
		request.setIdEstatus("");
		request.setTipoOperacion("");
		
		ResponseApi response = reporteriaService.actualizaCotizacionImpl(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());	
		
		/////////////
		when(cotizacionesDao.actualizaEstatusCotizacion(isA(String.class), isA(String.class), isA(String.class))).thenReturn(true);
		
		response = reporteriaService.actualizaCotizacionImpl(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());	
		
	}
}
