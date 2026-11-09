package com.baz.divisas.api.tef.parametria.dto;

import org.junit.jupiter.api.Test;

public class ReqParametroTest {

	@Test
	public void init() {
		ReqParametro r = new ReqParametro();
		r.setIdParametro("1");
		r.getIdParametro();
		r.toString();
	}
}
