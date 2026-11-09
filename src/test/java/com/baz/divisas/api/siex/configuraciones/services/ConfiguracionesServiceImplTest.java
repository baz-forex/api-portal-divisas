package com.baz.divisas.api.siex.configuraciones.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.BsonObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;

import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.siex.configuraciones.dao.ConfiguracionesDao;
import com.baz.divisas.api.siex.configuraciones.dto.ReqCampoRestringido;
import com.baz.divisas.api.siex.configuraciones.model.CampoRestringido;
import com.baz.divisas.api.siex.configuraciones.model.Restriccion;
import com.baz.divisas.general.dto.ResponseApi;
import com.mongodb.client.result.UpdateResult;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ConfiguracionesServiceImplTest {

	@InjectMocks
	private ConfiguracionesServiceImpl configService;
	
	@Mock
	private ConfiguracionesDao configDao;
	
	@Mock
	private BitacoraServiceImpl bitacoraService;
	
	@Test
	void testGetCamposRestringidosImpl() {
		
		when(configDao.findCamposRestringidos()).thenReturn(new ArrayList<CampoRestringido>());
		ResponseApi response = configService.getCamposRestringidosImpl();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		when(configDao.findCamposRestringidos()).thenReturn(Arrays.asList(new CampoRestringido()));
		response = configService.getCamposRestringidosImpl();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
		
	@Test
	void testDescargaCamposRestringidosImpl() {
		
		CampoRestringido campo = new CampoRestringido();
		campo.setId("test");
		campo.getId();
		campo.setDescripcion("test");
		campo.getDescripcion();
		campo.setRestricciones(Arrays.asList(""));
		campo.getRestricciones();
		
		Restriccion empiezaCon = new Restriccion();
		empiezaCon.setActivo(true);
		empiezaCon.isActivo();
		empiezaCon.setDescripcion("");
		empiezaCon.getDescripcion();
		empiezaCon.setPalabras(Arrays.asList("empiezaCon"));
		empiezaCon.getPalabras();
				
		campo.setEmpiezaCon(empiezaCon);
		campo.getEmpiezaCon();
		
		Restriccion teminaCon = new Restriccion();
		teminaCon.setPalabras(Arrays.asList("teminaCon"));
				
		campo.setTerminaCon(teminaCon);
		campo.getTerminaCon();
		
		Restriccion contiene = new Restriccion();
		contiene.setPalabras(Arrays.asList("contiene"));
				
		campo.setContiene(contiene);
		campo.getContiene();
		
		Restriccion igualA = new Restriccion();
		igualA.setPalabras(Arrays.asList("igualA"));
				
		campo.setIgualA(igualA);
		campo.getIgualA();
		
		List<CampoRestringido> camposRestringidos = new ArrayList<>();
		camposRestringidos.add(campo);
		when(configDao.findConfigCamposRestringidos()).thenReturn(camposRestringidos);
		ByteArrayInputStream response = configService.descargaCamposRestringidosImpl();
		assertThat(response).isNotNull();

	}
	
	@Test
	void testGetReglasCampoRestringidoImpl() {
		
		when(configDao.findCamposRestringidos()).thenReturn(new ArrayList<CampoRestringido>());
		ResponseApi response = configService.getReglasCampoRestringidoImpl();

		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testGetCampoRestringidoImpl() {
		
		when(configDao.findCamposRestringidoById(isA(String.class), isA(String.class))).thenReturn(null);
		ResponseApi response = configService.getCampoRestringidoImpl("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		CampoRestringido campoRestringido = new CampoRestringido();

		when(configDao.findCamposRestringidoById(isA(String.class), isA(String.class))).thenReturn(campoRestringido);
		response = configService.getCampoRestringidoImpl("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
		
	@Test
	void testAgregarRestriccionCampoImpl() {
						
		UpdateResult result = UpdateResult.acknowledged(1L, 1L, new BsonObjectId());
		
		ReqCampoRestringido request = new ReqCampoRestringido();
		request.setIdsCampos(Arrays.asList(""));
		request.getIdsCampos();
		request.setIdRegla("");
		request.getIdRegla();
		request.setRestriccion("");
		request.getRestriccion();
		
		when(configDao.addRestriccionCampo(isA(String.class), isA(String.class), isA(String.class))).thenReturn(result);
		ResponseApi response = configService.agregarRestriccionCampoImpl(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		result = UpdateResult.acknowledged(1L, 0L, new BsonObjectId());
		when(configDao.addRestriccionCampo(isA(String.class), isA(String.class), isA(String.class))).thenReturn(result);
		response = configService.agregarRestriccionCampoImpl(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		result = UpdateResult.acknowledged(0L, 0L, new BsonObjectId());
		when(configDao.addRestriccionCampo(isA(String.class), isA(String.class), isA(String.class))).thenReturn(result);
		response = configService.agregarRestriccionCampoImpl(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		

	}
	
	@Test
	void testEliminarRestriccionCampoImpl() {
						
		UpdateResult result = UpdateResult.acknowledged(1L, 1L, new BsonObjectId());
		
		ReqCampoRestringido request = new ReqCampoRestringido();
		request.setIdsCampos(Arrays.asList(""));
		request.setIdRegla("");
		request.setRestriccion("");
		
		when(configDao.removeRestriccionCampo(isA(String.class), isA(String.class), isA(String.class))).thenReturn(result);
		ResponseApi response = configService.eliminarRestriccionCampoImpl(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		result = UpdateResult.acknowledged(1L, 0L, new BsonObjectId());
		when(configDao.removeRestriccionCampo(isA(String.class), isA(String.class), isA(String.class))).thenReturn(result);
		response = configService.eliminarRestriccionCampoImpl(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		result = UpdateResult.acknowledged(0L, 0L, new BsonObjectId());
		when(configDao.removeRestriccionCampo(isA(String.class), isA(String.class), isA(String.class))).thenReturn(result);
		response = configService.eliminarRestriccionCampoImpl(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
	}
	
}
