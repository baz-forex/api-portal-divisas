package com.baz.divisas.api.tef.parametria.dto;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ConsultaParametrosTest {

	@Test
	public void init() {
		ConsultaParametros c = new ConsultaParametros();
		c.setPaginacion(new Paginacion());
		List<Parametro> parametros = new ArrayList<>();
		c.setParametros(parametros);
		c.getPaginacion();
		c.getParametros();
	}
}
