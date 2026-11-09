package com.baz.divisas.api.core.clientes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Collections;

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

import com.baz.divisas.api.core.clientes.dao.ClientesDao;
import com.baz.divisas.api.core.clientes.model.BitacoraCargaMasiva;
import com.baz.divisas.api.core.clientes.model.ErrorCargaMasiva;
import com.baz.divisas.api.core.clientes.services.CargaMasivaServiceImpl;
import com.baz.divisas.api.core.clientes.util.ArchivoCargaMasiva;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CargaMasivaServiceImplTest {

	@InjectMocks
	private CargaMasivaServiceImpl cargaMasivaService;
	
	@Mock
	private ArchivoCargaMasiva archivoCargaMasivaService;
	
	@Mock
	private ClientesDao clientesDao;
	
	@Mock
	private BufferedReader br;

	
	@Test
	void testCargaArchivoActualizacionMasiva() {
		
		MockMultipartFile file = new MockMultipartFile("Test", IOUtils.EMPTY_BYTE_ARRAY);
		ResponseApi response = cargaMasivaService.cargaArchivoActualizacionMasiva(file);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		file = new MockMultipartFile("test", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Test\nTest".getBytes());
		response = cargaMasivaService.cargaArchivoActualizacionMasiva(file);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
		
	@Test
	void testConsultaBitacoraCargaMasiva() {
		
		BitacoraCargaMasiva bitacora = new BitacoraCargaMasiva();
		bitacora.setIdCarga("");
		bitacora.getIdCarga();
		bitacora.setNombreArchivo("");
		bitacora.getNombreArchivo();
		bitacora.setUsuario("");
		bitacora.getUsuario();
		bitacora.setNombreUsuario("");
		bitacora.getNombreUsuario();
		bitacora.setRegistrosCorrectos(0);
		bitacora.getRegistrosCorrectos();
		bitacora.setRegistrosErroneos(0);
		bitacora.getRegistrosErroneos();
		bitacora.setRegistrosTotales(0);
		bitacora.getRegistrosTotales();
		bitacora.setFechaCarga("");
		bitacora.getFechaCarga();
		
		ErrorCargaMasiva error = new ErrorCargaMasiva();
		error.setCadena("");
		error.getCadena();
		error.setDescripcion("");
		error.getDescripcion();
		error.setLinea(0);
		error.getLinea();
		error = new ErrorCargaMasiva(0, "", "");
		
		bitacora.setErrores(Arrays.asList(error));
		bitacora.getErrores();
		
		when(clientesDao.findBitacoraCargaMasiva()).thenReturn(Collections.emptyList());
		ResponseApi response = cargaMasivaService.consultaBitacoraCargaMasiva();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		when(clientesDao.findBitacoraCargaMasiva()).thenReturn(Arrays.asList(bitacora));
		response = cargaMasivaService.consultaBitacoraCargaMasiva();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testReporteErroresCargaMasiva() {
		
		BitacoraCargaMasiva bitacora = new BitacoraCargaMasiva();
		
		when(clientesDao.findCargaMasivaById("")).thenReturn(bitacora);
		ByteArrayInputStream response = cargaMasivaService.reporteErroresCargaMasiva("");
		assertThat(response).isNull();
	}
		
}
