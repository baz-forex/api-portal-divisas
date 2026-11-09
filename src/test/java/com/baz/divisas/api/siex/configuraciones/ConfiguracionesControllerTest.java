package com.baz.divisas.api.siex.configuraciones;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.baz.divisas.api.siex.configuraciones.dto.ReqCampoRestringido;
import com.baz.divisas.api.siex.configuraciones.model.CampoRestringido;
import com.baz.divisas.api.siex.configuraciones.services.ConfiguracionesServiceImpl;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
class ConfiguracionesControllerTest {

	@InjectMocks
	private ConfiguracionesController configController;
	@Mock
	private ConfiguracionesServiceImpl configService;

	
	@Test
	void testGetCamposRestringidos() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK, new ArrayList<CampoRestringido>());
		when(configService.getCamposRestringidosImpl()).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = configController.getCamposRestringidos();
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testDescargaCamposRestringidos() {
		
		when(configService.descargaCamposRestringidosImpl()).thenReturn(new ByteArrayInputStream(IOUtils.EMPTY_BYTE_ARRAY));	
		
		ResponseEntity<Resource> response = configController.descargaCamposRestringidos();
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetReglasCampoRestringido() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK, new ArrayList<CampoRestringido>());
		when(configService.getReglasCampoRestringidoImpl()).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = configController.getReglasCampoRestringido();
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetCampoRestringido() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK, new CampoRestringido());
		when(configService.getCampoRestringidoImpl(isA(String.class), isA(String.class))).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = configController.getCampoRestringido("", "");
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testAgregarRestriccionCampo() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(configService.agregarRestriccionCampoImpl(isA(ReqCampoRestringido.class))).thenReturn(respApi);	
		
		ReqCampoRestringido request = new ReqCampoRestringido();
		request.setIdsCampos(Arrays.asList());
		request.getIdsCampos();
		request.setIdRegla("");
		request.getIdRegla();
		request.setRestriccion("");
		request.getRestriccion();
		
		ResponseEntity<ResponseApi> response = configController.agregarRestriccionCampo(request);
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testEliminarRestriccionCampo() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(configService.eliminarRestriccionCampoImpl(isA(ReqCampoRestringido.class))).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = configController.eliminarRestriccionCampo(new ReqCampoRestringido());
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	

}
