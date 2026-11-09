package com.baz.divisas.api.tef.parametria.dto;

import org.junit.jupiter.api.Test;

public class PaginacionTest {
	@Test
	public void init() {
		Paginacion pag = new Paginacion();
		pag.setPagina(1);
		pag.setRegistrosPagina(2);
		pag.setTotalPaginas(12);
		pag.setTotalRegistros(123);
		pag.getPagina();
		pag.getRegistrosPagina();
		pag.getTotalPaginas();
		pag.getTotalRegistros();
	}
}
