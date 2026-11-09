package com.baz.divisas.api.tef.parametria.dto;

import org.junit.jupiter.api.Test;

public class ResConsultaParametrosTest {

	@Test
	public void init() {
		ResConsultaParametros r = new ResConsultaParametros();
		r.setCodigo("1212");
		r.setFolio("1212");
		r.setInfo("121212");
		r.setMensaje("asdadadas");
		r.setResultado(new ConsultaParametros());
		r.getCodigo();
		r.getFolio();
		r.getInfo();
		r.getMensaje();
		r.getResultado();
	}
}
