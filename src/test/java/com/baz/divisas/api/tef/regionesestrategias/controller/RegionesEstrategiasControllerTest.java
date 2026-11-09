package com.baz.divisas.api.tef.regionesestrategias.controller;

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

import com.baz.divisas.api.tef.regionesestrategias.dto.RequestEstrategia;
import com.baz.divisas.api.tef.regionesestrategias.service.IRegionesEstrategias;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
public class RegionesEstrategiasControllerTest {

	@InjectMocks
	RegionesEstrategiasController regionesEstrategiasController;

	@Mock
	IRegionesEstrategias regionesEstrategiasService;

	@Test
	void testGetEstrategiasRegiones() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(regionesEstrategiasService.consultaEstrategiasRegiones()).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regionesEstrategiasController.getEstrategiasRegiones();
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testAsignarEstrategiaRegion() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		
		RequestEstrategia request = new RequestEstrategia();
		request.setActivo(true);
		request.isActivo();
		request.setActivoEstrategia(true);
		request.isActivoEstrategia();
		request.setIdDivisa("");
		request.getIdDivisa();
		request.setIdRegion("");
		request.getIdRegion();
		request.setMonto(0.0);
		request.getMonto();
		request.setTipoOperacion("");
		request.getTipoOperacion();
		
		when(regionesEstrategiasService.asignarEstrategiaRegion(isA(RequestEstrategia.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regionesEstrategiasController.asignarEstrategiaRegion(request);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testActualizarEstatusEstrategiasRegion() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		
		RequestEstrategia request = new RequestEstrategia();

		when(regionesEstrategiasService.actualizarEstatusEstrategiaRegion(isA(RequestEstrategia.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regionesEstrategiasController.actualizarEstatusEstrategiasRegion(request);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
}
