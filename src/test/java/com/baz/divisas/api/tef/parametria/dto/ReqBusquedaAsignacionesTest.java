package com.baz.divisas.api.tef.parametria.dto;

import org.junit.jupiter.api.Test;

public class ReqBusquedaAsignacionesTest {

	@Test
	public void init() {
		ReqBusquedaAsignaciones r= new ReqBusquedaAsignaciones();
		r.setAsignacion("12312321");
		r.setDescripcion("122122");
		r.setIdParametro("2");
		r.setPaginacion(new PaginacionParametro());
		r.setParametro("121212");
		r.setSucursal("1212312312");
		r.getAsignacion();
		r.getDescripcion();
		r.getIdParametro();
		r.getPaginacion();
		r.getParametro();
		r.getSucursal();	
	}
}
