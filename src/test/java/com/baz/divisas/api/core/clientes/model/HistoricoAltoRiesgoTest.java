package com.baz.divisas.api.core.clientes.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class HistoricoAltoRiesgoTest {

	@Test
	public void testDto() {
		HistoricoAltoRiesgo c = new HistoricoAltoRiesgo();
		c.setNombre("");
		c.getNombre();
		c.setDescripcion("");
		c.getDescripcion();
		c.setFecha("");
		c.getFecha();
		c.setId("");
		c.getId();
		c.setRegistros(0);
		c.getRegistros();
		c.setTipo("altoRiesgo");
		assertThat(c.getTipo()).isEqualTo("altoRiesgo");
		c.toString();
	}
}
