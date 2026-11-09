package com.baz.divisas.api.tef.parametria.dto;

import org.junit.jupiter.api.Test;

public class ReqBusquedaParametrosTest {

	@Test
	public void init() {
		ReqBusquedaParametros r = new ReqBusquedaParametros();
		r.setDescripcion("1212");
		r.setIdParametro("12");
		r.setPaginacion(new PaginacionParametro());
		r.setParametro("1231231212");
		r.getDescripcion();
		r.getIdParametro();
		r.getPaginacion();
		r.getParametro();
	}
}
