package com.baz.divisas.api.core.reporteria;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.isA;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.baz.divisas.api.core.reporteria.dto.ReqBusquedaIncidencias;
import com.baz.divisas.api.core.reporteria.dto.ReqReporteOperaciones;
import com.baz.divisas.api.core.reporteria.dto.ReqReporteRascadito;
import com.baz.divisas.api.core.reporteria.model.Incidencia;
import com.baz.divisas.api.core.reporteria.services.ReporteriaServiceImpl;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
class ReporteriaControllerTest {

	@InjectMocks
	private ReporteriaController reporteriaController;	
	@Mock
	private ReporteriaServiceImpl reporteriaService;
	
	
	@Test
	void testGetIncidencias() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK, new ArrayList<Incidencia>());
		when(reporteriaService.getIncidenciasImpl(isA(ReqBusquedaIncidencias.class))).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = reporteriaController.getIncidencias(new ReqBusquedaIncidencias());
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetDetalleIncidencia() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK, new Incidencia());
		when(reporteriaService.getIncidenciaImpl(isA(String.class))).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = reporteriaController.getIncidencia("");
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testDescargarIncidencias() throws UnsupportedEncodingException {
		String message = "test";
		ByteArrayInputStream respApi = new ByteArrayInputStream(message.getBytes("UTF-8"));
		when(reporteriaService.descargaIncidenciasImpl(isA(ReqBusquedaIncidencias.class))).thenReturn(respApi);	
		
		ResponseEntity<Resource> response = reporteriaController.descargaIncidencias(new ReqBusquedaIncidencias());
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetOperaciones() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(reporteriaService.getOperacionesImpl(isA(ReqReporteOperaciones.class))).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = reporteriaController.getOperaciones(new ReqReporteOperaciones());
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testDescargaOperaciones() {
		
		when(reporteriaService.descargaOperacionesImpl(isA(ReqReporteOperaciones.class))).thenReturn(new ByteArrayInputStream(IOUtils.EMPTY_BYTE_ARRAY));
		
		ResponseEntity<Resource> response = reporteriaController.descargaOperaciones(new ReqReporteOperaciones());
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetAcumuladoOperaciones() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(reporteriaService.getAcumuladoOperacionesImpl(isA(ReqReporteOperaciones.class))).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = reporteriaController.getAcumuladoOperaciones(new ReqReporteOperaciones());
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testDescargaGanadoresRascadito() {
		
		when(reporteriaService.descargaGanadoresRascaditoImpl(isA(ReqReporteRascadito.class))).thenReturn(new ByteArrayInputStream(IOUtils.EMPTY_BYTE_ARRAY));
		
		ResponseEntity<Resource> response = reporteriaController.descargaGanadoresRascadito(new ReqReporteRascadito());
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testDescargaGolDeOro() {
		
		when(reporteriaService.descargaGanadoresGolDeOroImpl()).thenReturn(new ByteArrayInputStream(IOUtils.EMPTY_BYTE_ARRAY));
		
		ResponseEntity<Resource> response = reporteriaController.descargaGolDeOro();
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	
	@Test
	void testdescargaInventarioMundialVerde() {
		
		when(reporteriaService.descargaInventarioMundialVerdeImpl()).thenReturn(new ByteArrayInputStream(IOUtils.EMPTY_BYTE_ARRAY));
		
		ResponseEntity<Resource> response = reporteriaController.descargaInventarioMundialVerde();
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	
	@Test
	void testDescargaOperacionesMundialVerde() {
		
		when(reporteriaService.descargaGanadoresVeranoMundialista()).thenReturn(new ByteArrayInputStream(IOUtils.EMPTY_BYTE_ARRAY));
		
		ResponseEntity<Resource> response = reporteriaController.descargaOperacionesMundialVerde();
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	
	
	@Test
	void testdescargaInventarioVeranoMundialista() {
		
		when(reporteriaService.descargaInventarioVeranoMundialistaImpl()).thenReturn(new ByteArrayInputStream(IOUtils.EMPTY_BYTE_ARRAY));
		
		ResponseEntity<Resource> response = reporteriaController.descargaInventarioVeranoMundialista();
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testDescargaCuponesMasSalud() {
		
		when(reporteriaService.descargaCuponesMasSaludImpl()).thenReturn(new ByteArrayInputStream(IOUtils.EMPTY_BYTE_ARRAY));
		
		ResponseEntity<Resource> response = reporteriaController.descargaCuponesMasSalud();
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testDescargaOperacionesCostalsanta() {
		
		when(reporteriaService.descargaGanadoresCostalSanta()).thenReturn(new ByteArrayInputStream(IOUtils.EMPTY_BYTE_ARRAY));
		
		ResponseEntity<Resource> response = reporteriaController.descargaOperacionesCostalsanta();
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testDescargaInventarioCostalSanta() {
		
		when(reporteriaService.descargaInventarioCostalSanta()).thenReturn(new ByteArrayInputStream(IOUtils.EMPTY_BYTE_ARRAY));
		
		ResponseEntity<Resource> response = reporteriaController.descargaInventarioCostalSanta();
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
}
