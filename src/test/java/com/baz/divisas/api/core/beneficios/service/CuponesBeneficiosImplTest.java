package com.baz.divisas.api.core.beneficios.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;

import com.baz.divisas.api.core.beneficios.dao.CuponesBeneficiosDao;
import com.baz.divisas.api.core.clientes.model.HistoricoAltoRiesgo;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CuponesBeneficiosImplTest {

	@InjectMocks
	private CuponesBeneficiosImpl cuponesBeneficiosImpl;

	@Mock
	private CuponesBeneficiosDao cuponesDao;

	@Test
	void testConsultaCargaHistorica() {
		when(cuponesDao.consultaCargaHistorica()).thenReturn(new ArrayList<>());
		ResponseApi response = cuponesBeneficiosImpl.consultaCargaHistorica();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());

		List<HistoricoAltoRiesgo> historico = new ArrayList<>();
		historico.add(new HistoricoAltoRiesgo());
		when(cuponesDao.consultaCargaHistorica()).thenReturn(historico);
		response = cuponesBeneficiosImpl.consultaCargaHistorica();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testConsultaCargaHistoricaCRM() {
		when(cuponesDao.consultaCargaHistoricaCRM()).thenReturn(new ArrayList<>());
		ResponseApi response = cuponesBeneficiosImpl.consultaCargaHistoricaCRM();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());

		List<HistoricoAltoRiesgo> historico = new ArrayList<>();
		historico.add(new HistoricoAltoRiesgo());
		when(cuponesDao.consultaCargaHistoricaCRM()).thenReturn(historico);
		response = cuponesBeneficiosImpl.consultaCargaHistoricaCRM();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testRegistraCuponesCRM() {
		String contenido = "12345,ABCDE\n67890,FGHIJ\n";
		MockMultipartFile archivo = new MockMultipartFile("archivo", "cupones.csv", "text/csv", contenido.getBytes());

		when(cuponesDao.registraCuponesCRM(anyList())).thenReturn(2);
		doNothing().when(cuponesDao).registraBitacoraCuponesCRM(any(), any(), isA(int.class), any(), any());

		ResponseApi response = cuponesBeneficiosImpl.registraCuponesCRM(archivo, "nombre", "desc", "2026-01-01", "2026-12-31");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testRegistraCuponesCRMCuponesInvalidos() {
		String contenido = "12345,AB\n67890,CD\n";
		MockMultipartFile archivo = new MockMultipartFile("archivo", "cupones.csv", "text/csv", contenido.getBytes());

		ResponseApi response = cuponesBeneficiosImpl.registraCuponesCRM(archivo, "nombre", "desc", "2026-01-01", "2026-12-31");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testRegistraCuponesCRMArchivoInvalido() {
		MockMultipartFile archivo = new MockMultipartFile("archivo", "cupones.csv", "text/csv", (byte[]) null);
		ResponseApi response = cuponesBeneficiosImpl.registraCuponesCRM(archivo, "nombre", "desc", "2026-01-01", "2026-12-31");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testConsultaCargaHistoricaMasSalud() {
		when(cuponesDao.consultaCargaHistoricaMasSalud(any())).thenReturn(new ArrayList<>());
		ResponseApi response = cuponesBeneficiosImpl.consultaCargaHistoricaMasSalud("1");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());

		List<HistoricoAltoRiesgo> historico = new ArrayList<>();
		historico.add(new HistoricoAltoRiesgo());
		when(cuponesDao.consultaCargaHistoricaMasSalud(any())).thenReturn(historico);
		response = cuponesBeneficiosImpl.consultaCargaHistoricaMasSalud("1");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testRegistraCuponesMasSalud() {
		String contenido = "CUPON1\nCUPON2\nCUPON3\n";
		MockMultipartFile archivo = new MockMultipartFile("archivo", "cupones.txt", "text/plain", contenido.getBytes());

		when(cuponesDao.registraCuponesMasSaludBulk(anyList())).thenReturn(3);
		doNothing().when(cuponesDao).registraBitacoraCuponesMasSalud(any(), any(), any(), isA(int.class));

		ResponseApi response = cuponesBeneficiosImpl.registraCuponesMasSalud(archivo, "nombre", "desc", "1");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testRegistraCuponesMasSaludInvalidos() {
		String contenido = "CUPON_INVALIDO_MUY_LARGO_1234567890\n";
		MockMultipartFile archivo = new MockMultipartFile("archivo", "cupones.txt", "text/plain", contenido.getBytes());

		ResponseApi response = cuponesBeneficiosImpl.registraCuponesMasSalud(archivo, "nombre", "desc", "1");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testRegistraCuponesMasSaludArchivoInvalido() {
		MockMultipartFile archivo = new MockMultipartFile("archivo", "cupones.txt", "text/plain", (byte[]) null);
		ResponseApi response = cuponesBeneficiosImpl.registraCuponesMasSalud(archivo, "nombre", "desc", "1");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testConsultaCargaHistoricaAlianzas() {
		when(cuponesDao.consultaCargaHistoricaAlianzas()).thenReturn(new ArrayList<>());
		ResponseApi response = cuponesBeneficiosImpl.consultaCargaHistoricaAlianzas();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());

		List<HistoricoAltoRiesgo> historico = new ArrayList<>();
		historico.add(new HistoricoAltoRiesgo());
		when(cuponesDao.consultaCargaHistoricaAlianzas()).thenReturn(historico);
		response = cuponesBeneficiosImpl.consultaCargaHistoricaAlianzas();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testRegistraCuponesAlianzas() {
		String contenido = "ALIANZA1,ABCDE\nALIANZA2,FGHIJ\n";
		MockMultipartFile archivo = new MockMultipartFile("archivo", "cupones.csv", "text/csv", contenido.getBytes());

		when(cuponesDao.registraCuponesAlianzas(anyList())).thenReturn(2);
		doNothing().when(cuponesDao).registraBitacoraCuponesAlianzas(any(), any(), isA(int.class), any(), any());

		ResponseApi response = cuponesBeneficiosImpl.registraCuponesAlianzas(archivo, "nombre", "desc", "2026-01-01", "2026-12-31");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testRegistraCuponesAlianzasArchivoInvalido() {
		MockMultipartFile archivo = new MockMultipartFile("archivo", "cupones.csv", "text/csv", (byte[]) null);
		ResponseApi response = cuponesBeneficiosImpl.registraCuponesAlianzas(archivo, "nombre", "desc", "2026-01-01", "2026-12-31");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
}
