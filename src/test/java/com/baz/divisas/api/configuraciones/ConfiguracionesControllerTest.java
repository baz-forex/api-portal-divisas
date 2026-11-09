package com.baz.divisas.api.configuraciones;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.baz.divisas.api.catalogos.model.CatPaisNacionalidad;
import com.baz.divisas.api.catalogos.model.Catalogo;
import com.baz.divisas.api.core.configuraciones.ConfiguracionesController;
import com.baz.divisas.api.core.configuraciones.dto.BeanSegmentoLealtad;
import com.baz.divisas.api.core.configuraciones.dto.BeanSegmentoRentabilidad;
import com.baz.divisas.api.core.configuraciones.dto.BeanValoresRentabilidad;
import com.baz.divisas.api.core.configuraciones.dto.ReqCampoRestringido;
import com.baz.divisas.api.core.configuraciones.dto.ReqEditSegmentoLealtad;
import com.baz.divisas.api.core.configuraciones.dto.ReqEditSegmentoRent;
import com.baz.divisas.api.core.configuraciones.dto.ReqEditValoresRentabilidad;
import com.baz.divisas.api.core.configuraciones.dto.RespConsultaCatalogoRentLealVal;
import com.baz.divisas.api.core.configuraciones.model.CampoRestringido;
import com.baz.divisas.api.core.configuraciones.model.Notificacion;
import com.baz.divisas.api.core.configuraciones.model.ParametriaBusquedas;
import com.baz.divisas.api.core.configuraciones.model.SegmentoSucursal;
import com.baz.divisas.api.core.configuraciones.services.ConfiguracionesServiceImpl;
import com.baz.divisas.api.core.configuraciones.services.PerfilDeClientesServiceImpl;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
class ConfiguracionesControllerTest {

	@InjectMocks
	private ConfiguracionesController configController;
	@Mock
	private ConfiguracionesServiceImpl configService;
	@Mock
	private PerfilDeClientesServiceImpl clientesServiceImpl;
	
