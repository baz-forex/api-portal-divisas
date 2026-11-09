package com.baz.divisas.api.core.sucursales.dto;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DtoTest {

	@Test
	void testDto() {
		RespPaginadoRespuestas respPaginadoRespuestas = new RespPaginadoRespuestas();
		respPaginadoRespuestas.getTotalRegistros();
		
		RespConsultaSucursales respConsultaSucursales = new RespConsultaSucursales();
		respConsultaSucursales.getElementosPagina();
		respConsultaSucursales.getPaginaActual();
		respConsultaSucursales.getPaginas();
		respConsultaSucursales.getSucursales();
		
		RespConsultaRespuestas respConsultaRespuestas = new RespConsultaRespuestas();
		respConsultaRespuestas.getElementosPagina();
		respConsultaRespuestas.getPaginaActual();
		respConsultaRespuestas.getPaginas();
		respConsultaRespuestas.getRespuestas();
		
		RespConsultaPreguntas respConsultaPreguntas = new RespConsultaPreguntas();
		respConsultaPreguntas.getPreguntas();
		respConsultaPreguntas.getTitulo();
		
		RespConsultaPregRespSucursal respConsultaPregRespSucursal = new RespConsultaPregRespSucursal();
		respConsultaPregRespSucursal.getPreguntas();
		respConsultaPregRespSucursal.getRespuestas();
		
		
		assertNull(null);
	}
}
