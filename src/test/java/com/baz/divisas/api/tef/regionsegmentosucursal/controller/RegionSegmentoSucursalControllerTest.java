package com.baz.divisas.api.tef.regionsegmentosucursal.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.baz.divisas.api.tef.regionsegmentosucursal.dto.ReqSegmentoDivisa;
import com.baz.divisas.api.tef.regionsegmentosucursal.dto.ReqSegmentoSucursal;
import com.baz.divisas.api.tef.regionsegmentosucursal.dto.ReqSegmentoSucursalDelete;
import com.baz.divisas.api.tef.regionsegmentosucursal.services.IRegionSegmentoSucursalService;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
public class RegionSegmentoSucursalControllerTest {

	@InjectMocks
	RegionSegmentoSucursalController segmentoSucursalController;

	@Mock
	IRegionSegmentoSucursalService segmentoSucursalService;

	@Test
	void testGetRegionesSegmentoSucursales() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(segmentoSucursalService.consultaSucursalesSegmento()).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = segmentoSucursalController.getSucursalesSegmentos();
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testRegistrarRegionSegmentoSucursal() {

		ReqSegmentoSucursal request = new ReqSegmentoSucursal();
		request.setIdSegmento("");
		request.getIdSegmento();
		request.setSucursales(Arrays.asList());
		request.getSucursales();
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(segmentoSucursalService.asignarSucursalesSegmento(isA(String.class), any())).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = segmentoSucursalController.asignarSucursalesSegmento(request);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testDesaccinaSucursal() {

		ReqSegmentoSucursalDelete request = new ReqSegmentoSucursalDelete();
		request.setIdSegmento("");
		request.getIdSegmento();
		request.setIdSucursal("");
		request.getIdSucursal();
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(segmentoSucursalService.eliminarSucursalesSegmento(isA(String.class), isA(String.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = segmentoSucursalController.eliminaSucursalSegmento(request);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testConsultaTCSegmentoSucursales() {

		ReqSegmentoDivisa request = new ReqSegmentoDivisa();
		request.setIdSegmento("");
		request.getIdSegmento();
		request.setIdDivisa("");
		request.getIdDivisa();
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(segmentoSucursalService.consultaTCSegmentoSucursales(isA(String.class), isA(String.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = segmentoSucursalController.obtenerTCSegmentoSucursales(request);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	//OLP
	
	@Test
	void testGetRegionesSegmentoSucursalesOlp() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(segmentoSucursalService.consultaSucursalesSegmentoOlp()).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = segmentoSucursalController.getSucursalesSegmentosOlp();
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testRegistrarRegionSegmentoSucursalOlp() {

		ReqSegmentoSucursal request = new ReqSegmentoSucursal();
		request.setIdSegmento("");
		request.getIdSegmento();
		request.setSucursales(Arrays.asList());
		request.getSucursales();
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(segmentoSucursalService.asignarSucursalesSegmentoOlp(isA(String.class), any())).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = segmentoSucursalController.asignarSucursalesSegmentoOlp(request);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testDesaccinaSucursalOlp() {

		ReqSegmentoSucursalDelete request = new ReqSegmentoSucursalDelete();
		request.setIdSegmento("");
		request.getIdSegmento();
		request.setIdSucursal("");
		request.getIdSucursal();
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(segmentoSucursalService.eliminarSucursalesSegmentoOlp(isA(String.class), isA(String.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = segmentoSucursalController.eliminaSucursalSegmentoOlp(request);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testConsultaTCSegmentoSucursalesOlp() {

		ReqSegmentoDivisa request = new ReqSegmentoDivisa();
		request.setIdSegmento("");
		request.getIdSegmento();
		request.setIdDivisa("");
		request.getIdDivisa();
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(segmentoSucursalService.consultaTCSegmentoSucursalesOlp(isA(String.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = segmentoSucursalController.obtenerTCSegmentoSucursalesOLP(request);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
}
