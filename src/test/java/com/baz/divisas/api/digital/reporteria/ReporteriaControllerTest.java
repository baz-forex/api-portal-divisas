package com.baz.divisas.api.digital.reporteria;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.baz.divisas.api.digital.reporteria.dto.ReqActualizaEstatus;
import com.baz.divisas.api.digital.reporteria.dto.ReqBusqOperacionesApp;
import com.baz.divisas.api.digital.reporteria.services.ReporteriaServiceImpl;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
public class ReporteriaControllerTest {
	
	@InjectMocks
	private ReporteriaController reporteriaController;
	@Mock
	private ReporteriaServiceImpl reporteriaService;
	
	@Test
	void testGetOperacionesApp() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(reporteriaService.getOperacionesAppImpl(isA(ReqBusqOperacionesApp.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = reporteriaController.getOperacionesApp(new ReqBusqOperacionesApp());
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testDescargaOperacionesApp() {
		
		when(reporteriaService.descargaOperacionesAppImpl(isA(ReqBusqOperacionesApp.class))).thenReturn(new ByteArrayInputStream(IOUtils.EMPTY_BYTE_ARRAY));
		
		ResponseEntity<Resource> response = reporteriaController.descargaOperacionesApp(new ReqBusqOperacionesApp());
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetIncidenciasApp() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(reporteriaService.getIncidenciasAppImpl()).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = reporteriaController.getIncidenciasApp();
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testActualizaCotizacion() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(reporteriaService.actualizaCotizacionImpl(isA(ReqActualizaEstatus.class))).thenReturn(respApi);
		
		ReqActualizaEstatus request = new ReqActualizaEstatus();
		request.setIdCotizacion("");
		request.getIdCotizacion();
		request.setIdEstatus("");
		request.getIdEstatus();
		request.setTipoOperacion("");
		request.getTipoOperacion();
		
		ResponseEntity<ResponseApi> response = reporteriaController.actualizaCotizacion(request);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

}
