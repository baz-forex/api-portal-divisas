/**
 * 
 */
package com.baz.divisas.api.tef.spread.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baz.divisas.api.tef.spread.dto.Spread;
import com.baz.divisas.api.tef.spread.services.ISpreadsService;
import com.baz.divisas.general.dto.ResponseApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Spreads Controller
 * @author 813196
 *
 */
@RestController
@RequestMapping("/api/tef/spreads")
@Api(tags = "spreds")
@Validated
public class SpreadsController {
	
	@Autowired
	private ISpreadsService spreadsService;
	
	/**
	 * Metodo que devuelve el catalogo de Spreads
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping(value = "")
	@ApiOperation(value = "Devuelve catalogo de Spreads")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getSpreads() {

		ResponseApi resultado = spreadsService.consultaSpreads();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo que devuelve Spreads por Divisa
	 * 
	 * @param idDivisa - Id Divisa
	 * @return ResponseEntity
	 */
	@GetMapping(value = "/{idDivisa}")
	@ApiOperation(value = "Devuelve Spreads por Divisa")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getSpreadsDivisa(@PathVariable String idDivisa) {

		ResponseApi resultado = spreadsService.consultaSpreadsDivisa(idDivisa);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	
	/**
	 * Metodo que actualiza el catalogo de Spreads
	 * 
	 * @param spread - Spread
	 * @return ResponseEntity
	 */
	@PutMapping(value = "")
	@ApiOperation(value = "Actualiza Spreads por divisa")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> updateSpread(@RequestBody Spread spread) {

		ResponseApi resultado = spreadsService.actualizaSpreadDivisa(spread);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
}
