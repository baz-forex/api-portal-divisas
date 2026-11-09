package com.baz.divisas.api.tef.parametria.dto;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ConsultaAsignacionesTest {

	@Test
	public void init() {
		ConsultaAsignaciones c= new ConsultaAsignaciones();
		c.setPaginacion(new Paginacion());
		List<AsignacionParametro> arregloAsignacion = new ArrayList<>();
		c.setAsignaciones(arregloAsignacion);
		c.getAsignaciones();
		c.getPaginacion();
	}
}
