package com.baz.divisas.api.tef.regionessegmentoestrategia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baz.divisas.api.tef.regionessegmentoestrategia.dto.RequestSegmentoEstrategia;
import com.baz.divisas.api.tef.regionessegmentoestrategia.service.IRegionesSegmentosEstrategia;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.model.Actualizar;
import com.baz.divisas.general.model.Registrar;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controlador para regiones segmentos estrategias
 * 
 */
@RestController
@RequestMapping("/api/tef/regiones-segmentos-estrategias")
@Api(tags = "TEF/Segmento-Estrategia")
@Validated
public class RegionesSegmentosEstrategiaController {
	
	@Autowired 
	private IRegionesSegmentosEstrategia reginesSegmentos;
	
	/**
	 * Constructor de la clase
	 */
	public RegionesSegmentosEstrategiaController() {
		super();
	}
	
	/**
	 * Metodo que devuelve los segmentos con sus estrategias
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping(value = "/regionSegmentoEstrategia")
	@ApiOperation(value = "Devuelve las regiones y estrategias")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getEstrategiasSegmentos() {

		ResponseApi resultado = reginesSegmentos.consultaEstrategiasSegmentos();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo que asigna una estrategia a un segmento
	 * 
	 * @param request - RequestSegmentoEstrategia
	 * 
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/registrar")
	@ApiOperation(value = "Registrar una estrategia segmento region")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> asignarEstrategiaSegmento( 
			@Validated(Registrar.class) @RequestBody RequestSegmentoEstrategia request) {

		ResponseApi resultado = reginesSegmentos.asignarEstrategiaSegmento(request);
		
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo de actualizacion del estatus de una estrategia en el segmento
	 * 
	 * @param request - RequestSegmentoEstrategia
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar")
	  @ApiOperation(value = "Actualizar una  Regiones-Segmento-Estrategia")
	  @ApiResponses(value = { 
			  @ApiResponse(code = 401, message = "Unauthorized"),
			  @ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> actualizarEstatusEstrategiasSegmento(
			@Validated(Actualizar.class) @RequestBody RequestSegmentoEstrategia request){
		
		ResponseApi resultado = reginesSegmentos.actualizarEstatusEstrategiaSegmento(request);
		
		return new ResponseEntity<>(resultado,resultado.getHttpStatus());
	}
	
}
