package com.baz.divisas.api.tef.regionesestrategias.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baz.divisas.api.tef.regionesestrategias.dto.RequestEstrategia;
import com.baz.divisas.api.tef.regionesestrategias.service.IRegionesEstrategias;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.model.Actualizar;
import com.baz.divisas.general.model.Registrar;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controlador para regiones estrategias
 * 
 */
@RestController
@RequestMapping("/api/tef/regiones-estrategias")
@Api(tags = "TEF/Region-Estrategia")
@Validated
public class RegionesEstrategiasController {

	@Autowired
	private IRegionesEstrategias regionesEstrategiasService;

	/**
	 * Constructor de la clase
	 */
	public RegionesEstrategiasController() {
		super();

	}

	/**
	 * Metodo que devuelve las regiones con sus estrategias
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping(value = "/regionEstrategia")
	@ApiOperation(value = "Devuelve las estrategias en las regiones")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getEstrategiasRegiones() {

		ResponseApi resultado = regionesEstrategiasService.consultaEstrategiasRegiones();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo que asigna una estrategia a una region
	 * 
	 * @param request - RequestEstrategia
	 * 
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/registrar")
	@ApiOperation(value = "Asignar estrategias a una región")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> asignarEstrategiaRegion(@Valid @Validated(Registrar.class) 
																   @RequestBody RequestEstrategia request) {

		ResponseApi resultado = regionesEstrategiasService.asignarEstrategiaRegion(request);
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de actualizacion del estatus de una estrategia en la region
	 * 
	 * @param request - RequestEstrategia
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar-estatus")
	@ApiOperation(value = "Actualiza el estatus de una estrategia en una región")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi>  actualizarEstatusEstrategiasRegion(@Valid @Validated(Actualizar.class) 
																			 @RequestBody RequestEstrategia request) {

		ResponseApi resultado = regionesEstrategiasService.actualizarEstatusEstrategiaRegion(request);
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
}
