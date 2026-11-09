/**
 * 
 */
package com.baz.divisas.api.tef.regionsegmentosucursal.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baz.divisas.api.tef.regionsegmentosucursal.dto.ReqSegmentoDivisa;
import com.baz.divisas.api.tef.regionsegmentosucursal.dto.ReqSegmentoSucursal;
import com.baz.divisas.api.tef.regionsegmentosucursal.dto.ReqSegmentoSucursalDelete;
import com.baz.divisas.api.tef.regionsegmentosucursal.services.IRegionSegmentoSucursalService;
import com.baz.divisas.general.dto.ResponseApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author 1077023
 *
 */
@RestController
@RequestMapping("/api/tef/region-sucursal-segmento")
@Api(tags = "TEF/Segmento-Sucursal")
@Validated
public class RegionSegmentoSucursalController {
	
	@Autowired
	private IRegionSegmentoSucursalService regionSegmentoSucursalService;
	
	/**
	 * Metodo que devuelve los segmentos con sus sucursales asignadas
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping(value = "")
	@ApiOperation(value = "Devuelve las sucursales en los segmentos")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getSucursalesSegmentos() {

		ResponseApi resultado = regionSegmentoSucursalService.consultaSucursalesSegmento();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo que asigna una sucursal a un segmento
	 * 
	 * @param request - ReqSegmentoSucursal
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/registrar")
	@ApiOperation(value = "Asignar sucursales a un segmento")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> asignarSucursalesSegmento(@Valid @RequestBody ReqSegmentoSucursal request) {
		ResponseApi resultado = regionSegmentoSucursalService.asignarSucursalesSegmento(request.getIdSegmento(),
				request.getSucursales());

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	
	/**
	 * Metodo que elimina una sucursal de un segmento
	 * 
	 * @param request - ReqSegmentoSucursalDelete
	 * @return ResponseEntity
	 */
	@DeleteMapping(value = "/desasigna-sucursal")
	@ApiOperation(value = "Eliminar una sucursal de un segmento")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> eliminaSucursalSegmento(@Valid @RequestBody ReqSegmentoSucursalDelete request) {

		ResponseApi resultado = regionSegmentoSucursalService.eliminarSucursalesSegmento(request.getIdSegmento(), 
				request.getIdSucursal());

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());

	}
	
	
	/**
	 * Metodo que devuelve las sucursales asignadas de las regiones
	 * 
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/tipos-cambio")
	@ApiOperation(value = "Devuelve Tipos de cambio de Segmentos con sucursales")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> obtenerTCSegmentoSucursales(@Valid @RequestBody ReqSegmentoDivisa request) {

		ResponseApi resultado = regionSegmentoSucursalService.consultaTCSegmentoSucursales(request.getIdSegmento(), 
				 request.getIdDivisa());

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	
	//OLP
	
	/**
	 * Metodo que devuelve los segmentos con sus sucursales asignadas
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping(value = "/olp")
	@ApiOperation(value = "Devuelve las sucursales en los segmentos")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getSucursalesSegmentosOlp() {

		ResponseApi resultado = regionSegmentoSucursalService.consultaSucursalesSegmentoOlp();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo que asigna una sucursal a un segmento
	 * 
	 * @param request - ReqSegmentoSucursal
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/registrar/olp")
	@ApiOperation(value = "Asignar sucursales a un segmento")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> asignarSucursalesSegmentoOlp(@Valid @RequestBody ReqSegmentoSucursal request) {
		ResponseApi resultado = regionSegmentoSucursalService.asignarSucursalesSegmentoOlp(request.getIdSegmento(),
				request.getSucursales());

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	
	/**
	 * Metodo que elimina una sucursal de un segmento
	 * 
	 * @param request - ReqSegmentoSucursalDelete
	 * @return ResponseEntity
	 */
	@DeleteMapping(value = "/desasigna-sucursal/olp")
	@ApiOperation(value = "Eliminar una sucursal de un segmento")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> eliminaSucursalSegmentoOlp(@Valid @RequestBody ReqSegmentoSucursalDelete request) {

		ResponseApi resultado = regionSegmentoSucursalService.eliminarSucursalesSegmentoOlp(request.getIdSegmento(), 
				request.getIdSucursal());

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());

	}
	
	/**
	 * Metodo que devuelve las sucursales asignadas de las regiones
	 * 
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/tipos-cambio/olp")
	@ApiOperation(value = "Devuelve Tipos de cambio de Segmentos con sucursales")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> obtenerTCSegmentoSucursalesOLP(@Valid @RequestBody ReqSegmentoDivisa request) {

		ResponseApi resultado = regionSegmentoSucursalService.consultaTCSegmentoSucursalesOlp(request.getIdSegmento());

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
}
