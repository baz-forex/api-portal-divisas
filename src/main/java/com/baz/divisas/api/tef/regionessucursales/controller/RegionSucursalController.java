/**
 * 
 */
package com.baz.divisas.api.tef.regionessucursales.controller;

import javax.validation.Valid;

import com.baz.divisas.api.tef.regionessucursales.dto.ReqRegionSucursales;
import com.baz.divisas.api.tef.regionessucursales.dto.ReqRegionDivisa;
import com.baz.divisas.api.tef.regionessucursales.dto.ReqRegionSucursalDelete;
import com.baz.divisas.api.tef.regionessucursales.services.IRegionSucursalService;
import com.baz.divisas.general.dto.ResponseApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author 1077023
 *
 */
@RestController
@RequestMapping("/api/tef/region-sucursal")
@Api(tags = "TEF/Region-Sucursal")
@Validated
public class RegionSucursalController {

	@Autowired
	private IRegionSucursalService regionSucursalService;

	/**
	 * Metodo que devuelve las regiones con sus sucursales asignadas
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping(value = "")
	@ApiOperation(value = "Devuelve las sucursales en las regiones")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getSucursalesRegiones() {

		ResponseApi resultado = regionSucursalService.consultaSucursalesRegiones();
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo que asigna sucursales a una region
	 * 
	 * @param request - RegionSucursales
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/registrar")
	@ApiOperation(value = "Asignar sucursales a una región")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> asignarSucursalesRegion(@Valid @RequestBody ReqRegionSucursales request) {

		ResponseApi resultado = regionSucursalService.asignarSucursalesRegion(request.getIdRegion(), request.getIdSucursal());
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo que elimina una sucursal de una region
	 * 
	 * @param request - ReqRegionSucursalDelete
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/eliminar-sucursal")
	@ApiOperation(value = "Eliminar una sucursal de una región")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> eliminarSucursalRegion(@Valid @RequestBody ReqRegionSucursalDelete request){

		ResponseApi resultado = regionSucursalService.eliminarSucursalRegion(request.getIdRegion(), 
																			 request.getIdSucursal());
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo que devuelve las sucursales asignadas de las regiones
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping(value = "/obtener-region-segmento-sucursal")
	@ApiOperation(value = "Devuelve Regiones con sucursales y Segmentos con sucursales")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getRegionesSegmentosSucursales() {

		ResponseApi resultado = regionSucursalService.consultaRegionesSegmentosSucursales();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo que devuelve las sucursales asignadas de las regiones
	 * 
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/tipos-cambio")
	@ApiOperation(value = "Devuelve Tipos de cambio de Regiones con sucursales")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> obtenerTCRegionesSucursales(@Valid @RequestBody ReqRegionDivisa request) {

		ResponseApi resultado = regionSucursalService.consultaTCRegionesSucursales(request.getIdRegion(), 
				 request.getIdDivisa());

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	//OLP
	
	/**
	 * Metodo que devuelve las regiones con sus sucursales asignadas
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping(value = "/olp")
	@ApiOperation(value = "Devuelve las sucursales en las regiones")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getSucursalesRegionesOlp() {

		ResponseApi resultado = regionSucursalService.consultaSucursalesRegionesOlp();
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo que asigna sucursales a una region
	 * 
	 * @param request - RegionSucursales
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/registrar/olp")
	@ApiOperation(value = "Asignar sucursales a una región")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> asignarSucursalesRegionOlp(@Valid @RequestBody ReqRegionSucursales request) {

		ResponseApi resultado = regionSucursalService.asignarSucursalesRegionOlp(request.getIdRegion(), request.getIdSucursal());
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo que elimina una sucursal de una region
	 * 
	 * @param request - ReqRegionSucursalDelete
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/eliminar-sucursal/olp")
	@ApiOperation(value = "Eliminar una sucursal de una región")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> eliminarSucursalRegionOlp(@Valid @RequestBody ReqRegionSucursalDelete request){

		ResponseApi resultado = regionSucursalService.eliminarSucursalRegionOlp(request.getIdRegion(), 
																			 request.getIdSucursal());
		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo que devuelve las sucursales asignadas de las regiones
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping(value = "/obtener-region-segmento-sucursal/olp")
	@ApiOperation(value = "Devuelve Regiones con sucursales y Segmentos con sucursales de OLP")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getRegionesSegmentosSucursalesOlp() {

		ResponseApi resultado = regionSucursalService.consultaRegionesSegmentosSucursalesOlp();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	
	/**
	 * Metodo que devuelve las sucursales asignadas de las regiones
	 * 
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/tipos-cambio/olp")
	@ApiOperation(value = "Devuelve Tipos de cambio de Regiones con sucursales")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> obtenerTCRegionesSucursalesOlp(@Valid @RequestBody ReqRegionDivisa request) {

		ResponseApi resultado = regionSucursalService.consultaTCRegionesSucursalesOlp(request.getIdRegion());

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
}
