package com.baz.divisas.api.tef.sucursales.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.baz.divisas.api.tef.general.model.Limites;

class SucursalTest {

	@Test
	void testGettersSetters() {
		Sucursal sucursal = new Sucursal();

		sucursal.setId("0172");
		assertThat(sucursal.getId()).isEqualTo("0172");

		sucursal.setDescripcion("Sucursal Centro");
		assertThat(sucursal.getDescripcion()).isEqualTo("Sucursal Centro");

		sucursal.setSucursal(172);
		assertThat(sucursal.getSucursal()).isEqualTo(172);

		sucursal.setNomCC("CC Norte");
		assertThat(sucursal.getNomCC()).isEqualTo("CC Norte");

		sucursal.setTipoCanal("Canal A");
		assertThat(sucursal.getTipoCanal()).isEqualTo("Canal A");

		sucursal.setNomCanal("Canal Principal");
		assertThat(sucursal.getNomCanal()).isEqualTo("Canal Principal");

		sucursal.setNomPais("Mexico");
		assertThat(sucursal.getNomPais()).isEqualTo("Mexico");

		sucursal.setNomEstado("CDMX");
		assertThat(sucursal.getNomEstado()).isEqualTo("CDMX");

		sucursal.setIdEntidad(9);
		assertThat(sucursal.getIdEntidad()).isEqualTo(9);

		sucursal.setNomEntidad("Ciudad de Mexico");
		assertThat(sucursal.getNomEntidad()).isEqualTo("Ciudad de Mexico");

		sucursal.setNomMunicipio("Tlalpan");
		assertThat(sucursal.getNomMunicipio()).isEqualTo("Tlalpan");

		sucursal.setNomCalle("Insurgentes");
		assertThat(sucursal.getNomCalle()).isEqualTo("Insurgentes");

		sucursal.setNomColonia("Centro");
		assertThat(sucursal.getNomColonia()).isEqualTo("Centro");

		sucursal.setNomLocalidad("Localidad A");
		assertThat(sucursal.getNomLocalidad()).isEqualTo("Localidad A");

		sucursal.setIdEstatus(1);
		assertThat(sucursal.getIdEstatus()).isEqualTo(1);

		sucursal.setNomEstatus("Activo");
		assertThat(sucursal.getNomEstatus()).isEqualTo("Activo");

		sucursal.setNomTipoOperacion("Compra");
		assertThat(sucursal.getNomTipoOperacion()).isEqualTo("Compra");

		sucursal.setCodigoPostal("14000");
		assertThat(sucursal.getCodigoPostal()).isEqualTo("14000");

		sucursal.setLatitud("19.3262");
		assertThat(sucursal.getLatitud()).isEqualTo("19.3262");

		sucursal.setLongitud("-99.1671");
		assertThat(sucursal.getLongitud()).isEqualTo("-99.1671");

		sucursal.setAsignada(true);
		assertThat(sucursal.isAsignada()).isTrue();

		sucursal.setDescripcionRegion("Region Norte");
		assertThat(sucursal.getDescripcionRegion()).isEqualTo("Region Norte");

		List<Limites> limites = new ArrayList<>();
		sucursal.setLimites(limites);
		assertThat(sucursal.getLimites()).isNotNull();

		List<Divisas> divisas = new ArrayList<>();
		sucursal.setDivisas(divisas);
		assertThat(sucursal.getDivisas()).isNotNull();

		Divisas tcOlp = new Divisas();
		tcOlp.setId("OLP");
		sucursal.setTcOlp(tcOlp);
		assertThat(sucursal.getTcOlp()).isNotNull();
		assertThat(sucursal.getTcOlp().getId()).isEqualTo("OLP");

		sucursal.setFechaModificacion("2026-07-14");
		assertThat(sucursal.getFechaModificacion()).isEqualTo("2026-07-14");

		sucursal.setFechaUltimoCambio("2026-07-14 10:00:00");
		assertThat(sucursal.getFechaUltimoCambio()).isEqualTo("2026-07-14 10:00:00");

		sucursal.setRegionSucursalArchivo("Norte");
		assertThat(sucursal.getRegionSucursalArchivo()).isEqualTo("Norte");

		sucursal.setNombreSucursalArchivo("Torres Esmeralda");
		assertThat(sucursal.getNombreSucursalArchivo()).isEqualTo("Torres Esmeralda");

		sucursal.setTipoSucursalArchivo("Tipo A");
		assertThat(sucursal.getTipoSucursalArchivo()).isEqualTo("Tipo A");

		sucursal.setMunicipioSucursalArchivo("Tlalpan");
		assertThat(sucursal.getMunicipioSucursalArchivo()).isEqualTo("Tlalpan");

		sucursal.setEstadoSucursalArchivo("CDMX");
		assertThat(sucursal.getEstadoSucursalArchivo()).isEqualTo("CDMX");

		sucursal.setCalleSucursalArchivo("Insurgentes 123");
		assertThat(sucursal.getCalleSucursalArchivo()).isEqualTo("Insurgentes 123");

		sucursal.setNumeroSucursalArchivo("0172");
		assertThat(sucursal.getNumeroSucursalArchivo()).isEqualTo("0172");

		sucursal.setCpSucursalArchivo("14000");
		assertThat(sucursal.getCpSucursalArchivo()).isEqualTo("14000");

		sucursal.setNumeroRegionArchivo("5");
		assertThat(sucursal.getNumeroRegionArchivo()).isEqualTo("5");

		LocalDateTime fechaActualizacion = LocalDateTime.of(2026, 7, 14, 10, 30, 0);
		sucursal.setFechaActualizacion(fechaActualizacion);
		assertThat(sucursal.getFechaActualizacion()).isEqualTo(fechaActualizacion);

		sucursal.setIdNumeroEconomico("NE001");
		assertThat(sucursal.getIdNumeroEconomico()).isEqualTo("NE001");

		sucursal.setFechaUltimoCambioOLP("2026-07-15 08:00:00");
		assertThat(sucursal.getFechaUltimoCambioOLP()).isEqualTo("2026-07-15 08:00:00");
	}

	@Test
	void testConstructorVacio() {
		Sucursal sucursal = new Sucursal();
		assertThat(sucursal.getId()).isNull();
		assertThat(sucursal.getDescripcion()).isNull();
		assertThat(sucursal.getSucursal()).isNull();
		assertThat(sucursal.getFechaActualizacion()).isNull();
		assertThat(sucursal.getIdNumeroEconomico()).isNull();
		assertThat(sucursal.getFechaUltimoCambioOLP()).isNull();
	}
}
