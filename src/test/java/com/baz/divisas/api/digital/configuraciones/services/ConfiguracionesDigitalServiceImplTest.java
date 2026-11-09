package com.baz.divisas.api.digital.configuraciones.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.bson.Document;

import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.digital.configuraciones.dao.ConfiguracionesDigitalDao;
import com.baz.divisas.api.digital.configuraciones.model.Monto;
import com.baz.divisas.api.digital.configuraciones.model.RangoMontos;
import com.baz.divisas.api.digital.configuraciones.model.OperacionesCanceladas;
import com.baz.divisas.api.digital.configuraciones.model.OperacionesPermitidas;
import com.baz.divisas.api.digital.configuraciones.model.ParametriaOperacion;
import com.baz.divisas.api.digital.configuraciones.model.ParametriaRemesas;
import com.baz.divisas.api.digital.configuraciones.model.ParametriaSeguimientoTC;
import com.baz.divisas.general.dto.ResponseApi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ConfiguracionesDigitalServiceImplTest {

	@InjectMocks
	private ConfiguracionesDigitalServiceImpl configService;
	
	@Mock
	private ConfiguracionesDigitalDao configDao;
	
	@Mock
	private BitacoraServiceImpl bitacora;
	
	@Test
	void testGetParametriaOperacion() {
		
		ParametriaOperacion parametria = new ParametriaOperacion();
		
		Monto fluctuacion = new Monto();
		fluctuacion.setUnidadMoneda("");
		fluctuacion.setValor("");
		fluctuacion.setValorPesos("");
		
		fluctuacion.getUnidadMoneda();
		fluctuacion.getValor();
		fluctuacion.getValorPesos();
		
		RangoMontos montos = new RangoMontos();
		montos.setDivisa("");
		montos.setMontoMaximo("");
		montos.setMontoMinimo("");
		
		montos.getDivisa();
		montos.getMontoMaximo();
		montos.getMontoMinimo();
		
		OperacionesPermitidas operacionesPermitidas = new OperacionesPermitidas();
		operacionesPermitidas.setOperaciones("");
		operacionesPermitidas.setPlazo("");
		
		operacionesPermitidas.getOperaciones();
		operacionesPermitidas.getPlazo();
		
		OperacionesCanceladas OperacionesCanceladas = new OperacionesCanceladas();
		OperacionesCanceladas.setOperaciones("");
		OperacionesCanceladas.setPeriodoBloqueo("");
		OperacionesCanceladas.setPeriodoLimite("");
		OperacionesCanceladas.setPlazoBloqueo("");
		OperacionesCanceladas.setPlazoLimite("");
		
		OperacionesCanceladas.getOperaciones();
		OperacionesCanceladas.getPeriodoBloqueo();
		OperacionesCanceladas.getPeriodoLimite();
		OperacionesCanceladas.getPlazoBloqueo();
		OperacionesCanceladas.getPlazoLimite();
		
		parametria.setCompraActiva(false);
		parametria.setDescripcion("");
		parametria.setFluctuacion(fluctuacion);
		parametria.setGeolocalizacionPrecios(false);
		parametria.setMontos(montos);
		parametria.setOperacionesCanceladas(null);
		parametria.setOperacionesPermitidas(operacionesPermitidas);
		parametria.setVentaActiva(false);
		parametria.setVigenciaEnDias("");

		parametria.isCompraActiva();
		parametria.getDescripcion();
		parametria.getFluctuacion();
		parametria.isGeolocalizacionPrecios();
		parametria.getMontos();
		parametria.getOperacionesCanceladas();
		parametria.getOperacionesPermitidas();
		parametria.isVentaActiva();
		parametria.getVigenciaEnDias();
		
		when(configDao.findParametria(isA(String.class), isA(String.class))).thenReturn(new Document());
		ResponseApi response = configService.getParametriaOperacion("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

		when(configDao.findParametria(isA(String.class), isA(String.class))).thenReturn(null);
		response = configService.getParametriaOperacion("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}

	@Test
	void testUpdateParametriaOperacion() {

		ParametriaOperacion parametria = new ParametriaOperacion();
		
		when(configDao.updateParametria(isA(String.class),isA(String.class),isA(Document.class))).thenReturn(null);
		when(bitacora.registrar(isA(AccionBitacora.class),isA(String.class),isA(ParametriaOperacion.class),isA(ParametriaOperacion.class))).thenReturn("");
		ResponseApi response = configService.updateParametriaOperacion("", "", "", parametria);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		when(configDao.updateParametria(isA(String.class),isA(String.class),isA(Document.class))).thenReturn(new Document());
		response = configService.updateParametriaOperacion("", "", "412", parametria);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//////////////////
		response = configService.updateParametriaOperacion("", "", "413", parametria);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
	}

	@Test
	void testGetParametriaSeguimientoTC() {
		
		ParametriaSeguimientoTC parametria = new ParametriaSeguimientoTC();
		parametria.setActivo(true);
		parametria.isActivo();
		parametria.setIntervalo(new Monto());
		parametria.getIntervalo();
		parametria.setMaximo(new Monto());
		parametria.getMaximo();
		parametria.setMinimo(new Monto());
		parametria.getMinimo();
		parametria.setRangoDias(Arrays.asList());
		parametria.getRangoDias();
		
		when(configDao.findParametria(isA(String.class), isA(String.class))).thenReturn(new Document());
		ResponseApi response = configService.getParametriaSeguimientoTC("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

		when(configDao.findParametria(isA(String.class), isA(String.class))).thenReturn(null);
		response = configService.getParametriaSeguimientoTC("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}

	@Test
	void testUpdateParametriaSeguimientoTC() {

		ParametriaSeguimientoTC parametria = new ParametriaSeguimientoTC();
		
		when(configDao.updateParametria(isA(String.class),isA(String.class),isA(Document.class))).thenReturn(null);
		when(bitacora.registrar(isA(AccionBitacora.class),isA(String.class),isA(ParametriaSeguimientoTC.class),isA(ParametriaSeguimientoTC.class))).thenReturn("");
		ResponseApi response = configService.updateParametriaSeguimientoTC("", parametria);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		when(configDao.updateParametria(isA(String.class),isA(String.class),isA(Document.class))).thenReturn(new Document());
		response = configService.updateParametriaSeguimientoTC("412", parametria);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//////////////////
		response = configService.updateParametriaSeguimientoTC("413", parametria);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
	}

	@Test
	void testGetParametriaRemesas() {
		
		ParametriaRemesas parametria = new ParametriaRemesas();
		parametria.setActivo(true);
		parametria.isActivo();
		parametria.setMontoBeneficio("");
		parametria.getMontoBeneficio();
		parametria.setMontoMinimoOperacion("");
		parametria.getMontoMinimoOperacion();
		parametria.setMontoSugerenciaDos("");
		parametria.getMontoSugerenciaDos();
		parametria.setMontoSugerenciaUno("");
		parametria.getMontoSugerenciaUno();
		parametria.setSubProductos(Arrays.asList());
		parametria.getSubProductos();
		parametria.setTipoBeneficio("");
		parametria.getTipoBeneficio();
		parametria.setTipoCliente(Arrays.asList());
		parametria.getTipoCliente();
		parametria.setTipoCuenta(Arrays.asList());
		parametria.getTipoCuenta();
		parametria.setVigenciaBeneficio("");
		parametria.getVigenciaBeneficio();		
		
		when(configDao.findParametria(isA(String.class), isA(String.class))).thenReturn(new Document());
		ResponseApi response = configService.getParametriaRemesas("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

		when(configDao.findParametria(isA(String.class), isA(String.class))).thenReturn(null);
		response = configService.getParametriaRemesas("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}

	@Test
	void testUpdateParametriaRemesas() {

		ParametriaRemesas parametria = new ParametriaRemesas();
		
		when(configDao.updateParametria(isA(String.class),isA(String.class),isA(Document.class))).thenReturn(null);
		when(bitacora.registrar(isA(AccionBitacora.class),isA(String.class),isA(ParametriaRemesas.class),isA(ParametriaRemesas.class))).thenReturn("");
		ResponseApi response = configService.updateParametriaRemesas("", parametria);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		when(configDao.updateParametria(isA(String.class),isA(String.class),isA(Document.class))).thenReturn(new Document());
		response = configService.updateParametriaRemesas("412", parametria);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//////////////////
		response = configService.updateParametriaRemesas("413", parametria);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
	}
}
