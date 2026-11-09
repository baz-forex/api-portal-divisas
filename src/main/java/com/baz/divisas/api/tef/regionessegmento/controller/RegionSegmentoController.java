package com.baz.divisas.api.tef.regionessegmento.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baz.divisas.api.tef.regionessegmento.dto.ReqRegionSegmentos;
import com.baz.divisas.api.tef.regionessegmento.dto.ReqRegionSegmentoDelete;
import com.baz.divisas.api.tef.regionessegmento.services.IRegionSegmentoService;
import com.baz.divisas.general.dto.ResponseApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Clase RegionSegmentoController
 *
 */
@RestController
@RequestMapping("/api/tef/region-segmento")
@Api(tags = "TEF/Region-Segmento")
@Validated
public class RegionSegmentoController {

	@Autowired
	private IRegionSegmentoService regionSegmentoService;

	/**
	 * Metodo que devuelve las regiones con sus segmentos asignados
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping(value = "")
	@ApiOperation(value = "Devuelve los segmentos en las regiones")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getSegmentosRegiones() {

		ResponseApi resultado = regionSegmentoService.consultaSegmentosRegiones();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo que asigna segmentos a una region
	 * 
	 * @param request - ReqRegionSegmento
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/agregar-segmentos")
	@ApiOperation(value = "Asignar segmentos a región")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> asignarSegmentosRegion(@Valid @RequestBody ReqRegionSegmentos request) {

		ResponseApi resultado = regionSegmentoService.asignarSegmentosRegion(request.getIdRegion(), request.getIdSegmento());

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo que elimina un segmento de una region
	 * 
	 * @param request - ReqRegionSegmentoDelete
	 * @return ResponseEntity
	 */
	@DeleteMapping(value = "/eliminar")
	@ApiOperation(value = "Eliminar un segmento de una región")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> eliminaSegmentoRegion(@Valid @RequestBody ReqRegionSegmentoDelete request) {

		ResponseApi resultado = regionSegmentoService.eliminaSegmentoRegion(request.getIdRegion(), request.getIdSegmento());

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());

	}
	
	//OLP
	
	/**
	 * Metodo que devuelve las regiones con sus segmentos asignados
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping(value = "/olp")
	@ApiOperation(value = "Devuelve los segmentos en las regiones")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getSegmentosRegionesOlp() {

		ResponseApi resultado = regionSegmentoService.consultaSegmentosRegionesOlp();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo que asigna segmentos a una region
	 * 
	 * @param request - ReqRegionSegmento
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/agregar-segmentos/olp")
	@ApiOperation(value = "Asignar segmentos a región")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> asignarSegmentosRegionOlp(@Valid @RequestBody ReqRegionSegmentos request) {

		ResponseApi resultado = regionSegmentoService.asignarSegmentosRegionOlp(request.getIdRegion(), request.getIdSegmento());

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo que elimina un segmento de una region
	 * 
	 * @param request - ReqRegionSegmentoDelete
	 * @return ResponseEntity
	 */
	@DeleteMapping(value = "/eliminar/olp")
	@ApiOperation(value = "Eliminar un segmento de una región")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> eliminaSegmentoRegionOlp(@Valid @RequestBody ReqRegionSegmentoDelete request) {

		ResponseApi resultado = regionSegmentoService.eliminaSegmentoRegionOlp(request.getIdRegion(), request.getIdSegmento());

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());

	}
}