	@Test
	void testGetParametriaBusquedas() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK, new ParametriaBusquedas());
		when(configService.getParametriaBusquedas()).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = configController.getParametriaBusquedas();
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testActualizaParametriaBusquedas() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(configService.actualizaParametriaBusquedas(isA(ParametriaBusquedas.class))).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = configController.actualizaParametriaBusquedas(new ParametriaBusquedas());
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetCamposRestringidos() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK, new ArrayList<CampoRestringido>());
		when(configService.getCamposRestringidos()).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = configController.getCamposRestringidos();
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetCampoRestringido() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK, new CampoRestringido());
		when(configService.getCamposRestringido(isA(String.class))).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = configController.getCampoRestringido("");
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testAgregarRestriccionCampo() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(configService.agregarRestriccionCampo(isA(ReqCampoRestringido.class))).thenReturn(respApi);	
		
		ReqCampoRestringido request = new ReqCampoRestringido();
		request.setIdCampo("");
		request.setRestriccion("");
		
		ResponseEntity<ResponseApi> response = configController.agregarRestriccionCampo(new ReqCampoRestringido());
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testEliminarRestriccionCampo() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(configService.eliminarRestriccionCampo(isA(ReqCampoRestringido.class))).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = configController.eliminarRestriccionCampo(new ReqCampoRestringido());
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testUpdatePaisRestringido() {
					
		CatPaisNacionalidad pais = new CatPaisNacionalidad();
		pais.setId("1");
		pais.getId();
		pais.setDescripcion("MEXICO");
		pais.getDescripcion();
		pais.setRestriccionPais(true);
		pais.isRestriccionPais();
		pais.setRestriccionNacionalidad(false);
		pais.isRestriccionNacionalidad();
		
		List<CatPaisNacionalidad> lisPais = Arrays.asList(pais);	
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK,lisPais);
		when(configService.actualizaRestriccionPaisNacionalidad(any())).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = configController.
											   updatePaisesRestringidos(Arrays.asList(new CatPaisNacionalidad()));
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetCatSegmentoSucursal() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK, new ArrayList<Catalogo>());
		when(configService.getCatSegmentosSucursal()).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = configController.getCatSegmentoSucursal();
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetSegmentosSucursal() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK, new ArrayList<SegmentoSucursal>());
		when(configService.getSegmentosSucursal(isA(String.class))).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = configController.getSegmentosSucursal("");
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetSegmentoSucursal() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK, new SegmentoSucursal());
		when(configService.getSegmentoSucursal(isA(String.class))).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = configController.getSegmentoSucursal("");
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testConsultaCatalogosSegmentosLealRentVal() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK, new RespConsultaCatalogoRentLealVal());
		when(clientesServiceImpl.getPerfilesDelCliente(isA(String.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = configController.consultaCatalogosSegmentosLealRentVal("");

		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testConsultaCatalogosSegmentosValorRent() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK, new RespConsultaCatalogoRentLealVal());
		when(clientesServiceImpl.getValoresRentabilida()).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = configController.consultaCatalogosSegmentosValorRent();

		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

	}

	
	@Test
	void testEditarCatSegmentoLealtad() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(clientesServiceImpl.editarCatSegmentoLealtad(any())).thenReturn(respApi);
		
		ReqEditSegmentoLealtad request = new ReqEditSegmentoLealtad();
		request.setSegmentoLealtad(new BeanSegmentoLealtad());
		request.getSegmentoLealtad();

		ResponseEntity<ResponseApi> response = configController.editarCatSegmentoLealtad(request);

		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	

	@Test
	void testEditarCatSegmentoRentabilidad() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(clientesServiceImpl.editarCatSegmentoRentabilidad(any())).thenReturn(respApi);
		
		ReqEditSegmentoRent request = new ReqEditSegmentoRent();
		request.setSegmentoRentabilidad(new BeanSegmentoRentabilidad());
		request.getSegmentoRentabilidad();

		ResponseEntity<ResponseApi> response = configController.editarCatSegmentoRentabilidad(request);

		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	@Test
	void testEditarCatValoresRentabilidad() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(clientesServiceImpl.editarCatValoresRentabilidad(any())).thenReturn(respApi);

		ReqEditValoresRentabilidad request = new ReqEditValoresRentabilidad();
		request.setValoresRentabilidad(new BeanValoresRentabilidad());
		request.getValoresRentabilidad();
		
		ResponseEntity<ResponseApi> response = configController.editarCatValoresRentabilidad(request);

		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testRegistraSegmentoSucursal() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(configService.registraSegmentoSucursal(isA(SegmentoSucursal.class))).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = configController.registraSegmentoSucursal(new SegmentoSucursal());
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testActualizaSegmentoSucursal() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(configService.actualizarSegmentoSucursal(isA(SegmentoSucursal.class))).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = configController.actualizaSegmentoSucursal(new SegmentoSucursal());
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testActualizaEstatusSegmentoSucursal() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(configService.actualizarEstatusSegmentoSucursal(isA(String.class))).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = configController.actualizaEstatusSegmentoSucursal("");
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testEliminaSegmentoSucursal() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(configService.eliminarSegmentoSucursal(isA(String.class))).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = configController.eliminaSegmentoSucursal("");
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	@Test
	void testGetCatEstatusNotificacion() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK, new ArrayList<Catalogo>());
		when(configService.getCatEstatusNotificacion()).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = configController.getCatEstatusNotificacion();
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetNotificaciones() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK, new ArrayList<Notificacion>());
		when(configService.getNotificaciones(isA(String.class), isA(String.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = configController.getNotificaciones("", "");		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
		
		response = configController.getNotificaciones("");		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetNotificacion() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK, new ArrayList<Notificacion>());
		when(configService.getNotificacion(isA(String.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = configController.getNotificacion("");	
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testRegistrarNotificacion() throws IOException {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(configService.registraNotificacion(isA(Notificacion.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = configController.registrarNotificacion(new Notificacion());	
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testActualizarNotificacion() throws IOException {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(configService.actualizaNotificacion(isA(Notificacion.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = configController.actualizarNotificacion(new Notificacion());	
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testActualizarEstatusNotificacion() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(configService.actualizarEstatusNotificacion(isA(String.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = configController.actualizaEstatusNotificacion("");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testReiniciarNotificacion() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(configService.reiniciaNotificacion(isA(String.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = configController.reiniciarNotificacion("");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

}
