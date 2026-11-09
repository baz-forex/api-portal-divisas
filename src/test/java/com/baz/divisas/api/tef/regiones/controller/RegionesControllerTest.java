package com.baz.divisas.api.tef.regiones.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.baz.divisas.api.tef.regiones.model.Region;
import com.baz.divisas.api.tef.regiones.services.IRegionesService;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
public class RegionesControllerTest {

	@InjectMocks
	RegionesController regionesController;

	@Mock
	IRegionesService regionesService;

	@Test
	void testGetRegiones() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(regionesService.consultaRegiones()).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = regionesController.getRegiones();
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testRegistraRegion() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(regionesService.creaRegion(isA(String.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regionesController.registraRegion(new Region("", true, null));
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

	}

	@Test
	void testActualizaDescripcionRegion() {
		
		Region region = new Region();
		region.setActivo(true);
		region.isActivo();
		region.setDescripcion("");
		region.getDescripcion();
		region.setDivisas(Arrays.asList());
		region.getDivisas();
		region.setId("");
		region.getId();
		region.setSegmentos(Arrays.asList());
		region.getSegmentos();
		region.setSucursales(Arrays.asList());
		region.getSucursales();
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(regionesService.actualizaDescripcionRegion(isA(String.class), isA(String.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regionesController.actualizaDescripcionRegion(region);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testActualizaEstatusRegion() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(regionesService.actualizaEstatusRegion(isA(String.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regionesController.actualizaEstatusRegion("");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testEliminaRegion() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(regionesService.eliminaRegion(isA(String.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regionesController.eliminaRegion("");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	@Test
	void testActualizaEstatusRegionDivisa() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(regionesService.actualizaEstatusDivisaRegion(isA(String.class), isA(String.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regionesController.actualizaEstatusRegionDivisa("", "");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	//OLP
	@Test
	void testRegistraRegionOlp() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(regionesService.creaRegionOlp(isA(String.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regionesController.registraRegionOlp(new Region("", true, null));
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

	}

	@Test
	void testActualizaDescripcionRegionOlp() {
		
		Region region = new Region();
		region.setActivo(true);
		region.isActivo();
		region.setDescripcion("");
		region.getDescripcion();
		region.setDivisas(Arrays.asList());
		region.getDivisas();
		region.setId("");
		region.getId();
		region.setSegmentos(Arrays.asList());
		region.getSegmentos();
		region.setSucursales(Arrays.asList());
		region.getSucursales();
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(regionesService.actualizaDescripcionRegionOlp(isA(String.class), isA(String.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regionesController.actualizaDescripcionRegionOlp(region);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testActualizaEstatusRegionOlp() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(regionesService.actualizaEstatusRegionOlp(isA(String.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regionesController.actualizaEstatusRegionOlp("");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testEliminaRegionOlp() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(regionesService.eliminaRegionOlp(isA(String.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regionesController.eliminaRegionOlp("");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	@Test
	void testActualizaEstatusRegionDivisaOlp() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(regionesService.actualizaEstatusDivisaRegionOlp(isA(String.class), isA(String.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regionesController.actualizaEstatusRegionDivisaOlp("", "");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

}
