package com.baz.divisas.api.bitacora;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.baz.divisas.api.bitacora.dto.BitacoraRequest;
import com.baz.divisas.api.bitacora.model.Bitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
class BitacoraControllerTest {

	@InjectMocks
	private BitacoraController bitacoraController;	
	@Mock
	private BitacoraServiceImpl bitacoraService;

	@Test
	void testGetBitacoraId() {
			
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK, new Bitacora());
		when(bitacoraService.getBitacoraPorId(isA(String.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = bitacoraController.getBitacoraPorId("");
		assertThat(response.getStatusCode().value()).isEqualTo(200);
	}
	
	@Test
	void testGetBitacoraFecha() {
			
		List<Bitacora> lista = new ArrayList<>();
		lista.add(new Bitacora());
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK, lista);
		when(bitacoraService.getBitacora(isA(BitacoraRequest.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = bitacoraController.getBitacoraPorFecha(new BitacoraRequest());
		assertThat(response.getStatusCode().value()).isEqualTo(200);
	}

}
