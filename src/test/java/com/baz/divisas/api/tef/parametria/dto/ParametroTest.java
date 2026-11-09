package com.baz.divisas.api.tef.parametria.dto;

import org.junit.jupiter.api.Test;

public class ParametroTest {

	@Test
	public void init() {
		Parametro p = new Parametro();
		p.setDescripcion("12312");
		p.setId("1212");
		p.setParametro("123131231");
		p.setSucursalesAsignadas(3);
		p.getDescripcion();
		p.getId();
		p.getParametro();
		p.getSucursalesAsignadas();
		
	}
}
