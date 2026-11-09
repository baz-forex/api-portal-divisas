package com.baz.divisas.api.tef.regiones.controller;

import javax.validation.Valid;

import com.baz.divisas.api.tef.regiones.model.Region;
import com.baz.divisas.api.tef.regiones.services.IRegionesService;
import com.baz.divisas.general.dto.ResponseApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
 * Controlador para Regiones.
 *
 * @author 164945 ogatica
 */
@RestController
@RequestMapping("/api/tef/regiones")
@Api(tags = "TEF/Regiones")
@Validated
public class RegionesController {

	/** The regiones service. */
	@Autowired
	private IRegionesService regionesService;

	/**
	 * Constructor de la clase.
	 */
	public RegionesController() {
		super();
	}

	/**
	 * Metodo que devuelve todas las regiones.
	 *
	 * @return ResponseEntity
	 */
	@GetMapping(value = "")
	@ApiOperation(value = "Devuelve todas las regiones")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getRegiones() {

		ResponseApi resultado = regionesService.consultaRegiones();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo de registro de una region.
	 *
	 * @param region - Region
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/registrar")
	@ApiOperation(value = "Registrar una región")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> registraRegion(@Valid @RequestBody Region region) {

		ResponseApi resultado = regionesService.creaRegion(region.getDescripcion());

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de actualizacion de la descripcion de la region.
	 *
	 * @param region - Region
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar")
	@ApiOperation(value = "Actualizar la descripción de la región")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaDescripcionRegion(@Valid @RequestBody Region region) {

		ResponseApi resultado = regionesService.actualizaDescripcionRegion(region.getId(), region.getDescripcion());

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de actualizacion de estatus de la region.
	 *
	 * @param id - Id de la region
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar-estatus/{id}")
	@ApiOperation(value = "Actualizar el estatus de una región")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaEstatusRegion(@PathVariable String id) {

		ResponseApi resultado = regionesService.actualizaEstatusRegion(id);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de eliminacion de una region.
	 *
	 * @param id - region
	 * @return ResponseEntity
	 */
	@DeleteMapping(value = "/eliminar/{id}")
	@ApiOperation(value = "Eliminar una región")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> eliminaRegion(@PathVariable String id) {

		ResponseApi resultado = regionesService.eliminaRegion(id);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo de actualizacion de estatus de la Divisa en la Region.
	 *
	 * @param idRegion - Id de la region
	 * @param idDivisa - Id de la divisa
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar-estatus/{idDivisa}/{idRegion}")
	@ApiOperation(value = "Actualizar estatus de la divisa en la región")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaEstatusRegionDivisa(@PathVariable String idRegion, 
																	@PathVariable String idDivisa) {

		ResponseApi resultado = regionesService.actualizaEstatusDivisaRegion(idRegion, idDivisa);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	
	/**
	 * Metodo que devuelve todas las regiones OLP.
	 *
	 * @return ResponseEntity
	 */
	@GetMapping(value = "/olp")
	@ApiOperation(value = "Devuelve todas las regiones de OLP")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> getRegionesOlp() {

		ResponseApi resultado = regionesService.consultaRegionesOlp();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	
	/**
	 * Metodo de registro de una region.
	 *
	 * @param region - Region
	 * @return ResponseEntity
	 */
	@PostMapping(value = "/registrar/olp")
	@ApiOperation(value = "Registrar una región")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> registraRegionOlp(@Valid @RequestBody Region region) {

		ResponseApi resultado = regionesService.creaRegionOlp(region.getDescripcion());

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de actualizacion de la descripcion de la region.
	 *
	 * @param region - Region
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar/olp")
	@ApiOperation(value = "Actualizar la descripción de la región")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaDescripcionRegionOlp(@Valid @RequestBody Region region) {

		ResponseApi resultado = regionesService.actualizaDescripcionRegionOlp(region.getId(), region.getDescripcion());

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de actualizacion de estatus de la region.
	 *
	 * @param id - Id de la region
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar-estatus/olp/{id}")
	@ApiOperation(value = "Actualizar el estatus de una región")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaEstatusRegionOlp(@PathVariable String id) {

		ResponseApi resultado = regionesService.actualizaEstatusRegionOlp(id);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	/**
	 * Metodo de eliminacion de una region.
	 *
	 * @param id - region
	 * @return ResponseEntity
	 */
	@DeleteMapping(value = "/eliminar/olp/{id}")
	@ApiOperation(value = "Eliminar una región")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> eliminaRegionOlp(@PathVariable String id) {

		ResponseApi resultado = regionesService.eliminaRegionOlp(id);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Metodo de actualizacion de estatus de la Divisa en la Region.
	 *
	 * @param idRegion - Id de la region
	 * @param idDivisa - Id de la divisa
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/actualizar-estatus/olp/{idDivisa}/{idRegion}")
	@ApiOperation(value = "Actualizar estatus de la divisa en la región")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaEstatusRegionDivisaOlp(@PathVariable String idRegion, 
																	@PathVariable String idDivisa) {

		ResponseApi resultado = regionesService.actualizaEstatusDivisaRegionOlp(idRegion, idDivisa);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
}
