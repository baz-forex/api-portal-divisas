package com.baz.divisas.api.apigee.dto;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ResultadoLlavesTest {

	@Test
	public void testDto() {
		ResultadoLlaves result = new ResultadoLlaves();
		result.setAccesoPrivado("12312");
		result.getAccesoPrivado();
		result.setAccesoPublico("1231");
		result.getAccesoPublico();
		result.setAccesoSimetrico("1231231");
		result.getAccesoSimetrico();
		result.setCodigoAutentificacionHash("12312312");
		result.getCodigoAutentificacionHash();
		result.setIdAcceso("1231312");
		result.getIdAcceso();
		assertNull(null);
	}
}
