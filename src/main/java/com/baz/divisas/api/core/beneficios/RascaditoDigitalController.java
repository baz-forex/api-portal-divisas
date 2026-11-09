package com.baz.divisas.api.core.beneficios;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baz.divisas.api.core.beneficios.dto.ResponseRascadito;
import com.baz.divisas.api.core.beneficios.service.RascaditoDigitalImpl;
import com.baz.divisas.general.dto.ResponseApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The Class RascaditoDigitalController.
 */
@RestController
@RequestMapping("/api/core/beneficios/rascadito")
@Api(tags = "rascadito digital")
@Validated
public class RascaditoDigitalController {
	
	@Autowired
	private RascaditoDigitalImpl rascadigoDigitalImpl;
	
	/**
	 * Actualiza estatus direct landing page.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@PutMapping(value = "actualiazarEstatus/{id}")
	@ApiOperation(value = "Actualiza Estatus Landing Page ")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> actualizaEstatusDirectLandingPage(@PathVariable String id) {

		ResponseApi resultado = rascadigoDigitalImpl.activaDesactivaBanner(id);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());

	}
	
	/**
	 * Guardarconfiguracion landing page.
	 *
	 * @param requestRascadito the request rascadito
	 * @return the response entity
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@PostMapping(value = "registrar")
	@ApiOperation(value = "Registra Configuración Landing Page")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	public ResponseEntity<ResponseApi> guardarconfiguracionLandingPage(
			@Valid @ModelAttribute ResponseRascadito requestRascadito)
			throws IOException {

		ResponseApi resultado = rascadigoDigitalImpl.guardarConfiguracionLandingPage(requestRascadito);

		return new ResponseEntity<>(resultado, resultado.getHttpStatus());
	}
	
	/**
	 * Consultar parametria.
	 *
	 * @return the response entity
	 */
	@GetMapping(value = "consulta-parametria")
	@ApiOperation(value = "Consulta Parametria Landing Page")
	@ApiResponses(value = {@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	
	public ResponseEntity<ResponseApi> consultarParametria (){
		
		ResponseApi resultado = rascadigoDigitalImpl.consultarParametria();
		
		return new ResponseEntity<>(resultado,resultado.getHttpStatus());
	}
	
	/**
	 * Actualizar parametria.
	 *
	 * @param requestRascadito the request rascadito
	 * @return the response entity
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@PutMapping(value = "actualizar-parametria")
	@ApiOperation(value = "Actualiza Parametria Landing Page")
	@ApiResponses(value = {@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	
	public ResponseEntity<ResponseApi> actualizarParametria(
			@Valid @ModelAttribute ResponseRascadito requestRascadito) throws IOException{
		
		ResponseApi resultado = rascadigoDigitalImpl.actualizarParametria(requestRascadito);
		
		return new ResponseEntity<>(resultado,  resultado.getHttpStatus());
		
	}

	/**
	 * Estatus parametria.
	 *
	 * @return the response entity
	 */
	@GetMapping(value = "estatus-parametria")
	@ApiOperation(value = "Estatus Parametria Landing Page")
	@ApiResponses(value = {@ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 404, message = "Not Found") })
	
	public ResponseEntity<ResponseApi> estatusParametria (){
		
		ResponseApi resultado = rascadigoDigitalImpl.estatusParametria();
		
		return new ResponseEntity<>(resultado,resultado.getHttpStatus());
	}

}
