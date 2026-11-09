package com.baz.divisas.api.tef.sucursales.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baz.divisas.api.tef.sucursales.dto.ReqActSucursal;
import com.baz.divisas.api.tef.sucursales.service.ISucursales;
import com.baz.divisas.general.dto.ResponseApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controlador Sucursales.
 *
 * @author 1023825
 */
@RestController
@RequestMapping("api/tef/tablawscecostos")
@Api(tags = "TEF/Sucursales")
@Validated
public class SucursalesController {

	/** The sucursales service. */
	@Autowired
	private ISucursales sucursalesService;

	/**
	 * Metodo de consulta de sucursales Asignadas/Desasignadas.
	 *
	 * @param asignadas - true/false
	 * @return ResponseApi
	 */
	
	/**
	 * Gets the sucursales.
	 *
	 * @param asignadas the asignadas
	 * @return the sucursales
	 */
	@GetMapping(value = "sucursales/{asignadas}")
	@ApiOperation(value = "Obtiene sucursales Asignadas/Desasignadas")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> getSucursales(@PathVariable boolean asignadas) {

		ResponseApi resultado = sucursalesService.getSucursalesImpl(asignadas);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	
	/**
	 * Gets the TC sucursales.
	 *
	 * @return the TC sucursales
	 */
	@GetMapping(value = "sucursales/tipo-cambio")
	@ApiOperation(value = "Obtiene TC de sucursales")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> getTCSucursales() {

		ResponseApi resultado = sucursalesService.consultaTCSucursales();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	

	/**
	 * Actualizar TC sucursal.
	 *
	 * @param request the request
	 * @return the response entity
	 */
	@PostMapping(value = "sucursales/actualiza-sucursal")
	@ApiOperation(value = "Actualiza Tipos de cambio en la sucursal")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizarTCSucursal(@Valid @RequestBody ReqActSucursal request) {

		ResponseApi resultado = sucursalesService.actualizaTCSucursal(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	
	/**
	 * Carga archivo TC.
	 *
	 * @param file the file
	 * @return the response entity
	 */
	@PostMapping(value = "sucursales/actualizacion-masiva")
	@ApiOperation(value = "Carga archivo para actualizacion masiva de tipos de cambios sucursales")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> cargaArchivoTC(@RequestParam MultipartFile file) {

		ResponseApi resultado = sucursalesService.cargaArchivoTC(file);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());

	}
	
	
	/**
	 * Metodo de Descarga de Operaciones
	 * 
	 * @param request - ReqReporteOperaciones
	 * 
	 * @return Reporte de Operaciones
	 */
	@GetMapping(value = "sucursales/tipo-cambio/descarga")
	@ApiOperation(value = "Descarga los TC de las sucursales de divisas")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<Resource> descargaSucursales() {
		
		InputStreamResource file = new InputStreamResource(sucursalesService.descargaTCSucursales());
				
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=LayoutTCSucursales.xls")
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}
	
	
	
	/**
	 * Gets the TC sucursales OLP.
	 *
	 * @return the TC sucursales
	 */
	@GetMapping(value = "sucursales/tipo-cambio/olp")
	@ApiOperation(value = "Obtiene TC de sucursales")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> getTCSucursalesOlp() {

		ResponseApi resultado = sucursalesService.consultaTCSucursalesOlp();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	
	/**
	 * Actualizar TC sucursal OLP.
	 *
	 * @param request the request
	 * @return the response entity
	 */
	@PostMapping(value = "sucursales/actualiza-sucursal/olp")
	@ApiOperation(value = "Actualiza Tipos de cambio en la sucursal")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizarTCSucursalOlp(@Valid @RequestBody ReqActSucursal request) {

		ResponseApi resultado = sucursalesService.actualizaTCSucursalOlp(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Carga archivo TC OLP.
	 *
	 * @param file the file
	 * @return the response entity
	 */
	@PostMapping(value = "sucursales/actualizacion-masiva/olp")
	@ApiOperation(value = "Carga archivo para actualizacion masiva de tipos de cambios sucursales")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> cargaArchivoTCOlp(@RequestParam MultipartFile file) {

		ResponseApi resultado = sucursalesService.cargaArchivoTCOlp(file);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());

	}
	
	
	/**
	 * Metodo de Descarga de sucursales OLP
	 * 
	 * @param request - ReqReporteOperaciones
	 * 
	 * @return Reporte de Operaciones
	 */
	@GetMapping(value = "sucursales/tipo-cambio/descarga/olp")
	@ApiOperation(value = "Descarga los TC de las sucursales de divisas")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<Resource> descargaSucursalesOlp() {
		
		InputStreamResource file = new InputStreamResource(sucursalesService.descargaTCSucursalesOlp());
				
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=LayoutTCSucursalesOLP.xls")
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}
}
