package com.baz.divisas.api.tef.parametria.dto;

import org.junit.jupiter.api.Test;

public class PaginacionParametroTest {

	@Test
	public void init() {
		PaginacionParametro p = new PaginacionParametro();
		p.setPagina(1);
		p.setRegistrosPagina(2);
		p.getPagina();
		p.getRegistrosPagina();
		p.toString();
	}
}
