package com.baz.divisas.api.tef.spread;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.bson.BsonObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.tef.spread.dao.SpreadsDao;
import com.baz.divisas.api.tef.spread.dto.Spread;
import com.baz.divisas.api.tef.spread.services.impl.SpreadsServiceImpl;
import com.baz.divisas.general.dto.ResponseApi;
import com.mongodb.client.result.UpdateResult;

@ExtendWith(MockitoExtension.class)
class SpreadsServiceImplTest {

	@InjectMocks
	private SpreadsServiceImpl spreadsService;

	@Mock
	private SpreadsDao spreadsDao;

	@Mock
	private BitacoraServiceImpl bitacora;

	@Test
	void testConsultaSpreads() {

		ArrayList<Spread> listSpreads = new ArrayList<>();

		when(spreadsDao.findSpreads()).thenReturn(listSpreads);
		ResponseApi response = spreadsService.consultaSpreads();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());

		listSpreads.add(new Spread());

		when(spreadsDao.findSpreads()).thenReturn(listSpreads);
		response = spreadsService.consultaSpreads();
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testConsultaSpreadsDivisa() {

		when(spreadsDao.findSpreadDivisa(isA(String.class))).thenReturn(null);
		ResponseApi response = spreadsService.consultaSpreadsDivisa("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());


		Spread spread = new Spread();
		spread.setCompraAbono(0.0);
		spread.getCompraAbono();
		spread.setCompraEfectivo(0.0);
		spread.getCompraEfectivo();
		spread.setVentaCargo(0.0);
		spread.getVentaCargo();
		spread.setVentaEfectivo(0.0);
		spread.getVentaEfectivo();
		spread.setIdDivisa("");
		spread.getIdDivisa();

		when(spreadsDao.findSpreadDivisa(isA(String.class))).thenReturn(spread);
		response = spreadsService.consultaSpreadsDivisa("");
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void testActualizaSpreadDivisa() {

		UpdateResult updateResult = UpdateResult.acknowledged(0L, 0L, new BsonObjectId());

		when(spreadsDao.updateSpreadDivisa(isA(Spread.class))).thenReturn(updateResult);
		ResponseApi response = spreadsService.actualizaSpreadDivisa(new Spread());
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.NOT_FOUND.value());

		updateResult = UpdateResult.acknowledged(1L, 0L, new BsonObjectId());

		when(spreadsDao.updateSpreadDivisa(isA(Spread.class))).thenReturn(updateResult);
		response = spreadsService.actualizaSpreadDivisa(new Spread());
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

		updateResult = UpdateResult.acknowledged(1L, 1L, new BsonObjectId());

		when(spreadsDao.updateSpreadDivisa(isA(Spread.class))).thenReturn(updateResult);
		response = spreadsService.actualizaSpreadDivisa(new Spread());
		assertThat(response.getHttpStatus().value()).isEqualTo(HttpStatus.OK.value());

	}

}