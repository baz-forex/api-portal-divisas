package com.baz.divisas.api.tef.regionsegmento.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.baz.divisas.api.tef.regionessegmento.controller.RegionSegmentoController;
import com.baz.divisas.api.tef.regionessegmento.dto.ReqRegionSegmentos;
import com.baz.divisas.api.tef.regionessegmento.dto.ReqRegionSegmentoDelete;
import com.baz.divisas.api.tef.regionessegmento.services.IRegionSegmentoService;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
public class RegionSegmentoControllerTest {
    
    @InjectMocks
    RegionSegmentoController regioneSegmentoController;
    
    @Mock
    IRegionSegmentoService regionSegmentoService;
  
	@Test
	void testGetSegmentosRegiones() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(regionSegmentoService.consultaSegmentosRegiones()).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regioneSegmentoController.getSegmentosRegiones();
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testAsignarSegmentosRegion() {

		ReqRegionSegmentos request = new ReqRegionSegmentos();
		request.setIdRegion("");
		request.getIdRegion();
		request.setIdSegmento(Arrays.asList());
		request.getIdSegmento();
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(regionSegmentoService.asignarSegmentosRegion(isA(String.class), any())).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regioneSegmentoController.asignarSegmentosRegion(request);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testEliminaSegmentoRegion() {

		ReqRegionSegmentoDelete request = new ReqRegionSegmentoDelete();
		request.setIdRegion("");
		request.getIdRegion();
		request.setIdSegmento("");
		request.getIdSegmento();
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(regionSegmentoService.eliminaSegmentoRegion(isA(String.class), isA(String.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regioneSegmentoController.eliminaSegmentoRegion(request);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	
	//OLP
	
	@Test
	void testGetSegmentosRegionesOlp() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(regionSegmentoService.consultaSegmentosRegionesOlp()).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regioneSegmentoController.getSegmentosRegionesOlp();
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testAsignarSegmentosRegionOlp() {

		ReqRegionSegmentos request = new ReqRegionSegmentos();
		request.setIdRegion("");
		request.getIdRegion();
		request.setIdSegmento(Arrays.asList());
		request.getIdSegmento();
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(regionSegmentoService.asignarSegmentosRegionOlp(isA(String.class), any())).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regioneSegmentoController.asignarSegmentosRegionOlp(request);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testEliminaSegmentoRegionOlp() {

		ReqRegionSegmentoDelete request = new ReqRegionSegmentoDelete();
		request.setIdRegion("");
		request.getIdRegion();
		request.setIdSegmento("");
		request.getIdSegmento();
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(regionSegmentoService.eliminaSegmentoRegionOlp(isA(String.class), isA(String.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regioneSegmentoController.eliminaSegmentoRegionOlp(request);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
}
