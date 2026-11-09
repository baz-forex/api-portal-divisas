package com.baz.divisas.api.tef.catalogos;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baz.divisas.api.catalogos.model.Catalogo;
import com.baz.divisas.api.tef.catalogos.service.ICatalogos;
import com.baz.divisas.general.dto.ResponseApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controlador de Tipo Operaciones
 * 
 *
 */
@RestController
@RequestMapping("/api/tef/catOperaciones")
@Api(tags = "TEF/Catalogos/TiposOperacion")
@Validated
public class TiposOperacionController {

	@Autowired
	private ICatalogos tiposOperacionImpl;

	/**
	 * Metodo que devuelve catalogo de tipos de operacion
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping(value = "/operaciones")
	@ApiOperation(value = "Devuelve los tipos de operaciones.")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> get() {

		ResponseApi resultado = tiposOperacionImpl.getTiposOperacion();

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}

	
	/**
	 * Actualiza estatus del tipo operacion.
	 *
	 * @param tipoOperacion - Tipo de Operacion
	 * 
	 * @return the response entity
	 */
	@PutMapping(value = "/actualizar")
	@ApiOperation(value = "Actualizar estatus del Tipo de Operacion")
	@ApiResponses(value = { 
			@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found")})
	public ResponseEntity<ResponseApi> actualizaEstatus(@Valid @RequestBody Catalogo tipoOperacion){

		ResponseApi resultado = tiposOperacionImpl.actualizaEstatusTipoOperacion(tipoOperacion);

		return new ResponseEntity<>(resultado,resultado.getHttpStatus());
	}

}
