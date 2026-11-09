package com.baz.divisas.api.digital.configuraciones;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import com.baz.divisas.api.digital.configuraciones.model.ParametriaChatbot;
import com.baz.divisas.api.digital.configuraciones.model.ParametriaOperacion;
import com.baz.divisas.api.digital.configuraciones.model.ParametriaRemesas;
import com.baz.divisas.api.digital.configuraciones.model.ParametriaSeguimientoTC;
import com.baz.divisas.api.digital.configuraciones.services.ConfiguracionesDigitalServiceImpl;
import com.baz.divisas.api.tef.parametria.dto.ReqActAsignacion;
import com.baz.divisas.api.tef.parametria.dto.ReqArchivo;
import com.baz.divisas.api.tef.parametria.dto.ReqAsignacion;
import com.baz.divisas.api.tef.parametria.dto.ReqBusquedaAsignaciones;
import com.baz.divisas.api.tef.parametria.dto.ReqBusquedaParametros;
import com.baz.divisas.api.tef.parametria.dto.ReqDetalleParametro;
import com.baz.divisas.api.tef.parametria.dto.ReqEliminacionAsignacion;
import com.baz.divisas.api.tef.parametria.services.ParametriaServiceImpl;
import com.baz.divisas.api.tef.parametria.services.ParametriaSucursalesImpl;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class ConfiguracionesDigitalControllerTest {
	
	@InjectMocks
	private ConfiguracionesDigitalController configController;
	@Mock
	private ConfiguracionesDigitalServiceImpl configService;
	
	@Mock
	private ParametriaServiceImpl parametriaChatbot;
	
	@Mock 
	private ParametriaSucursalesImpl parametriaSucursal;
	
	@Test
	void testGetParametriaOperacion() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(configService.getParametriaOperacion(isA(String.class), isA(String.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = configController.getParametriaOperacion("", "app");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
		
		//////////////////
		response = configController.getParametriaOperacion("", "sapp");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testActualizaParametriaOperacion() {
		
		ParametriaOperacion parametria = new ParametriaOperacion();
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(configService.updateParametriaOperacion(isA(String.class) ,isA(String.class), isA(String.class),isA(ParametriaOperacion.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = configController.actualizaParametriaOperacion("", "app", parametria);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
		
		//////////////////
		response = configController.actualizaParametriaOperacion("", "sapp", parametria);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetParametriaSeguimientoTC() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(configService.getParametriaSeguimientoTC(isA(String.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = configController.getParametriaSeguimientoTC("");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	@Test
	void testActualizaParametriaSeguimientoTC() {
		
		ParametriaSeguimientoTC parametria = new ParametriaSeguimientoTC();
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(configService.updateParametriaSeguimientoTC(isA(String.class) ,isA(ParametriaSeguimientoTC.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = configController.actualizaParametriaSeguimientoTC("", parametria);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
		
	}
	
	@Test
	void testGetParametriaRemesas() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(configService.getParametriaRemesas(isA(String.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = configController.getParametriaRemesas("");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	@Test
	void testActualizaParametriaRemesas() {
		
		ParametriaRemesas parametria = new ParametriaRemesas();
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(configService.updateParametriaRemesas(isA(String.class) ,isA(ParametriaRemesas.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = configController.actualizaParametriaRemesas("", parametria);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
		
	}
	

	@Test
	void testGetParametriaChatBot() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(parametriaChatbot.getParametriaChatBot(isA(String.class),isA(String.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = configController.getParametriaChatBot("412","USD");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
		
		response = configController.getParametriaChatBot("413","CAD");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
		
	}
	
	@Test
	void testActualizaParametriaChatBot() {
				
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(parametriaChatbot.updateParametriaOperacion(isA(String.class) ,isA(String.class) ,isA(ParametriaChatbot.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = configController.actualizaParametriaChatBot("412","USD", new ParametriaChatbot());
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
		
		response = configController.actualizaParametriaChatBot("413","CAD", new ParametriaChatbot());
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	public void testConsultaParametrosSucursal() {
				
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(parametriaSucursal.consultaParametros(isA(ReqBusquedaParametros.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = configController.consultaParametrosSucursal(new ReqBusquedaParametros());
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	
	@Test
	public void testRegistraParametroSucursal() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(parametriaSucursal.registraParametro(isA(ReqDetalleParametro.class), isA(String.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = configController.registraParametroSucursal(new ReqDetalleParametro(), "1234");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	
	@Test
	public void testActualizaParametroSucursal() {
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(parametriaSucursal.actualizaParametro(isA(ReqDetalleParametro.class), isA(String.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = configController.actualizaParametroSucursal(new ReqDetalleParametro(), "1234");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	public void testRegistraParametrosMasivos() {
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(parametriaSucursal.registraParametrosMasivos(isA(ReqArchivo.class), isA(String.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = configController.registraParametrosMasivos(new ReqArchivo(), "1213");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	public void testBuscaAsignacionesParametros() {
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(parametriaSucursal.buscaParametrosAsignados(isA(ReqBusquedaAsignaciones.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = configController.buscaAsignacioneParametros(new ReqBusquedaAsignaciones());
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	public void testCrearAsignacionParametro() {
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(parametriaSucursal.crearAsignacionParametro(isA(ReqAsignacion.class), isA(String.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = configController.crearAsignacionParametro(new ReqAsignacion(), "1234");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	public void testActualizaAsignacionParametro() {
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(parametriaSucursal.actualizaAsignacionParametro(isA(ReqActAsignacion.class),isA(String.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = configController.actualizaAsignacionParametro(new ReqActAsignacion(), "1234");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	public void testEliminaAsignacionParametro() {
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(parametriaSucursal.eliminaAsignacionParametro(isA(ReqEliminacionAsignacion.class),isA(String.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = configController.eliminaAsignacionParametro(new ReqEliminacionAsignacion(), "1234");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	public void testRegistraAsignacionesParametrosMasivos() {
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(parametriaSucursal.registraAsignacionesParametrosMasivos(isA(ReqArchivo.class),isA(String.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = configController.registraAsignacionesParametrosMasivos(new ReqArchivo(), "1234");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
}
