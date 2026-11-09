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

import com.baz.divisas.api.tef.general.LimitesOnUpsert;
import com.baz.divisas.api.tef.general.LimitesOnUpdateStatus;
import com.baz.divisas.api.tef.limites.dto.LimitesPorRegionDto;
import com.baz.divisas.api.tef.limites.services.ILimitesPorRegionService;
import com.baz.divisas.general.dto.ResponseApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controlador para limites de region
 * 
 * @author 164945 ogatica
 */
@RestController
@RequestMapping("/api/tef/limites-region")
@Api(tags = "TEF/Limites/Region")
@Validated
public class LimitesPorRegionController {

	@Autowired
	private ILimitesPorRegionService limitesService;

	/**
	 * Constructor de la clase
	 */
	public LimitesPorRegionController() {
		super();
	}

	/**
	 * Metodo que devuelve los limite por region
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping(value = "")
	@ApiOperation(value = "Devuelve los limite por region")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getLimitesRegion() {

		ResponseApi resultado = limitesService.consultaLimitesRegion();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de registro de limite por region
	 * 
	 * @param request - LimitesPorRegionDto
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/registrar")
	@ApiOperation(value = "Registrar un limite en la region")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> registraLimiteRegion(
			@Validated(LimitesOnUpsert.class) @RequestBody LimitesPorRegionDto request) {

		ResponseApi resultado = limitesService.creaLimiteRegion(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de actualizacion de limite por region
	 * 
	 * @param request - LimitesPorRegionDto
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar")
	@ApiOperation(value = "Actualizar un limite en la region")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaLimiteRegion(
			@Validated(LimitesOnUpsert.class) @RequestBody LimitesPorRegionDto request) {

		ResponseApi resultado = limitesService.actualizaLimiteRegion(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de actualizacion de estatus limite por region
	 * 
	 * @param request - LimitesPorRegionDto
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar-estatus")
	@ApiOperation(value = "Actualizar estatus de un limite por region")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaEstatusLimiteRegion(
			@Validated(LimitesOnUpdateStatus.class) @RequestBody LimitesPorRegionDto request) {

		ResponseApi resultado = limitesService.actualizaEstatusLimiteRegion(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	//OLP
	/**
	 * Metodo que devuelve los limite por region
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping(value = "/olp")
	@ApiOperation(value = "Devuelve los limite por region")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getLimitesRegionOlp() {

		ResponseApi resultado = limitesService.consultaLimitesRegionOlp();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de registro de limite por region
	 * 
	 * @param request - LimitesPorRegionDto
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/registrar/olp")
	@ApiOperation(value = "Registrar un limite en la region")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> registraLimiteRegionOlp(
			@Validated(LimitesOnUpsert.class) @RequestBody LimitesPorRegionDto request) {

		ResponseApi resultado = limitesService.creaLimiteRegionOlp(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de actualizacion de limite por region
	 * 
	 * @param request - LimitesPorRegionDto
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar/olp")
	@ApiOperation(value = "Actualizar un limite en la region")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaLimiteRegionOlp(
			@Validated(LimitesOnUpsert.class) @RequestBody LimitesPorRegionDto request) {

		ResponseApi resultado = limitesService.actualizaLimiteRegionOlp(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de actualizacion de estatus limite por region
	 * 
	 * @param request - LimitesPorRegionDto
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar-estatus/olp")
	@ApiOperation(value = "Actualizar estatus de un limite por region")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaEstatusLimiteRegionOlp(
			@Validated(LimitesOnUpdateStatus.class) @RequestBody LimitesPorRegionDto request) {

		ResponseApi resultado = limitesService.actualizaEstatusLimiteRegionOlp(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
}
