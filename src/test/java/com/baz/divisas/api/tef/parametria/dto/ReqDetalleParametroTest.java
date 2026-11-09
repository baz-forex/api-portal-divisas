package com.baz.divisas.api.tef.parametria.dto;

import org.junit.jupiter.api.Test;

public class ReqDetalleParametroTest {

	@Test
	public void init() {
		ReqDetalleParametro r = new ReqDetalleParametro();
		r.setDescripcion("121212");
		r.setIdParametro("12122");
		r.setParametro("1212122");
		r.getDescripcion();
		r.getIdParametro();
		r.getParametro();
		r.toString();
	}
}
