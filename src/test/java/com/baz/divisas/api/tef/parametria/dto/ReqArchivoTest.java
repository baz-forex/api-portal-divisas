package com.baz.divisas.api.tef.parametria.dto;

import org.junit.jupiter.api.Test;

public class ReqArchivoTest {

	@Test
	public void init() {
		ReqArchivo r = new ReqArchivo();
		r.setArchivo("12312312312");
		r.getArchivo();
	}
}
