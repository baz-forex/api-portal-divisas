package com.baz.divisas.api.core.clientes.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class RegistrosTest {

	@Test
	public void testDto() {
		Registros c = new Registros();
		c.setRegistrosExitosos(1);
		assertThat(c.getRegistrosExitosos()).isEqualTo(1);

		List<String> erroneos = Arrays.asList("error1", "error2");
		c.setRegistrosErroneos(erroneos);
		assertThat(c.getRegistrosErroneos()).hasSize(2);
		assertThat(c.getRegistrosErroneos().get(0)).isEqualTo("error1");
		c.toString();
	}

	@Test
	public void testConstructorConParametros() {
		List<String> erroneos = Arrays.asList("registro1", "registro2", "registro3");
		Registros c = new Registros(5, erroneos);
		assertThat(c.getRegistrosExitosos()).isEqualTo(5);
		assertThat(c.getRegistrosErroneos()).hasSize(3);
	}
}
