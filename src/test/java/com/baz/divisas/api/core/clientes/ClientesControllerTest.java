package com.baz.divisas.api.core.clientes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.baz.divisas.api.core.clientes.dto.ReqBloqueoClientes;
import com.baz.divisas.api.core.clientes.dto.ReqBusquedaClientes;
import com.baz.divisas.api.core.clientes.dto.ReqClientesAgrupados;
import com.baz.divisas.api.core.clientes.model.ClienteDetalle;
import com.baz.divisas.api.core.clientes.services.CargaMasivaServiceAltoRiesgo;
import com.baz.divisas.api.core.clientes.services.CargaMasivaServiceImpl;
import com.baz.divisas.api.core.clientes.services.ClientesServiceImpl;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
class ClientesControllerTest {

	@InjectMocks
	private ClientesController clientesController;
	@Mock
	private ClientesServiceImpl clientesService;
	@Mock
	private CargaMasivaServiceImpl cargaMasivaService;
	
	@Mock
	private CargaMasivaServiceAltoRiesgo cargaMasivaAlRiesgo;

	@Test
	void testBusquedaClientes() {
		
		ReqBusquedaClientes request = new ReqBusquedaClientes();
		request.setApellidoMaterno("");
		request.getApellidoMaterno();
		request.setApellidoPaterno("");
		request.getApellidoPaterno();
		request.setFechaNacimiento("");
		request.getFechaNacimiento();
		request.setFolioIdentificacion("");
		request.getFolioIdentificacion();
		request.setIdCliente("");
		request.getIdCliente();
		request.setIdNegocio("");
		request.getIdNegocio();
		request.setNombre("");
		request.getNombre();		
		request.setTipoBusqueda("");
		request.getTipoBusqueda();
		request.setTipoIdentificacion("");
		request.getTipoIdentificacion();		
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(clientesService.busquedaClientes(isA(ReqBusquedaClientes.class))).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = clientesController.busquedaClientes(request);
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}	
	
	@Test
	void testBusquedaCliente() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(clientesService.busquedaClienteById(isA(String.class))).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = clientesController.busquedaCliente("");
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
		
	@Test
	void testConsultaBitacoraActCliente() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(clientesService.consultaBitacoraCliente(isA(String.class))).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = clientesController.consultaBitacoraActCliente("");
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
		
	@Test
	void testActualizaCliente() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(clientesService.actualizaCliente(isA(ClienteDetalle.class))).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = clientesController.actualizaCliente(new ClienteDetalle());
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	

	@Test
	void testCargaArchivoActualizacionMasiva() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(cargaMasivaService.cargaArchivoActualizacionMasiva(any())).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = clientesController.cargaArchivoActualizacionMasiva(null);
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testConsultaBitacoraCargaMasiva() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(cargaMasivaService.consultaBitacoraCargaMasiva()).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = clientesController.consultaBitacoraCargaMasiva();
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	
	@Test
	void testReporteErroresCargaMasiva() {
		
		
		when(cargaMasivaService.reporteErroresCargaMasiva(isA(String.class))).
		thenReturn(new ByteArrayInputStream("Test".getBytes(StandardCharsets.UTF_8)));	
		
		ResponseEntity<Resource> response = clientesController.reporteErroresCargaMasiva("");
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testConsultaAgrupadoClientes() {
				
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		
		when(clientesService.consultaAgrupadoClientes(isA(String.class), isA(int.class), isA(int.class))).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = clientesController.consultaAgrupadoClientes(0, 0, "");
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
		
	@Test
	void testRegistraAgrupadoClientes() {
				
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		
		when(clientesService.registraAgrupadoClientes(isA(ReqClientesAgrupados.class))).thenReturn(respApi);	
		
		ReqClientesAgrupados request = new ReqClientesAgrupados();
		request.setIdGrupo("");
		request.getIdGrupo();
		request.setClientes(Arrays.asList(""));
		request.getClientes();
		
		ResponseEntity<ResponseApi> response = clientesController.registraAgrupadoClientes(request);
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testActualizaAgrupadoClientes() {
				
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		
		when(clientesService.actualizaAgrupadoClientes(isA(ReqClientesAgrupados.class))).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = clientesController.actualizaAgrupadoClientes(new ReqClientesAgrupados());
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testEliminaAgrupadoClientes() {
				
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		
		when(clientesService.eliminaAgrupadoClientes(isA(String.class))).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = clientesController.eliminaAgrupadoClientes("");
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testConsultaBloqueoClientes() {
				
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		
		when(clientesService.consultaBloqueoClientes(isA(String.class))).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = clientesController.consultaBloqueoClientes("");
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
		
	@Test
	void testRegistraBloqueoClientes() {
				
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		
		when(clientesService.registraBloqueoClientes(isA(ReqBloqueoClientes.class))).thenReturn(respApi);	
		
		ReqBloqueoClientes request = new ReqBloqueoClientes();
		request.setCliente("");
		request.getCliente();
		request.setFolioIdentificacion("");
		request.getFolioIdentificacion();
		request.setId("");
		request.getId();
		request.setTipo("");
		request.getTipo();
		request.setTipoIdentificacion("");
		request.getTipoIdentificacion();
		
		ResponseEntity<ResponseApi> response = clientesController.registraBloqueoClientes(request);
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testEliminaBloqueoClientes() {
				
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		
		when(clientesService.eliminaBloqueoClientes(isA(String.class))).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = clientesController.eliminaBloqueoClientes("");
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetCatTiposBloqueoClientes() {

		ResponseEntity<ResponseApi> response = clientesController.getCatTiposBloqueoClientes();
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testCargaClientesAltoRiesgo() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(cargaMasivaAlRiesgo.registraClientesAltoRiesgo(any(), any(), any())).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = clientesController.cargaClientesAltoRiesgo(null, null, null);
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetCargasHistoricasClientes() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(clientesService.consultaCargaHistoricaAltoRiesgo()).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = clientesController.getCargasHistoricasClientes();
		 
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
		
		
	}
}
