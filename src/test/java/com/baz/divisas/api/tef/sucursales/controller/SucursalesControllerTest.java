package com.baz.divisas.api.tef.sucursales.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.baz.divisas.api.tef.sucursales.dto.ReqActSucursal;
import com.baz.divisas.api.tef.sucursales.service.impl.SucursalesService;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
class SucursalesControllerTest {

	@InjectMocks
	private SucursalesController sucursalesController;

	@Mock
	private SucursalesService sucursalesService;

	@Test
	void testGetSucursales() {
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(sucursalesService.getSucursalesImpl(isA(boolean.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = sucursalesController.getSucursales(true);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetTCSucursales() {
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(sucursalesService.consultaTCSucursales()).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = sucursalesController.getTCSucursales();
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testActualizarTCSucursal() {
		ReqActSucursal req = new ReqActSucursal();		
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(sucursalesService.actualizaTCSucursal(isA(ReqActSucursal.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = sucursalesController.actualizarTCSucursal(req);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testCargaArchivoTC() {
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(sucursalesService.cargaArchivoTC(any())).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = sucursalesController.cargaArchivoTC(null);
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	
	}
	
	@Test
	void testDescargaOperaciones() throws UnsupportedEncodingException {
		String message = "test";
		ByteArrayInputStream respApi = new ByteArrayInputStream(message.getBytes("UTF-8"));
		when(sucursalesService.descargaTCSucursales()).thenReturn(respApi);	
		
		ResponseEntity<Resource> response = sucursalesController.descargaSucursales();
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	
	@Test
	void testGetTCSucursalesOlp() {
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(sucursalesService.consultaTCSucursalesOlp()).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = sucursalesController.getTCSucursalesOlp();
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testActualizarTCSucursalOlp() {
		ReqActSucursal req = new ReqActSucursal();		
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(sucursalesService.actualizaTCSucursalOlp(isA(ReqActSucursal.class))).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = sucursalesController.actualizarTCSucursalOlp(req);
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testCargaArchivoTCOlp() {
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(sucursalesService.cargaArchivoTCOlp(any())).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = sucursalesController.cargaArchivoTCOlp(null);
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
		
	
	}
	
	@Test
	void testDescargaOperacionesOlp() throws UnsupportedEncodingException {
		String message = "test";
		ByteArrayInputStream respApi = new ByteArrayInputStream(message.getBytes("UTF-8"));
		when(sucursalesService.descargaTCSucursalesOlp()).thenReturn(respApi);	
		
		ResponseEntity<Resource> response = sucursalesController.descargaSucursalesOlp();
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
}