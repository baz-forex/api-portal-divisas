package com.baz.divisas.api.tef.regionessucursales.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.baz.divisas.api.tef.regionessucursales.dto.ReqRegionSucursales;
import com.baz.divisas.api.tef.regionessucursales.dto.ReqRegionDivisa;
import com.baz.divisas.api.tef.regionessucursales.dto.ReqRegionSucursalDelete;
import com.baz.divisas.api.tef.regionessucursales.services.IRegionSucursalService;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
public class RegionSucursalControllerTest {

	@InjectMocks
	RegionSucursalController regionSucursalController;

	@Mock
	IRegionSucursalService regionSucursalServive;

	@Test
	void testGetSucursalesRegiones() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(regionSucursalServive.consultaSucursalesRegiones()).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regionSucursalController.getSucursalesRegiones();
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testAsignarSucursalesRegion() {

		ReqRegionSucursales request = new ReqRegionSucursales();
		request.setIdRegion("");
		request.getIdRegion();
		request.setIdSucursal(null);
		request.getIdSucursal();
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(regionSucursalServive.asignarSucursalesRegion(isA(String.class), any())).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regionSucursalController.asignarSucursalesRegion(request);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testEliminarSucursalRegion() {

		ReqRegionSucursalDelete request = new ReqRegionSucursalDelete();
		request.setIdRegion("");
		request.getIdRegion();
		request.setIdSucursal("");
		request.getIdSucursal();
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(regionSucursalServive.eliminarSucursalRegion(isA(String.class), isA(String.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regionSucursalController.eliminarSucursalRegion(request);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetRegionesSegmentosSucursales() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(regionSucursalServive.consultaRegionesSegmentosSucursales()).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regionSucursalController.getRegionesSegmentosSucursales();
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testObtenerTCRegionesSucursales() {
		ReqRegionDivisa req = new ReqRegionDivisa();		
		req.setIdRegion("");
		req.setIdDivisa("");
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(regionSucursalServive.consultaTCRegionesSucursales(isA(String.class), isA(String.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = regionSucursalController.obtenerTCRegionesSucursales(req);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
		
	}

	//OLP
	@Test
	void testGetSucursalesRegionesOlp() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(regionSucursalServive.consultaSucursalesRegionesOlp()).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regionSucursalController.getSucursalesRegionesOlp();
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testAsignarSucursalesRegionOlp() {

		ReqRegionSucursales request = new ReqRegionSucursales();
		request.setIdRegion("");
		request.getIdRegion();
		request.setIdSucursal(null);
		request.getIdSucursal();
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(regionSucursalServive.asignarSucursalesRegionOlp(isA(String.class), any())).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regionSucursalController.asignarSucursalesRegionOlp(request);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testEliminarSucursalRegionOlp() {

		ReqRegionSucursalDelete request = new ReqRegionSucursalDelete();
		request.setIdRegion("");
		request.getIdRegion();
		request.setIdSucursal("");
		request.getIdSucursal();
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(regionSucursalServive.eliminarSucursalRegionOlp(isA(String.class), isA(String.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regionSucursalController.eliminarSucursalRegionOlp(request);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetRegionesSegmentosSucursalesOlp() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(regionSucursalServive.consultaRegionesSegmentosSucursalesOlp()).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = regionSucursalController.getRegionesSegmentosSucursalesOlp();
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}	
	
	@Test
	void testObtenerTCRegionesSucursalesOlp() {
		ReqRegionDivisa req = new ReqRegionDivisa();		
		req.setIdRegion("");
		req.setIdDivisa("");
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(regionSucursalServive.consultaTCRegionesSucursalesOlp(isA(String.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = regionSucursalController.obtenerTCRegionesSucursalesOlp(req);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
		
	}
}
