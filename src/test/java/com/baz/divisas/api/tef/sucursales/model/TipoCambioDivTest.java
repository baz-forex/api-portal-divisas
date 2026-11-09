package com.baz.divisas.api.tef.sucursales.model;

import org.junit.jupiter.api.Test;

public class TipoCambioDivTest {

	@Test
	public void init() {
		TipoCambioDiv t = new TipoCambioDiv();
		t.setCodigoDivisa("asda");
		t.setCodigoOrigen("1212");
		t.setId("12");
		t.setIdTipoOperacion("122112");
		t.setMontoDivisa("12");
		t.getCodigoDivisa();
		t.getCodigoOrigen();
		t.getId();
		t.getIdTipoOperacion();
		t.getMontoDivisa();
		t.toString();
	}
}
