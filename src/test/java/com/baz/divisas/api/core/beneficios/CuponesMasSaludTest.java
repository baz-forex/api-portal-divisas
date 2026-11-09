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
public class CuponesMasSaludTest {

	@InjectMocks
	private CuponesBeneficios cuponesBeneficios;
	
	@Mock
	private CuponesBeneficiosImpl cuponesBeneficiosImpl;
	
	@Test
	public void testCargaCuponesMasSalud() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(cuponesBeneficiosImpl.registraCuponesMasSalud(any(), any(), any(), any())).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = cuponesBeneficios.cargaCuponesMasSalud(null, null, null, null);
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	public void testGetCargasHistoricaMasSalud() {
		
		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(cuponesBeneficiosImpl.consultaCargaHistoricaMasSalud("1")).thenReturn(respApi);	
		
		ResponseEntity<ResponseApi> response = cuponesBeneficios.getCargasHistoricaMasSalud("1");
		
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
}
