package com.baz.divisas.api.tef.limites.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baz.divisas.api.tef.general.LimitesOnUpdateStatus;
import com.baz.divisas.api.tef.general.LimitesOnUpsert;
import com.baz.divisas.api.tef.limites.dto.LimitesPorSucursalDto;
import com.baz.divisas.api.tef.limites.services.ILimitesPorSucursalService;
import com.baz.divisas.general.dto.ResponseApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controlador para limites de sucursal.
 *
 * @author 164945 ogatica
 */
@RestController
@RequestMapping("/api/tef/limites-sucursal")
@Api(tags = "TEF/Limites/Sucursal")
@Validated
public class LimitesPorSucursalController {

	/** The limites service. */
	@Autowired
	private ILimitesPorSucursalService limitesService;

	/**
	 * Constructor de la clase.
	 */
	public LimitesPorSucursalController() {
		super();
	}
	
	/**
	 * Metodo que devuelve los limite por sucursal.
	 *
	 * @return ResponseEntity
	 */
	@GetMapping(value = "")
	@ApiOperation(value = "Devuelve los limite por sucursal")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getLimitesSucursal() {

		ResponseApi resultado = limitesService.consultaLimitesSucursal();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de registro de limite por sucursal.
	 *
	 * @param request - LimitesPorSucursalDto
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/registrar")
	@ApiOperation(value = "Registrar un limite por sucursal")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> registraLimiteSucursal(
			@Validated(LimitesOnUpsert.class) @RequestBody LimitesPorSucursalDto request) {

		ResponseApi resultado = limitesService.creaLimiteSucursal(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de actualizacion de limite por sucursal.
	 *
	 * @param request - LimitesPorSucursalDto
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar")
	@ApiOperation(value = "Actualizar un limite por sucursal")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaLimiteSucursal(
			@Validated(LimitesOnUpsert.class) @RequestBody LimitesPorSucursalDto request) {

		ResponseApi resultado = limitesService.actualizaLimiteSucursal(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de actualizacion de estatus limite por sucursal.
	 *
	 * @param request - LimitesPorSucursalDto
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar-estatus")
	@ApiOperation(value = "Actualizar estatus de un limite por sucursal")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaEstatusLimiteSucursal(
			@Validated(LimitesOnUpdateStatus.class) @RequestBody LimitesPorSucursalDto request) {

		ResponseApi resultado = limitesService.actualizaEstatusLimiteSucursal(request);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	
	//OLP
	
	/**
	 * Metodo que devuelve los limite por sucursal.
	 *
	 * @return ResponseEntity
	 */
	@GetMapping(value = "/olp")
	@ApiOperation(value = "Devuelve los limite por sucursal")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getLimitesSucursalOlp() {
		ResponseApi resultado = limitesService.consultaLimitesSucursalOlp();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	//Limites masivos
	
	/**
	 * Carga archivo Limites.
	 *
	 * @param file the file
	 * @return the response entity
	 */
	@PostMapping(value = "/actualizacion-masiva")
	@ApiOperation(value = "Carga archivo para actualizacion masiva de limites de operación de sucursales")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> cargaArchivoLimites(@RequestParam MultipartFile file) {

		ResponseApi resultado = limitesService.cargaArchivoLimites(file);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());

	}
	

	/**
	 * Descarga limites.
	 *
	 * @return the response entity
	 */
	@GetMapping(value = "/descarga")
	@ApiOperation(value = "Descarga los limites de las sucursales de divisas")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<Resource> descargaLimites() {
		
		InputStreamResource file = new InputStreamResource(limitesService.descargaLimitesSucursales("USD"));
				
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=LayoutLimitesSucursales.xls")
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}
	
	/**
	 * Descarga limites OLP.
	 *
	 * @param divisa the divisa
	 * @return the response entity
	 */
	@GetMapping(value = "/descarga/{divisa}")
	@ApiOperation(value = "Descarga los limites de las sucursales de divisas")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<Resource> descargaLimitesOLP(@PathVariable String divisa) {
		
		InputStreamResource file = new InputStreamResource(limitesService.descargaLimitesSucursales(divisa));
				
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=LayoutLimitesSucursalesOLP.xls")
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}
}
