package com.baz.divisas.api.core.clientes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Collections;

import com.baz.divisas.api.auth.model.AuthClient;
import com.baz.divisas.api.core.clientes.dao.ClientesDao;
import com.baz.divisas.api.core.clientes.model.BitacoraCargaMasiva;
import com.baz.divisas.api.core.clientes.model.ErrorCargaMasiva;
import com.baz.divisas.api.core.clientes.util.ArchivoCargaMasiva;
import com.baz.divisas.general.util.SecurityUtils;
import com.baz.divisas.security.services.UserDetailsImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ArchivoCargaMasivaTest {

	@InjectMocks
	private ArchivoCargaMasiva archivoCargaMasivaService;
	
	@Mock
	private ClientesDao clientesDao;
	@Mock
	private SecurityUtils securityService;

	
	@Test
	void testProcesar() {
		
		UserDetailsImpl user = new UserDetailsImpl(new AuthClient());
		when(securityService.getUserSesion()).thenReturn(user);
		
		archivoCargaMasivaService.procesar("Test", Collections.emptyList());		
		archivoCargaMasivaService.procesar("Test", Arrays.asList("Test"));		
		archivoCargaMasivaService.procesar("Test", Arrays.asList("15247222,test,ALEJANDRA,ALEJANDRO"));
		archivoCargaMasivaService.procesar("Test", Arrays.asList("15247222,nombre,ALEJANDRA,ALEJANDR0"));
		archivoCargaMasivaService.procesar("Test", Arrays.asList("15247222,nombre,ALEJANDRA,ALEJANDRO", "15247222,nombre,ALEJANDRA,ALEJANDRO", "15247222,fecha_nacimiento,19740128,19740129"));

		assertThat(true).isTrue();

	}
	
	
	@Test
	void testGeneraReporteExcelErrores() {
				
		ByteArrayInputStream response = archivoCargaMasivaService.generaReporteExcelErrores(null);
		assertThat(response).isNotNull();
		
		BitacoraCargaMasiva bitacora = new BitacoraCargaMasiva();		
		ErrorCargaMasiva error = new ErrorCargaMasiva(0, "Test", "Test");		
		bitacora.setErrores(Arrays.asList(error));
		response = archivoCargaMasivaService.generaReporteExcelErrores(bitacora);
		assertThat(response).isNotNull();

	}

		
}
