package com.baz.divisas.api.core.beneficios;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.baz.divisas.api.core.beneficios.service.CuponesBeneficiosImpl;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
public class CuponesTiempoAireTest {

	@InjectMocks
	private CuponesBeneficios cuponesTiempoAire;
	
	@Mock
	private CuponesBeneficiosImpl cuponesTiempoAireImpl;
	
	@Test
	public void testCargaCuponesTA() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(cuponesTiempoAireImpl.registraCuponesTA(any(), any(), any())).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = cuponesTiempoAire.cargaCuponesTA(null, null, null);
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	public void testGetCargasHistorica() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(cuponesTiempoAireImpl.consultaCargaHistorica()).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = cuponesTiempoAire.getCargasHistorica();
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
}
