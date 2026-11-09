package com.baz.divisas.api.tef.parametria.dto;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ReqAsignacionTest {

	@Test
	public void init() {
		ReqAsignacion r = new ReqAsignacion();
		r.setIdParametro("1212");
		r.setParametro("sadasdasd");
		List<String> arreglo = new ArrayList<>();
		r.setSucursales(arreglo);
		r.getIdParametro();
		r.getParametro();
		r.getSucursales();
	}
}
