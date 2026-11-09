package com.baz.divisas.api.core.clientes.model;

import org.junit.jupiter.api.Test;

public class ClienteAltoRiesgoTest {
	@Test
	public void testDto() {
		ClienteAltoRiesgo c = new ClienteAltoRiesgo();
		c.setId("");
		c.getId();
		c.setNombre("");
		c.getNombre();
		c.setApellidoPaterno("");
		c.getApellidoPaterno();
		c.setApellidoMaterno("");
		c.getApellidoMaterno();	
		c.setFechaNacimiento("");
		c.getFechaNacimiento();
		c.toString();
	}
}
