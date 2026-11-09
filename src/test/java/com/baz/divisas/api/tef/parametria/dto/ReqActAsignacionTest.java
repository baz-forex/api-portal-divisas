package com.baz.divisas.api.tef.parametria.dto;

import org.junit.jupiter.api.Test;

public class ReqActAsignacionTest {

	@Test
	public void init() {
		ReqActAsignacion r = new ReqActAsignacion();
		r.setIdAsignacion("12121");
		r.getIdAsignacion();
	}
}
