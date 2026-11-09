package com.baz.divisas.api.core.beneficios.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.baz.divisas.api.core.beneficios.dao.CuponesBeneficiosDao;
import com.baz.divisas.api.core.clientes.model.HistoricoAltoRiesgo;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CuponesMasSaludImplTest {

	@InjectMocks
	private CuponesBeneficiosImpl cupones;
	
	@Mock
	private CuponesBeneficiosDao cuponesDao;
	
	@Test
	void testRegistraCuponesMasSalud() {
		
		// Archivo vacío - no hay líneas, devuelve OK con 0 registros
		MockMultipartFile file = new MockMultipartFile("Test", IOUtils.EMPTY_BYTE_ARRAY);
		ResponseApi response = cupones.registraCuponesMasSalud(file, "carga1", "descripcion", "1");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		// Archivo CSV con cupones válidos
		String contenidoCsv = "ABC123\nDEF456\nGHI789\n";
		file = new MockMultipartFile("cupones", "cupones.csv", MediaType.TEXT_PLAIN_VALUE, contenidoCsv.getBytes());
		
		when(cuponesDao.registraCuponesMasSaludBulk(any())).thenReturn(3);
		
		response = cupones.registraCuponesMasSalud(file, "carga1", "descripcion", "1");
		assertNotNull(response);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		// Archivo CSV con cupones inválidos (más de 20 caracteres)
		String contenidoInvalido = "CUPONINVALIDOPORQUEESMUYLARGOMASDE20CHARS\n";
		file = new MockMultipartFile("cupones", "cupones.csv", MediaType.TEXT_PLAIN_VALUE, contenidoInvalido.getBytes());
		
		response = cupones.registraCuponesMasSalud(file, "carga2", "descripcion", "2");
		assertNotNull(response);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		// Archivo CSV con mezcla de válidos e inválidos
		String contenidoMixto = "ABC123\n\nDEF@456\nGHI789\n";
		file = new MockMultipartFile("cupones", "cupones.csv", MediaType.TEXT_PLAIN_VALUE, contenidoMixto.getBytes());
		
		when(cuponesDao.registraCuponesMasSaludBulk(any())).thenReturn(2);
		
		response = cupones.registraCuponesMasSalud(file, "carga3", "descripcion", "1");
		assertNotNull(response);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testRegistraCuponesMasSaludSinRegistros() {		
		// Archivo con líneas en blanco solamente
		String contenidoVacio = "\n\n\n";
		MockMultipartFile file = new MockMultipartFile("cupones", "cupones.csv", MediaType.TEXT_PLAIN_VALUE, contenidoVacio.getBytes());
		
		ResponseApi response = cupones.registraCuponesMasSalud(file, "carga4", "descripcion", "1");
		assertNotNull(response);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testConsultaCargaHistoricaMasSalud() {
				
		List<HistoricoAltoRiesgo> historico = new ArrayList<>(); 
		HistoricoAltoRiesgo hist = new HistoricoAltoRiesgo();
		hist.setDescripcion("Carga cupones Mas Salud");
		hist.setFecha("2026-07-15T12:00:00.000");
		hist.setId("abc123");
		hist.setNombre("carga1");
		hist.setRegistros(10);
		historico.add(hist);
		
		when(cuponesDao.consultaCargaHistoricaMasSalud("1")).thenReturn(historico);
		
		ResponseApi response = cupones.consultaCargaHistoricaMasSalud("1");
		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//////////////
		
		historico = new ArrayList<>(); 
		
		when(cuponesDao.consultaCargaHistoricaMasSalud("1")).thenReturn(historico);
		
		response = cupones.consultaCargaHistoricaMasSalud("1");
		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}
}
