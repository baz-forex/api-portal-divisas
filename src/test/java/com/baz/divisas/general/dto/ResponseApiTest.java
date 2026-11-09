package com.baz.divisas.general.dto;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ResponseApiTest {

	
	@Test
	public void valid() {
		ResponseApi responseApi = new ResponseApi(EstatusResponse.OK);
		responseApi.setCodigo("1");
		responseApi.getCodigo();
		List<String> detalle = new ArrayList<>();
		detalle.add("OK");
		responseApi.setDetalles(detalle);
		responseApi.getDetalles();
		responseApi.setFolio("123123");
		responseApi.getFolio();
		responseApi.setInfo("12122");
		responseApi.getInfo();
		responseApi.setMensaje("Exitoso");
		responseApi.getMensaje();
		responseApi.setResultado("1");
		responseApi.getResultado();
		responseApi.setHttpStatus(HttpStatus.OK);
		responseApi.getHttpStatus();
		responseApi.toString();
		
		responseApi = new ResponseApi(EstatusResponse.OK, detalle);
		responseApi = new ResponseApi(EstatusResponse.OK, new Object());
				
	}
}
