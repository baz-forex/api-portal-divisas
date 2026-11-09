package com.baz.divisas.api.tef.dashboard.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.baz.divisas.api.tef.dashboard.dto.RequestRegionTC;
import com.baz.divisas.api.tef.dashboard.services.impl.DashboardServiceImpl;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
class DashboardControllerTest {

	@InjectMocks
	private DashboardController dashboardController;

	@Mock
	private DashboardServiceImpl dashboardService;

	@Test
	void testGetRegiones() {
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(dashboardService.consultaRegiones(isA(String.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = dashboardController.getRegiones("");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetSegmentosRegion() {
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(dashboardService.consultaSegmentosRegion(isA(String.class), isA(String.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = dashboardController.getSegmentosRegion("", "");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testActualizarTCRegion() {
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(dashboardService.actualizarTCRegion(isA(RequestRegionTC.class))).thenReturn(respApi);

		RequestRegionTC request = new RequestRegionTC();
		request.setActivo(true);
		request.isActivo();
		request.setDivisa("");
		request.getDivisa();
		request.setId("");
		request.getId();
		request.setMontoTC(0.0);
		request.getMontoTC();
		request.setTipoOperacionTC("");
		request.getTipoOperacionTC();
		
		ResponseEntity<ResponseApi> response = dashboardController.actualizarTCRegion(request);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testActualizarTCSegmento() {
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(dashboardService.actualizarTCSegmento(isA(RequestRegionTC.class))).thenReturn(respApi);

		RequestRegionTC request = new RequestRegionTC();
		
		ResponseEntity<ResponseApi> response = dashboardController.actualizarTCSegmento(request);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

}