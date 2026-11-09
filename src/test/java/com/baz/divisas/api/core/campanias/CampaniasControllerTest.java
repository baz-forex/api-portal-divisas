package com.baz.divisas.api.core.campanias;

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

import com.baz.divisas.api.core.beneficios.CampaniasController;
import com.baz.divisas.api.core.beneficios.dto.ReqAltaPremio;
import com.baz.divisas.api.core.beneficios.dto.ReqEliminaPremio;
import com.baz.divisas.api.core.beneficios.dto.ReqEstatusCampania;
import com.baz.divisas.api.core.beneficios.dto.ReqEstatusPremio;
import com.baz.divisas.api.core.beneficios.dto.ReqTipoCampania;
import com.baz.divisas.api.core.beneficios.model.BeanCampania;
import com.baz.divisas.api.core.beneficios.service.CampaniasServiceImpl;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@ExtendWith(MockitoExtension.class)
public class CampaniasControllerTest {

	@InjectMocks
	private CampaniasController controller;
	@Mock
	private CampaniasServiceImpl campaniasServiceImpl;
	@Test
	void testCrear() {
		when(campaniasServiceImpl.crear(isA(BeanCampania.class))).thenReturn(new ResponseApi(EstatusResponse.OK));
		ResponseEntity<ResponseApi> res = controller.crear(new BeanCampania());
		assertThat(res.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	@Test
	void testObtener() {
		when(campaniasServiceImpl.obtener()).thenReturn(new ResponseApi(EstatusResponse.OK));
		ResponseEntity<ResponseApi> res = controller.obtener();
		assertThat(res.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	@Test
	void testActualizar() {
		when(campaniasServiceImpl.actualizar(isA(BeanCampania.class))).thenReturn(new ResponseApi(EstatusResponse.OK));
		ResponseEntity<ResponseApi> res = controller.actualizar(new BeanCampania());
		assertThat(res.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	@Test
	void testEliminar() {
		when(campaniasServiceImpl.eliminar(isA(String.class))).thenReturn(new ResponseApi(EstatusResponse.OK));
		ResponseEntity<ResponseApi> res = controller.eliminar("");
		assertThat(res.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	@Test
	void testActualizarTipo() {
		when(campaniasServiceImpl.actualizarTipo(isA(ReqTipoCampania.class))).thenReturn(new ResponseApi(EstatusResponse.OK));
		ResponseEntity<ResponseApi> res = controller.actualizarTipo(new ReqTipoCampania());
		assertThat(res.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	@Test
	void testActualizarEstatus() {
		when(campaniasServiceImpl.actualizarEstatus(isA(ReqEstatusCampania.class))).thenReturn(new ResponseApi(EstatusResponse.OK));
		ResponseEntity<ResponseApi> res = controller.actualizarEstatus(new ReqEstatusCampania());
		assertThat(res.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	@Test
	void testCrearPremio() {
		when(campaniasServiceImpl.crearPremio(isA(ReqAltaPremio.class))).thenReturn(new ResponseApi(EstatusResponse.OK));
		ResponseEntity<ResponseApi> res = controller.crearPremio(new ReqAltaPremio());
		assertThat(res.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	@Test
	void testActualizarPremio() {
		when(campaniasServiceImpl.actualizarPremio(isA(ReqAltaPremio.class))).thenReturn(new ResponseApi(EstatusResponse.OK));
		ResponseEntity<ResponseApi> res = controller.actualizarPremio(new ReqAltaPremio());
		assertThat(res.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	@Test
	void testEliminarPremio() {
		when(campaniasServiceImpl.eliminarPremio(isA(ReqEliminaPremio.class))).thenReturn(new ResponseApi(EstatusResponse.OK));
		ResponseEntity<ResponseApi> res = controller.eliminarPremio(new ReqEliminaPremio());
		assertThat(res.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
	@Test
	void testActualizaEstatusPremio() {
		when(campaniasServiceImpl.actualizarEstatusPremio(isA(ReqEstatusPremio.class))).thenReturn(new ResponseApi(EstatusResponse.OK));
		ResponseEntity<ResponseApi> res = controller.actualizaEstatusPremio(new ReqEstatusPremio());
		assertThat(res.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
	}
}
