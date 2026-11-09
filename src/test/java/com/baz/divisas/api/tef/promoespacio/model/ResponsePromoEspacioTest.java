package com.baz.divisas.api.tef.promoespacio.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ResponsePromoEspacioTest {

	@Test
	void testConstructorPorDefecto() {
		// El constructor inicializa codigo en 0
		ResponsePromoEspacio response = new ResponsePromoEspacio();
		assertThat(response.getCodigo()).isZero();
	}

	@Test
	void testGettersSetters() {
		ResponsePromoEspacio response = new ResponsePromoEspacio();

		response.setMessage("mensaje ok");
		assertThat(response.getMessage()).isEqualTo("mensaje ok");

		response.setRequest_id("REQ-123");
		assertThat(response.getRequest_id()).isEqualTo("REQ-123");

		response.setCodigo(200);
		assertThat(response.getCodigo()).isEqualTo(200);

		response.setMensaje("procesado");
		assertThat(response.getMensaje()).isEqualTo("procesado");

		response.setFolio("F-001");
		assertThat(response.getFolio()).isEqualTo("F-001");
	}

	@Test
	void testToString() {
		ResponsePromoEspacio response = new ResponsePromoEspacio();
		response.setMessage("ok");
		response.setRequest_id("REQ-999");
		response.setCodigo(200);
		response.setMensaje("procesado");
		response.setFolio("F-100");

		String json = response.toString();

		// toString serializa el objeto a JSON
		assertThat(json).isNotEmpty();
		assertThat(json).contains("REQ-999");
		assertThat(json).contains("procesado");
		assertThat(json).contains("F-100");
	}
}
