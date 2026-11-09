package com.baz.divisas.api.tef.parametria.dto;

import org.junit.jupiter.api.Test;

public class ReqEliminacionAsignacionTest {

	@Test
	public void init() {
		ReqEliminacionAsignacion r = new ReqEliminacionAsignacion();
		r.setIdAsignacion("1231312");
		r.getIdAsignacion();
	}
}
