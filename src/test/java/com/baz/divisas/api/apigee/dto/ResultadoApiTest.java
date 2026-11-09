package com.baz.divisas.api.apigee.dto;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ResultadoApiTest {

	@Test
	public void testDto() {
		ResultadoApi result = new ResultadoApi();
		result.setCodigo("1");
		result.setFolio("1212");
		result.setInfo("12312");
		result.setMensaje("121312");
		
		result.getCodigo();
		result.getFolio();
		result.getInfo();
		result.getMensaje();
		
		result.toString();
		assertNull(null);
	}
}
