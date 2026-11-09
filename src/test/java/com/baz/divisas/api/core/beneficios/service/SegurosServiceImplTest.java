package com.baz.divisas.api.core.beneficios.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.bson.BsonObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.baz.divisas.api.core.beneficios.dao.SegurosDao;
import com.baz.divisas.api.core.beneficios.dto.ReqEstatusSeguro;
import com.baz.divisas.api.core.beneficios.model.Seguro;
import com.baz.divisas.general.dto.ResponseApi;
import com.mongodb.client.result.InsertOneResult;

@ExtendWith(MockitoExtension.class)
public class SegurosServiceImplTest {
	
	@InjectMocks
	private SegurosServiceImpl segurosService;
	
	@Mock
	private SegurosDao segurosDao;
	
	@Test
	void testConsultaSeguros() {
		
		when(segurosDao.find()).thenReturn(Arrays.asList());
		
		ResponseApi response = segurosService.consultaSeguros();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		/////////////
		
		when(segurosDao.find()).thenReturn(Arrays.asList(new Seguro()));
		
		response = segurosService.consultaSeguros();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
		
	@Test
	void testConsultaSegurosPorEstatus() {
		
		when(segurosDao.findByEstatus(isA(String.class))).thenReturn(Arrays.asList());
		
		ResponseApi response = segurosService.consultaSegurosPorEstatus("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		/////////////		
		when(segurosDao.findByEstatus(isA(String.class))).thenReturn(Arrays.asList(new Seguro()));
		
		response = segurosService.consultaSegurosPorEstatus("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testConsultaSegurosPorId() {
		
		when(segurosDao.findById(isA(String.class))).thenReturn(null);
		
		ResponseApi response = segurosService.consultaSegurosPorId("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		/////////////		
		when(segurosDao.findById(isA(String.class))).thenReturn(new Seguro());
		
		response = segurosService.consultaSegurosPorId("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testCrear() {
		
		InsertOneResult insertResult = InsertOneResult.acknowledged(null);
		when(segurosDao.insert(isA(Seguro.class))).thenReturn(insertResult);
		
		ResponseApi response = segurosService.crear(new Seguro());
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		/////////////		
		insertResult = InsertOneResult.acknowledged(new BsonObjectId());
		when(segurosDao.insert(isA(Seguro.class))).thenReturn(insertResult);
		
		response = segurosService.crear(new Seguro());
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testActualizar() {
		
		when(segurosDao.update(isA(Seguro.class))).thenReturn(null);
		
		ResponseApi response = segurosService.actualizar(new Seguro());
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		/////////////		
		when(segurosDao.update(isA(Seguro.class))).thenReturn(new Seguro());
		
		response = segurosService.actualizar(new Seguro());
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void testActualizarEstatus() {
		
		when(segurosDao.updateEstatus(isA(String.class), isA(String.class))).thenReturn(null);
		
		ReqEstatusSeguro request = new ReqEstatusSeguro();
		request.setEstatus("");
		request.setId("");
		
		ResponseApi response = segurosService.actualizarEstatus(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		/////////////		
		when(segurosDao.updateEstatus(isA(String.class), isA(String.class))).thenReturn(new Seguro());
		
		response = segurosService.actualizarEstatus(request);
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}
}
