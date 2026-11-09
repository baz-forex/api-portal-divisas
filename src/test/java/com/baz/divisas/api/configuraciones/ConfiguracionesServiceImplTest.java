package com.baz.divisas.api.configuraciones;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.catalogos.model.CatPaisNacionalidad;
import com.baz.divisas.api.core.configuraciones.dao.ConfiguracionesDao;
import com.baz.divisas.api.core.configuraciones.dao.PerfilDeClientesDao;
import com.baz.divisas.api.core.configuraciones.dto.BeanLimitesRentabilidad;
import com.baz.divisas.api.core.configuraciones.dto.BeanSegmentoLealtad;
import com.baz.divisas.api.core.configuraciones.dto.BeanSegmentoRentabilidad;
import com.baz.divisas.api.core.configuraciones.dto.BeanValoresRentabilidad;
import com.baz.divisas.api.core.configuraciones.dto.ReqCampoRestringido;
import com.baz.divisas.api.core.configuraciones.dto.RespConsultaCatalogoRentLealVal;
import com.baz.divisas.api.core.configuraciones.model.CampoRestringido;
import com.baz.divisas.api.core.configuraciones.model.ConfigBusquedas;
import com.baz.divisas.api.core.configuraciones.model.ConfigPenalizaciones;
import com.baz.divisas.api.core.configuraciones.model.Notificacion;
import com.baz.divisas.api.core.configuraciones.model.ParametriaBusquedas;
import com.baz.divisas.api.core.configuraciones.model.SegmentoSucursal;
import com.baz.divisas.api.core.configuraciones.services.ConfiguracionesServiceImpl;
import com.baz.divisas.api.core.configuraciones.services.PerfilDeClientesServiceImpl;
import com.baz.divisas.general.dto.ResponseApi;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

