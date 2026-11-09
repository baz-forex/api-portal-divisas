package com.baz.divisas.api.tef.competencias.controller;

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

import com.baz.divisas.api.tef.competencias.services.ICargaCompetenciasService;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
public class CargaCompetenciasControllerTest {

	@InjectMocks
	CargaCompetenciasController competenciasController;

	@Mock
	ICargaCompetenciasService competenciasService;

	@Test
	void testConsultaCompetenciasFecha() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(competenciasService.consultaCompetenciasFecha(isA(String.class))).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = competenciasController.consultaCompetenciasFecha("");
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testConsultaCompetenciasAgrupadas() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);

		when(competenciasService.consultaCompetenciasAgrupadas()).thenReturn(respApi);
		
		ResponseEntity<ResponseApi> response = competenciasController.consultaCompetenciasAgrupadas();
		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
}
