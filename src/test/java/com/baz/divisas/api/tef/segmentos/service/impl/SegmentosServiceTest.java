package com.baz.divisas.api.tef.segmentos.service.impl;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
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

import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.tef.general.model.Segmento;
import com.baz.divisas.api.tef.segmentos.dao.SegmentosDao;
import com.baz.divisas.api.tef.segmentos.services.impl.SegmentosService;
import com.baz.divisas.general.dto.ResponseApi;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SegmentosServiceTest {

	@InjectMocks
	SegmentosService segmentosService;

	@Mock
	SegmentosDao segmentosDao;	
	@Mock
	BitacoraServiceImpl bitacora;

	@Test
	void testConsultaSegmentos() {

		
		when(segmentosDao.findAll()).thenReturn(null);

		ResponseApi response = segmentosService.consultaSegmentos();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		List<Segmento> segmentos = new ArrayList<>();
		when(segmentosDao.findAll()).thenReturn(segmentos);

		response = segmentosService.consultaSegmentos();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		segmentos.add(new Segmento());
		when(segmentosDao.findAll()).thenReturn(segmentos);

		response = segmentosService.consultaSegmentos();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testCreaSegmento() {
				
		Segmento segmento = new Segmento();
		
		when(segmentosDao.findByDescripcion(isA(String.class))).thenReturn(segmento);
		ResponseApi response = segmentosService.creaSegmento("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		
		when(segmentosDao.findByDescripcion(isA(String.class))).thenReturn(null);
		InsertOneResult result = InsertOneResult.acknowledged(null);
		when(segmentosDao.insert(isA(Segmento.class))).thenReturn(result);
		
		response = segmentosService.creaSegmento("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		//--------------------------------------------------------------------
		
		result = InsertOneResult.acknowledged(new BsonObjectId());
		when(segmentosDao.insert(isA(Segmento.class))).thenReturn(result);
		
		response = segmentosService.creaSegmento("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
	}

	@Test
	void testActualizaDescripcionSegmento() {
		
		when(segmentosDao.findById(isA(String.class))).thenReturn(null);
		
		ResponseApi response = segmentosService.actualizaDescripcionSegmento("", "");		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		Segmento segmento = new Segmento();

		when(segmentosDao.findById(isA(String.class))).thenReturn(segmento);
		UpdateResult result = UpdateResult.acknowledged(0L, 0L, new BsonObjectId());
		when(segmentosDao.updateDescripcion(isA(String.class), isA(String.class))).thenReturn(result);
		
		response = segmentosService.actualizaDescripcionSegmento("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());

		//--------------------------------------------------------------------
		
		result = UpdateResult.acknowledged(1L, 1L, new BsonObjectId());
		when(segmentosDao.updateDescripcion(isA(String.class), isA(String.class))).thenReturn(result);
		
		response = segmentosService.actualizaDescripcionSegmento("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//--------------------------------------------------------------------
		
		result = UpdateResult.acknowledged(1L, 0L, new BsonObjectId());
		when(segmentosDao.updateDescripcion(isA(String.class), isA(String.class))).thenReturn(result);
		
		response = segmentosService.actualizaDescripcionSegmento("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testActualizaEstatusSegmento() {
		
		when(segmentosDao.findById(isA(String.class))).thenReturn(null);
		
		ResponseApi response = segmentosService.actualizaEstatusSegmento("");		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		Segmento segmento = new Segmento();

		when(segmentosDao.findById(isA(String.class))).thenReturn(segmento);
		UpdateResult result = UpdateResult.acknowledged(0L, 0L, new BsonObjectId());
		when(segmentosDao.updateEstatus(isA(String.class), isA(boolean.class))).thenReturn(result);
		
		response = segmentosService.actualizaEstatusSegmento("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());

		//--------------------------------------------------------------------
		
		segmento.setActivo(true);
		result = UpdateResult.acknowledged(1L, 1L, new BsonObjectId());
		when(segmentosDao.updateEstatus(isA(String.class), isA(boolean.class))).thenReturn(result);
		
		response = segmentosService.actualizaEstatusSegmento("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//--------------------------------------------------------------------
		
		segmento.setActivo(false);
		result = UpdateResult.acknowledged(1L, 1L, new BsonObjectId());
		when(segmentosDao.updateEstatus(isA(String.class), isA(boolean.class))).thenReturn(result);
		
		response = segmentosService.actualizaEstatusSegmento("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//--------------------------------------------------------------------
		
		result = UpdateResult.acknowledged(1L, 0L, new BsonObjectId());
		when(segmentosDao.updateEstatus(isA(String.class), isA(boolean.class))).thenReturn(result);
		
		response = segmentosService.actualizaEstatusSegmento("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testEliminaRegion() {
		
		when(segmentosDao.findById(isA(String.class))).thenReturn(null);
		
		ResponseApi response = segmentosService.eliminaSegmento("");		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		Segmento segmento = new Segmento();

		when(segmentosDao.findById(isA(String.class))).thenReturn(segmento);
		DeleteResult result = DeleteResult.acknowledged(1);
		when(segmentosDao.delete(isA(String.class))).thenReturn(result);
		
		response = segmentosService.eliminaSegmento("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());	
		
		//--------------------------------------------------------------------
		segmento.setSucursales(Arrays.asList(""));

		when(segmentosDao.findById(isA(String.class))).thenReturn(segmento);
		result = DeleteResult.acknowledged(1);
		when(segmentosDao.delete(isA(String.class))).thenReturn(result);
		
		response = segmentosService.eliminaSegmento("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());		
		
		//--------------------------------------------------------------------
		segmento.setAsignado(true);

		when(segmentosDao.findById(isA(String.class))).thenReturn(segmento);
		result = DeleteResult.acknowledged(1);
		when(segmentosDao.delete(isA(String.class))).thenReturn(result);
		
		response = segmentosService.eliminaSegmento("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
	
	//OLP
	
	@Test
	void testConsultaSegmentosOlp() {

		
		when(segmentosDao.findAllOlp()).thenReturn(null);

		ResponseApi response = segmentosService.consultaSegmentosOlp();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		List<Segmento> segmentos = new ArrayList<>();
		when(segmentosDao.findAllOlp()).thenReturn(segmentos);

		response = segmentosService.consultaSegmentosOlp();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		//--------------------------------------------------------------------
		
		segmentos.add(new Segmento());
		when(segmentosDao.findAllOlp()).thenReturn(segmentos);

		response = segmentosService.consultaSegmentosOlp();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testCreaSegmentoOlp() {
				
		Segmento segmento = new Segmento();
		
		when(segmentosDao.findByDescripcionOlp(isA(String.class))).thenReturn(segmento);
		ResponseApi response = segmentosService.creaSegmentoOlp("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		
		when(segmentosDao.findByDescripcionOlp(isA(String.class))).thenReturn(null);
		InsertOneResult result = InsertOneResult.acknowledged(null);
		when(segmentosDao.insertOlp(isA(Segmento.class))).thenReturn(result);
		
		response = segmentosService.creaSegmentoOlp("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		//--------------------------------------------------------------------
		
		result = InsertOneResult.acknowledged(new BsonObjectId());
		when(segmentosDao.insertOlp(isA(Segmento.class))).thenReturn(result);
		
		response = segmentosService.creaSegmentoOlp("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
	}

	@Test
	void testActualizaDescripcionSegmentoOlp() {
		
		when(segmentosDao.findByIdOlp(isA(String.class))).thenReturn(null);
		
		ResponseApi response = segmentosService.actualizaDescripcionSegmentoOlp("", "");		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		Segmento segmento = new Segmento();

		when(segmentosDao.findByIdOlp(isA(String.class))).thenReturn(segmento);
		UpdateResult result = UpdateResult.acknowledged(0L, 0L, new BsonObjectId());
		when(segmentosDao.updateDescripcionOlp(isA(String.class), isA(String.class))).thenReturn(result);
		
		response = segmentosService.actualizaDescripcionSegmentoOlp("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());

		//--------------------------------------------------------------------
		
		result = UpdateResult.acknowledged(1L, 1L, new BsonObjectId());
		when(segmentosDao.updateDescripcionOlp(isA(String.class), isA(String.class))).thenReturn(result);
		
		response = segmentosService.actualizaDescripcionSegmentoOlp("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//--------------------------------------------------------------------
		
		result = UpdateResult.acknowledged(1L, 0L, new BsonObjectId());
		when(segmentosDao.updateDescripcionOlp(isA(String.class), isA(String.class))).thenReturn(result);
		
		response = segmentosService.actualizaDescripcionSegmentoOlp("", "");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testActualizaEstatusSegmentoOlp() {
		
		when(segmentosDao.findByIdOlp(isA(String.class))).thenReturn(null);
		
		ResponseApi response = segmentosService.actualizaEstatusSegmentoOlp("");		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		Segmento segmento = new Segmento();

		when(segmentosDao.findByIdOlp(isA(String.class))).thenReturn(segmento);
		UpdateResult result = UpdateResult.acknowledged(0L, 0L, new BsonObjectId());
		when(segmentosDao.updateEstatusOlp(isA(String.class), isA(boolean.class))).thenReturn(result);
		
		response = segmentosService.actualizaEstatusSegmentoOlp("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());

		//--------------------------------------------------------------------
		
		segmento.setActivo(true);
		result = UpdateResult.acknowledged(1L, 1L, new BsonObjectId());
		when(segmentosDao.updateEstatusOlp(isA(String.class), isA(boolean.class))).thenReturn(result);
		
		response = segmentosService.actualizaEstatusSegmentoOlp("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//--------------------------------------------------------------------
		
		segmento.setActivo(false);
		result = UpdateResult.acknowledged(1L, 1L, new BsonObjectId());
		when(segmentosDao.updateEstatusOlp(isA(String.class), isA(boolean.class))).thenReturn(result);
		
		response = segmentosService.actualizaEstatusSegmentoOlp("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
		
		//--------------------------------------------------------------------
		
		result = UpdateResult.acknowledged(1L, 0L, new BsonObjectId());
		when(segmentosDao.updateEstatusOlp(isA(String.class), isA(boolean.class))).thenReturn(result);
		
		response = segmentosService.actualizaEstatusSegmentoOlp("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testEliminaRegionOlp() {
		
		when(segmentosDao.findByIdOlp(isA(String.class))).thenReturn(null);
		
		ResponseApi response = segmentosService.eliminaSegmentoOlp("");		
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
		//--------------------------------------------------------------------
		Segmento segmento = new Segmento();

		when(segmentosDao.findByIdOlp(isA(String.class))).thenReturn(segmento);
		DeleteResult result = DeleteResult.acknowledged(1);
		when(segmentosDao.deleteOlp(isA(String.class))).thenReturn(result);
		
		response = segmentosService.eliminaSegmentoOlp("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());	
		
		//--------------------------------------------------------------------
		segmento.setSucursales(Arrays.asList(""));

		when(segmentosDao.findByIdOlp(isA(String.class))).thenReturn(segmento);
		result = DeleteResult.acknowledged(1);
		when(segmentosDao.deleteOlp(isA(String.class))).thenReturn(result);
		
		response = segmentosService.eliminaSegmentoOlp("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());		
		
		//--------------------------------------------------------------------
		segmento.setAsignado(true);

		when(segmentosDao.findByIdOlp(isA(String.class))).thenReturn(segmento);
		result = DeleteResult.acknowledged(1);
		when(segmentosDao.deleteOlp(isA(String.class))).thenReturn(result);
		
		response = segmentosService.eliminaSegmentoOlp("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
}
