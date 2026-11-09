package com.baz.divisas.api.tef.sucursales.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class SucursalDivAuxTest {

	@Test
	void testGettersSetters() {
		SucursalDivAux sucursalDivAux = new SucursalDivAux();

		sucursalDivAux.setIdSucursal("0172");
		assertThat(sucursalDivAux.getIdSucursal()).isEqualTo("0172");

		sucursalDivAux.setFechaModificacion("2026-07-14");
		assertThat(sucursalDivAux.getFechaModificacion()).isEqualTo("2026-07-14");

		List<Divisa> divisas = new ArrayList<>();
		Divisa divisa = new Divisa();
		divisa.setIdDivisa("USD");
		divisas.add(divisa);
		sucursalDivAux.setDivisas(divisas);
		assertThat(sucursalDivAux.getDivisas()).isNotEmpty();
		assertThat(sucursalDivAux.getDivisas().get(0).getIdDivisa()).isEqualTo("USD");

		sucursalDivAux.setFechaUltimoCambioOLP("2026-07-15 10:30:00");
		assertThat(sucursalDivAux.getFechaUltimoCambioOLP()).isEqualTo("2026-07-15 10:30:00");
	}

	@Test
	void testConstructorVacio() {
		SucursalDivAux sucursalDivAux = new SucursalDivAux();
		assertThat(sucursalDivAux.getIdSucursal()).isNull();
		assertThat(sucursalDivAux.getFechaModificacion()).isNull();
		assertThat(sucursalDivAux.getDivisas()).isNull();
		assertThat(sucursalDivAux.getFechaUltimoCambioOLP()).isNull();
	}
}
