package com.baz.divisas.api.tef.parametria.dto;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
public class AsignacionParametroTest {

	@Test
	public void init() {
		AsignacionParametro a = new AsignacionParametro();
		a.setAsignacion("1231");
		a.setDescripcion("1212");
		a.setId("1");
		a.setIdParametro("asdasdasd");
		a.setParametro("");
		String x = "";
		List<String> arreglo = new ArrayList<>();
		arreglo.add(x);
		a.setSucursales(arreglo);
		
		a.getAsignacion();
		a.getDescripcion();
		a.getId();
		a.getIdParametro();
		a.getParametro();
		a.getSucursales();
	}
}
