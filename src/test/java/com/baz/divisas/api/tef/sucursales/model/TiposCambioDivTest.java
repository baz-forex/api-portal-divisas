package com.baz.divisas.api.tef.sucursales.model;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class TiposCambioDivTest {

	@Test
	public void init() {
		TiposCambioDiv t = new TiposCambioDiv();
		t.setTiposCambios(new ArrayList<>());
		t.getTiposCambios();
		t.toString();
	}
}
