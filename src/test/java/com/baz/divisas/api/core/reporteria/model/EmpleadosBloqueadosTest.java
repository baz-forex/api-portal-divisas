package com.baz.divisas.api.core.reporteria.model;

import org.junit.jupiter.api.Test;

public class EmpleadosBloqueadosTest {

	@Test
	public void test() {
		EmpleadosBloqueados emp = new EmpleadosBloqueados();		
		emp.setNombreCompleto("");		
		emp.getNombreCompleto();
	}
}
