package com.baz.divisas.general.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PropPromoEspacioTest {

	@Test
	void testGetAuthUsuario() {
		assertThat(PropPromoEspacio.getAuthUsuario()).isNotNull();
	}

	@Test
	void testGetAuthClave() {
		assertThat(PropPromoEspacio.getAuthClave()).isNotNull();
	}

	@Test
	void testGetUrlToken() {
		assertThat(PropPromoEspacio.getUrlToken()).isNotNull();
	}

	@Test
	void testGetUrlTableros() {
		assertThat(PropPromoEspacio.getUrlTableros()).isNotNull();
	}

	@Test
	void testGetUrlTokenV2() {
		assertThat(PropPromoEspacio.getUrlTokenV2()).isNotNull();
	}

	@Test
	void testGetUrlTablerosV2() {
		assertThat(PropPromoEspacio.getUrlTablerosV2()).isNotNull();
	}

	@Test
	void testGetClientId() {
		assertThat(PropPromoEspacio.getClientId()).isNotNull();
	}

	@Test
	void testGetClienteName() {
		assertThat(PropPromoEspacio.getClienteName()).isNotNull();
	}

	@Test
	void testGetClienteSecret() {
		assertThat(PropPromoEspacio.getClienteSecret()).isNotNull();
	}

	@Test
	void testGetPublicKey() {
		assertThat(PropPromoEspacio.getPublicKey()).isNotNull();
	}
}
