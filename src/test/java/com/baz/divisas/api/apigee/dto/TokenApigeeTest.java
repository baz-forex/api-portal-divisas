package com.baz.divisas.api.apigee.dto;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TokenApigeeTest {

	@Test
	public void testDto() {
		TokenApigee token = new TokenApigee();
		token.setAccess_token("123113312");
		token.getAccess_token();
		token.setCodigo("1");
		token.getCodigo();
		token.setDetalles(new ArrayList<String>());
		token.getDetalles();
		token.setFolio("1231312");
		token.getFolio();
		token.setMensaje("Exito");
		token.getMensaje();
		token.toString();
	}
}
