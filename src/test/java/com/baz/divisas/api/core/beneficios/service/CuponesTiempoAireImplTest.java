package com.baz.divisas.api.core.beneficios.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
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
import org.springframework.web.multipart.MultipartFile;

import com.baz.divisas.api.core.beneficios.dao.CuponesBeneficiosDao;
import com.baz.divisas.api.core.clientes.model.HistoricoAltoRiesgo;
import com.baz.divisas.api.tef.sucursales.utils.ArchivosExcel;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CuponesTiempoAireImplTest {

	@InjectMocks
	private CuponesBeneficiosImpl cupones;
	
	@Mock
	private CuponesBeneficiosDao cuponesDao;
	
	@Mock
	private BufferedReader br;
	
	private MultipartFile fileMock;
	ArchivosExcel lectorExcel;
	
	@Test
	void testRegistraCuponesTA() {

		ResponseApi result = cupones.registraCuponesTA(fileMock, "123123", "sdadasd");

		assertNotNull(result);
		
	}
	
	
	@Test
	void testConsultaCargaHistorica() {
				
		List<HistoricoAltoRiesgo> historico = new ArrayList<>(); 
		HistoricoAltoRiesgo hist = new HistoricoAltoRiesgo();
		hist.setDescripcion("Test");
		hist.setFecha("123123312312312312");
		hist.setId("21312313123123");
		hist.setNombre("123123123123");
		hist.setRegistros(12);
		historico.add(hist);
		
		when(cuponesDao.consultaCargaHistorica()).thenReturn(historico);
		
		ResponseApi response = cupones.consultaCargaHistorica();
		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//////////////
		
		historico = new ArrayList<>(); 
		
		when(cuponesDao.consultaCargaHistorica()).thenReturn(historico);
		
		response = cupones.consultaCargaHistorica();
		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}
}
