package com.baz.divisas.api.tef.dashboard.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baz.divisas.api.tef.dashboard.dto.RequestRegionTC;
import com.baz.divisas.api.tef.dashboard.services.IDashboardService;
import com.baz.divisas.general.dto.ResponseApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * DashboardController
 *
 */
@RestController
@RequestMapping("/api/tef/dashboard")
@Api(tags = "TEF/Dashboard")
@Validated
public class DashboardController {

	@Autowired
	private IDashboardService dashboardService;

	/**
	 * Constructor de la clase
	 */
	public DashboardController() {
		super();
	}

	/**
	 * Metodo de consulta de Regiones
	 *
	 * @param idDivisa - Id de la Divisa
	 * @return ResponseEntity
	 */
	@GetMapping(value = "/regionTC/{idDivisa}")
	@ApiOperation(value = "Devuelve regiones Segmentos")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getRegiones(@Valid @PathVariable String idDivisa) {

		ResponseApi resultado = dashboardService.consultaRegiones(idDivisa);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de consulta de Segmentos de la Region
	 *
	 * @param idRegion - Id de la Region
	 * @param idDivisa - Id de la Divisa
	 * @return ResponseEntity
	 */
	@GetMapping(value = "/regionSegmentoTC/{idRegion}/{idDivisa}")
	@ApiOperation(value = "Actualizar una region")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getSegmentosRegion(@Valid @PathVariable  String idRegion, 
												   @Valid @PathVariable  String idDivisa) {

		ResponseApi resultado = dashboardService.consultaSegmentosRegion(idRegion,idDivisa);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de actualizacion de Tipo de Cambio Spot de la Region
	 * 
	 * @param request - RequestRegionTC
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar-region-tc")
	@ApiOperation(value = "Actualiza los tipos de cambios de regiones")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizarTCRegion( @Valid @RequestBody RequestRegionTC request) {

		ResponseApi resultado = dashboardService.actualizarTCRegion(request);
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de actualizacion de Tipo de Cambio Spot del Segmento
	 * 
	 * @param request - RequestRegionTC
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar-segmento-tc")
	@ApiOperation(value = "Actualiza los tipos de cambios del segmento")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizarTCSegmento( @Valid @RequestBody RequestRegionTC request) {

		ResponseApi resultado = dashboardService.actualizarTCSegmento(request);
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

}
