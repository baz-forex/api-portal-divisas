package com.baz.divisas.api.tef.regiones.service.impl;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

import com.baz.divisas.api.tef.general.model.Limites;
import com.baz.divisas.api.tef.general.model.MontosLimite;
import com.baz.divisas.api.tef.regiones.dao.RegionesDao;
import com.baz.divisas.api.tef.regiones.model.Divisa;
import com.baz.divisas.api.tef.regiones.model.Estrategia;
import com.baz.divisas.api.tef.regiones.model.Region;
import com.baz.divisas.api.tef.regiones.model.TipoOperacion;
import com.baz.divisas.api.tef.regiones.services.impl.RegionesService;
import com.baz.divisas.general.dto.ResponseApi;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RegionesServiceTest {

	@InjectMocks
	RegionesService regionesService;

	@Mock
	RegionesDao regionesDao;

	@Test
	void testConsultaRegiones() {

		
		when(regionesDao.findAllAndExcludeFields(any())).thenReturn(null);

		ResponseApi response = regionesService.consultaRegiones();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		List<Region> regiones = new ArrayList<>();
		when(regionesDao.findAllAndExcludeFields(any())).thenReturn(regiones);

		response = regionesService.consultaRegiones();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		regiones.add(new Region());
		when(regionesDao.findAllAndExcludeFields(any())).thenReturn(regiones);

		response = regionesService.consultaRegiones();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testCreaRegion() {
				
		Region region = new Region();
		
		when(regionesDao.findByDescripcion(isA(String.class))).thenReturn(region);
		ResponseApi response = regionesService.creaRegion("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		
		when(regionesDao.findByDescripcion(isA(String.class))).thenReturn(null);
		InsertOneResult result = InsertOneResult.acknowledged(null);
		when(regionesDao.insert(isA(Region.class))).thenReturn(result);
		response = regionesService.creaRegion("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		//--------------------------------------------------------------------
		
		result = InsertOneResult.acknowledged(new BsonObjectId());
		when(regionesDao.insert(isA(Region.class))).thenReturn(result);
		response = regionesService.creaRegion("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
	}

	@Test
	void testActualizaDescripcionRegion() {
		
		when(regionesDao.findById(isA(String.class))).thenReturn(null);
		
		ResponseApi response = regionesService.actualizaDescripcionRegion("", "");		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		Region region = new Region();

		when(regionesDao.findById(isA(String.class))).thenReturn(region);
		UpdateResult result = UpdateResult.acknowledged(0L, 0L, new BsonObjectId());
		when(regionesDao.updateDescripcion(isA(String.class), isA(String.class))).thenReturn(result);
		
		response = regionesService.actualizaDescripcionRegion("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());

		//--------------------------------------------------------------------
		
		result = UpdateResult.acknowledged(1L, 1L, new BsonObjectId());
		when(regionesDao.updateDescripcion(isA(String.class), isA(String.class))).thenReturn(result);
		
		response = regionesService.actualizaDescripcionRegion("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testActualizaEstatusRegion() {
		
		when(regionesDao.findById(isA(String.class))).thenReturn(null);
		
		ResponseApi response = regionesService.actualizaEstatusRegion("");		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		Region region = new Region();

		when(regionesDao.findById(isA(String.class))).thenReturn(region);
		UpdateResult result = UpdateResult.acknowledged(0L, 0L, new BsonObjectId());
		when(regionesDao.updateEstatus(isA(String.class), isA(boolean.class))).thenReturn(result);
		
		response = regionesService.actualizaEstatusRegion("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());

		//--------------------------------------------------------------------
		
		region.setActivo(true);
		result = UpdateResult.acknowledged(1L, 1L, new BsonObjectId());
		when(regionesDao.updateEstatus(isA(String.class), isA(boolean.class))).thenReturn(result);
		
		response = regionesService.actualizaEstatusRegion("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testEliminaRegion() {
		
		when(regionesDao.findById(isA(String.class))).thenReturn(null);
		
		ResponseApi response = regionesService.eliminaRegion("");		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		Region region = new Region();

		when(regionesDao.findById(isA(String.class))).thenReturn(region);
		DeleteResult result = DeleteResult.acknowledged(1);
		when(regionesDao.delete(isA(String.class))).thenReturn(result);
		
		response = regionesService.eliminaRegion("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
	}

	@Test
	void testActualizaEstatusDivisaRegion() {
		
		Region region = new Region();

		when(regionesDao.findById(isA(String.class))).thenReturn(null);
		ResponseApi response = regionesService.actualizaEstatusDivisaRegion("", "");
		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		
		when(regionesDao.findById(isA(String.class))).thenReturn(region);
		when(regionesDao.updateEstatusDivisaRegion(isA(String.class),isA(String.class),isA(boolean.class))).thenReturn(false);
		
		response = regionesService.actualizaEstatusDivisaRegion("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		//--------------------------------------------------------------------
		
		Divisa divisa = new Divisa("USD", "DÓLAR AMERICANO", Arrays.asList(),true);
		divisa.setActivo(true);
		divisa.isActivo();
		divisa.setDescripcion("");
		divisa.getDescripcion();
		divisa.setId("USD");
		divisa.getId();
		divisa.setTiposOperacion(Arrays.asList());
		divisa.getTiposOperacion();
		
		TipoOperacion tipoOpe = new TipoOperacion();
		tipoOpe.setIdTipoOperacion("");
		tipoOpe.getIdTipoOperacion();
		tipoOpe.setActivo(true);
		tipoOpe.isActivo();
		tipoOpe.setDescripcion("");
		tipoOpe.getDescripcion();
		
		Estrategia estrategia = new Estrategia();
		estrategia = new Estrategia(true, 0.0, "", "");
		estrategia.setActivo(true);
		estrategia.isActivo();
		estrategia.setIdBitacoraActualizacion("");
		estrategia.getIdBitacoraActualizacion();
		estrategia.setIdBitacoraAlta("");
		estrategia.getIdBitacoraAlta();
		estrategia.setMonto(0.0);
		estrategia.getMonto();
		
		tipoOpe.setEstrategia(estrategia);
		tipoOpe.getEstrategia();
		
		MontosLimite limite = new MontosLimite();
		limite = new MontosLimite(0.0, 0.0,0.0, 0.0);	
		limite.setCliente(0.0);
		limite.getCliente();
		limite.setUsuario(0.0);
		limite.getUsuario();
		
		Limites limites = new Limites();		
		limites = new Limites(true, limite, limite);
		limites.setActivo(true);
		limites.isActivo();
		limites.setDiario(limite);
		limites.getDiario();
		limites.setMensual(limite);
		limites.getMensual();
		
		tipoOpe.setLimites(limites);
		tipoOpe.getLimites();
		tipoOpe.setTipoCambioSpot(0.0);
		tipoOpe.getTipoCambioSpot();
		
		region.setDivisas(Arrays.asList(divisa));
		when(regionesDao.updateEstatusDivisaRegion(isA(String.class),isA(String.class),isA(boolean.class))).thenReturn(false);
		
		response = regionesService.actualizaEstatusDivisaRegion("", "USD");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		//--------------------------------------------------------------------
		
		region.getDivisas().get(0).setActivo(false);
		when(regionesDao.updateEstatusDivisaRegion(isA(String.class),isA(String.class),isA(boolean.class))).thenReturn(true);
		
		response = regionesService.actualizaEstatusDivisaRegion("", "USD");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}

	//OLP
	@Test
	void testConsultaRegionesOlp() {

		
		when(regionesDao.findAllAndExcludeFieldsOlp(any())).thenReturn(null);

		ResponseApi response = regionesService.consultaRegionesOlp();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		List<Region> regiones = new ArrayList<>();
		when(regionesDao.findAllAndExcludeFieldsOlp(any())).thenReturn(regiones);

		response = regionesService.consultaRegionesOlp();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		regiones.add(new Region());
		when(regionesDao.findAllAndExcludeFieldsOlp(any())).thenReturn(regiones);

		response = regionesService.consultaRegionesOlp();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testCreaRegionOlp() {
				
		Region region = new Region();
		
		when(regionesDao.findByDescripcionOlp(isA(String.class))).thenReturn(region);
		ResponseApi response = regionesService.creaRegionOlp("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		
		when(regionesDao.findByDescripcionOlp(isA(String.class))).thenReturn(null);
		InsertOneResult result = InsertOneResult.acknowledged(null);
		when(regionesDao.insertOlp(isA(Region.class))).thenReturn(result);
		response = regionesService.creaRegionOlp("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		//--------------------------------------------------------------------
		
		result = InsertOneResult.acknowledged(new BsonObjectId());
		when(regionesDao.insertOlp(isA(Region.class))).thenReturn(result);
		response = regionesService.creaRegionOlp("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
	}

	@Test
	void testActualizaDescripcionRegionOlp() {
		
		when(regionesDao.findByIdOlp(isA(String.class))).thenReturn(null);
		
		ResponseApi response = regionesService.actualizaDescripcionRegionOlp("", "");		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		Region region = new Region();

		when(regionesDao.findByIdOlp(isA(String.class))).thenReturn(region);
		UpdateResult result = UpdateResult.acknowledged(0L, 0L, new BsonObjectId());
		when(regionesDao.updateDescripcionOlp(isA(String.class), isA(String.class))).thenReturn(result);
		
		response = regionesService.actualizaDescripcionRegionOlp("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());

		//--------------------------------------------------------------------
		
		result = UpdateResult.acknowledged(1L, 1L, new BsonObjectId());
		when(regionesDao.updateDescripcionOlp(isA(String.class), isA(String.class))).thenReturn(result);
		
		response = regionesService.actualizaDescripcionRegionOlp("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testActualizaEstatusRegionOlp() {
		
		when(regionesDao.findByIdOlp(isA(String.class))).thenReturn(null);
		
		ResponseApi response = regionesService.actualizaEstatusRegionOlp("");		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		Region region = new Region();

		when(regionesDao.findByIdOlp(isA(String.class))).thenReturn(region);
		UpdateResult result = UpdateResult.acknowledged(0L, 0L, new BsonObjectId());
		when(regionesDao.updateEstatusOlp(isA(String.class), isA(boolean.class))).thenReturn(result);
		
		response = regionesService.actualizaEstatusRegionOlp("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());

		//--------------------------------------------------------------------
		
		region.setActivo(true);
		result = UpdateResult.acknowledged(1L, 1L, new BsonObjectId());
		when(regionesDao.updateEstatusOlp(isA(String.class), isA(boolean.class))).thenReturn(result);
		
		response = regionesService.actualizaEstatusRegionOlp("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testEliminaRegionOlp() {
		
		when(regionesDao.findByIdOlp(isA(String.class))).thenReturn(null);
		
		ResponseApi response = regionesService.eliminaRegionOlp("");		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		Region region = new Region();

		when(regionesDao.findByIdOlp(isA(String.class))).thenReturn(region);
		DeleteResult result = DeleteResult.acknowledged(1);
		when(regionesDao.deleteOlp(isA(String.class))).thenReturn(result);
		
		response = regionesService.eliminaRegionOlp("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
	}

	@Test
	void testActualizaEstatusDivisaRegionOlp() {
		
		Region region = new Region();

		when(regionesDao.findByIdOlp(isA(String.class))).thenReturn(null);
		ResponseApi response = regionesService.actualizaEstatusDivisaRegionOlp("", "");
		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		
		when(regionesDao.findByIdOlp(isA(String.class))).thenReturn(region);
		when(regionesDao.updateEstatusDivisaRegionOlp(isA(String.class),isA(String.class),isA(boolean.class))).thenReturn(false);
		
		response = regionesService.actualizaEstatusDivisaRegionOlp("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		//--------------------------------------------------------------------
		
		Divisa divisa = new Divisa("OLP", "ONZA DE PLATA", Arrays.asList(),true);
		divisa.setActivo(true);
		divisa.isActivo();
		divisa.setDescripcion("");
		divisa.getDescripcion();
		divisa.setId("OLP");
		divisa.getId();
		divisa.setTiposOperacion(Arrays.asList());
		divisa.getTiposOperacion();
		
		TipoOperacion tipoOpe = new TipoOperacion();
		tipoOpe.setIdTipoOperacion("");
		tipoOpe.getIdTipoOperacion();
		tipoOpe.setActivo(true);
		tipoOpe.isActivo();
		tipoOpe.setDescripcion("");
		tipoOpe.getDescripcion();
		
		Estrategia estrategia = new Estrategia();
		estrategia = new Estrategia(true, 0.0, "", "");
		estrategia.setActivo(true);
		estrategia.isActivo();
		estrategia.setIdBitacoraActualizacion("");
		estrategia.getIdBitacoraActualizacion();
		estrategia.setIdBitacoraAlta("");
		estrategia.getIdBitacoraAlta();
		estrategia.setMonto(0.0);
		estrategia.getMonto();
		
		tipoOpe.setEstrategia(estrategia);
		tipoOpe.getEstrategia();
		
		MontosLimite limite = new MontosLimite();
		limite = new MontosLimite(0.0, 0.0,0.0, 0.0);	
		limite.setCliente(0.0);
		limite.getCliente();
		limite.setUsuario(0.0);
		limite.getUsuario();
		
		Limites limites = new Limites();		
		limites = new Limites(true, limite, limite);
		limites.setActivo(true);
		limites.isActivo();
		limites.setDiario(limite);
		limites.getDiario();
		limites.setMensual(limite);
		limites.getMensual();
		
		tipoOpe.setLimites(limites);
		tipoOpe.getLimites();
		tipoOpe.setTipoCambioSpot(0.0);
		tipoOpe.getTipoCambioSpot();
		
		region.setDivisas(Arrays.asList(divisa));
		when(regionesDao.updateEstatusDivisaRegionOlp(isA(String.class),isA(String.class),isA(boolean.class))).thenReturn(false);
		
		response = regionesService.actualizaEstatusDivisaRegionOlp("", "OLP");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		//--------------------------------------------------------------------
		
		region.getDivisas().get(0).setActivo(false);
		when(regionesDao.updateEstatusDivisaRegionOlp(isA(String.class),isA(String.class),isA(boolean.class))).thenReturn(true);
		
		response = regionesService.actualizaEstatusDivisaRegionOlp("", "OLP");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}
}
