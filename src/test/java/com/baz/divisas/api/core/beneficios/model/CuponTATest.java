package com.baz.divisas.api.core.beneficios.model;

import org.junit.jupiter.api.Test;

public class CuponTATest {

	@Test
	public void init() {
		CuponTA cupon = new CuponTA();
		cupon.setEstado("123123");
		cupon.setId("1213");
		cupon.getEstado();
		cupon.getId();
	}
}
