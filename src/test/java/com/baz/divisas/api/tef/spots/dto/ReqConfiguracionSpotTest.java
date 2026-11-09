package com.baz.divisas.api.tef.spots.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ReqConfiguracionSpotTest {

	@Test
	void testGettersSetters() {
		ReqConfiguracionSpot req = new ReqConfiguracionSpot();

		req.setId("Bloomberg");
		assertThat(req.getId()).isEqualTo("Bloomberg");

		req.setAcunacion(4.0);
		assertThat(req.getAcunacion()).isEqualTo(4.0);

		req.setX1(3.0);
		assertThat(req.getX1()).isEqualTo(3.0);

		req.setX2(5.0);
		assertThat(req.getX2()).isEqualTo(5.0);

		req.setSpotMinimo(1230.0);
		assertThat(req.getSpotMinimo()).isEqualTo(1230.0);

		req.setFrecuencia("15");
		assertThat(req.getFrecuencia()).isEqualTo("15");

		req.setDireccionActualizacion("1");
		assertThat(req.getDireccionActualizacion()).isEqualTo("1");

		req.setDiferenciaCompraVenta(5.0);
		assertThat(req.getDiferenciaCompraVenta()).isEqualTo(5.0);
	}

	@Test
	void testValoresNulosPorDefecto() {
		ReqConfiguracionSpot req = new ReqConfiguracionSpot();
		assertThat(req.getId()).isNull();
		assertThat(req.getAcunacion()).isNull();
		assertThat(req.getX1()).isNull();
		assertThat(req.getX2()).isNull();
		assertThat(req.getSpotMinimo()).isNull();
		assertThat(req.getFrecuencia()).isNull();
		assertThat(req.getDireccionActualizacion()).isNull();
		assertThat(req.getDiferenciaCompraVenta()).isNull();
	}
}
