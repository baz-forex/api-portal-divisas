package com.baz.divisas.api.core.reporteria.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.baz.divisas.api.core.beneficios.model.CuponMasSalud;

class ReporteCuponesMasSaludXlsTest {

	@Test
	void testGeneraExcelListaVacia() {
		ReporteCuponesMasSaludXls reporte = new ReporteCuponesMasSaludXls();
		List<CuponMasSalud> cupones = new ArrayList<>();

		ByteArrayInputStream resultado = reporte.generaExcel(cupones);
		assertThat(resultado).isNotNull();
	}

	@Test
	void testGeneraExcelConDatos() {
		ReporteCuponesMasSaludXls reporte = new ReporteCuponesMasSaludXls();

		CuponMasSalud cupon1 = new CuponMasSalud();
		cupon1.setId("CUPON001");
		cupon1.setTipo("1");
		cupon1.setSucursal("0172");
		cupon1.setNombreCliente("Juan Perez");
		cupon1.setFechaEntrega("2026-07-14");
		cupon1.setHoraEntrega("10:30:00");
		cupon1.setMonto("500.00");
		cupon1.setDivisa("USD");

		CuponMasSalud cupon2 = new CuponMasSalud();
		cupon2.setId("CUPON002");
		cupon2.setTipo("2");
		cupon2.setSucursal("2244");
		cupon2.setNombreCliente("Maria Lopez");
		cupon2.setFechaEntrega("2026-07-15");
		cupon2.setHoraEntrega("11:00:00");
		cupon2.setMonto("300.00");
		cupon2.setDivisa("EUR");

		List<CuponMasSalud> cupones = Arrays.asList(cupon1, cupon2);

		ByteArrayInputStream resultado = reporte.generaExcel(cupones);
		assertThat(resultado).isNotNull();
	}

	@Test
	void testGeneraExcelConValoresNulos() {
		ReporteCuponesMasSaludXls reporte = new ReporteCuponesMasSaludXls();

		CuponMasSalud cupon = new CuponMasSalud();
		cupon.setId("CUPON003");
		cupon.setTipo("1");

		List<CuponMasSalud> cupones = Arrays.asList(cupon);

		ByteArrayInputStream resultado = reporte.generaExcel(cupones);
		assertThat(resultado).isNotNull();
	}
}
