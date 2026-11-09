package com.baz.divisas.api.bitacora;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.bson.BsonObjectId;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;

import com.baz.divisas.api.auth.model.AuthClient;
import com.baz.divisas.api.bitacora.dao.BitacoraDao;
import com.baz.divisas.api.bitacora.dto.BitacoraRequest;
import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.model.Bitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.SecurityUtils;
import com.baz.divisas.security.services.UserDetailsImpl;
import com.mongodb.client.result.UpdateResult;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BitacoraServiceImplTest {

	@InjectMocks
	private BitacoraServiceImpl bitacoraService;
	@Mock
	private SecurityUtils securityService;
	@Mock
	private BitacoraDao bitacoraDao;
	
	@Test
	void testRegistra() {		
		

		UserDetailsImpl user = new UserDetailsImpl(new AuthClient());
					
		when(securityService.getUserSesion()).thenReturn(user);
		doNothing().when(bitacoraDao).insertBitacora(isA(Bitacora.class));
		
		bitacoraService.registrar(AccionBitacora.REGISTRAR, "Test", null, null);
		
        verify(securityService, times(1)).getUserSesion();

	}

	@Test
	void testRegistraCompleto() {	
		
		UserDetailsImpl user = new UserDetailsImpl(new AuthClient());
		user.setIdModulo("Test");

		when(securityService.getUserSesion()).thenReturn(user);
		
		doNothing().when(bitacoraDao).insertBitacora(isA(Bitacora.class));
		
		bitacoraService.registrar(AccionBitacora.REGISTRAR, "Test", new Document(), new Document());
		
		verify(securityService, times(1)).getUserSesion();
	}
	
	@Test
	void testRegistraModuloEmpty() {	
		

		UserDetailsImpl user = new UserDetailsImpl(new AuthClient());
		user.setIdModulo("");

		when(securityService.getUserSesion()).thenReturn(user);
		
		doNothing().when(bitacoraDao).insertBitacora(isA(Bitacora.class));
		
		bitacoraService.registrar(AccionBitacora.REGISTRAR, "Test", new Document(), new Document());
		
		verify(securityService, times(1)).getUserSesion();
	}
	
	@Test
	void testRegistraSinDetalle() {			
		
		UserDetailsImpl user = new UserDetailsImpl(new AuthClient());

		when(securityService.getUserSesion()).thenReturn(user);
		doNothing().when(bitacoraDao).insertBitacora(isA(Bitacora.class));
		
		bitacoraService.registrar(AccionBitacora.REGISTRAR, "Test");
		
		verify(securityService, times(1)).getUserSesion();

	}
	
	@Test
	void testRegistraCambioEstatus() {			
		
		UserDetailsImpl user = new UserDetailsImpl(new AuthClient());

		when(securityService.getUserSesion()).thenReturn(user);
		doNothing().when(bitacoraDao).insertBitacora(isA(Bitacora.class));
		
		bitacoraService.registrarCambioEstatus(AccionBitacora.REGISTRAR, "Test", true);
		bitacoraService.registrarCambioEstatus(AccionBitacora.REGISTRAR, "Test", false);
		
		verify(securityService, times(2)).getUserSesion();

	}
	
	@Test
	void testRegistraUpdate() {			
		
		UserDetailsImpl user = new UserDetailsImpl(new AuthClient());

		when(securityService.getUserSesion()).thenReturn(user);
		doNothing().when(bitacoraDao).insertBitacora(isA(Bitacora.class));
		
		UpdateResult updateResult = UpdateResult.acknowledged(1L, 1L, new BsonObjectId());		
		bitacoraService.registrar(AccionBitacora.REGISTRAR, "Test", new Object(), new Object(), updateResult);
		
		verify(securityService, times(1)).getUserSesion();
		
		///////////////
		updateResult = UpdateResult.acknowledged(0L, 0L, new BsonObjectId());		
		bitacoraService.registrar(AccionBitacora.REGISTRAR, "Test", new Object(), new Object(), updateResult);
		
		verify(securityService, times(1)).getUserSesion();
	}
	
	@Test
	void testGetBitacora() {		
		
		List<Bitacora> lista = new ArrayList<>();
		Bitacora bitacora = new Bitacora();
		lista.add(bitacora);
		
		BitacoraRequest request = new BitacoraRequest();
		request.setIdUsuario("");
		request.getIdUsuario();
		request.setIdModulo("");
		request.getIdModulo();
		request.setFechaInicio("");
		request.getFechaInicio();
		request.setFechaFin("");
		request.getFechaFin();
		
		when(bitacoraDao.find(isA(BitacoraRequest.class))).thenReturn(lista);		
		ResponseApi response = bitacoraService.getBitacora(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		bitacora.setDatosActuales(new Document());
		
		when(bitacoraDao.find(isA(BitacoraRequest.class))).thenReturn(lista);		
		response = bitacoraService.getBitacora(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		bitacora.setDatosAnteriores(new Document());
		
		when(bitacoraDao.find(isA(BitacoraRequest.class))).thenReturn(lista);		
		response = bitacoraService.getBitacora(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		lista = new ArrayList<>();
		when(bitacoraDao.find(isA(BitacoraRequest.class))).thenReturn(lista);		
		response = bitacoraService.getBitacora(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		when(bitacoraDao.find(isA(BitacoraRequest.class))).thenReturn(null);
		response = bitacoraService.getBitacora(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	void testGetBitacoraId() {
		
		Bitacora bitacora = new Bitacora();
		bitacora.setId("");
		bitacora.getId();
		bitacora.setIdUsuario("");
		bitacora.getIdUsuario();
		bitacora.setTipoAccion("");
		bitacora.getTipoAccion();
		bitacora.setDescripcionAccion("");
		bitacora.getDescripcionAccion();
		bitacora.setDatosAnteriores(new Document());
		bitacora.getDatosAnteriores();
		bitacora.setDatosActuales(new Document());
		bitacora.getDatosActuales();
		bitacora.setNombre("");
		bitacora.getNombre();
		bitacora.setApellidoPaterno("");
		bitacora.getApellidoPaterno();
		bitacora.setApellidoMaterno("");
		bitacora.getApellidoMaterno();
		bitacora.isDetalle();
				
		when(bitacoraDao.findById(isA(String.class))).thenReturn(new Bitacora());
		
		ResponseApi response = bitacoraService.getBitacoraPorId("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetBitacoraIdSinResultados() {			
		
		when(bitacoraDao.findById(isA(String.class))).thenReturn(null);
		
		ResponseApi response = bitacoraService.getBitacoraPorId("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}
}