import org.apache.commons.io.IOUtils;
import org.bson.BsonObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ConfiguracionesServiceImplTest {

	@InjectMocks
	private ConfiguracionesServiceImpl configService;
	
	@Mock
	private ConfiguracionesDao configDao;
	
	@Mock
	private BitacoraServiceImpl bitacoraService;
	
	@Mock
	private PerfilDeClientesDao perfilDeClientesDao;
	
	@InjectMocks
	private PerfilDeClientesServiceImpl perfilDeClientesService;
	
	@Test
	void testGetParametriaBusquedas() {
		
		ConfigBusquedas config = new ConfigBusquedas();
		config.setLimiteCoincidenciaNombre(0);
		config.setPrecisionNombre(0);
		config.setValorPrimarioApellidos(0);
		config.setValorPrimarioNombres(0);
		config.setValorSecundarioApellidos(0);
		config.setValorSecundarioNombres(0);
		config.getLimiteCoincidenciaNombre();
		config.getPrecisionNombre();
		config.getValorPrimarioApellidos();
		config.getValorPrimarioNombres();
		config.getValorSecundarioApellidos();
		config.getValorSecundarioNombres();
		
		ConfigPenalizaciones penalizaciones = new ConfigPenalizaciones();
		penalizaciones.setDesorden(0);
		penalizaciones.setFechaNacimiento(0);
		penalizaciones.setParametroFaltante(0);
		penalizaciones.getDesorden();
		penalizaciones.getFechaNacimiento();
		penalizaciones.getParametroFaltante();
		
		ParametriaBusquedas param = new ParametriaBusquedas();
		param.setBusquedaClientes(config);
		param.setBusquedaListaNegra(config);
		param.setPenalizaciones(penalizaciones);
		param.getBusquedaClientes();
		param.getBusquedaListaNegra();
		param.getPenalizaciones();
		
		when(configDao.findParametriaBusquedas()).thenReturn(param);
		ResponseApi response = configService.getParametriaBusquedas();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetParametriaSinResultados() {
		when(configDao.findParametriaBusquedas()).thenReturn(null);
		ResponseApi response = configService.getParametriaBusquedas();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}

	@Test
	void testUpdateParametriaBusquedas() {

		ParametriaBusquedas param = new ParametriaBusquedas();
		when(configDao.updateParametriaBusquedas(any(ParametriaBusquedas.class))).thenReturn(true);
		
		ResponseApi response = configService.actualizaParametriaBusquedas(param);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testUpdateParametriaFail() {

		ParametriaBusquedas param = new ParametriaBusquedas();
		when(configDao.updateParametriaBusquedas(any(ParametriaBusquedas.class))).thenReturn(false);
		
		ResponseApi response = configService.actualizaParametriaBusquedas(param);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
	
	@Test
	void testGetCamposRestringidos() {
		
		when(configDao.findCamposRestringidos()).thenReturn(Arrays.asList(new CampoRestringido()));
		ResponseApi response = configService.getCamposRestringidos();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetCamposRestringidosEmpty() {
		
		when(configDao.findCamposRestringidos()).thenReturn(new ArrayList<CampoRestringido>());
		ResponseApi response = configService.getCamposRestringidos();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	void testGetCampoRestringido() {
		
		CampoRestringido campoRestringido = new CampoRestringido();
		campoRestringido.setDescripcion("");
		campoRestringido.setId("");
		campoRestringido.setRestricciones(Arrays.asList());
		campoRestringido.getDescripcion();
		campoRestringido.getId();
		campoRestringido.getRestricciones();
		
		when(configDao.findCamposRestringidoById(isA(String.class))).thenReturn(campoRestringido);
		ResponseApi response = configService.getCamposRestringido("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetCampoRestringidoNull() {
		
		when(configDao.findCamposRestringidoById(isA(String.class))).thenReturn(null);
		ResponseApi response = configService.getCamposRestringido("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}
		
	@Test
	void testAgregarRestriccionCampo() {
						
		UpdateResult result = UpdateResult.acknowledged(1L, 1L, new BsonObjectId());
		
		when(configDao.addRestriccionCampo(isA(String.class), isA(String.class))).thenReturn(result);
		ResponseApi response = configService.agregarRestriccionCampo(new ReqCampoRestringido("",""));
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		result = UpdateResult.acknowledged(0L, 0L, new BsonObjectId());
		when(configDao.addRestriccionCampo(isA(String.class), isA(String.class))).thenReturn(result);
		response = configService.agregarRestriccionCampo(new ReqCampoRestringido("",""));
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		result = UpdateResult.acknowledged(1L, 0L, new BsonObjectId());
		when(configDao.addRestriccionCampo(isA(String.class), isA(String.class))).thenReturn(result);
		response = configService.agregarRestriccionCampo(new ReqCampoRestringido("",""));
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	void testEliminarRestriccionCampo() {
						
		UpdateResult result = UpdateResult.acknowledged(1L, 1L, new BsonObjectId());
		
		when(configDao.removeRestriccionCampo(isA(String.class), isA(String.class))).thenReturn(result);
		ResponseApi response = configService.eliminarRestriccionCampo(new ReqCampoRestringido("",""));
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		result = UpdateResult.acknowledged(0L, 0L, new BsonObjectId());
		when(configDao.removeRestriccionCampo(isA(String.class), isA(String.class))).thenReturn(result);
		response = configService.eliminarRestriccionCampo(new ReqCampoRestringido("",""));
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		result = UpdateResult.acknowledged(1L, 0L, new BsonObjectId());
		when(configDao.removeRestriccionCampo(isA(String.class), isA(String.class))).thenReturn(result);
		response = configService.eliminarRestriccionCampo(new ReqCampoRestringido("",""));
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	void testGetCatSegmentosSucursal() {
		
		SegmentoSucursal segmento = new SegmentoSucursal();
		segmento.setActivo(true);
		segmento.setDescripcion("");
		segmento.setDetalle("");
		segmento.setId("");
		segmento.setIdRegion("");
		segmento.setNombre("");
		segmento.setSucursales(new HashSet<>());
		segmento.setTipo("");
		segmento.setNegocio("");
		segmento.getDescripcion();
		segmento.getDetalle();
		segmento.getId();
		segmento.getIdRegion();
		segmento.getNombre();
		segmento.getSucursales();
		segmento.getTipo();
		segmento.getNegocio();
		assertThat(segmento.isActivo()).isEqualTo(true);
		
		ResponseApi response = configService.getCatSegmentosSucursal();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	@Test
	void testGetSegmentosSucursal() {

		when(configDao.findSegmentosSucursalByTipo(isA(String.class))).thenReturn(Arrays.asList(new SegmentoSucursal()));
		ResponseApi response = configService.getSegmentosSucursal("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		when(configDao.findSegmentosSucursalByTipo(isA(String.class))).thenReturn(Arrays.asList());
		response = configService.getSegmentosSucursal("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());

	}
	
	@Test
	void testGetSegmentoSucursal() {

		when(configDao.findSegmentoSucursalById(isA(String.class))).thenReturn(new SegmentoSucursal());
		ResponseApi response = configService.getSegmentoSucursal("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		when(configDao.findSegmentoSucursalById(isA(String.class))).thenReturn(null);
		response = configService.getSegmentoSucursal("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());

	}
	
	@Test
	void testRegistraSegmentoSucursal() {
		
		SegmentoSucursal segmento = new SegmentoSucursal();
		segmento.setTipo("");
		segmento.setIdRegion("");
		
		when(configDao.findSegmentoSucursalByIdRegion(isA(String.class), isA(String.class))).thenReturn(null);
		ResponseApi response = configService.registraSegmentoSucursal(segmento);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		when(configDao.findSegmentoSucursalByIdRegion(isA(String.class), isA(String.class))).thenReturn(segmento);
		response = configService.registraSegmentoSucursal(segmento);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		segmento.setTipo("SUCURSALES");
		
		InsertOneResult insertResult = InsertOneResult.acknowledged(new BsonObjectId());

		when(configDao.findSegmentoSucursalByIdRegion(isA(String.class), isA(String.class))).thenReturn(null);
		when(configDao.insertSegmentoSucursal(isA(SegmentoSucursal.class))).thenReturn(insertResult);
		response = configService.registraSegmentoSucursal(segmento);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		insertResult = InsertOneResult.acknowledged(null);

		when(configDao.findSegmentoSucursalByIdRegion(isA(String.class), isA(String.class))).thenReturn(null);
		when(configDao.insertSegmentoSucursal(isA(SegmentoSucursal.class))).thenReturn(insertResult);
		response = configService.registraSegmentoSucursal(segmento);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());

	}
	
	@Test
	void testActualizarSegmentoSucursal() {
		
		SegmentoSucursal segmento = new SegmentoSucursal();
		segmento.setId("");
		segmento.setTipo("");
		segmento.setIdRegion("");
		
		SegmentoSucursal segmentoReq = new SegmentoSucursal();
		segmentoReq.setId("");
		segmentoReq.setTipo("");
		segmentoReq.setIdRegion("");
		
		when(configDao.findSegmentoSucursalByIdRegion(isA(String.class), isA(String.class))).thenReturn(null);
		ResponseApi response = configService.actualizarSegmentoSucursal(segmentoReq);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		when(configDao.findSegmentoSucursalByIdRegion(isA(String.class), isA(String.class))).thenReturn(segmento);
		response = configService.actualizarSegmentoSucursal(segmentoReq);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		segmentoReq.setId("id");		
		when(configDao.findSegmentoSucursalByIdRegion(isA(String.class), isA(String.class))).thenReturn(segmento);
		response = configService.actualizarSegmentoSucursal(segmentoReq);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		UpdateResult updateResult = UpdateResult.acknowledged(1L, 1L, new BsonObjectId());
		segmentoReq.setTipo("SUCURSALES");		

		when(configDao.findSegmentoSucursalByIdRegion(isA(String.class), isA(String.class))).thenReturn(null);
		when(configDao.findSegmentoSucursalById(isA(String.class))).thenReturn(segmento);
		when(configDao.updateSegmentoSucursal(isA(SegmentoSucursal.class))).thenReturn(updateResult);
		response = configService.actualizarSegmentoSucursal(segmentoReq);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		updateResult = UpdateResult.acknowledged(0L, 0L, new BsonObjectId());
		when(configDao.findSegmentoSucursalByIdRegion(isA(String.class), isA(String.class))).thenReturn(null);
		when(configDao.findSegmentoSucursalById(isA(String.class))).thenReturn(segmento);
		when(configDao.updateSegmentoSucursal(isA(SegmentoSucursal.class))).thenReturn(updateResult);
		response = configService.actualizarSegmentoSucursal(segmentoReq);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());

	}
		
	@Test
	void testActualizarEstatusSegmentoSucursal() {
		
		SegmentoSucursal segmento = new SegmentoSucursal();
		segmento.setActivo(true);
		segmento.setIdRegion("");
		segmento.setNombre("");
			
		when(configDao.findSegmentoSucursalById(isA(String.class))).thenReturn(null);
		ResponseApi response = configService.actualizarEstatusSegmentoSucursal("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());

		
		UpdateResult updateResult = UpdateResult.acknowledged(1L, 1L, new BsonObjectId());

		when(configDao.findSegmentoSucursalById(isA(String.class))).thenReturn(segmento);
		when(configDao.updateEstatusSegmentoSucursal(isA(String.class), isA(boolean.class))).thenReturn(updateResult);
		response = configService.actualizarEstatusSegmentoSucursal("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		segmento.setActivo(false);

		when(configDao.findSegmentoSucursalById(isA(String.class))).thenReturn(segmento);
		when(configDao.updateEstatusSegmentoSucursal(isA(String.class), isA(boolean.class))).thenReturn(updateResult);
		response = configService.actualizarEstatusSegmentoSucursal("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		updateResult = UpdateResult.acknowledged(0L, 0L, new BsonObjectId());
		
		when(configDao.findSegmentoSucursalById(isA(String.class))).thenReturn(segmento);
		when(configDao.updateEstatusSegmentoSucursal(isA(String.class), isA(boolean.class))).thenReturn(updateResult);
		response = configService.actualizarEstatusSegmentoSucursal("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());

	}
	
	@Test
	void testEliminarEstatusSegmentoSucursal() {
		
		SegmentoSucursal segmento = new SegmentoSucursal();
		segmento.setIdRegion("");
		segmento.setNombre("");
			
		when(configDao.removeSegmentoSucursal(isA(String.class))).thenReturn(null);
		ResponseApi response = configService.eliminarSegmentoSucursal("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());

		when(configDao.removeSegmentoSucursal(isA(String.class))).thenReturn(segmento);
		response = configService.eliminarSegmentoSucursal("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}

	@Test
	void testActualizaRestriccionPaisNacionalidad() {
		
		List<CatPaisNacionalidad> listPaises = new ArrayList<CatPaisNacionalidad>();
		
		ResponseApi response = configService.actualizaRestriccionPaisNacionalidad(listPaises);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());

		
		CatPaisNacionalidad paisAnterior = new CatPaisNacionalidad();
		paisAnterior.setRestriccionPais(true);
		paisAnterior.setRestriccionNacionalidad(true);

		CatPaisNacionalidad pais = new CatPaisNacionalidad();
		pais.setRestriccionPais(true);
		pais.setRestriccionNacionalidad(true);
		
		listPaises.add(pais);

		when(configDao.updateRestriccionPaisNacionalidad(isA(CatPaisNacionalidad.class))).thenReturn(null);
		response = configService.actualizaRestriccionPaisNacionalidad(listPaises);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
//		when(configDao.updateRestriccionPaisNacionalidad(isA(CatPaisNacionalidad.class))).thenReturn(paisAnterior);
//		response = configService.actualizaRestriccionPaisNacionalidad(listPaises);
//		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		pais.setRestriccionPais(false);
		pais.setRestriccionNacionalidad(false);
		
		when(configDao.updateRestriccionPaisNacionalidad(isA(CatPaisNacionalidad.class))).thenReturn(paisAnterior);
		response = configService.actualizaRestriccionPaisNacionalidad(listPaises);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		paisAnterior.setRestriccionPais(false);
		pais.setRestriccionPais(true);
		
		when(configDao.updateRestriccionPaisNacionalidad(isA(CatPaisNacionalidad.class))).thenReturn(paisAnterior);
		response = configService.actualizaRestriccionPaisNacionalidad(listPaises);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}
	
	@Test
	void testConsultaCatalogosSegmentosLealRentVal() {
		
		List<BeanValoresRentabilidad> beanValoresRentabilidad = new ArrayList<>();
		List<BeanSegmentoRentabilidad> beanSegmentoRentabilidad = new ArrayList<>();
		List<BeanSegmentoLealtad> beanSegmentoLealtad = new ArrayList<>();
		RespConsultaCatalogoRentLealVal respConsultaCatalogoRentLealVal = new RespConsultaCatalogoRentLealVal();
		
		respConsultaCatalogoRentLealVal.setSegmentoLealtad(beanSegmentoLealtad);
		respConsultaCatalogoRentLealVal.setSegmentoRentabilidad(beanSegmentoRentabilidad);
		respConsultaCatalogoRentLealVal.setValoresRentabilidad(beanValoresRentabilidad);
		
		respConsultaCatalogoRentLealVal.getSegmentoLealtad();
		respConsultaCatalogoRentLealVal.getSegmentoRentabilidad();
		respConsultaCatalogoRentLealVal.getValoresRentabilidad();
		
		when(perfilDeClientesDao.consultaCatalogosRentLealValores(isA(String.class))).thenReturn(respConsultaCatalogoRentLealVal);
		ResponseApi response = perfilDeClientesService.getPerfilesDelCliente("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		when(perfilDeClientesDao.consultaCatalogosRentLealValores(isA(String.class))).thenReturn(null);
		response = perfilDeClientesService.getPerfilesDelCliente("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}

	@Test
	void testConsultaCatalogosSegmentosValorRent() {
		
		List<BeanValoresRentabilidad> objetoValoresRentabilidad = new ArrayList<>();
		BeanValoresRentabilidad beanValoresRentabilidad = new BeanValoresRentabilidad();
		beanValoresRentabilidad.setId("id");
		beanValoresRentabilidad.setDescripcion("valor");
		beanValoresRentabilidad.setValor(10.0);
		beanValoresRentabilidad.setActivo(true);
		
		objetoValoresRentabilidad.add(beanValoresRentabilidad);
		
		when(perfilDeClientesDao.valoresRentabilidad()).thenReturn(objetoValoresRentabilidad);
		ResponseApi response = perfilDeClientesService.getValoresRentabilida();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		when(perfilDeClientesDao.valoresRentabilidad()).thenReturn(null);
		response = perfilDeClientesService.getValoresRentabilida();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
	}

	@Test
	void testEditarCatSegmentoLealtad() {
		
		BeanSegmentoLealtad beanSegmentoLealtad = new BeanSegmentoLealtad();

		
		
		beanSegmentoLealtad.setId("");
		beanSegmentoLealtad.setIdNegocio("");
		beanSegmentoLealtad.setDescripcion("");
		beanSegmentoLealtad.setLimiteMeses(1.0);
		beanSegmentoLealtad.setTransaccionMinimo(2.0);
		beanSegmentoLealtad.setTransaccionMaximo(3.0);
		beanSegmentoLealtad.setActivo(true);
		
		beanSegmentoLealtad.getId();
		beanSegmentoLealtad.getIdNegocio();
		beanSegmentoLealtad.getDescripcion();
		beanSegmentoLealtad.getLimiteMeses();
		beanSegmentoLealtad.getTransaccionMinimo();
		beanSegmentoLealtad.getTransaccionMaximo();
		beanSegmentoLealtad.getActivo();
		
		BeanLimitesRentabilidad limitesRentabilidad =new BeanLimitesRentabilidad();
		limitesRentabilidad.setId("");
		limitesRentabilidad.setMaximoPremios(0);
		limitesRentabilidad.setEstatus(true);
		limitesRentabilidad.setDescripcion("");
		
		List<BeanLimitesRentabilidad> beanLimitesRentabilidad = new ArrayList<>();
		List<BeanSegmentoLealtad> catalogoBeanSegmentoLealtad = new ArrayList<>();
		catalogoBeanSegmentoLealtad.add(beanSegmentoLealtad);
		
		beanSegmentoLealtad.setLimitesRentabilidad(beanLimitesRentabilidad);
		when(perfilDeClientesDao.segmentoLealtad()).thenReturn(catalogoBeanSegmentoLealtad);
		when(perfilDeClientesDao.editarCatSegmentoLealtad(any(),isA(String.class))).thenReturn(true);
		ResponseApi response = perfilDeClientesService.editarCatSegmentoLealtad(beanSegmentoLealtad);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		beanSegmentoLealtad.getLimitesRentabilidad().add(new BeanLimitesRentabilidad());
		
		when(perfilDeClientesDao.editarCatSegmentoLealtad(any(),isA(String.class))).thenReturn(true);
		response = perfilDeClientesService.editarCatSegmentoLealtad(beanSegmentoLealtad);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		when(perfilDeClientesDao.editarCatSegmentoLealtad(any(),isA(String.class))).thenReturn(false);
		response = perfilDeClientesService.editarCatSegmentoLealtad(beanSegmentoLealtad);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
	}
	
	@Test
	void testEditarCatSegmentoRentabilidad() {
		
		List<BeanSegmentoRentabilidad> catalogo = new ArrayList<>();
		BeanSegmentoRentabilidad beanSegmentoRentabilidad = new BeanSegmentoRentabilidad();
		beanSegmentoRentabilidad.setId("id");
		beanSegmentoRentabilidad.setDescripcion("desc");
		beanSegmentoRentabilidad.setMontoMinimo(0); 
		beanSegmentoRentabilidad.setMontoMaximo(0); 
		beanSegmentoRentabilidad.setMaximoPremios(0); 
		beanSegmentoRentabilidad.setActivo(true); 
		
		catalogo.add(beanSegmentoRentabilidad);
		
		beanSegmentoRentabilidad.getId();
		beanSegmentoRentabilidad.getDescripcion();
		beanSegmentoRentabilidad.getMontoMinimo(); 
		beanSegmentoRentabilidad.getMontoMaximo(); 
		beanSegmentoRentabilidad.getMaximoPremios(); 
		beanSegmentoRentabilidad.getActivo(); 
		
		when(perfilDeClientesDao.segmentorentavilidad()).thenReturn(catalogo);
		when(perfilDeClientesDao.editarCatSegmentoRentabilidad(any())).thenReturn(true);
		ResponseApi response = perfilDeClientesService.editarCatSegmentoRentabilidad(beanSegmentoRentabilidad);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

		when(perfilDeClientesDao.editarCatSegmentoRentabilidad(any())).thenReturn(false);
		response = perfilDeClientesService.editarCatSegmentoRentabilidad(new BeanSegmentoRentabilidad());
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
	
	@Test
	void testEditarCatValoresRentabilidad() {
		
		List<BeanValoresRentabilidad> catalogo = new ArrayList<>();
		BeanValoresRentabilidad beanValoresRentabilidad = new BeanValoresRentabilidad();
		beanValoresRentabilidad.setId("id");
		beanValoresRentabilidad.setDescripcion("valor");
		beanValoresRentabilidad.setValor(10.0);
		beanValoresRentabilidad.setActivo(true);
		catalogo.add(beanValoresRentabilidad);
		
		beanValoresRentabilidad.getId();
		beanValoresRentabilidad.getDescripcion();
		beanValoresRentabilidad.getValor();
		beanValoresRentabilidad.getActivo();
		
		when(perfilDeClientesDao.valoresRentabilidad()).thenReturn(catalogo);
		when(perfilDeClientesDao.editarCatValoresRentabilidad(any())).thenReturn(true);
		ResponseApi response = perfilDeClientesService.editarCatValoresRentabilidad(beanValoresRentabilidad);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

		when(perfilDeClientesDao.editarCatValoresRentabilidad(any())).thenReturn(false);
		response = perfilDeClientesService.editarCatValoresRentabilidad(new BeanValoresRentabilidad());
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());

	}
	
	@Test
	void testGetCatEstatusNotificacion() {
		ResponseApi response = configService.getCatEstatusNotificacion();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testGetNotificaciones() {

		Notificacion notificacion = new Notificacion();
		notificacion.setActivo(true);
		notificacion.setEstatus("");
		notificacion.setFechaActualizacion(LocalDateTime.now());
		notificacion.setFechaFin(LocalDate.now());
		notificacion.setFechaInicio(LocalDate.now());
		notificacion.setFechaRegistro(LocalDateTime.now());
		notificacion.setEstatusNotificacion();
		notificacion.setFileImagen(null);
		notificacion.setGeneralSucursales(true);
		notificacion.setId("");
		notificacion.setImagen(null);
		notificacion.setNombre("");
		notificacion.setSucursales(Arrays.asList());
		notificacion.setTiempoSegundos(0);
		
		notificacion.getEstatus();
		notificacion.getFechaActualizacion();
		notificacion.getFechaFin();
		notificacion.getFechaInicio();
		notificacion.getFechaRegistro();
		notificacion.getFileImagen();
		notificacion.getImagen();
		notificacion.getNombre();
		notificacion.getSucursales();
		notificacion.getTiempoSegundos();
		notificacion.isActivo();
		notificacion.isGeneral();
		notificacion.isGeneralSucursales();
		
		when(configDao.findNotificaciones(isA(String.class), isA(String.class))).
		thenReturn(Arrays.asList(notificacion));
		
		ResponseApi response = configService.getNotificaciones("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		when(configDao.findNotificaciones(isA(String.class), isA(String.class))).thenReturn(Arrays.asList());
		response = configService.getNotificaciones("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());

	}
	
	@Test
	void testGetNotificacion() {
		
		when(configDao.findNotificacionById(isA(String.class), any())).thenReturn(new Notificacion());		
		ResponseApi response = configService.getNotificacion("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		when(configDao.findNotificacionById(isA(String.class), any())).thenReturn(null);
		response = configService.getNotificacion("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());

	}
	
	@Test
	void testRegistraNotificacion() throws IOException {
		
		Notificacion notificacion = new Notificacion();
		notificacion.setFileImagen(new MockMultipartFile("Test", IOUtils.EMPTY_BYTE_ARRAY));
		
		InsertOneResult insertResult = InsertOneResult.acknowledged(new BsonObjectId());
		when(configDao.insertNotificacion(isA(Notificacion.class))).thenReturn(insertResult);		
		ResponseApi response = configService.registraNotificacion(notificacion);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		insertResult = InsertOneResult.acknowledged(null);
		notificacion.setGeneralSucursales(true);
		when(configDao.insertNotificacion(isA(Notificacion.class))).thenReturn(insertResult);
		response = configService.registraNotificacion(notificacion);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
	
	@Test
	void testActualizaNotificacion() throws IOException {
		
		Notificacion notificacion = new Notificacion();
		notificacion.setId("");		
		notificacion.setFechaRegistro(LocalDateTime.now());
		notificacion.setFileImagen(new MockMultipartFile("Test", IOUtils.EMPTY_BYTE_ARRAY));
		
		notificacion.setFechaActualizacion(LocalDateTime.now());
		notificacion.setFechaFin(LocalDate.now());
		notificacion.setFechaInicio(LocalDate.now());
		notificacion.setFechaRegistro(LocalDateTime.now());
		
		when(configDao.findNotificacionById(isA(String.class), any())).thenReturn(null);
		ResponseApi response = configService.actualizaNotificacion(notificacion);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		UpdateResult result = UpdateResult.acknowledged(1L, 1L, new BsonObjectId());
		when(configDao.findNotificacionById(isA(String.class), any())).thenReturn(new Notificacion());
		when(configDao.updateNotificacion(isA(Notificacion.class))).thenReturn(result);		
		response = configService.actualizaNotificacion(notificacion);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		notificacion.setFileImagen(null);
		notificacion.setGeneralSucursales(true);
		when(configDao.findNotificacionById(isA(String.class), any())).thenReturn(new Notificacion());
		when(configDao.updateNotificacion(isA(Notificacion.class))).thenReturn(result);		
		response = configService.actualizaNotificacion(notificacion);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}	
	
	@Test
	void testSetEstatusNotificacion() {
		
		Notificacion notificacion = new Notificacion();
		notificacion.setFechaInicio(LocalDate.now());
		notificacion.setFechaFin(LocalDate.now());
		notificacion.setEstatusNotificacion();				
		assertThat(notificacion.getEstatus()).isEqualTo("Vigente");
		
		notificacion.setFechaInicio(LocalDate.now().minusDays(1));
		notificacion.setFechaFin(LocalDate.now().plusDays(1));
		notificacion.setEstatusNotificacion();				
		assertThat(notificacion.getEstatus()).isEqualTo("Vigente");
		
		notificacion.setFechaInicio(LocalDate.now());
		notificacion.setFechaFin(LocalDate.now().plusDays(1));
		notificacion.setEstatusNotificacion();				
		assertThat(notificacion.getEstatus()).isEqualTo("Vigente");
		
		notificacion.setFechaInicio(LocalDate.now().minusDays(1));
		notificacion.setFechaFin(LocalDate.now().minusDays(1));
		notificacion.setEstatusNotificacion();				
		assertThat(notificacion.getEstatus()).isEqualTo("No Vigente");
		
		notificacion.setFechaInicio(LocalDate.now().plusDays(1));
		notificacion.setFechaFin(LocalDate.now().plusDays(1));
		notificacion.setEstatusNotificacion();				
		assertThat(notificacion.getEstatus()).isEqualTo("Próxima");
		
	}
	
	@Test
	void testIsGeneral() {
		
		Notificacion notificacion = new Notificacion();
		notificacion.setGeneralSucursales(true);
		assertThat(notificacion.isGeneral()).isTrue();
		
		notificacion.setGeneralSucursales(false);
		notificacion.setSucursales(Arrays.asList(""));
		assertThat(notificacion.isGeneral()).isTrue();
		
		notificacion.setGeneralSucursales(false);
		notificacion.setSucursales(Arrays.asList());
		assertThat(notificacion.isGeneral()).isFalse();
		
	}
	
	@Test
	void testActualizarEstatusNotificacion() {
		
		Notificacion notificacion = new Notificacion();
		notificacion.setId("");
		notificacion.setActivo(true);
			
		when(configDao.findNotificacionById(isA(String.class), any())).thenReturn(null);
		ResponseApi response = configService.actualizarEstatusNotificacion("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());

		
		UpdateResult updateResult = UpdateResult.acknowledged(1L, 1L, new BsonObjectId());

		when(configDao.findNotificacionById(isA(String.class), any())).thenReturn(notificacion);
		when(configDao.updateEstatusNotificacion(isA(String.class), isA(boolean.class))).thenReturn(updateResult);
		response = configService.actualizarEstatusNotificacion("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		notificacion.setActivo(false);

		when(configDao.findNotificacionById(isA(String.class), any())).thenReturn(notificacion);
		when(configDao.updateEstatusNotificacion(isA(String.class), isA(boolean.class))).thenReturn(updateResult);
		response = configService.actualizarEstatusNotificacion("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		updateResult = UpdateResult.acknowledged(0L, 0L, new BsonObjectId());
		
		when(configDao.findNotificacionById(isA(String.class), any())).thenReturn(notificacion);
		when(configDao.updateEstatusNotificacion(isA(String.class), isA(boolean.class))).thenReturn(updateResult);
		response = configService.actualizarEstatusNotificacion("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());

	}

}
