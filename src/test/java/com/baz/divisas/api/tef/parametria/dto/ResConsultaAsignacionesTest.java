package com.baz.divisas.api.tef.parametria.dto;

import org.junit.jupiter.api.Test;

public class ResConsultaAsignacionesTest {

	@Test
	public void init() {
		ResConsultaAsignaciones r = new ResConsultaAsignaciones();
		r.setResultado(new ConsultaAsignaciones());
		r.getResultado();
	}
}
