package com.baz.divisas.api.tef.limites.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baz.divisas.api.tef.general.LimitesOnUpdateStatus;
import com.baz.divisas.api.tef.general.LimitesOnUpsert;
import com.baz.divisas.api.tef.limites.dto.LimitesPorClienteDto;
import com.baz.divisas.api.tef.limites.services.ILimitesPorClienteService;
import com.baz.divisas.general.dto.ResponseApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controlador para limites de cliente
 * 
 * @author 164945 ogatica
 */
@RestController
@RequestMapping("/api/tef/limites-cliente")
@Api(tags = "TEF/Limites/Cliente")
@Validated
public class LimitesPorClienteController {

	@Autowired
	private ILimitesPorClienteService limitesService;

	/**
	 * Constructor de la clase
	 */
	public LimitesPorClienteController() {
		super();
	}

	/**
	 * Metodo que devuelve los limite por cliente
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping(value = "")
	@ApiOperation(value = "Devuelve los limite por cliente")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getLimitesCliente() {

		ResponseApi resultado = limitesService.consultaLimitesCliente();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de registro de limite por cliente
	 * 
	 * @param request - LimitesPorClienteDto
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/registrar")
	@ApiOperation(value = "Registrar un limite por cliente")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> registraLimiteCliente(
			@Validated(LimitesOnUpsert.class) @RequestBody LimitesPorClienteDto request) {
		
		ResponseApi resultado = limitesService.creaLimiteCliente(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de actualizacion de limite por cliente
	 * 
	 * @param request - LimitesPorClienteDto
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar")
	@ApiOperation(value = "Actualizar un limite por cliente")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaLimiteCliente(
			@Validated(LimitesOnUpdateStatus.class) @RequestBody LimitesPorClienteDto request) {

		ResponseApi resultado = limitesService.actualizaLimiteCliente(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de actualizacion de estatus limite por cliente
	 * 
	 * @param id - limite por cliente
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar-estatus/{id}")
	@ApiOperation(value = "Actualizar estatus de un limite por cliente")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaEstatusLimiteCliente(@PathVariable String id) {
		
		ResponseApi resultado = limitesService.actualizaEstatusLimiteCliente(id);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	
	/**
	 * Metodo que devuelve los limite por cliente
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping(value = "/olp")
	@ApiOperation(value = "Devuelve los limite por cliente")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getLimitesClienteOlp() {

		ResponseApi resultado = limitesService.consultaLimitesClienteOlp();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
}
