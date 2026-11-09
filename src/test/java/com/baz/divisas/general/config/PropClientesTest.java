package com.baz.divisas.general.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PropClientesTest {

	@Test
	void testGetAuthUsuario() {
		String resultado = PropClientes.getAuthUsuario();
		assertThat(resultado).isNotNull();
	}

	@Test
	void testGetAuthClave() {
		String resultado = PropClientes.getAuthClave();
		assertThat(resultado).isNotNull();
	}

	@Test
	void testGetAuthUsuarioAdmin() {
		String resultado = PropClientes.getAuthUsuarioAdmin();
		assertThat(resultado).isNotNull();
	}

	@Test
	void testGetAuthClaveAdmin() {
		String resultado = PropClientes.getAuthClaveAdmin();
		assertThat(resultado).isNotNull();
	}

	@Test
	void testGetUrlMiddleware() {
		String resultado = PropClientes.getUrlMiddleware();
		assertThat(resultado).isNotNull();
	}

	@Test
	void testGetUrlWsAuthToken() {
		String resultado = PropClientes.getUrlWsAuthToken();
		assertThat(resultado).isNotNull();
	}

	@Test
	void testGetUrlWsBusqIdCliente() {
		String resultado = PropClientes.getUrlWsBusqIdCliente();
		assertThat(resultado).isNotNull();
	}

	@Test
	void testGetUrlWsBusqDinamicaClientes() {
		String resultado = PropClientes.getUrlWsBusqDinamicaClientes();
		assertThat(resultado).isNotNull();
	}

	@Test
	void testGetUrlWsActualizaClientes() {
		String resultado = PropClientes.getUrlWsActualizaClientes();
		assertThat(resultado).isNotNull();
	}

	@Test
	void testGetUrlWsTipoCambio() {
		String resultado = PropClientes.getUrlWsTipoCambio();
		assertThat(resultado).isNotNull();
	}

	@Test
	void testGetBitacoraCargaMasivaClientes() {
		String resultado = PropClientes.getBitacoraCargaMasivaClientes();
		assertThat(resultado).isNotNull();
	}

	@Test
	void testGetBitacoraActualizacionClientes() {
		String resultado = PropClientes.getBitacoraActualizacionClientes();
		assertThat(resultado).isNotNull();
	}

	@Test
	void testGetClientesAgrupados() {
		String resultado = PropClientes.getClientesAgrupados();
		assertThat(resultado).isNotNull();
	}

	@Test
	void testGetBitacoraCargaHistAltoRiesgo() {
		String resultado = PropClientes.getBitacoraCargaHistAltoRiesgo();
		assertThat(resultado).isNotNull();
	}

	@Test
	void testGetClientesAltoRiesgo() {
		String resultado = PropClientes.getClientesAltoRiesgo();
		assertThat(resultado).isNotNull();
	}

	@Test
	void testGetBitacoraCuponesTA() {
		String resultado = PropClientes.getBitacoraCuponesTA();
		assertThat(resultado).isNotNull();
	}

	@Test
	void testGetCuponesTA() {
		String resultado = PropClientes.getCuponesTA();
		assertThat(resultado).isNotNull();
	}

	@Test
	void testGetCuponesBeneficios() {
		String resultado = PropClientes.getCuponesBeneficios();
		assertThat(resultado).isNotNull();
	}

	@Test
	void testGetBitacoraCuponesCRM() {
		String resultado = PropClientes.getBitacoraCuponesCRM();
		assertThat(resultado).isNotNull();
	}

	@Test
	void testGetCuponesMasSalud() {
		String resultado = PropClientes.getCuponesMasSalud();
		assertThat(resultado).isNotNull();
	}

	@Test
	void testGetBitacoraCuponesMasSalud() {
		String resultado = PropClientes.getBitacoraCuponesMasSalud();
		assertThat(resultado).isNotNull();
	}

	@Test
	void testGetBitacoraCuponesAlianzas() {
		String resultado = PropClientes.getBitacoraCuponesAlianzas();
		assertThat(resultado).isNotNull();
	}

	@Test
	void testFormatoFechaBdCompleta() {
		assertThat(PropClientes.FORMATOFECHABDCOMPLETA).isEqualTo("yyyy-MM-dd'T'HH:mm:ss.SSS");
	}
}
