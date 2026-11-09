package com.baz.divisas.api.tef.limites.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baz.divisas.api.tef.general.LimitesOnUpdateStatus;
import com.baz.divisas.api.tef.general.LimitesOnUpsert;
import com.baz.divisas.api.tef.general.model.Limites;
import com.baz.divisas.api.tef.limites.services.ILimitesPorDivisaService;
import com.baz.divisas.general.dto.ResponseApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controlador para limites de divisas
 * 
 * @author 164945 ogatica
 */
@RestController
@RequestMapping("/api/tef/limites-divisa")
@Api(tags = "TEF/Limites/Divisas")
@Validated
public class LimitesPorDivisaController {

	@Autowired
	private ILimitesPorDivisaService limitesService;

	/**
	 * Constructor de la clase
	 */
	public LimitesPorDivisaController() {
		super();
	}

	/**
	 * Metodo que devuelve los limite por divisa
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping(value = "")
	@ApiOperation(value = "Devuelve los limite por divisa")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getLimitesDivisa() {

		ResponseApi resultado = limitesService.consultaLimitesDivisa();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de registro de limite por divisa
	 * 
	 * @param request - Limites
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/registrar")
	@ApiOperation(value = "Registrar un limite por divisa")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> registraLimiteDivisa(
			@Validated(LimitesOnUpsert.class) @RequestBody Limites request) {

		ResponseApi resultado = limitesService.creaLimiteDivisa(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de actualizacion de limite por divisa
	 * 
	 * @param request - Limites
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar")
	@ApiOperation(value = "Actualizar un limite por divisa")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaLimiteDivisa(
			@Validated(LimitesOnUpsert.class) @RequestBody Limites request) {

		ResponseApi resultado = limitesService.actualizaLimiteDivisa(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de actualizacion de estatus limite por divisa
	 * 
	 * @param request - Limites
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar-estatus")
	@ApiOperation(value = "Actualizar estatus de un limite por divisa")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaEstatusLimiteDivisa(
			@Validated(LimitesOnUpdateStatus.class) @RequestBody Limites request) {

		ResponseApi resultado = limitesService.actualizaEstatusLimiteDivisa(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	
	/**
	 * Metodo que devuelve los limite por divisa
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping(value = "/olp")
	@ApiOperation(value = "Devuelve los limite por divisa")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getLimitesDivisaOLP() {

		ResponseApi resultado = limitesService.consultaLimitesDivisaOLP();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
}
