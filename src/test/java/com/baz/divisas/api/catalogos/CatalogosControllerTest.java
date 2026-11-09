package com.baz.divisas.api.catalogos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.isA;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.baz.divisas.api.catalogos.model.Catalogo;
import com.baz.divisas.api.catalogos.services.CatalogosServiceImpl;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
class CatalogosControllerTest {

	@InjectMocks
	private CatalogosController catalogosController;
	@Mock
	private CatalogosServiceImpl catalogosService;

	
	@Test
	void testGetCatPaisNacionalidad() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(catalogosService.getCatPaisNacionalidad()).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = catalogosController.getCatPaisNacionalidad();
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetCatEstados() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(catalogosService.getCatEstados()).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = catalogosController.getCatEstados();
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetCatDivisas() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(catalogosService.getCatDivisas()).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = catalogosController.getCatDivisas();
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetCatIdentificaciones() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(catalogosService.getCatIdentificaciones()).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = catalogosController.getCatIdentificaciones();
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testActualizaDivisa() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(catalogosService.actualizaEstatusDivisa(isA(Catalogo.class))).thenReturn(respApi);	
		
		Catalogo catalogoDivisa = new Catalogo();
		catalogoDivisa = new Catalogo("", "");
		catalogoDivisa = new Catalogo("", "", true);
		catalogoDivisa.setId("");
		catalogoDivisa.getId();
		catalogoDivisa.setDescripcion("");
		catalogoDivisa.getDescripcion();
		catalogoDivisa.setActivo(true);
		catalogoDivisa.isActivo();
		
		ResponseEntity<ResponseApi> response = catalogosController.actualizaDivisa(catalogoDivisa);
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testRegistraDivisa() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(catalogosService.creaDivisa(isA(Catalogo.class))).thenReturn(respApi);	
		
		Catalogo catalogoDivisa = new Catalogo();
		
		ResponseEntity<ResponseApi> response = catalogosController.registraDivisa(catalogoDivisa);
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetCatEstatusCotizacion() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(catalogosService.getCatEstatusCotizacion()).thenReturn(respApi);	
				
		ResponseEntity<ResponseApi> response = catalogosController.getCatEstatusCotizacion();
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetCatTipoOperacion() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(catalogosService.getCatTipoOperacion()).thenReturn(respApi);	
				
		ResponseEntity<ResponseApi> response = catalogosController.getCatTipoOperacion();
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetCatTipoCliente() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(catalogosService.getCatTipoCliente()).thenReturn(respApi);	
				
		ResponseEntity<ResponseApi> response = catalogosController.getCatTipoCliente();
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetCatPerfilClienteCampanias() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(catalogosService.getCatPerfilClienteCampanias()).thenReturn(respApi);	
				
		ResponseEntity<ResponseApi> response = catalogosController.getCatPerfilClienteCampanias();
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetCatTipoLimiteCampanias() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(catalogosService.getCatTipoLimiteCampanias()).thenReturn(respApi);	
				
		ResponseEntity<ResponseApi> response = catalogosController.getCatTipoLimiteCampanias();
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetCatTipoAcumuladoCampanias() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(catalogosService.getCatTipoAcumuladoCampanias()).thenReturn(respApi);	
				
		ResponseEntity<ResponseApi> response = catalogosController.getCatTipoAcumuladoCampanias();
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetCatEstatusCampanias() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(catalogosService.getCatEstatusCampanias()).thenReturn(respApi);	
				
		ResponseEntity<ResponseApi> response = catalogosController.getCatEstatusCampanias();
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetCatRangoEdadesCampanias() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(catalogosService.getCatRangoEdadesCampanias()).thenReturn(respApi);	
				
		ResponseEntity<ResponseApi> response = catalogosController.getCatRangoEdadesCampanias();
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	
}
