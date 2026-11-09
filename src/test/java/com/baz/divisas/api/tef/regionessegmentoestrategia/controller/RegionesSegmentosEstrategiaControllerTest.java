package com.baz.divisas.api.tef.regionessegmentoestrategia.controller;

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

import com.baz.divisas.api.tef.regionessegmentoestrategia.dto.RequestSegmentoEstrategia;
import com.baz.divisas.api.tef.regionessegmentoestrategia.service.IRegionesSegmentosEstrategia;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
public class RegionesSegmentosEstrategiaControllerTest {

	@InjectMocks
	RegionesSegmentosEstrategiaController regionesSegmentosEstrategiasController;

	@Mock
	IRegionesSegmentosEstrategia regionesSegmentosEstrategiaService;

	@Test
	void testGetEstrategiasSegmentos() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(regionesSegmentosEstrategiaService.consultaEstrategiasSegmentos()).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regionesSegmentosEstrategiasController.getEstrategiasSegmentos();
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testAsignarEstrategiaSegmento() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		
		RequestSegmentoEstrategia request = new RequestSegmentoEstrategia();
		request.setIdSegmento("");
		request.getIdSegmento();
		
		when(regionesSegmentosEstrategiaService.asignarEstrategiaSegmento(isA(RequestSegmentoEstrategia.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regionesSegmentosEstrategiasController.asignarEstrategiaSegmento(request);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testActualizarEstatusEstrategiasRegion() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		
		RequestSegmentoEstrategia request = new RequestSegmentoEstrategia();

		when(regionesSegmentosEstrategiaService.actualizarEstatusEstrategiaSegmento(isA(RequestSegmentoEstrategia.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regionesSegmentosEstrategiasController.actualizarEstatusEstrategiasSegmento(request);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
}
