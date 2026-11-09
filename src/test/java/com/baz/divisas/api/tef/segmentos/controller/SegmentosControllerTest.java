package com.baz.divisas.api.tef.segmentos.controller;

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

import com.baz.divisas.api.tef.general.model.Segmento;
import com.baz.divisas.api.tef.segmentos.services.impl.SegmentosService;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
class SegmentosControllerTest {

	@InjectMocks
	private SegmentosController segmentosController;

	@Mock
	private SegmentosService segmentosService;

	@Test
	void testGetSegmentos() {
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(segmentosService.consultaSegmentos()).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = segmentosController.getSegmentos();
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testRegistraSegmento() {
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(segmentosService.creaSegmento(isA(String.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = segmentosController.registraSegmento(new Segmento("", true, null));
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testActualizaDescripcionSegmento() {
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(segmentosService.actualizaDescripcionSegmento(isA(String.class), isA(String.class))).thenReturn(respApi);

		Segmento segmento = new Segmento();
		segmento.setId("");
		segmento.setDescripcion("");
		segmento.setDivisas(Arrays.asList());
		segmento.getDivisas();
		
		ResponseEntity<ResponseApi> response = segmentosController.actualizaDescripcionSegmento(segmento);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testActualizaEstatusSegmento() {
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		
		when(segmentosService.actualizaEstatusSegmento(isA(String.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = segmentosController.actualizaEstatusSegmento("");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testEliminaSegmento() {
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		
		when(segmentosService.eliminaSegmento(isA(String.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = segmentosController.eliminaSegmento("");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	//OLP
	
	@Test
	void testGetSegmentosOlp() {
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(segmentosService.consultaSegmentosOlp()).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = segmentosController.getSegmentosOlp();
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testRegistraSegmentoOlp() {
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(segmentosService.creaSegmentoOlp(isA(String.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = segmentosController.registraSegmentoOlp(new Segmento("", true, null));
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testActualizaDescripcionSegmentoOlp() {
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(segmentosService.actualizaDescripcionSegmentoOlp(isA(String.class), isA(String.class))).thenReturn(respApi);

		Segmento segmento = new Segmento();
		segmento.setId("");
		segmento.setDescripcion("");
		segmento.setDivisas(Arrays.asList());
		segmento.getDivisas();
		
		ResponseEntity<ResponseApi> response = segmentosController.actualizaDescripcionSegmentoOlp(segmento);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testActualizaEstatusSegmentoOlp() {
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		
		when(segmentosService.actualizaEstatusSegmentoOlp(isA(String.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = segmentosController.actualizaEstatusSegmentoOlp("");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testEliminaSegmentoOlp() {
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		
		when(segmentosService.eliminaSegmentoOlp(isA(String.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = segmentosController.eliminaSegmentoOlp("");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
}