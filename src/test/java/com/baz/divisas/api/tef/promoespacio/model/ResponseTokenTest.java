package com.baz.divisas.api.tef.promoespacio.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ResponseTokenTest {

	@Test
	void testGettersSetters() {
		ResponseToken response = new ResponseToken();

		response.setCodigo(200);
		assertThat(response.getCodigo()).isEqualTo(200);

		Resultado resultado = new Resultado();
		resultado.setToken("token-abc");
		response.setResultado(resultado);
		assertThat(response.getResultado()).isNotNull();
		assertThat(response.getResultado().getToken()).isEqualTo("token-abc");

		response.setStatus("OK");
		assertThat(response.getStatus()).isEqualTo("OK");

		Auth data = new Auth();
		response.setData(data);
		assertThat(response.getData()).isNotNull();

		response.setMensaje("procesado");
		assertThat(response.getMensaje()).isEqualTo("procesado");
	}

	@Test
	void testToString() {
		ResponseToken response = new ResponseToken();
		response.setCodigo(200);
		response.setStatus("OK");
		response.setMensaje("procesado");

		String json = response.toString();

		assertThat(json).isNotEmpty();
		assertThat(json).contains("200");
		assertThat(json).contains("procesado");
	}
}
