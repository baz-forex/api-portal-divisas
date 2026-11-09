package com.baz.divisas.api.tef.competencias.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baz.divisas.api.tef.competencias.services.ICargaCompetenciasService;
import com.baz.divisas.general.dto.ResponseApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * The Class CargaCompetenciasController.
 */
@RestController
@RequestMapping("/api/tef/competencias")
@Api(tags = "TEF/Competencias")
@Validated
public class CargaCompetenciasController {

	@Autowired
	private ICargaCompetenciasService competenciasImpl;

	/**
	 * Metodo que devuelve las competencias.
	 *
	 * @param fecha the fecha
	 * @return ResponseEntity
	 */
	@GetMapping(value = "/{fecha}")
	@ApiOperation(value = "Consulta de competencias por fecha")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> consultaCompetenciasFecha(@PathVariable String fecha) {

		ResponseApi resultado = competenciasImpl.consultaCompetenciasFecha(fecha);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());

	}

	/**
	 * Consulta competencia agrupadas.
	 *
	 * @return the response entity
	 */
	@GetMapping(value = "/competenciaAgrupadas")
	@ApiOperation(value = "Consulta agrupado de competencias")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> consultaCompetenciasAgrupadas() {

		ResponseApi resultado = competenciasImpl.consultaCompetenciasAgrupadas();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());

	}

}
