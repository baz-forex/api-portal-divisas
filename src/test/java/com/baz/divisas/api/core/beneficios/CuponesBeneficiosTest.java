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
import org.springframework.mock.web.MockMultipartFile;

import com.baz.divisas.api.core.beneficios.service.CuponesBeneficiosImpl;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
class CuponesBeneficiosTest {

	@InjectMocks
	private CuponesBeneficios cuponesBeneficios;

	@Mock
	private CuponesBeneficiosImpl cuponesBeneficiosImpl;

	@Test
	void testCargaCuponesTA() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(cuponesBeneficiosImpl.registraCuponesTA(any(), any(), any())).thenReturn(respApi);

		MockMultipartFile archivo = new MockMultipartFile("archivo", "cupones.csv", "text/csv", "contenido".getBytes());
		ResponseEntity<ResponseApi> response = cuponesBeneficios.cargaCuponesTA(archivo, "nombre", "descripcion");

		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testGetCargasHistorica() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(cuponesBeneficiosImpl.consultaCargaHistorica()).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = cuponesBeneficios.getCargasHistorica();

		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testCargaCuponesCRM() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(cuponesBeneficiosImpl.registraCuponesCRM(any(), any(), any(), any(), any())).thenReturn(respApi);

		MockMultipartFile archivo = new MockMultipartFile("archivo", "cupones_crm.csv", "text/csv", "contenido".getBytes());
		ResponseEntity<ResponseApi> response = cuponesBeneficios.cargaCuponesCRM(archivo, "nombre", "descripcion", "2026-01-01", "2026-12-31");

		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testGetCargasHistoricaCRM() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(cuponesBeneficiosImpl.consultaCargaHistoricaCRM()).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = cuponesBeneficios.getCargasHistoricaCRM();

		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testCargaCuponesMasSalud() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(cuponesBeneficiosImpl.registraCuponesMasSalud(any(), any(), any(), any())).thenReturn(respApi);

		MockMultipartFile archivo = new MockMultipartFile("archivo", "cupones_salud.csv", "text/csv", "contenido".getBytes());
		ResponseEntity<ResponseApi> response = cuponesBeneficios.cargaCuponesMasSalud(archivo, "nombre", "descripcion", "1");

		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testGetCargasHistoricaMasSalud() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(cuponesBeneficiosImpl.consultaCargaHistoricaMasSalud(any())).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = cuponesBeneficios.getCargasHistoricaMasSalud("1");

		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testCargaCuponesAlianzas() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(cuponesBeneficiosImpl.registraCuponesAlianzas(any(), any(), any(), any(), any())).thenReturn(respApi);

		MockMultipartFile archivo = new MockMultipartFile("archivo", "cupones_alianzas.csv", "text/csv", "contenido".getBytes());
		ResponseEntity<ResponseApi> response = cuponesBeneficios.cargaCuponesAlianzas(archivo, "nombre", "descripcion", "2026-01-01", "2026-12-31");

		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testGetCargasHistoricaAlianzas() {

		ResponseApi respApi = new ResponseApi(EstatusResponse.OK);
		when(cuponesBeneficiosImpl.consultaCargaHistoricaAlianzas()).thenReturn(respApi);

		ResponseEntity<ResponseApi> response = cuponesBeneficios.getCargasHistoricaAlianzas();

		assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
}
