package com.baz.divisas.api.core.beneficios.model;

import org.junit.jupiter.api.Test;

public class CuponMasSaludTest {

	@Test
	public void init() {
		CuponMasSalud cupon = new CuponMasSalud();
		cupon.setId("DIV2600001");
		cupon.setTipo("1");
		cupon.setEstado("entregado");
		cupon.setDivisa("USD");
		cupon.setFechaEntrega("2026-07-15");
		cupon.setHoraEntrega("12:18:09");
		cupon.setIdCliente("123456");
		cupon.setMonto("249");
		cupon.setNombreCliente("Juan Perez Lopez");
		cupon.setSucursal("2244");
		
		cupon.getId();
		cupon.getTipo();
		cupon.getEstado();
		cupon.getDivisa();
		cupon.getFechaEntrega();
		cupon.getHoraEntrega();
		cupon.getIdCliente();
		cupon.getMonto();
		cupon.getNombreCliente();
		cupon.getSucursal();
	}
}
