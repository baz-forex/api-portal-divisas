package com.baz.divisas.api.tef.parametria;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;

import com.baz.divisas.api.tef.parametria.dto.ReqActualizaCampoParametria;
import com.baz.divisas.api.tef.parametria.dto.ReqAltaParametria;
import com.baz.divisas.api.tef.parametria.dto.ReqConsultaParametria;
import com.baz.divisas.api.tef.parametria.dto.ReqEliminarCampoParametria;
import com.baz.divisas.api.tef.parametria.services.ParametriaServiceImpl;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ParametriaServiceControllerTest {
	@InjectMocks
	private ParametriaServiceController serv;
	@Mock
	private ParametriaServiceImpl impl;
	
	private ResponseApi response;
	private ResponseEntity<ResponseApi> result;
	@BeforeEach
	void initialize() {
		serv = new ParametriaServiceController(impl);
		response = new ResponseApi(EstatusResponse.OK);
	}
	
	@Test
	void testAltaParametria() {
		when(impl.alta(isA(ReqAltaParametria.class))).thenReturn(response);
		result = serv.altaParametria(new ReqAltaParametria());
		assertThat(result).isNotNull();
	}
	
	@Test
	void testConsultaParametria() {
		when(impl.consultar(isA(ReqConsultaParametria.class))).thenReturn(response);
		result = serv.consultaParametria(new ReqConsultaParametria());
		assertThat(result).isNotNull();
	}
	
	@Test
	void testConsultaParametria2() {
		when(impl.consultar()).thenReturn(response);
		result = serv.consultaParametria();
		assertThat(result).isNotNull();
	}
	
	@Test
	void testEliminar() {
		when(impl.eliminar(isA(ReqConsultaParametria.class))).thenReturn(response);
		result = serv.eliminar(new ReqConsultaParametria());
		assertThat(result).isNotNull();
	}
	
	@Test
	void testConsultaActivo() {
		when(impl.consultarActivo(isA(ReqConsultaParametria.class))).thenReturn(response);
		result = serv.consultaActivo(new ReqConsultaParametria());
		assertThat(result).isNotNull();
	}
	
	@Test
	void testActivar() {
		when(impl.actualizarCampoActivo(isA(ReqConsultaParametria.class), isA(Boolean.class))).thenReturn(response);
		result = serv.activar(new ReqConsultaParametria());
		assertThat(result).isNotNull();
	}
	
	@Test
	void testDesactivar() {
		when(impl.actualizarCampoActivo(isA(ReqConsultaParametria.class), isA(Boolean.class))).thenReturn(response);
		result = serv.desactivar(new ReqConsultaParametria());
		assertThat(result).isNotNull();
	}
	
	@Test
	void testActualizarCampo() {
		when(impl.actualizarCampo(isA(ReqActualizaCampoParametria.class))).thenReturn(response);
		result = serv.actualizarCampo(new ReqActualizaCampoParametria());
		assertThat(result).isNotNull();
	}
	
	@Test
	void testEliminarCampo() {
		when(impl.eliminarCampo(isA(ReqEliminarCampoParametria.class))).thenReturn(response);
		result = serv.eliminarCampo(new ReqEliminarCampoParametria());
		assertThat(result).isNotNull();
	}
}
