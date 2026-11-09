package com.baz.divisas.api.core.beneficios.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CuponModelTest {

	@Test
	void testCupon() {
		Cupon cupon = new Cupon();
		cupon.setId("ABC123");
		assertThat(cupon.getId()).isEqualTo("ABC123");
	}

	@Test
	void testCuponCRM() {
		CuponCRM cupon = new CuponCRM();
		cupon.setId("ABCDE");
		cupon.setIdCliente("12345");
		cupon.setFechaInicio("2026-01-01");
		cupon.setFechaFin("2026-12-31");

		assertThat(cupon.getId()).isEqualTo("ABCDE");
		assertThat(cupon.getIdCliente()).isEqualTo("12345");
		assertThat(cupon.getFechaInicio()).isEqualTo("2026-01-01");
		assertThat(cupon.getFechaFin()).isEqualTo("2026-12-31");
	}

	@Test
	void testCuponAlianzas() {
		CuponAlianzas cupon = new CuponAlianzas();
		cupon.setId("FGHIJ");
		cupon.setAlianza("ALIANZA1");
		cupon.setFechaVigencia("2026-12-31");

		assertThat(cupon.getId()).isEqualTo("FGHIJ");
		assertThat(cupon.getAlianza()).isEqualTo("ALIANZA1");
		assertThat(cupon.getFechaVigencia()).isEqualTo("2026-12-31");
	}

	@Test
	void testCuponMasSalud() {
		CuponMasSalud cupon = new CuponMasSalud();
		cupon.setId("CUPON1");
		cupon.setTipo("1");
		cupon.setEstado("disponible");
		cupon.setDivisa("USD");
		cupon.setFechaEntrega("2026-07-14");
		cupon.setHoraEntrega("10:30:00");
		cupon.setIdCliente("CLI001");
		cupon.setMonto("500.00");
		cupon.setNombreCliente("Juan Perez");
		cupon.setSucursal("0172");

		assertThat(cupon.getId()).isEqualTo("CUPON1");
		assertThat(cupon.getTipo()).isEqualTo("1");
		assertThat(cupon.getEstado()).isEqualTo("disponible");
		assertThat(cupon.getDivisa()).isEqualTo("USD");
		assertThat(cupon.getFechaEntrega()).isEqualTo("2026-07-14");
		assertThat(cupon.getHoraEntrega()).isEqualTo("10:30:00");
		assertThat(cupon.getIdCliente()).isEqualTo("CLI001");
		assertThat(cupon.getMonto()).isEqualTo("500.00");
		assertThat(cupon.getNombreCliente()).isEqualTo("Juan Perez");
		assertThat(cupon.getSucursal()).isEqualTo("0172");
	}

	@Test
	void testCuponMasSaludEstadoDefault() {
		CuponMasSalud cupon = new CuponMasSalud();
		assertThat(cupon.getEstado()).isEqualTo("disponible");
	}
}
