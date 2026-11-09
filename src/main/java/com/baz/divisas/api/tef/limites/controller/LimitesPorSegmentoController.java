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
import com.baz.divisas.api.tef.limites.dto.LimitesPorSegmentoDto;
import com.baz.divisas.api.tef.limites.services.ILimitesPorSegmentoService;
import com.baz.divisas.general.dto.ResponseApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controlador para limites de region-segmentos.
 *
 * @author 164945 ogatica
 */
@RestController
@RequestMapping("/api/tef/limites/region-segmento")
@Api(tags = "TEF/Limites/Segmento")
@Validated
public class LimitesPorSegmentoController {

	/** The limites service. */
	@Autowired
	private ILimitesPorSegmentoService limitesService;

	/**
	 * Constructor de la clase.
	 */
	public LimitesPorSegmentoController() {
		super();
	}

	/**
	 * Metodo que devuelve los limite por segmento.
	 *
	 * @return ResponseEntity
	 */
	@GetMapping(value = "")
	@ApiOperation(value = "Devuelve los limite por segmento")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getLimitesSegmento() {

		ResponseApi resultado = limitesService.consultaLimitesSegmento();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de registro de limite del segmento.
	 *
	 * @param request - LimitesPorSegmentoDto
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/registrar")
	@ApiOperation(value = "Registrar un limite por region-segmento")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> registraLimiteSegmento(
			@Validated(LimitesOnUpsert.class) @RequestBody LimitesPorSegmentoDto request) {

		ResponseApi resultado = limitesService.creaLimiteSegmento(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de actualizacion de limite del segmento.
	 *
	 * @param request - LimitesPorSegmentoDto
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar")
	@ApiOperation(value = "Actualizar un limite por region-segmento")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaLimiteSegmento(
			@Validated(LimitesOnUpsert.class) @RequestBody LimitesPorSegmentoDto request) {

		ResponseApi resultado = limitesService.actualizaLimiteSegmento(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de actualizacion de estatus limite del segmento.
	 *
	 * @param request - LimitesPorSegmentoDto
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar-estatus")
	@ApiOperation(value = "Actualizar estatus de un limite por region-segmento")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaEstatusLimiteSegmento(
			@Validated(LimitesOnUpdateStatus.class) @RequestBody LimitesPorSegmentoDto request) {

		ResponseApi resultado = limitesService.actualizaEstatusLimiteSegmento(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	//OLP
	
	/**
	 * Metodo que devuelve los limite por segmento.
	 *
	 * @return ResponseEntity
	 */
	@GetMapping(value = "/olp")
	@ApiOperation(value = "Devuelve los limite por segmento")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getLimitesSegmentoOlp() {

		ResponseApi resultado = limitesService.consultaLimitesSegmentoOlp();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo de registro de limite del segmento.
	 *
	 * @param request - LimitesPorSegmentoDto
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/registrar/olp")
	@ApiOperation(value = "Registrar un limite por region-segmento")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> registraLimiteSegmentoOlp(
			@Validated(LimitesOnUpsert.class) @RequestBody LimitesPorSegmentoDto request) {

		ResponseApi resultado = limitesService.creaLimiteSegmentoOlp(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de actualizacion de limite del segmento.
	 *
	 * @param request - LimitesPorSegmentoDto
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar/olp")
	@ApiOperation(value = "Actualizar un limite por region-segmento")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaLimiteSegmentoOlp(
			@Validated(LimitesOnUpsert.class) @RequestBody LimitesPorSegmentoDto request) {

		ResponseApi resultado = limitesService.actualizaLimiteSegmentoOlp(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de actualizacion de estatus limite del segmento.
	 *
	 * @param request - LimitesPorSegmentoDto
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar-estatus/olp")
	@ApiOperation(value = "Actualizar estatus de un limite por region-segmento")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaEstatusLimiteSegmentoOlp(
			@Validated(LimitesOnUpdateStatus.class) @RequestBody LimitesPorSegmentoDto request) {

		ResponseApi resultado = limitesService.actualizaEstatusLimiteSegmentoOlp(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
}
